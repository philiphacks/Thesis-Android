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

public class LoginActivity extends Activity {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> properties;

	private LoginComponent login;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ServerConnection.getInstance().setHost("10.0.2.2");
        ServerConnection.getInstance().setPort("3000");
        ServerConnection.getInstance().connect();
        setContentView(R.layout.login);
        properties = new HashMap<String, Object>();
			login = new LoginComponent(this);
    	
			TextView usernameView = (TextView) this.findViewById(R.id.usernameView);
			EditText usernameText = (EditText) this.findViewById(R.id.usernameText);
			TextView passwordView = (TextView) this.findViewById(R.id.passwordView);
			EditText passwordText = (EditText) this.findViewById(R.id.passwordText);
			Button loginButton = (Button) this.findViewById(R.id.loginButton);


    }


    private void showToast(String msg) {
    	Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
    	error.show();
    }


		public LoginComponent getComponent() {
			return login;
		}


	
}
