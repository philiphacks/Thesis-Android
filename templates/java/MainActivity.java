package be.pds.thesis;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;

public abstract class MainActivity extends Activity {

	protected ArrayList<User> connectedUsers;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);    	
    }
    
    abstract public void setConnectedUsers(String users);
    abstract public void addConnectedUser(User u);
    abstract public void remConnectedUser(User u);
}
