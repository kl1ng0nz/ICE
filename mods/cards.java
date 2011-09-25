package mods;
import mods.*;


public class cards implements module
{
	Deck deck = new Deck(true);
	public cards(){}

	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception
	{
		if(command.compareTo("getCard")==0)
		{
			core.writeChannel(channel,"You drew: "+deck.getNextCard());
			return true;
		}
		
		if(command.compareTo("shuffle")==0)
		{
			deck.shuffle();
			core.writeChannel(channel,"Shuffled");
			return true;
		}
		return false;
	}

	public String GetCommands(boolean isAdmin)
	{
		return "getCard, shuffle";
	}

}

