package be.pds.thesis;

import android.app.Activity;
import android.os.Bundle;

public abstract class MainActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);    	
    }
    
    abstract public void setConnectedUsers(String users);
}
