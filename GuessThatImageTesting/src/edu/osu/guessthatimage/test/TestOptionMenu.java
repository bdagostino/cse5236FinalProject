package edu.osu.guessthatimage.test;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;

import com.robotium.solo.Solo;

import edu.osu.guessthatimage.LoginScreen;
import edu.osu.guessthatimage.OptionMenu;
import edu.osu.guessthatimage.Game;
import edu.osu.guessthatimage.R;
import junit.framework.TestCase;

//Use Case 4
public class TestOptionMenu extends ActivityInstrumentationTestCase2<OptionMenu> {
private Solo solo;
	
	public TestOptionMenu(){
		super(OptionMenu.class);
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
	
	public void test_1_gameStart(){
		solo.clickOnButton("Start New Game");
		solo.waitForActivity("edu.osu.guessthatimage.Game", 1000);
		solo.assertCurrentActivity("Should Be New Game Session", "Game");	
	}
	
	public void test_2_gameSettings(){
		solo.clickOnButton("Game Settings");
		solo.waitForActivity("edu.osu.guessthatimage.Settings", 1000);
		solo.assertCurrentActivity("Should Be Settings Page", "Settings");
	}
	
	public void test_3_Help(){
		solo.clickOnButton("Help");
		solo.waitForActivity("edu.osu.guessthatimage.Help", 1000);
		solo.assertCurrentActivity("Should Be Help Page", "Help");
	}
	
	public void test_4_LeaderBoard(){
		solo.clickOnButton("Leader Board");
		solo.waitForActivity("edu.osu.guessthatimage.LeaderBoard", 1000);
		solo.assertCurrentActivity("Should Be LeaderBoard Page", "LeaderBoard");
	}
	
	public void test_5_Exit(){
		solo.clickOnButton("Exit");
		solo.assertCurrentActivity("Should Be Null", "");
	}
}
