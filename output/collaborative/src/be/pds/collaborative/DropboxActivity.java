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

public class DropboxActivity extends Activity {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> properties;

	private DropboxComponent dropbox;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropbox);
        properties = new HashMap<String, Object>();
			dropbox = new DropboxComponent(this);
    	
			Button dropboxButton = (Button) this.findViewById(R.id.dropboxButton);
			Button dirContentButton = (Button) this.findViewById(R.id.dirContentButton);
			TextView contentText = (TextView) this.findViewById(R.id.contentText);
			ListView dirContent = (ListView) this.findViewById(R.id.dirContent);


    }


    private void showToast(String msg) {
    	Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
    	error.show();
    }


    	@Override
	    protected void onResume() {
	    	super.onResume();
	    	if (dropbox != null) {
	    		AndroidAuthSession session = dropbox.getApi().getSession();
	    		if (session.authenticationSuccessful()) {
	    			try {
	    				session.finishAuthentication();

	    				TokenPair tokens = session.getAccessTokenPair();
	    				dropbox.storeKeys(tokens.key, tokens.secret);
	    				dropbox.setLoggedIn(true);
	    			} catch (IllegalStateException e) {
	    				showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage());
	    				Log.i(TAG, "Error authenticating", e);
	    			}
	    		}
	    	}
	    }
		public DropboxComponent getComponent() {
			return dropbox;
		}


	
}
