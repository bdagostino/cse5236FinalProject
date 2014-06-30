package edu.osu.guessthatimage;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class Settings extends PreferenceActivity{
	
	private final static String GAME_TIME = "time_setting";
	private final static String NUMBER_OF_PIC = "number_setting";
	private final static String GAME_TIME_DEF = "10";
	private final static String NUMBER_OF_PIC_DEF = "4";

	@Override
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
		.replace(android.R.id.content, new SettingsFragment()).commit();
	}
	
	public static String getTime(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(GAME_TIME, GAME_TIME_DEF);
	}
	
	public static String getNumber(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context)
				.getString(NUMBER_OF_PIC, NUMBER_OF_PIC_DEF);
	}
	
	

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static class SettingsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.settings);
		}
	}
	
}
