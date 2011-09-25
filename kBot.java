import mods.*;
import config.*;
import org.ini4j.*;
import org.ini4j.spi.*;
import java.io.*;
import java.net.*;
import java.math.*;
import javax.net.ssl.*;
import javax.net.*;
import java.security.*;
import java.security.cert.*;
import java.util.*;
import java.util.regex.*;

public class kBot implements X509TrustManager
{
	public void checkClientTrusted(X509Certificate[] chain, String authType){}
	public void checkServerTrusted(X509Certificate[] chain, String authType){}
	public X509Certificate[] getAcceptedIssuers(){return null;}
	
	public static void main(String[] args){new kBot();}
	
	Ini ini_config=null;
	
	public kBot()
	{	
		
		try
		{
			ini_config=new Ini(new File("kBot.ini").toURL());
		}catch(Exception e)
		{
			System.out.println("ERROR: Couldn't load ini file."+e);
			System.exit(0);
		}
		
		config.name=ini_config.get("kBot").get("name");
		config.mode=ini_config.get("kBot").get("mode");
		config.version=ini_config.get("kBot").get("version");
		config.server=ini_config.get("kBot").get("server");
		config.port=Integer.parseInt(ini_config.get("kBot").get("port"));
		config.ssl=Boolean.parseBoolean(ini_config.get("kBot").get("ssl"));
		config.prefix=ini_config.get("kBot").get("prefix");
		config._1command=Boolean.parseBoolean(ini_config.get("kBot").get("1command"));
		config.master=ini_config.get("kBot").get("master");
		config.join=ini_config.get("kBot").get("join");
		config.load=ini_config.get("kBot").get("load");
		
		try
		{
			config.mod_names = config.load.split(",");
			config.mods=new module[config.mod_names.length];
			for(int i=0;i<config.mod_names.length;++i)
			{
				config.mods[i]=core.LoadModule(config.mod_names[i]);
			}
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Couldn't load modules. "+e);
			System.exit(0);
		}
		
		try
		{
			if(!config.ssl)
			{
				config.sock=new Socket(config.server,config.port);
			}
			else
			{
				TrustManager[] tc = new TrustManager[]{this};
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null,tc,new SecureRandom());
				config.sock=sc.getSocketFactory().createSocket(config.server,config.port);
				
			}
		}catch(Exception e)
		{
			System.out.println("ERROR: Couldn't create socket. "+e);
			System.exit(0);
		}
		
		try
		{
			config.read = new BufferedReader(new InputStreamReader(config.sock.getInputStream(),"UTF8"));
			config.write = new BufferedWriter(new OutputStreamWriter(config.sock.getOutputStream(),"UTF8"));
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Couldn't create input/output objects. "+e);
			System.exit(0);
		}
		
		try
		{
			core.StartUp();
			core.writeSecret("PRIVMSG NickServ :IDENTIFY "+ini_config.get("kBot").get("password")+"\n");
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Couldn't do startup. "+e);
			System.exit(0);
		}
		
		for(;;)
		{
			try
			{
				String rline = core.readLine();
				String[] sp = rline.split(" ");
				
				if(sp[0].compareTo("PING")==0)
				{
					core.Pong(sp[1]);
				}
				
				if(sp.length >= 4)
				{
					if(sp[1].compareTo("PRIVMSG")==0)
					{
						boolean doit=false;
						if(config._1command){if(sp[3].startsWith(":"+config.prefix)){doit=true;}}
						else{if(sp[3].compareTo(":"+config.prefix)==0 && sp.length >= 5){doit=true;}}
						if(doit)
						{
							String channel = sp[2];
							
							if(!sp[2].startsWith("#"))
							{
								channel=(sp[0].substring(1).split("!"))[0];
							}
							
							String command = "";
							int args_start=5;
							if(config._1command)
							{
								command=sp[3].substring(1+config.prefix.length());
								args_start=4;
							}
							else
							{
								try
								{
									command=sp[4];
								}catch(Exception e){command="";}
							}
							boolean isAdmin=false;
							String args[]=null;
							try
							{
								args=new String[sp.length-args_start];								
								for(int i=0;i<sp.length;++i)
								{
									args[i]=sp[i+args_start];
								}
							}catch(Exception e){}
							if(sp[0].compareTo(":"+config.master) == 0)
							{
								isAdmin=true;
							}
							
							System.out.print("Executing: "+command+"(");
							for(int i=0;i<args.length;++i)
							{
								System.out.print(args[i]);
								if(i+1 < args.length)
								{
									System.out.print(",");
								}
							}
							System.out.println(")");
							for(int i=0;i<config.mods.length;++i)
							{
								if(config.mods[i].ExecCommand(command,args,channel,isAdmin))
								{
									break;
								}
							}
						
						}
					}
				}
			}
			catch(Exception e)
			{
				System.out.println("ERROR: "+e);
				break;
			}
		
		}
	}

}
