package edu.osu.guessthatimage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class GoogleImageHTTPClient {

	private static final String TAG = "GoogleImageHTTPClient";
	private static String BASE_URL = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=";

	/**
	 * Fetches and returns the string representing image information for a
	 * given word.
	 * 
	 * @param word
	 *            Word from dictionary
	 * @return String representing the image information.
	 * @throws IOException
	 */
	public String getImageData(String word) throws IOException {
		// This gets rid of white space.
		word = word.replaceAll("\\s+","_");
		Log.d("EYO", word);
		URL url = new URL(BASE_URL + word); // can throw exception
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		String out;
		try {
			InputStream in = connection.getInputStream();
			out = readStream(in);
			Log.d(TAG, out);
		} finally {
			connection.disconnect();
		}
		return out;
	}

	/**
	 * Reads the stream by breaking it into lines appended to each other.
	 * 
	 * @param in
	 *            Stream to read
	 * @return Equivalent string
	 * @throws IOException
	 */
	private String readStream(InputStream in) throws IOException {
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = null;
		while ((line = br.readLine()) != null) {
			buffer.append(line + "\r\n");
		}
		in.close();
		return buffer.toString();
	}
}
