package be.pds.collaborative;


import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.Toast;
import be.pds.thesis.AndroidComponent;

public class TwitterComponent extends AndroidComponent {

	private SharedPreferences prefs;
	private final Handler mTwitterHandler = new Handler();
	private HashMap<String, Object> properties;
		private Button tweetButton;


	public TwitterComponent(Activity a) {
		super(a);
		setupTwitter();
	}

	private void setupTwitter() { 
			tweetButton = (Button) this.parent.findViewById(R.id.tweetButton);
			tweetButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					action(properties);
				}
			});

	    this.prefs = PreferenceManager.getDefaultSharedPreferences(this.parent);
	    properties = new HashMap<String, Object>();
	    	properties.put("tweet", "I am using the collaborative application to do research!");
	}

	final Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(parent.getBaseContext(), "Tweet sent !", Toast.LENGTH_LONG).show();
		}
	};

	@Override
	public void action(HashMap<String, Object> properties) {
		authAndTweet();
	}
		
	private void authAndTweet() {
		if (TwitterUtils.isAuthenticated(prefs)) {
			sendTweet();
		} else {
			Intent i = new Intent(this.parent.getApplicationContext(), PrepareRequestTokenActivity.class);
			this.parent.startActivity(i);
		}
	}

	private void sendTweet() {
		String tweet = "";
		if (properties.get("tweet") != null) {
			tweet = (String)properties.get("tweet");
		}
		sendTweet(tweet);
	}

	@Override
	public void onMessage(String message) { }

	private void sendTweet(final String tweet) {
		Thread t = new Thread() {
			public void run() {
				try {
					TwitterUtils.sendTweet(prefs, tweet);
					mTwitterHandler.post(mUpdateTwitterNotification);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
	}

}
