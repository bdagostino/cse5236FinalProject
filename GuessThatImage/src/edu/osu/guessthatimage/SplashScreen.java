package edu.osu.guessthatimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashScreen extends Activity {
	protected boolean active = true;
	protected int splashTime = 5000;
	protected int timeIncrement = 100;
	protected int sleepTime = 100;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		// thread for displaying the SplashScreen
		Thread splashThread = new Thread() {
			@Override
			public void run() {
				try {
					int elapsedTime = 0;
					while (active && (elapsedTime < splashTime)) {
						sleep(sleepTime);
						if (active)
							elapsedTime = elapsedTime + timeIncrement;
					}
				} catch (InterruptedException e) {
					// do nothing
				} finally {
					finish();
					startActivity(new Intent(
							"edu.osu.guessthatimage.LoginScreen"));
				}
			}
		};
		splashThread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			active = false;
		}
		return true;
	}
}
