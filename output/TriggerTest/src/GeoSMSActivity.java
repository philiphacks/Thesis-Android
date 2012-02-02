package be.pds.triggertest;

import be.pds.thesis.*;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;

public class GeoSMSActivity extends Activity {

	private SMSComponent mySms;
	private GeoComponent myGeo;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

			mySms = new SMSComponent(this);
			myGeo = new GeoComponent(this);

    		mySms.setTrigger(myGeo);
    		myGeo.isTriggerOf(mySms);

					HashMap<String, Object> properties = new HashMap<String, Object>();
						String phoneNr = "5664";
						properties.put("phoneNr", phoneNr);
						String message = "Default message";
						properties.put("message", message);
					CallComponentAction cca = new CallComponentAction(properties);
					myGeo.addAction(cca);
    }

	
}
