package edu.osu.guessthatimage;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.os.Build;
import android.preference.PreferenceManager;

public class GameSession extends ActionBarActivity {
	private static EditText guessField;
	private static String currentWord;
	private static final int SETTINGS_NUM_IMAGES_DISPLAYED = 4;
	private static final int SETTINGS_TIMER = 4;
	private static int CURRENT_INDEX = 0;
	private static ArrayList<String> dictionary;
	private static Score playerScore;
	private static boolean guessMade;
	private static String guess;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_game_session);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		playerScore = new Score(0);
	    dictionary = new ArrayList<String>();
	    guessMade = false;
	    populateDictionary();
		guessField = (EditText) findViewById(R.id.guess_text);
	}
	 private static void populateDictionary() {
	        dictionary.add("Banana");
	        dictionary.add("Dinosaur");
	        dictionary.add("Apple");
	        dictionary.add("Cat");
	        dictionary.add("Hockey");
	        dictionary.add("Soccer");
	        dictionary.add("Football");
	        dictionary.add("Bird");
	        dictionary.add("Meme");
	        dictionary.add("Rocket");
	        dictionary.add("Pot");
	        dictionary.add("Heal");
	        dictionary.add("Flash");
	        dictionary.add("Force");
	        dictionary.add("Hyper");
	    }
	 
	 public void onResume() {
			super.onResume();
			playNewGame();
		}
	 
	private void playNewGame()
	{
		boolean skipped = false;
		int timer = SETTINGS_TIMER;
        while (timer > 0) {
            displayImages(dictionary, SETTINGS_NUM_IMAGES_DISPLAYED);
            System.out.println("Guess the word: ");
            while(!guessMade)
            { }
            String guess = getGuess();
            skipped = skip(guess);
            guessMade = false;
            while (!guess.equals(dictionary.get(CURRENT_INDEX)) && !skipped) {
                incorrectAnswer();
                while(!guessMade)
                { }
                guess = getGuess();
                skipped = skip(guess);
            }
            if (skipped) {
                playerScore.skipped();
                skipped = false;
                skipResponse();
            } else {
                playerScore.correctAnswer();
                correctAnswer();
            }
            CURRENT_INDEX++;
            timer--;
        }
	}
	private static String getGuess() {
		String temp = guessField.getText().toString().toLowerCase();
        return temp;
    }

    private static void incorrectAnswer() {
    }

    private static void skipResponse() {
    }

    private static void correctAnswer() {
    }

    private static boolean skip(String text) {
        if (text.equals("skip")) {
            return true;
        }
        return false;
    }
    private static void displayImages(ArrayList<String> dictionary, int num) {
        String currentWord = dictionary.get(CURRENT_INDEX);
//        Image image = new Image(currentWord, num);
//        image.fetchImages();
//        image.displayImages();
    }
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_session, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_game_session,
					container, false);
			return rootView;
		}
	}
	private void checkGuess() {
		this.guessMade = true;
	}
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.guess_button:
			checkGuess();
			break;
		}
	}

}
