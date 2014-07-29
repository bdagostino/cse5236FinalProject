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
	    dictionary.add("Gamepad");
	    dictionary.add("Milk");
	    dictionary.add("Dog");
	    dictionary.add("Arrow");
	    dictionary.add("Pen");
	    dictionary.add("Towel");
	    dictionary.add("Sword");
	    dictionary.add("Egg");
	    dictionary.add("Banana");
	    dictionary.add("Walrus");
	    dictionary.add("Horse");
	    dictionary.add("Chair");
	    dictionary.add("Grenade");
	    dictionary.add("Toothbrush");
	    dictionary.add("Train");
	    dictionary.add("Machinegun");
	    dictionary.add("Phone");
	    dictionary.add("Bomber");
	    dictionary.add("Mango");
	    dictionary.add("Bus");
	    dictionary.add("Shield");
	    dictionary.add("Salad");
	    dictionary.add("Oven");
	    dictionary.add("Shark");
	    dictionary.add("Light");
	    dictionary.add("Glass");
	    dictionary.add("Nuclearbomb");
	    dictionary.add("Radio");
	    dictionary.add("Table");
	    dictionary.add("Wine");
	    dictionary.add("Coke");
	    dictionary.add("Truck");
	    dictionary.add("Dice");
	    dictionary.add("Shoes");
	    dictionary.add("Apple");
	    dictionary.add("TV");
	    dictionary.add("Car");
	    dictionary.add("Burger");
	    dictionary.add("Dinosaur");
	    dictionary.add("Orange");
	    dictionary.add("Steak");
	    dictionary.add("School");
	    dictionary.add("Tank");
	    dictionary.add("OSU");
	    dictionary.add("Apache");
	    dictionary.add("Music");
	    dictionary.add("Floor");
	    dictionary.add("Bed");
	    dictionary.add("Watermelon");
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
//	public void nextWord()
//	{
//		//if this isn't the first time we are picking a word remove the previous word from the dictionary
//		if(index != -1)
//		{
//			usedwords.add(dictionary.remove(index));
//		}
//		
//		//if we've used all the words, recycle used words
//		if(dictionary.size() == 0)
//		{
//			//swap the empty dictionary with the words used
//			dictionary = usedwords;
//			usedwords = new ArrayList<String>(); //empty used words
//		}
//		
//		if(dictionary.size()-1 != 0)
//		{
//			index = rand.nextInt(dictionary.size()-1);
//		}
//		else
//		{
//			index = 0;
//		}
//	}
	
	public void nextWord()
	{
		if(index < dictionary.size())
		{
			index ++;
		}
	}
}

