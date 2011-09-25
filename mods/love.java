package mods;
import mods.*;
import config.*;

public class love implements module
{
	public love(){}
	
	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception
	{
		if(args.length >= 1)
		{
			if (isAdmin && (command.compareTo("love")==0)) 
			
			{
				core.writeChannel(channel,"here comes fagbot, "+args[0]);
				Runtime.getRuntime().exec("python fagbot.py");
				return true;
			}
		}
		return false;
	}

     
	public String GetCommands(boolean isAdmin)
	{
		return "love";
	}
	
}