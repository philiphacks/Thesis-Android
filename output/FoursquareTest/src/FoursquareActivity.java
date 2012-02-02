package be.pds.foursquaretest;

import be.pds.thesis.*;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;

public class FoursquareActivity extends Activity {

	private GeoComponent myGeo;
	private FoursquareComponent my4sq;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

			myGeo = new GeoComponent(this);
			my4sq = new FoursquareComponent(this);

    		myGeo.setTrigger(my4sq);
    		my4sq.isTriggerOf(myGeo);

					HashMap<String, Object> properties = new HashMap<String, Object>();
						String location = "51.134083, 5.019743";
						properties.put("location", location);
					CallComponentAction cca = new CallComponentAction(properties);
					my4sq.addAction(cca);
    }

	
}
