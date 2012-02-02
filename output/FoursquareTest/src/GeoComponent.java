package be.pds.foursquaretest;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import be.pds.thesis.*;

public class GeoComponent extends AndroidComponent {

	private final String TAG = getClass().toString();
	private Location location;
	private AndroidComponent trigger;

	public void setTrigger(AndroidComponent comp) {
		this.trigger = comp;
	}

	public GeoComponent(Activity a) {
		super(a);
		setupGeo();
	}


	public void action(HashMap<String, Object> properties) {
		properties.put("return", (Location)this.location);
	}

	private void setupGeo() {

		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this.parent.getSystemService(Context.LOCATION_SERVICE);
		
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// Called when a new location is found by the network location provider.
				useNewLocation(location);
			}

			@Override
			public void onProviderDisabled(String provider) { 
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
			}
		};
		
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		// the second is the minimum time interval between notifications
		// the third is the minimum change in distance between notifications
	}

	private void useNewLocation(Location loc) {
		this.location = loc;
		String locString = "I am at location (" + loc.getLongitude() + ", " + loc.getLatitude() + ")";
		Log.i(TAG, locString);
		for (AndroidAction a : actions) {
			if (a instanceof CallComponentAction) {
				a.setProperty("location", loc);
				a.setCalledComponent(this.trigger);
			}
			a.execute();	
		}
	}
}
