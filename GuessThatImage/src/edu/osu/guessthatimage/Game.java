package edu.osu.guessthatimage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
	private static TextView scoreNum;
	private int currentScore = 0;
	//private int tempScore = 0;
	
	private Thread timeThread = new Thread(new timeCount());
	private Thread scoreThread = new Thread(new scoreCount());
	
	private databaseHelperEasyTwo dh1;
	private databaseHelperEasyFive dh2;
	private databaseHelperEasyTen dh3;
	private databaseHelperMediumTwo dh4;
	private databaseHelperMediumFive dh5;
	private databaseHelperMediumTen dh6;
	private databaseHelperHardTwo dh7;
	private databaseHelperHardFive dh8;
	private databaseHelperHardTen dh9;
	
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
		guessField.setEnabled(false);
		playerScore = new Score(DEFAULT_SCORE); // score for player
	    dictionary = new ArrayList<String>(); // dictionary of words
	    Dictionary.populateDictionary(dictionary); // populate the dictionary
	    /*
	     * Restore values if the device is rotated.
	     */
	    if (savedInstanceState != null) {
			String guess = savedInstanceState.getString(GUESS_KEY);
			guessField.setText(guess);
			playerScore.setScore(savedInstanceState.getInt(SCORE_KEY));
			guessField.setEnabled(true);
		}
	    
	    timeNum = (TextView)findViewById(R.id.time_remaining);
	    btnTime = (Button)findViewById(R.id.start_time);
	    btnTime.setOnClickListener(this);
	    scoreNum = (TextView)findViewById(R.id.current_score);
	    
	    time = Integer.parseInt(Settings.getTime(getApplicationContext()));
	    Log.d("EYO", "" + Settings.getNumber(getApplicationContext()));
	    time *= 1;
	    currentScore = 0;

	    scoreThread.start();
	    
	    savePlayerScore(10);
	}
	
	private Handler timeHandler = new Handler();
	private Handler scoreHandler = new Handler();
	
	class timeCount implements Runnable{
        @Override
        public void run() {
            // TODO Auto-generated method stub
        	currentScore = 0;
        	//tempScore = 0;
        	
            while(time > 0){
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
                time--;
            }
            
            timeHandler.post(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                	int temp = Integer.parseInt(Settings.getTime(getApplicationContext()));
                    timeNum.setText(temp + "");
                    Toast.makeText(Game.this, "Out of time!", Toast.LENGTH_LONG).show();
                    endGame();
                }
            });
            time = Integer.parseInt(Settings.getTime(getApplicationContext()));   
            btnTime = (Button)findViewById(R.id.start_time);
            btnTime.setClickable(true);
        }
    }
	
	private void endGame()
	{
		startActivity(new Intent(this, LeaderBoard.class));
		finish();
	}
	
	class scoreCount implements Runnable{
		@Override
		public void run(){
			//currentScore = playerScore.getScore();
			//tempScore = playerScore.getScore();
			
			while(true){
				scoreHandler.post(new Runnable(){
					public void run(){
						currentScore = playerScore.getScore();
						scoreNum.setText(currentScore + "");
					}
				});
				try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		}
	}
	
	public void savePlayerScore(int score){
        String easy = "4";
        String medium = "2";
        String hard = "1";
        String two = "2";
        String five = "5";
        String ten = "10";
        this.dh1 = new databaseHelperEasyTwo(this);
        this.dh2 = new databaseHelperEasyFive(this);
        this.dh3 = new databaseHelperEasyTen(this);
        this.dh4 = new databaseHelperMediumTwo(this);
        this.dh5 = new databaseHelperMediumFive(this);
        this.dh6 = new databaseHelperMediumTen(this);
        this.dh7 = new databaseHelperHardTwo(this);
        this.dh8 = new databaseHelperHardFive(this);
        this.dh9 = new databaseHelperHardTen(this);
        String time = Settings.getTime(getApplicationContext());
        String difficulty = Settings.getNumber(getApplicationContext());
       
        if(time.equals(two) && difficulty.equals(easy)){
                        this.dh1.insert("Hello", "25");
                }else if(time.equals(five) && difficulty.equals(easy)){
                		this.dh2.insert("hello", "15");
                }else if(time.equals(ten) && difficulty.equals(easy)){
                		this.dh3.insert("hello", "15");
                }else if(time.equals(two) && difficulty.equals(medium)){
                		this.dh4.insert("hello", "15");
                }else if(time.equals(five) && difficulty.equals(medium)){
                		this.dh5.insert("hello", "15");
                }else if(time.equals(ten) && difficulty.equals(medium)){
                		this.dh6.insert("hello", "15");
                }else if(time.equals(two) && difficulty.equals(hard)){
                		this.dh7.insert("hello", "15");
                }else if(time.equals(five) && difficulty.equals(hard)){
                		this.dh8.insert("hello", "15");
                }else if(time.equals(ten) && difficulty.equals(hard)){
                		this.dh9.insert("hello", "15");
                }else{
                        Toast.makeText(getBaseContext(), "Fucking Error",
                Toast.LENGTH_SHORT).show();
                }
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
            new JSONWeatherTask().execute(dictionary.get(CURRENT_INDEX));
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
				Toast.makeText(getBaseContext(), "Correct (+2)",
	    				Toast.LENGTH_SHORT).show();
				new JSONWeatherTask().execute(dictionary.get(CURRENT_INDEX));
			}
			else
			{
				Toast.makeText(getBaseContext(), "Incorrect",
	    				Toast.LENGTH_SHORT).show();
				//btnGuess.setClickable(true);
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
				//btnGuess.setClickable(false);
				break;
			case R.id.start_time:
				btnTime.setClickable(false);
				guessField.setEnabled(true);
				timeThread.start();
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
//	    		Toast.makeText(getBaseContext(), "Motion detected",
//	    				Toast.LENGTH_SHORT).show();
	    }
	    
	    private void skipped()
	    {
	    	guessField.setText("SKIPPED " + CURRENT_INDEX);
	    	CURRENT_INDEX ++;
	    	playerScore.skipped();
	    	Toast.makeText(getBaseContext(), "Skipped (-1)",
    				Toast.LENGTH_SHORT).show();
	    	new JSONWeatherTask().execute(dictionary.get(CURRENT_INDEX));
	    }
	    @Override
	    public void onStop() {
	            super.onStop(); 
	            //Check device supported Accelerometer senssor or not
	            if (AccelerometerManager.isListening()) {
	                 
	                //Start Accelerometer Listening
	                AccelerometerManager.stopListening();
	                 
//	                Toast.makeText(getBaseContext(), "onStop Accelerometer Stoped",
//	                         Toast.LENGTH_SHORT).show();
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
	    /**
		 * An asynchronous task to fetch weather data from the server.
		 * 
		 * @author swaroop
		 * 
		 */
		class JSONWeatherTask extends AsyncTask<String, Integer, ArrayList<Bitmap>> {

			private static final String TAG = "JSONGoogleTask";

			@Override
			protected ArrayList<Bitmap> doInBackground(String... params) {
				String data = null;
				try {
					data = ((new GoogleImageHTTPClient()).getImageData(params[0]));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				Log.d(TAG, data);
				Image image = null;
				try {
					image = (new ImageParser(data)).getImage();
				} catch (JSONException e) {
					e.printStackTrace();
					Log.d(TAG, "Image parse error");
				}
				ArrayList<Bitmap> pictures = new ArrayList<Bitmap>();	
				int imageSetting = Integer.parseInt(Settings.getNumber(getApplicationContext()));
				
						
				for(int i = 0; i < imageSetting; i ++)
				{
					Log.d("BG", "View received");
					URL URL = null;
					try {
						URL = new URL(image.getLinks().get(i));
						Log.d("BG", "URL Created" + i);
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d("BG", "URL Failed");
					}
					Log.d("BG", (i+1) + "th iteration");
					try {
						Log.d("BG", "Inside tryblock"+i);
						Bitmap bitmap = BitmapFactory.decodeStream(URL.openConnection().getInputStream());
						Log.d("BG", "Post bitmap" + i);
						if(bitmap != null)
						{
							Bitmap resizedbitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
							pictures.add(resizedbitmap);
							//pictures.add(bitmap);
							Log.d("BG", "Bitmap Added");
						}
						else
						{
						pictures.add(bitmap);	
						Log.d("BG", "Bitmap Added");
						}	
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.d("BG", "Bitmap Failed");
					}
				}			
				Log.d("BG", "Returning");
				return pictures;
			}

			@Override
			protected void onPostExecute(ArrayList<Bitmap> bmp) {
				if (bmp == null) {
					Log.d("Post", "BMP null");
					return;
				}
				ImageView[] views = new ImageView[4];
				views[0] = (ImageView)findViewById(R.id.imageView1);
				views[1] = (ImageView)findViewById(R.id.imageView2);
				views[2] = (ImageView)findViewById(R.id.imageView3);
				views[3] = (ImageView)findViewById(R.id.imageView4);
				int imageSetting = Integer.parseInt(Settings.getNumber(getApplicationContext()));	
				for(int i = 0; i < imageSetting && i < bmp.size(); i ++)
				{
					views[i].setImageBitmap(bmp.get(i));
				}
//				view = (ImageView)findViewById(R.id.imageView3);
//				view.setImageBitmap(bmp.get(2));
//				view = (ImageView)findViewById(R.id.imageView4);
//				view.setImageBitmap(bmp.get(3));
				Log.d("Post", "Images created");
			}

		}


public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
    int width = bm.getWidth();
    int height = bm.getHeight();
    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;
    // CREATE A MATRIX FOR THE MANIPULATION
    Matrix matrix = new Matrix();
    // RESIZE THE BIT MAP
    matrix.postScale(scaleWidth, scaleHeight);

    // "RECREATE" THE NEW BITMAP
    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
    return resizedBitmap;
}


		
}
