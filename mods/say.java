package mods;
import mods.*;
import config.*;

public class say implements module
{
	public say(){}
	
	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception
	{
		if(args.length >= 1)
		{
			if (isAdmin && (command.compareTo("say")==0)) 
			
			{
				String[] argsx = new String[args.length-1];				for(int i=1;i<args.length;++i){argsx[i-1]=args[i];}
				core.writeChannel(args[0],core.implode(" ",argsx));
				return true;
			}
		}
		return false;
	}

     
	public String GetCommands(boolean isAdmin)
	{
		return "say";
	}
	
}