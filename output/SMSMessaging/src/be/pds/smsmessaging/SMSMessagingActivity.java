package be.pds.smsmessaging;

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

public class SMSMessagingActivity extends Activity {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> properties;

	private SMSComponent mySms;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        properties = new HashMap<String, Object>();
			mySms = new SMSComponent(this);
    	
			TextView viewNumber = (TextView) this.findViewById(R.id.viewNumber);
			EditText editPhoneNr = (EditText) this.findViewById(R.id.txtPhoneNo);
			TextView viewMessage = (TextView) this.findViewById(R.id.viewMessage);
			EditText editMessage = (EditText) this.findViewById(R.id.txtMessage);
			Button sendMessage = (Button) this.findViewById(R.id.btnSendSMS);


    }


    private void showToast(String msg) {
    	Toast error = Toast.makeText(this, msg, Toast.LENGTH_LONG);
    	error.show();
    }


		public SMSComponent getComponent() {
			return mySms;
		}


	
}
