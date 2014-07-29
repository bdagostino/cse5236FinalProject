package edu.osu.guessthatimage;

public class TimeUtilities {
	public static String prettyTime(int time)
	{
		String minutes = (time / 60) + "";
    	int s = (time - (Integer.parseInt(minutes) * 60));
    	String seconds = "";
    	if(s < 10){
    		seconds = "0"+s;
    	}
    	else{
    		seconds = "" +s;
    	}
    	return minutes + ":" + seconds;
	}
}
