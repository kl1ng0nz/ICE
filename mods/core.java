package mods;
import mods.*;
import config.*;

public class core implements module
{
	
	public core(){}
	
	public static String readLine() throws Exception
	{
		String s=config.read.readLine();
		System.out.println("->"+s);
		return s;
	}
	
	public static void writeRaw(String s) throws Exception
	{
		config.write.write(s);
		config.write.flush();
		System.out.print("<-"+s);
	}
	
	public static void writeLine(String s) throws Exception
	{
		writeRaw(s+"\n");
	}
	
	public static void writeChannel(String channel, String s) throws Exception
	{
		writeLine("PRIVMSG "+channel+" :"+s);
	}
	
	public static void writeSecret(String s) throws Exception
	{
		config.write.write(s);
		config.write.flush();
	}
	
	
	public static void StartUp() throws Exception
	{
		writeLine("USER "+config.name+" 0 "+config.name+" :"+config.name+" v"+config.version);
		SetNick(config.name);
		SetMode(config.mode,config.name);
		
		for(;;)
		{
			String rline = core.readLine();
			String[] sp = rline.split(" ");
			
			if(sp.length >= 2)
			{
				
				if(sp[0].compareTo("PING")==0)
				{
					Pong(sp[1]);
				}
				
				try
				{
					int motdid = Integer.parseInt(sp[1]);
					if(motdid == 376 || motdid == 422)
					{
						break;
					}
				}catch(Exception e){}
			}
		}
		
		Join(config.join);	
	}
	
	public static void SetNick(String nick) throws Exception
	{
		writeLine("NICK "+nick);
	}
	
	public static void SetMode(String mode, String target) throws Exception
	{
		writeLine("MODE "+target+" "+mode);
	}
	
	public static void Join(String channel) throws Exception
	{
		writeLine("JOIN "+channel);
	}
	
	public static void Part(String channel) throws Exception
	{
		writeLine("PART "+channel);
	}
	
	public static void Cycle(String channel) throws Exception
	{
		writeLine("CYCLE "+channel);
	}
	
	public static void Pong(String res) throws Exception
	{
		writeLine("PONG "+res);
	}
	
	public static void Quit(String msg) throws Exception
	{
		writeLine("QUIT "+msg);
	}
	
	public static void Quit() throws Exception
	{
		writeLine("QUIT");
	}
	
	public static String implode(String p, String[] str)
	{
		String msg="";
		for(int i=0;i<str.length;++i)
		{
			msg+=str[i];
			if(i+1 < str.length)
			{
				msg+=p;
			}
		}
		return msg;
	}
	
	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception
	{
		// ADMIN FUNCS
		if(isAdmin)
		{
			// QUIT
			if(command.compareTo("quit")==0)
			{
				if(args.length==0)
				{
					Quit();
					System.exit(0);
				}
				else
				{
					Quit(implode(" ",args));
					System.exit(0);
				}
			}
			
			// PART
			if(command.compareTo("part")==0)
			{
				if(args.length==0)
				{
					Part(channel);
					return true;
				}
				else
				{
					Part(args[0]);
					return true;
				}
			}
			
			// CYCLE
			if(command.compareTo("cycle")==0)
			{
				if(args.length==0)
				{
					Cycle(channel);
					return true;
				}
				else
				{
					Cycle(args[0]);
					return true;
				}
			}
			
			if(args.length >= 1)
			{
				// JOIN
				if(command.compareTo("join")==0)
				{
					Join(args[0]);
					return true;
				}
				
				// LOAD MODULE
				if(command.compareTo("loadModule")==0)
				{
					try
					{
						for(int i=0;i<config.mod_names.length;++i)
						{
							if(config.mod_names[i].compareTo(args[0]) == 0)
							{
								writeChannel(channel,"Module already loaded");
								return true;
							}
						}
						module md = LoadModule(args[0]);
						String[] mds = new String[config.mod_names.length+1];
						module[] mdx = new module[config.mods.length+1];
						
						for(int i=0;i<config.mod_names.length;++i)
						{
							mds[i]=config.mod_names[i];
							mdx[i]=config.mods[i];
						}
						
						mds[config.mods.length]=args[0];
						mdx[config.mods.length]=md;
						
						config.mod_names=mds;
						config.mods=mdx;
						
						writeChannel(channel,"Loaded module: "+args[0]);
					}
					catch(Exception e)
					{
						writeChannel(channel,"Couldn't load module");
					}
					return true;
				}
			}
		}
		
		// NORMAL FUNCS
		
		if(command.compareTo("help")==0)
		{
			for(int i=0;i<config.mod_names.length;++i)
			{
				writeChannel(channel,config.mod_names[i]+": "+config.mods[i].GetCommands(isAdmin));
			}
			return true;
		}
		
		return false;
	}
	
	public static module LoadModule(String name) throws Exception
	{
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		Class cls = cl.loadClass("mods."+name);
		return (module)cls.newInstance();
	}
	
	public String GetCommands(boolean isAdmin)
	{
		String admin_funcs = "quit, quit(msg), join(chan), part, part(chan), cycle, cycle(chan), loadModule(module)";
		String normal_funcs = "help";
		if(isAdmin){return admin_funcs+" "+normal_funcs;}
		return normal_funcs;
	}
}
