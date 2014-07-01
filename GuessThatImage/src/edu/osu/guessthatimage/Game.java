package edu.osu.guessthatimage;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements OnClickListener, AccelerometerListener{
	
	private static Button btnGuess;
	private EditText guessField;
	private static final int SETTINGS_NUM_IMAGES_DISPLAYED = 4;
	private static final int SETTINGS_TIMER = 4;
	private static int CURRENT_INDEX = 0;
	private static ArrayList<String> dictionary;
	private static Score playerScore;
	private static int DEFAULT_SCORE = 0;
	
	private static String GUESS_KEY = "GUESS";
	private static String SCORE_KEY = "SCORE";
	
	private static Button btnTime;
	private static TextView timeNum;
	private int time = 10;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		/*
		 * Initialize Variables.
		 */
		btnGuess = (Button) findViewById(R.id.button1); // guess button
		btnGuess.setOnClickListener(this);
		guessField = (EditText) findViewById(R.id.editText1); // guess text
		playerScore = new Score(DEFAULT_SCORE); // score for player
	    dictionary = new ArrayList<String>(); // dictionary of words
	    populateDictionary(); // populate the dictionary
	    /*
	     * Restore values if the device is rotated.
	     */
	    if (savedInstanceState != null) {
			String guess = savedInstanceState.getString(GUESS_KEY);
			guessField.setText(guess);
			playerScore.setScore(savedInstanceState.getInt(SCORE_KEY));
		}
	    
	    timeNum = (TextView)findViewById(R.id.time_remaining);
	    btnTime = (Button)findViewById(R.id.start_time);
	    btnTime.setOnClickListener(this);
	    
	    time = Integer.parseInt(Settings.getTime(getApplicationContext()));

	    
	}
	
	private Handler timeHandler = new Handler();
	
	class ClassCut implements Runnable{
        @Override
        public void run() {
            // TODO Auto-generated method stub
        	
            while(time > 0){
                time--;
                timeHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        timeNum.setText(time + "");
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            timeHandler.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                	int temp = Integer.parseInt(Settings.getTime(getApplicationContext()));
                    timeNum.setText(temp + "");
                    Toast.makeText(Game.this, "timeout!", Toast.LENGTH_LONG).show();
                }
            });
            time = Integer.parseInt(Settings.getTime(getApplicationContext()));   
            btnTime = (Button)findViewById(R.id.start_time);
            btnTime.setClickable(true);
        }
    }

	
	 private static void populateDictionary() {
	        dictionary.add("Banana");
	        dictionary.add("Dinosaur");
	        dictionary.add("Apple");
	        dictionary.add("Cat");
	        dictionary.add("Banana");
	        dictionary.add("Dinosaur");
	        dictionary.add("Apple");
	        dictionary.add("Cat");
	        dictionary.add("Banana");
	        dictionary.add("Dinosaur");
	        dictionary.add("Apple");
	        dictionary.add("Cat");
	        dictionary.add("Banana");
	        dictionary.add("Dinosaur");
	        dictionary.add("Apple");
	        dictionary.add("Cat");
	        dictionary.add("Banana");
	        dictionary.add("Dinosaur");
	        dictionary.add("Apple");
	        dictionary.add("Cat");
	    }
	 public void onResume() {
			super.onResume();
			//playNewGame();
			System.out.println("Resumed");
			 //Check device supported Accelerometer senssor or not
            if (AccelerometerManager.isSupported(this)) {
                 
                //Start Accelerometer Listening
                AccelerometerManager.startListening(this);
            }
		}
	 
	 private void checkGuess() {
			String temp = guessField.getText().toString().toLowerCase();
			System.out.println("Checkguess");
			if(temp.equals(dictionary.get(CURRENT_INDEX).toLowerCase()))
			{
				System.out.println("Correct guess");
				playerScore.correctAnswer();
				guessField.setText("CORRECT");
				CURRENT_INDEX ++;
			}
			guessField.setText("");
		}
	 
	 /**
		 * Stores the value of {@code result} on the {@code Bundle}.
		 * 
		 * @param savedInstanceState
		 *            The {@code Bundle} on which the value should be stored.
		 */
		@Override
		protected void onSaveInstanceState(Bundle savedInstanceState) {
			// Save the value in the field for when it rotates.
			String guess = guessField.getText().toString();
			savedInstanceState.putString(GUESS_KEY, guess);
			// Save player score
			int score = playerScore.getScore();
			savedInstanceState.putInt(SCORE_KEY, score);
		}
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				checkGuess();
				break;
			case R.id.start_time:
				btnTime.setClickable(false);
				new Thread(new ClassCut()).start();
				break;
			}
		}
		
		// SHAKING STUFF
		public void onAccelerationChanged(float x, float y, float z) {
	        // TODO Auto-generated method stub
	         
	    }
	 
	    public void onShake(float force) {
	    		// Do your stuff here
	    		skipped();
	        	// Called when Motion Detected
	    		Toast.makeText(getBaseContext(), "Motion detected",
	    				Toast.LENGTH_SHORT).show();
	    }
	    
	    private void skipped()
	    {
	    	guessField.setText("SKIPPED " + CURRENT_INDEX);
	    	CURRENT_INDEX ++;
	    	playerScore.skipped();
	    }
	    @Override
	    public void onStop() {
	            super.onStop();
	             
	            //Check device supported Accelerometer senssor or not
	            if (AccelerometerManager.isListening()) {
	                 
	                //Start Accelerometer Listening
	                AccelerometerManager.stopListening();
	                 
	                Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
	                         Toast.LENGTH_SHORT).show();
	            }
	            
	    }
	    @Override
	    public void onDestroy() {
	        super.onDestroy();
	        Log.i("Sensor", "Service  distroy");
	         
	        //Check device supported Accelerometer senssor or not
	        if (AccelerometerManager.isListening()) {
	             
	            //Start Accelerometer Listening
	            AccelerometerManager.stopListening();
	             
	            Toast.makeText(getBaseContext(), "onDestroy Accelerometer Stoped",
	                   Toast.LENGTH_SHORT).show();
	        }
	             
	    }
		
		
		
}
