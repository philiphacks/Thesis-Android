package be.pds.thesis;

import android.app.Activity;
import android.os.Bundle;

public abstract class GenericActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);    	
    }
    
    public String getClassName() {
		String FQClassName = this.getClass().getName();
		int firstChar;
		firstChar = FQClassName.lastIndexOf('.') + 1;
		if (firstChar > 0) {
			FQClassName = FQClassName.substring(firstChar);
		}
		return FQClassName;
	}
    
	abstract public AndroidComponent getComponent();
	abstract public void onMessage(String user, String message);
}
