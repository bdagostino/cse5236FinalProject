package edu.osu.guessthatimage;

import java.util.List;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SignUp extends Activity implements OnClickListener {
	
	private EditText etUsername;
	private EditText etPassword;
	private EditText etConfirm;
	private DatabaseHelperAccounts accountDb;

	public void onCreate(Bundle savedInstanceState ){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		
		etUsername = (EditText) findViewById(R.id.new_username);
		etPassword = (EditText) findViewById(R.id.new_password);
		etConfirm = (EditText) findViewById(R.id.password_confirm);
		View btnAdd = (Button) findViewById(R.id.signup_done_button);
		btnAdd.setOnClickListener(this);
		View btnCancel = (Button) findViewById(R.id.signup_cancel_button);
		btnCancel.setOnClickListener(this);
	}
	
	public void onClick(View v){
		switch (v.getId()){
		case R.id.signup_done_button:
			createAccount();
			//finish();
			break;
		case R.id.signup_cancel_button:
			finish();
			break;
		}
	}
	
	private void createAccount() {
		// this.output = (TextView) this.findViewById(R.id.out_text);
		String username = etUsername.getText().toString();
		String password = etPassword.getText().toString();
		String confirm = etConfirm.getText().toString();
		if ((password.equals(confirm)) && (!username.equals(""))
				&& (!password.equals("")) && (!confirm.equals(""))) {
			this.accountDb = new DatabaseHelperAccounts(this);
			List<String> records = this.accountDb.checkExistence(username);
			if(records.size() == 0){
				this.accountDb.insert(username, password);
			// this.labResult.setText("Added");
				Crouton.showText(this, "Account created", Style.INFO);
				finish();
			}
			else{
				new AlertDialog.Builder(this)
				.setTitle("Error")
				.setMessage("Username already exists!")
				.setNeutralButton("Try Again",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).show();
				etUsername.setText("");
				etPassword.setText("");
				etConfirm.setText("");
			}
		} else if ((username.equals("")) || (password.equals(""))
				|| (confirm.equals(""))) {
			Crouton.showText(this, "Invalid information entered", Style.ALERT);
			etUsername.setText("");
			etPassword.setText("");
			etConfirm.setText("");
		} else if (!password.equals(confirm)) {
			new AlertDialog.Builder(this)
					.setTitle("Error")
					.setMessage("passwords do not match")
					.setNeutralButton("Try Again",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int which) {
								}
							})

					.show();
			etUsername.setText("");
			etPassword.setText("");
			etConfirm.setText("");
		}
	}

}
