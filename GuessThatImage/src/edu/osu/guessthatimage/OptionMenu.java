package edu.osu.guessthatimage;

import de.keyboardsurfer.android.widget.crouton.*;
import de.keyboardsurfer.android.widget.crouton.Style;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

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
	
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.buttonNewGame:
			startActivity(new Intent(this, Game.class));
			break;
		case R.id.buttonSettings:
			startActivity(new Intent(this, Settings.class));
			break;
		case R.id.buttonHelp:
			startActivity(new Intent(this, Help.class));
			break;
		case R.id.buttonLeaderBoard:
			startActivity(new Intent(this, LeaderBoard.class));
			break;
		case R.id.buttonExit:
			finish();
			break;
		}
		
	}
}
