package edu.osu.guessthatimage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Help extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);
		
		View introduction = (TextView) findViewById(R.id.introduction);
		View btnCancel = (Button) findViewById(R.id.help_cancel);
		btnCancel.setOnClickListener(this);
	}

	@Override
	public void onClick(View v){
		switch (v.getId()){
		case R.id.help_cancel:
			finish();
			break;
		}
	}
}
