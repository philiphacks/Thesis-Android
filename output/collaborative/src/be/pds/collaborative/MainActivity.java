package be.pds.collaborative;

import be.pds.thesis.*;

import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;

import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.TokenPair;

public class MainActivity extends MainActivity {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> properties;

	private TwitterComponent twitter;
    private TextView connectedUsersView;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        properties = new HashMap<String, Object>();
			twitter = new TwitterComponent(this);
    	
			TextView welcomeMsgView = (TextView) this.findViewById(R.id.welcomeMsg);
			Button chatButton = (Button) this.findViewById(R.id.chatButton);
			Button documentsButton = (Button) this.findViewById(R.id.documentsButton);
			Button listButton = (Button) this.findViewById(R.id.listButton);
			Button tweetButton = (Button) this.findViewById(R.id.tweetButton);
			Button logoutButton = (Button) this.findViewById(R.id.logoutButton);
			TextView connectedUsersText = (TextView) this.findViewById(R.id.connectedUsersText);
			TextView connectedUsers = (TextView) this.findViewById(R.id.connectedUsers);

			final Activity parentAct = this;
				chatButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
								properties.put("class", "be.pds.collaborative.ChatActivity");
								ChangeActivityAction caa = new ChangeActivityAction(properties);
								caa.setCallingActivity(parentAct);
								caa.execute();
					}
				});
				documentsButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
								properties.put("class", "be.pds.collaborative.DropboxActivity");
								ChangeActivityAction caa = new ChangeActivityAction(properties);
								caa.setCallingActivity(parentAct);
								caa.execute();
					}
				});
				logoutButton.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
									ServerConnection.getInstance().quit();
								finish();
					}
				});

			ServerConnection.getInstance().addComponent("users", this);
			connectedUsersView = (TextView) this.findViewById(R.id.connectedUsers);
			ServerConnection.getInstance().getConnectedUsers();
    }

    public void setConnectedUsers(final String message) {
    	runOnUiThread(new Runnable() {
			public void run() {
				String output = connectedUsersView.getText().toString();
				output += message;
				connectedUsersView.setText(output);
			}
		});
    }

    private void showToast(String msg) {
    	Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
    	error.show();
    }


		public TwitterComponent getComponent() {
			return twitter;
		}


	
}
