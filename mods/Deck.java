package mods;
import mods.*;
import config.*;
import java.util.*;


public class Deck
{
	public static final String[] suits = {"\u2665","\u2666","\u2663","\u2660"};
	public static final String[] types = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};
	
	public int counter = 0;
	public String[] cards=null;
	
	public Deck(boolean include_joker)
	{
		int addon=0;
		if(include_joker)
		{
			addon=2;
		}
		int amt = suits.length*types.length+2;
		cards = new String[amt];
		if(include_joker)
		{
			cards[0]="BJoker";
			cards[1]="RJoker";
		}
		
		for(int i=0;i<suits.length;++i)
		{
			for(int j=0;j<types.length;++j)
			{
				cards[addon+(i*types.length)+j]=types[j]+suits[i];
			}
		}
		shuffle();
	}
	
	public void shuffle()
	{
		for(int i=0;i<cards.length;++i)
		{
			int j = config.rand.nextInt(cards.length);
			String b = cards[j];
			cards[j]=cards[i];
			cards[i]=b;
		}
		counter=0;
	}
	
	public String getNextCard()
	{
		if(counter>=cards.length)
		{
			shuffle();
		}
		
		return cards[counter++];
	}

}
