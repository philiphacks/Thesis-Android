package be.pds.twitter;

public class TwitterActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupmyTwitter();
    }

	
	private SharedPreferences prefs;
	private final Handler mTwitterHandler = new Handler();
	
	private TextView tweetInfo;
	private EditText tweetText;
	private Button sendTweet;
	
	private void setupmyTwitter() { 
	    tweetInfo = (TextView) findViewById(R.id.tweetInfo);
	    tweetText = (EditText) findViewById(R.id.tweetText);
	    sendTweet = (Button) findViewById(R.id.sendTweet);
	
	    this.prefs = PreferenceManager.getDefaultSharedPreferences(this);
	    sendTweet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				authAndTweet();
			}
		});
	}
	
	final Runnable mUpdateTwitterNotification = new Runnable() {
		public void run() {
			Toast.makeText(getBaseContext(), "Tweet sent !", Toast.LENGTH_LONG).show();
		}
	};
		
	private void authAndTweet() {
		if (TwitterUtils.isAuthenticated(prefs)) {
			sendTweet();
		} else {
			Intent i = new Intent(getApplicationContext(), PrepareRequestTokenActivity.class);
			i.putExtra("tweet_msg", tweetText.getText().toString());
			startActivity(i);
		}
	}
	
	private void sendTweet() {
		sendTweet(tweetText.getText().toString());
	}
	
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
