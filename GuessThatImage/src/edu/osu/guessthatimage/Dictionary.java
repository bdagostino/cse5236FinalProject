package edu.osu.guessthatimage;

import java.util.ArrayList;
import java.util.Random;

public class Dictionary
{
	private ArrayList<String> dictionary;
	private ArrayList<String> usedwords;
	private int index;
	private Random rand;
	
	public Dictionary()
	{
		dictionary = new ArrayList<String>(); // dictionary of words
		usedwords = new ArrayList<String>();
		rand = new Random();
		index = -1;
	}
	
	public void populateDictionary() {
	    dictionary = new ArrayList<String>(); // dictionary of words
	    dictionary.add("Armored Core");
	    dictionary.add("Planetside");
	    dictionary.add("Team Fortress 2");
	    dictionary.add("Katia Managan");
	    dictionary.add("Left 4 Dead");
	    dictionary.add("League of Legends");
	    dictionary.add("Yasuo");
	    dictionary.add("Phone");
	    dictionary.add("TV");
	    dictionary.add("Car");
	    dictionary.add("Cat");
	    dictionary.add("VALVe");
	    dictionary.add("Gabe Newell");
	    dictionary.add("Phreak");
	    dictionary.add("In Flames");
	    dictionary.add("Skyrim");
	    dictionary.add("Oblivion");
	    dictionary.add("Motorstorm");
	    dictionary.add("Metal");
	}
	
	public String getCurrentWord()
	{
		return dictionary.get(index);
	}
	
	public void nextWord()
	{
		//if this isn't the first time we are picking a word remove the previous word from the dictionary
		if(index != -1)
		{
			usedwords.add(dictionary.remove(index));
		}
		
		//if we've used all the words, recycle used words
		if(dictionary.size() == 0)
		{
			//swap the empty dictionary with the words used
			dictionary = usedwords;
			usedwords = new ArrayList<String>(); //empty used words
		}
		
		if(dictionary.size()-1 != 0)
		{
			index = rand.nextInt(dictionary.size()-1);
		}
		else
		{
			index = 0;
		}
	}
}

