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

public class ChatActivity extends GenericActivity {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> properties;

	private ChatComponent chat;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
				ServerConnection.getInstance().addComponent("chat", this);
        properties = new HashMap<String, Object>();
			chat = new ChatComponent(this);
    	
			ScrollView chatBox = (ScrollView) this.findViewById(R.id.scrollChat);


    }


    private void showToast(String msg) {
    	Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
    	error.show();
    }

	@Override
	public void onMessage(String user, String message) {
		
	}

		public ChatComponent getComponent() {
			return chat;
		}


	
}
