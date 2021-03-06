package edu.osu.guessthatimage;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ImageParser {

	Image image;
	JSONObject mData;

	public ImageParser(String data) throws JSONException {
		mData = new JSONObject(data);
		parseData();
	}

	private void parseData() throws JSONException {
		image = new Image();
		JSONObject responseObject = mData.getJSONObject("responseData");
  		JSONArray resultArray = responseObject.getJSONArray("results");
  		Log.d("Parse", "Array Length of Results " + resultArray.length());
  		for(int i = 0; i < 4; i ++)
  		{
  			JSONObject j = resultArray.getJSONObject(i);
  	  		String url = j.getString("unescapedUrl");
  	  		image.addLink(url);
  	  		Log.d("Parse", url);
  	  		image.setURL(url);
  		}
	}

	public Image getImage() {
		return image;
	}

}
