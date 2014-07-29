package edu.osu.guessthatimage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONException;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar.LayoutParams;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements OnClickListener, AccelerometerListener{
	
	private static Button btnGuess;
	private EditText guessField;
	private static Score playerScore;
	private static int DEFAULT_SCORE = 0;
	
	private static String GUESS_KEY = "GUESS";
	private static String SCORE_KEY = "SCORE";
	private static String TIME_KEY = "TIME";
	private static String NUMBM_KEY = "BITMAPS";
	private static String ORIGINAL_KEY = "ORIGINAL";
	private static String TIME_COUNTER = "TIMECOUNTER";
	private static String TIME_FLAG = "TIMEFLAG";
	private static String RELOAD_FLAG = "RELOADFLAG";
	
	private static TextView timeNum;
	private int currentTime = 10;
	private static TextView scoreNum;
	private int currentScore = 0;
	private static TextView loadingTip;
	
	private Thread timeThread = new Thread(new timeCount());
	private Thread scoreThread = new Thread(new scoreCount());
	
	private databaseHelperHighScores dhScore;

	private Dictionary dictionary;
	
	private Bitmap[] bitMapArray;
	private Bitmap[] originalImages;
	
	private boolean runFlag = true;
	private boolean pauseFlag = true;
	private int timeCounter = 10;
	
	private boolean reloadFlag = true;
	
	private ImageView[] views = new ImageView[4];
	private LayoutInflater layoutInflater;
	//private final PopupWindow mPopupWindow;
	private View popupView;
	
	private Display display;
	
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
		dictionary = new Dictionary(); //create a dictionary
	    dictionary.populateDictionary(); // populate the dictionary
	    
	    loadingTip = (TextView)findViewById(R.id.loading_tip);
	    
	    views[0] = (ImageView)findViewById(R.id.imageView1);
		views[1] = (ImageView)findViewById(R.id.imageView2);
		views[2] = (ImageView)findViewById(R.id.imageView3);
		views[3] = (ImageView)findViewById(R.id.imageView4);
		for(int i = 0;i < 4;i ++){
			views[i].setOnClickListener(this);
		}
	    
		
		
	    /*
	     * Restore values if the device is rotated.
	     */
	    if (savedInstanceState != null) {
			String guess = savedInstanceState.getString(GUESS_KEY);
			guessField.setText(guess);
			playerScore.setScore(savedInstanceState.getInt(SCORE_KEY));
			guessField.setEnabled(true);
			currentTime = savedInstanceState.getInt(TIME_KEY);
			timeCounter = savedInstanceState.getInt(TIME_COUNTER);
			pauseFlag = savedInstanceState.getBoolean(TIME_FLAG);
			dictionary.setIndex(savedInstanceState.getInt("INDEX"));
			
			reloadFlag = savedInstanceState.getBoolean(RELOAD_FLAG);
			if(reloadFlag == false){
				int numBitMapsLoaded = savedInstanceState.getInt(NUMBM_KEY);
				bitMapArray = new Bitmap[numBitMapsLoaded];
				originalImages  = new Bitmap[numBitMapsLoaded];
				/*
				ImageView[] views = new ImageView[4];
				views[0] = (ImageView)findViewById(R.id.imageView1);
				views[1] = (ImageView)findViewById(R.id.imageView2);
				views[2] = (ImageView)findViewById(R.id.imageView3);
				views[3] = (ImageView)findViewById(R.id.imageView4);
				*/
				for(int i = 0; i < numBitMapsLoaded; i ++)
				{
					Bitmap bm = savedInstanceState.getParcelable(NUMBM_KEY+i+"");
					views[i].setImageBitmap(bm);
					bitMapArray[i] = bm;
					bm = savedInstanceState.getParcelable(ORIGINAL_KEY+i+"");
					originalImages[i] = bm;
				}
			}
			else
			{
				new JSONWeatherTask().execute(dictionary.getCurrentWord());
			}
		}
	    else
	    {
	    	dictionary.nextWord(); //get the first word
	    	//Crouton.showText(this, "Loading...", Style.INFO);
	    	new JSONWeatherTask().execute(dictionary.getCurrentWord());
	    	//Crouton.showText(this, "Now Guess!", Style.INFO);
	    	currentTime = Integer.parseInt(Settings.getTime(getApplicationContext()));
	    	currentTime *= 60;
	    }
	    
	    timeNum = (TextView)findViewById(R.id.time_remaining);
	    scoreNum = (TextView)findViewById(R.id.current_score);
	    
	    Log.d("EYO", "" + Settings.getNumber(getApplicationContext()));
	    currentScore = 0;
	    guessField.setEnabled(true);
		timeThread.start();
	    scoreThread.start();
	}
	
	
	//////////////////////////////////////////
	//////////////////Timer///////////////////
	//////////////////////////////////////////
	
	private Handler timeHandler = new Handler();
	private Handler scoreHandler = new Handler();
	
	class timeCount implements Runnable{
        
        @Override
        public void run() {
                currentScore = playerScore.getScore();
                //tempScore = 0;
               
            while(currentTime > 0 && runFlag == true){
                timeHandler.post(new Runnable() {
                    @Override
                    public void run() {                    	
                    	if(pauseFlag == false){
                        	loadingTip.setText("Now Guess!");
                        	timeNum.setText(TimeUtilities.prettyTime(currentTime));
                    	}
                    	else{
                    		loadingTip.setText("Loading...");
                    		timeNum.setText(TimeUtilities.prettyTime(currentTime) + " - paused");
                    	}
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                
                if(pauseFlag == false){
                	timeCounter --;
                	if(timeCounter == 0){
                		timeCounter = 10;
                		currentTime--;
                	}
                }
            }
           
            if(runFlag == true){
                timeHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        int temp = Integer.parseInt(Settings.getTime(getApplicationContext()));
                        timeNum.setText(temp + "");
                        Toast.makeText(Game.this, "Out of time!", Toast.LENGTH_LONG).show();
                        endGame();
                    }
                });
                //currentTime = Integer.parseInt(Settings.getTime(getApplicationContext()));  
            }
        }
    }
       
    public void kill()
    {
    	runFlag = false;
    }
    
    public void pauseTime(){
    	pauseFlag = true;
    }
    
    public void startTime(){
    	pauseFlag = false;
    }
    
    public void enReload(){
    	reloadFlag = true;
    }
    
    public void unReload(){
    	reloadFlag = false;
    }
	
	private void endGame()
	{
		savePlayerScore(playerScore.getScore());
		startActivity(new Intent(this, LeaderBoard.class));
		finish();
	}
	
	//////////////////////////////////////////
	//////////////////Score///////////////////
	//////////////////////////////////////////
	
	class scoreCount implements Runnable{
		@Override
		public void run(){
			while(true){
				scoreHandler.post(new Runnable(){
					public void run(){
						currentScore = playerScore.getScore();
						scoreNum.setText("Score: " + currentScore);
					}
				});
				try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		}
	}
	
	public void savePlayerScore(int score){
        this.dhScore = new databaseHelperHighScores(this);
        String time = Settings.getTime(getApplicationContext());
        String difficulty = Settings.getNumber(getApplicationContext());
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        String username = settings.getString("name", "player");
       
        this.dhScore.insert(username, score+"", difficulty,time);
        
        }
	
	 public void onResume() {
			super.onResume();
			System.out.println("Resumed");
			 //Check device supported Accelerometer sensor or not
            if (AccelerometerManager.isSupported(this)) {
                AccelerometerManager.startListening(this);
            }   
		}
	 
	 private void checkGuess() {
			String temp = guessField.getText().toString().toLowerCase();
			temp = temp.replace("\n", "");
			temp = temp.replace(".", "");
			temp = temp.replace(" ", "");
			System.out.println("Checkguess");
			String currentWord = dictionary.getCurrentWord().toLowerCase();
			currentWord = currentWord.replace("\n", "");
			currentWord = currentWord.replace(".", "");
			currentWord = currentWord.replace(" ", "");
			if(temp.equals(currentWord))
			{
				System.out.println("Correct guess");
				playerScore.correctAnswer();
				guessField.setText("CORRECT");
				dictionary.nextWord();
				Crouton.showText(this, "Correct (+2)", Style.INFO);
				//Crouton.showText(this, "Loading...", Style.INFO);
				new JSONWeatherTask().execute(dictionary.getCurrentWord());
				//Crouton.showText(this, "Now Guess!", Style.INFO);
			}
			else
			{
				Crouton.showText(this, "Incorrect", Style.ALERT);
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
			// Save remaining time
			int time = currentTime;
			savedInstanceState.putInt(TIME_KEY, time);
			savedInstanceState.putInt(TIME_COUNTER, timeCounter);
			savedInstanceState.putBoolean(TIME_FLAG, pauseFlag);
			// buffer the images for rotation
			savedInstanceState.putBoolean(RELOAD_FLAG, reloadFlag);
			if(reloadFlag == false){
				int numBitMapsLoaded = bitMapArray.length;
				savedInstanceState.putInt(NUMBM_KEY, numBitMapsLoaded);
				for(int i = 0; i < numBitMapsLoaded; i ++)
				{
					savedInstanceState.putParcelable(NUMBM_KEY+i+"", bitMapArray[i]);
					savedInstanceState.putParcelable(ORIGINAL_KEY+i+"", originalImages[i]);
				}
			}
			savedInstanceState.putInt("INDEX", dictionary.getIndex());
		}
		
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.button1:
				checkGuess();
				break;
			case R.id.imageView1:
				LayoutInflater layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
			    popupView = layoutInflater.inflate(R.layout.popup, null);  
			    final PopupWindow mPopupWindow1 = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
			    mPopupWindow1.setTouchable(true);
		        mPopupWindow1.setOutsideTouchable(true);
		        mPopupWindow1.setFocusable(true);;
			    ImageView popImg1 = (ImageView)popupView.findViewById(R.id.popupImageView);
			    //mPopupWindow1.showAsDropDown(views[0]);
			    mPopupWindow1.showAtLocation(views[0], Gravity.CENTER, 0, 0);
			    popupView.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						switch (event.getAction() & MotionEvent.ACTION_MASK) {
						case MotionEvent.ACTION_DOWN:
							mPopupWindow1.dismiss();
						}
						return false;
					}
				});
			    popImg1.setImageBitmap(originalImages[0]);
				break;
			case R.id.imageView2:
				layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
			    View popupView = layoutInflater.inflate(R.layout.popup, null);  
			    final PopupWindow mPopupWindow2 = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
			    mPopupWindow2.setTouchable(true);
		        mPopupWindow2.setOutsideTouchable(true);
		        mPopupWindow2.setFocusable(true);;
			    ImageView popImg2 = (ImageView)popupView.findViewById(R.id.popupImageView);
			    //mPopupWindow2.showAsDropDown(views[1]);
			    mPopupWindow2.showAtLocation(views[1], Gravity.CENTER, 0, 0);
			    popupView.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						switch (event.getAction() & MotionEvent.ACTION_MASK) {
						case MotionEvent.ACTION_DOWN:
							mPopupWindow2.dismiss();
						}
						return false;
					}
				});
			    popImg2.setImageBitmap(originalImages[1]);
				break;
			case R.id.imageView3:
				layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
			    popupView = layoutInflater.inflate(R.layout.popup, null);  
			    final PopupWindow mPopupWindow3 = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
			    mPopupWindow3.setTouchable(true);
		        mPopupWindow3.setOutsideTouchable(true);
		        mPopupWindow3.setFocusable(true);;
			    ImageView popImg3 = (ImageView)popupView.findViewById(R.id.popupImageView);
			    //mPopupWindow3.showAsDropDown(views[2]);
			    mPopupWindow3.showAtLocation(views[2], Gravity.CENTER, 0, 0);
			    popupView.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						switch (event.getAction() & MotionEvent.ACTION_MASK) {
						case MotionEvent.ACTION_DOWN:
							mPopupWindow3.dismiss();
						}
						return false;
					}
				});
			    popImg3.setImageBitmap(originalImages[2]);
				break;
			case R.id.imageView4:
				layoutInflater = (LayoutInflater)getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);  
			    popupView = layoutInflater.inflate(R.layout.popup, null);  
			    final PopupWindow mPopupWindow4 = new PopupWindow(popupView,LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
			    mPopupWindow4.setTouchable(true);
		        mPopupWindow4.setOutsideTouchable(true);
		        mPopupWindow4.setFocusable(true);;
			    ImageView popImg4 = (ImageView)popupView.findViewById(R.id.popupImageView);
			    //mPopupWindow4.showAsDropDown(views[3]);
			    mPopupWindow4.showAtLocation(views[3], Gravity.CENTER, 0, 0);
			    popupView.setOnTouchListener(new View.OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						switch (event.getAction() & MotionEvent.ACTION_MASK) {
						case MotionEvent.ACTION_DOWN:
							mPopupWindow4.dismiss();
						}
						return false;
					}
				});
			    popImg4.setImageBitmap(originalImages[3]);
				break;
			}
		}
		
		//////////////////////////////////////////
		//////////////////Shake///////////////////
		//////////////////////////////////////////
		
		public void onAccelerationChanged(float x, float y, float z) {
	    }
	 
	    public void onShake(float force) {
	    		// Do your stuff here
	    		skipped();
	    }
	    
	    private void skipped()
	    {
	    	dictionary.nextWord();
	    	playerScore.skipped();
	    	Crouton.showText(this, "Skipped (-1)", Style.CONFIRM);
	    	//Crouton.showText(this, "Loading...", Style.INFO);
	    	new JSONWeatherTask().execute(dictionary.getCurrentWord());
	    	//Crouton.showText(this, "Now Guess!", Style.INFO);
	    }
	    @Override
	    public void onStop() {
	            super.onStop();
	            //Check device supported Accelerometer sensor or not
	            if (AccelerometerManager.isListening()) {
	                AccelerometerManager.stopListening();
	            }         
	    }
	    @Override
	    public void onDestroy() {
	        super.onDestroy();
            kill();
	        Log.i("Sensor", "Service  distroy");
	        //Check device supported Accelerometer senssr or not
	        if (AccelerometerManager.isListening()) {
	            AccelerometerManager.stopListening();
	        }         
	    }
	    
	    //////////////////////////////////////////
	    //////////////////Images//////////////////
	    //////////////////////////////////////////
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
				enReload();
				pauseTime();
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
				
				originalImages = new Bitmap[imageSetting];	
				bitMapArray = new Bitmap[imageSetting];
				
				for(int i = 0; i < imageSetting; i ++)
				{
					URL URL = null;
					try {
						URL = new URL(image.getLinks().get(i));
					} catch (MalformedURLException e) {
						e.printStackTrace();
						Log.d(TAG, "URL Failed");
					}
					try {
						Bitmap bitmap = BitmapFactory.decodeStream(URL.openConnection().getInputStream());
						//originalImages[i] = bitmap;
						if(bitmap != null)
						{					
							int width = bitmap.getWidth();
							int height = bitmap.getHeight();
							Bitmap resizedbitmap = bitmap;
							while(width > 800 || height > 800){
								width *= 0.8;
								height *= 0.8;
								resizedbitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
							}
							originalImages[i] = resizedbitmap;
							resizedbitmap = Bitmap.createScaledBitmap(bitmap, 120, 120, true);
							bitMapArray[i] = resizedbitmap;
							pictures.add(resizedbitmap);
						}
						else
						{
							pictures.add(bitmap);	
						}	
					} catch (IOException e) {
						e.printStackTrace();
						Log.d(TAG, "Bitmap Failed");
					}
				}	
				unReload();
				return pictures;
			}

			@Override
			protected void onPostExecute(ArrayList<Bitmap> bmp) {
				if (bmp == null) {
					Log.d("Post", "BMP null");
					return;
				}
				
				//ImageView[] views = new ImageView[4];
				views[0] = (ImageView)findViewById(R.id.imageView1);
				views[1] = (ImageView)findViewById(R.id.imageView2);
				views[2] = (ImageView)findViewById(R.id.imageView3);
				views[3] = (ImageView)findViewById(R.id.imageView4);
				int imageSetting = Integer.parseInt(Settings.getNumber(getApplicationContext()));	
				for(int i = 0; i < imageSetting && i < bmp.size(); i ++)
				{
					//bitMapArray[i] = bmp.get(i);
					views[i].setImageBitmap(bmp.get(i));
				}
				startTime();
			}

		}
}
