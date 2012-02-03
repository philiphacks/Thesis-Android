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

public class LoginActivity extends Activity {

	private LoginComponent login;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

			login = new LoginComponent(this);
			TextView usernameView = (TextView) this.findViewById(R.id.usernameView);
			EditText usernameText = (EditText) this.findViewById(R.id.usernameText);
			TextView passwordView = (TextView) this.findViewById(R.id.passwordView);
			EditText passwordText = (EditText) this.findViewById(R.id.passwordText);
			Button loginButton = (Button) this.findViewById(R.id.loginButton);


    }


	
}
