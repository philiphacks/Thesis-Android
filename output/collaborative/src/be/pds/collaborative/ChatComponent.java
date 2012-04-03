package be.pds.collaborative;

import java.util.HashMap;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import be.pds.thesis.AndroidComponent;
import be.pds.thesis.ServerConnection;

public class ChatComponent extends AndroidComponent {
	
	private final String TAG = getClass().getName();
	private HashMap<String, Object> properties;
	private String user;
		private EditText textInput;
		private TextView textOutput;
		private Button sendButton;
	
	public ChatComponent(Activity a) {
		super(a);
		properties = new HashMap<String, Object>();
		user = ServerConnection.getInstance().getUser();
		ServerConnection.getInstance().getLastMessages();

			textInput = (EditText) this.parent.findViewById(R.id.textInput);
			textOutput = (TextView) this.parent.findViewById(R.id.textOutput);
			sendButton = (Button) this.parent.findViewById(R.id.sendButton);
			sendButton.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					action(properties);
				}
			});
	}

	public void onMessage(String message) {
		final String m = message;
		parent.runOnUiThread(new Runnable() {
			public void run() {
				String output = textOutput.getText().toString();
				output += m;
				textOutput.setText(output);
			}
		});
	}

	@Override
	public void action(HashMap<String, Object> properties) {
		Log.i(TAG, "Send button clicked!");
		String message = textInput.getText().toString();
		String json = "{\"chat\" : { \"user\" : \"" + user + "\", \"message\" : \"" + message + "\" } }";

		ServerConnection.getInstance().sendMessage(json);
		textInput.setText("");
	}
}
