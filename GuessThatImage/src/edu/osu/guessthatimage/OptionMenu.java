package edu.osu.guessthatimage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import edu.osu.guessthatimage.*;

public class OptionMenu extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_menu);
		
		View btnNewGame = findViewById(R.id.buttonNewGame);
		btnNewGame.setOnClickListener(this);
		View btnAudio = findViewById(R.id.buttonSettings);
		btnAudio.setOnClickListener(this);
		View btnVideo = findViewById(R.id.buttonHelp);
		btnVideo.setOnClickListener(this);
		View btnImage = findViewById(R.id.buttonLeaderBoard);
		btnImage.setOnClickListener(this);
		View btnExit = findViewById(R.id.buttonExit);
		btnExit.setOnClickListener(this);
	}
	
	/*
	private void quitApplication(){
			new AlertDialog.Builder(this)
			.setTitle(R.string.exit)
			.setMessage("Quit TicTacToe?")
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					System.exit(0);
				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {}
			})
			.show();
	    }
    
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return true;
    }
    
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.menu_settings:
    			startActivity(new Intent(this, Settings.class));
    			return true;
	    	case R.id.menu_help:
	    		startActivity(new Intent(this, Help.class));
	    		return true;
	    	case R.id.menu_exit:
	    		quitApplication();
	    		return true;
	    	case R.id.menu_contacts:
	    		startActivity(new Intent(this, ContactsView.class));
	    		return true;	
	    }
    	return false;
    }
	*/
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.buttonNewGame:
			startActivity(new Intent(this, Game.class));
			break;
		case R.id.buttonSettings:
			//startActivity(new Intent(this, Audio.class));
			break;
		case R.id.buttonHelp:
			//startActivity(new Intent(this, Help.class));
			break;
		case R.id.buttonLeaderBoard:
			//startActivity(new Intent(this, Sensors.class));
			break;
		case R.id.buttonExit:
			//{
				//quitApplication();
				//stopService(new Intent(this, MyPlaybackService.class));
			//}
			finish();
			break;
		}
		
	}
}
