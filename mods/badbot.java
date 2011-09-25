package mods;
import mods.*;
import config.*;

public class badbot implements module
{
	public badbot(){}
	
	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception
	{
		if(args.length >= 1)
		{
			if (isAdmin && (command.compareTo("badbot")==0)) 
			
			{
				core.writeChannel(channel,"here comes badbot! "+args[0]);
				Runtime.getRuntime().exec("python badbot.py");
				return true;
			}
		}
		return false;
	}

     
	public String GetCommands(boolean isAdmin)
	{
		return "badbot";
	}
	
}