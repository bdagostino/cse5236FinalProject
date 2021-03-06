package edu.osu.guessthatimage.test;

import com.robotium.solo.Solo;

import android.preference.ListPreference;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.EditText;
import junit.framework.TestCase;
import edu.osu.guessthatimage.R;
import edu.osu.guessthatimage.LoginScreen;
import edu.osu.guessthatimage.OptionMenu;

//Test Use Case 1, 2, and 3
public class TestLogin extends ActivityInstrumentationTestCase2<LoginScreen> {
	private Solo solo;
	
	public TestLogin(){
		super(LoginScreen.class);
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
	
	//Use Case 3
	public void test_1_setup_account(){
		String username = "Test";
		String password = "Me";
		solo.clickOnButton("Sign up new account");
		solo.waitForActivity("edu.osu.guessthatimage.SignUp", 1000);
		solo.assertCurrentActivity("The Activity Should Be SignUp Menu", "SignUp");
		EditText userNameEditableField = (EditText) solo.getView(R.id.new_username);
		EditText passwordEditableField = (EditText) solo.getView(R.id.new_password);
		EditText passwordConfirmEditableField = (EditText) solo.getView(R.id.password_confirm);
		solo.clearEditText(userNameEditableField); 
		solo.enterText(userNameEditableField,String.valueOf(username));
		solo.clearEditText(passwordEditableField); 
		solo.enterText(passwordEditableField,String.valueOf(password));
		solo.clearEditText(passwordConfirmEditableField); 
		solo.enterText(passwordConfirmEditableField,String.valueOf(password));
		solo.clickOnButton("Sign up!");
		solo.assertCurrentActivity("The Activity Should Be Login Screen", "LoginScreen");
		
	}
	
	//Use Case 2
	public void test_2_login_correct(){
		String username = "Test";
		String password = "Me";
		EditText userNameEditableField = (EditText) solo.getView(R.id.username_text);
		EditText passwordEditableField = (EditText) solo.getView(R.id.password_text);
		solo.clearEditText(userNameEditableField); 
		solo.enterText(userNameEditableField,String.valueOf(username));
		solo.clearEditText(passwordEditableField); 
		solo.enterText(passwordEditableField,String.valueOf(password));
		
		solo.clickOnButton("Login");
		solo.waitForActivity("edu.osu.guessthatimage.OptionMenu", 1000);
		solo.assertCurrentActivity("The Activity Should Be Option Menu", "OptionMenu");		
	}
}
