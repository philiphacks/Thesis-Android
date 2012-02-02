package be.pds.timer;

import be.pds.thesis.*;

import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TimerActivity extends Activity {

	private TimerComponent myTimer;
	private GeoComponent myGeo;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

			myTimer = new TimerComponent(this);
			myGeo = new GeoComponent(this);

    		myTimer.setTrigger(myGeo);
    		myGeo.isTriggerOf(myTimer);

				HashMap<String, Object> properties = new HashMap<String, Object>();
					CallComponentAction cca = new CallComponentAction(properties);
					myTimer.addAction(cca);
					UseComponentResultAction ucra = new UseComponentResultAction(properties);
					myTimer.addAction(ucra);
    }


	
}
