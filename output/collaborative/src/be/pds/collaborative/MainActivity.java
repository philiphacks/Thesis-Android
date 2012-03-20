package be.pds.collaborative;

import be.pds.thesis.*;

import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class MainActivity extends Activity {

	private TwitterComponent twitter;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
			twitter = new TwitterComponent(this);
			TextView welcomeMsgView = (TextView) this.findViewById(R.id.welcomeMsg);
			Button chatButton = (Button) this.findViewById(R.id.chatButton);
			Button documentsButton = (Button) this.findViewById(R.id.documentsButton);
			Button listButton = (Button) this.findViewById(R.id.listButton);
			Button tweetButton = (Button) this.findViewById(R.id.tweetButton);
			Button logoutButton = (Button) this.findViewById(R.id.logoutButton);
			TextView connectedUsersTextView = (TextView) this.findViewById(R.id.connectedUsersText);
			TextView connectedUsersView = (TextView) this.findViewById(R.id.connectedUsers);

    }


	
}
