package mods;

public interface module
{
	public boolean ExecCommand(String command, String[] args, String channel, boolean isAdmin) throws Exception;
	public String GetCommands(boolean isAdmin);
}
