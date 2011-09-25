package mods;
import mods.*;
import config.*;

public class example implements module
{
	public example(){}
	
	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception
	{
		if(command.compareTo("example_command")==0)
		{
			core.writeChannel(channel,"This is an example command!");
			return true;
		}
		return false;
	}

	public String GetCommands(boolean isAdmin)
	{
		return "example_command";
	}

}

