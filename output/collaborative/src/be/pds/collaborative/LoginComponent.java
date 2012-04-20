package be.pds.collaborative;

import android.view.View;

import android.app.Activity;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.util.Log;
import java.util.HashMap;

import be.pds.thesis.*;

public class LoginComponent extends AndroidComponent {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> result;
	private HashMap<String, Object> properties;

	private TextView usernameView;
	private EditText usernameText;
	private TextView passwordView;
	private EditText passwordText;
	private Button loginButton;

	public LoginComponent(Activity a) {
		super(a);
		properties = new HashMap<String, Object>();
	    usernameView = (TextView) parent.findViewById(R.id.usernameView);
	    usernameText = (EditText) parent.findViewById(R.id.usernameText);
	    passwordView = (TextView) parent.findViewById(R.id.passwordView);
	    passwordText = (EditText) parent.findViewById(R.id.passwordText);
	    loginButton = (Button) parent.findViewById(R.id.loginButton);

					final Activity parentAct = parent;
					final HashMap<String, Object> props = properties;
					loginButton.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
		action(props);

		properties.put("class", "be.pds.collaborative.Main");
			HashMap<String, String> data = new HashMap<String, String>();
					data.put("user", (String)usernameText.getText().toString());
				properties.put("data", data);
				if (ServerConnection.getInstance().checkServerProperty("LoginProperty")) {
					ChangeActivityAction caa = new ChangeActivityAction(properties);
					caa.setCallingActivity(parentAct);
					caa.execute();
				}
						}
					});
	}

	public void action(HashMap<String, Object> properties) {
					properties.put("username", usernameText.getText().toString());
					properties.put("password", passwordText.getText().toString());
		login();
	}

	private void login() {
		String msg = "{\"auth\" : { \"user\" : \"" + properties.get("username") + "\", \"pass\" : \"" + properties.get("password") + "\" } }";
		ServerConnection.getInstance().sendMessage(msg);
	}

	@Override
	public void onMessage(String message) { }
}
