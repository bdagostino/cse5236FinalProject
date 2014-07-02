package edu.osu.guessthatimage;

import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginScreen extends Activity implements OnClickListener {
	private DatabaseHelperAccounts accountDb;
	private EditText userNameEditableField;
	private EditText passwordEditableField;
	private final static String OPT_NAME = "name";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		userNameEditableField = (EditText) findViewById(R.id.username_text);
		passwordEditableField = (EditText) findViewById(R.id.password_text);
		View btnLogin = (Button) findViewById(R.id.login_button);
		btnLogin.setOnClickListener(this);
		View btnCancel = (Button) findViewById(R.id.cancel_button);
		btnCancel.setOnClickListener(this);
		View btnNewUser = (Button) findViewById(R.id.new_user_button);
		btnNewUser.setOnClickListener(this);
	}

	private void checkLogin() {
		//startActivity(new Intent(this, OptionMenu.class));
		//finish();
		
		String username = this.userNameEditableField.getText().toString();
		String password = this.passwordEditableField.getText().toString();
		this.accountDb = new DatabaseHelperAccounts(this);
		List<String> names = this.accountDb.selectAll(username, password);
		if (names.size() > 0) { // Login successful
			// Save username as the name of the player
			SharedPreferences settings = PreferenceManager
					.getDefaultSharedPreferences(this);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(OPT_NAME, username);
			editor.commit();

			// Bring up the GameOptions screen
			startActivity(new Intent(this, OptionMenu.class));
			finish();
		} else {
			// Try again?
			new AlertDialog.Builder(this)
					.setTitle("Error")
					.setMessage("Login failed")
					.setNeutralButton("Try Again",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							}).show();
			userNameEditableField.setText("");
			passwordEditableField.setText("");
		}
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_button:
			checkLogin();
			break;
		case R.id.cancel_button:
			finish();
			break;
		case R.id.new_user_button:
			startActivity(new Intent(this, SignUp.class));
			break;
		}
	}
}
