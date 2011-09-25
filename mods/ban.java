package mods;
import mods.*;
import config.*;

public class ban implements module
{
	public ban(){}
	
	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception
	{
		if(args.length >= 1)
		{
			if (isAdmin && (command.compareTo("ban")==0)) 
			
			{
					core.writeChannel("ChanServ","BAN "+channel+" "+args[0]);
				return true;
			}
		}
		return false;
	}

     
	public String GetCommands(boolean isAdmin)
	{
		return "ban";
	}
	
}