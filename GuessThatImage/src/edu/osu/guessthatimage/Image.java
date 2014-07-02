package edu.osu.guessthatimage;

import java.util.ArrayList;

/**
 * Put a short phrase describing the program here.
 * 
 * @author Put your name here
 * 
 */
public final class Image {

    private String URL;
    private ArrayList<String> links;

    public Image(String url) {
    	this.URL = url;
    	links = new ArrayList<String>();
    }
    public Image() {
    	this.URL = "";
    	links = new ArrayList<String>();
    }
    public String getURL() {
        return this.URL;
    }
    
    public void addLink(String url)
    {
    	links.add(url);
    }
    
    public ArrayList<String> getLinks(){
return links;
}
    public void setURL(String url)
    {
    	this.URL = url;
    }

    public String toString()
    {
    	return this.URL;
    }

}
