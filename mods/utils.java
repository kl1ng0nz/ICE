package mods;
import mods.*;
import config.*;
import java.math.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import javax.xml.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class utils implements module
{
	public utils()
	{
		System.setProperty("http.agent","Mozilla/5.0 (X11; Linux i686) AppleWebKit/535.1 (KHTML, like Gecko) Ubuntu/11.04 Chromium/14.0.825.0 Chrome/14.0.825.0 Safari/535.1");
	}
	
	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception
	{
		if(args.length >= 1)
		{
			// ROLL DICE
			if(command.compareTo("roll")==0)
			{
				String die = core.implode("",args).trim();
				int addon=0;
				String s_addon="";
				try
				{
					Pattern p = Pattern.compile("^(\\d+)d(\\d+)([\\+|-]\\d+)?$");
					Matcher m = p.matcher(die);
					m.find();
					int low=Integer.parseInt(m.group(1));
					int high=Integer.parseInt(m.group(2))*low;
					try
					{
						String adx=m.group(3);
						if(adx.startsWith("+"))
						{
							adx=adx.substring(1);
						}
						addon=Integer.parseInt(adx);
						if(addon!=0)
						{
							s_addon=m.group(3);
						}
					}catch(Exception h){}
					int roll =(low+config.rand.nextInt(1+high-low)+addon);
					core.writeChannel(channel,"Rolled: "+m.group(1)+"d"+m.group(2)+s_addon+" = "+roll);
					
				}
				catch(Exception e)
				{
					core.writeChannel(channel,"Couldn't roll dice.");
				}
				
				
				return true;
			}
			
			// CALCULATE
			if(command.compareTo("calc")==0)
			{
				String eval = core.implode("",args).trim();
				try
				{
					MathEvaluator me = new MathEvaluator(eval);
					core.writeChannel(channel,"Calc: "+eval+" = "+me.getValue());
				}
				catch(Exception e)
				{
					core.writeChannel(channel,"Couldn't calculate.");
				}
				return true;
			}
			
			// CONVERT
			if(command.compareTo("convert")==0)
			{
				String conv = core.implode("%20",args);
				try
				{
					URL url = new URL("http://www.google.com/ig/calculator?q="+conv);
					InputStream ins = url.openStream();
					BufferedReader rd = new BufferedReader(new InputStreamReader(ins));
					String[] lns = rd.readLine().split("\"");
					ins.close();
					core.writeChannel(channel,lns[1]+" = "+lns[3]);
				}
				catch(Exception e)
				{
					core.writeChannel(channel,"Couldn't convert.");
				}
				return true;
			}
			
			
			// WEATHER
			if(command.compareTo("weather")==0)
			{
				try
				{
					String loc = URLEncoder.encode(core.implode(" ",args),"UTF8");
					URL url = new URL("http://www.google.com/ig/api?weather="+loc);
					InputStream ins = url.openStream();
					DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
					DocumentBuilder db = dbf.newDocumentBuilder();
					Document dom = db.parse(ins);
					ins.close();
					String location = ((Element)((Element)dom.getElementsByTagName("forecast_information").item(0)).getElementsByTagName("city").item(0)).getAttribute("data");
					Element el = (Element)dom.getElementsByTagName("current_conditions").item(0);
					String cond = ((Element)el.getElementsByTagName("condition").item(0)).getAttribute("data");
					String temp_f = ((Element)el.getElementsByTagName("temp_f").item(0)).getAttribute("data");
					String temp_c = ((Element)el.getElementsByTagName("temp_c").item(0)).getAttribute("data");
					String humidity = ((Element)el.getElementsByTagName("humidity").item(0)).getAttribute("data");
					
					core.writeChannel(channel,"Weather for "+location+": "+cond+", "+humidity+", "+temp_c+"\u00B0C("+temp_f+"\u00B0F"+")");
				}
				catch(Exception e)
				{
					core.writeChannel(channel,"Couldn't get weather.");
					
				}
				return true;
			}
		}
		
		return false;
	}
	
	public String GetCommands(boolean isAdmin)
	{
		return "roll(dice), calc(stuff), convert(x to y), weather(location)";
	}

}
