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
	    dictionary.add("Cat");
	    dictionary.add("Banana");
	    dictionary.add("Walrus");
	    dictionary.add("Chair");
	    dictionary.add("Light");
	    dictionary.add("Game");
	    dictionary.add("Radio");
	    dictionary.add("Truck");
	    dictionary.add("TV");
	    dictionary.add("Car");
	    dictionary.add("Dinosaur");
	    dictionary.add("School");
	    dictionary.add("OSU");
	    dictionary.add("Music");
	    dictionary.add("Floor");
	    dictionary.add("Door");
	    dictionary.add("Keyboard");
	    dictionary.add("Mouse");
	    dictionary.add("Speaker");
	}
	
	public String getCurrentWord()
	{
		return dictionary.get(index);
	}
	
	public int getIndex()
	{
		return index;
	}
	public void setIndex(int i)
	{
		index = i;
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

