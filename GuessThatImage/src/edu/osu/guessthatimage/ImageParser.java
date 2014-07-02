package edu.osu.guessthatimage;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class ImageParser {

	private static final int ERROR_CODE = 404;
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
