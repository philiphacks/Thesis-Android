package be.pds.collaborative;

import android.app.Activity;
import android.widget.Toast;
import android.util.Log;
import java.util.HashMap;

import be.pds.thesis.*;

public class LoginComponent extends AndroidComponent {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> result;


	public LoginComponent(Activity a) {
		super(a);
	}

	public void action(HashMap<String, Object> properties) {
		login();
	}

	private void login() {
		
	}
}
