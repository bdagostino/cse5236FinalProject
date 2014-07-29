package edu.osu.guessthatimage.test;

import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.robotium.solo.Solo;

import edu.osu.guessthatimage.LoginScreen;
import edu.osu.guessthatimage.OptionMenu;
import edu.osu.guessthatimage.Game;
import edu.osu.guessthatimage.R;
import junit.framework.Assert;
import junit.framework.TestCase;

//Test Use Case 5
public class TestGame extends ActivityInstrumentationTestCase2<Game> 
{
	private Solo solo;
		
		public TestGame(){
			super(Game.class);
		}
		
		@Override 
		protected void setUp() throws Exception{
			super.setUp(); 
			solo= new Solo(getInstrumentation(),getActivity());
		}
		
		@Override 
		protected void tearDown() throws Exception{
			solo.finishOpenedActivities();
		}
		
		public void test_1_CorrectGuess()
		{
			String correctWord = "cat";
			//Enter Correct Guess
			EditText guessField = (EditText) solo.getView(R.id.editText1);
			solo.clearEditText(guessField); 
			solo.enterText(guessField,String.valueOf(correctWord));
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			//Guess
			solo.clickOnButton("Guess");
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			//Check that the user gets 2 points for a correct response
			TextView current_score_Text_View = (TextView) solo.getCurrentActivity().findViewById(R.id.current_score);
			Assert.assertEquals("Score: 2", current_score_Text_View.getText().toString());
		}
		
		public void test_2_InCorrectGuess()
		{			
			String inCorrectWord = "dog";
			//Enter Correct Guess
			EditText guessField = (EditText) solo.getView(R.id.editText1);
			solo.clearEditText(guessField); 
			solo.enterText(guessField,String.valueOf(inCorrectWord));
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			//Guess
			solo.clickOnButton("Guess");
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			//Check that the user gets 0 points for an incorrect response
			TextView current_score_Text_View = (TextView) solo.getCurrentActivity().findViewById(R.id.current_score);
			Assert.assertEquals("Score: 0", current_score_Text_View.getText().toString());
		}
		
		public void test_3_Skip()
		{
			((Game)solo.getCurrentActivity()).onShake(1);
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			//Check that the user loses 1 point for skipping
			TextView current_score_Text_View = (TextView) solo.getCurrentActivity().findViewById(R.id.current_score);
			Assert.assertEquals("Score: -1", current_score_Text_View.getText().toString());
		}
		
		public void test_4_ScoreBoardTransition()
		{
			//Make the remaining time equal 1 second so we dont have to wait
			((Game)solo.getCurrentActivity()).SetCurrentTime(1);
			//Wait for the game to transition to leaderboard or too much time has passed
			solo.waitForActivity("edu.osu.guessthatimage.LeaderBoard",5000);
			//Check to make sure the currennt activity is the leaderboard
			solo.assertCurrentActivity("Should Be LeaderBoard", "LeaderBoard");	
		}
}
