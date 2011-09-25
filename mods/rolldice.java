package mods;
import config.*;
import mods.*;
import java.math.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;
import javax.xml.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class rolldice implements module
{
	public rolldice(){}
	
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
		}
		return false;
	}
	
	public String GetCommands(boolean isAdmin)
	{
		return "roll(dice)";
	}
}