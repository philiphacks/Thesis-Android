package be.pds.triggertest;

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
	private AndroidComponent isTriggerOf;

	public void isTriggerOf(AndroidComponent comp) {
		this.isTriggerOf = comp;
	}

	public GeoComponent(Activity a) {
		super(a);
		setupGeo();
	}


	public void action(HashMap<String, Object> properties) {
		// do nothing.
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
		String location = "I am at location (" + loc.getLongitude() + ", " + loc.getLatitude() + ")";
		Log.i(TAG, location);
		for (AndroidAction a : actions) {
			if (a instanceof CallComponentAction) {
				a.setProperty("message", location);
				a.setCalledComponent(this.isTriggerOf);
			}
			a.execute();	
		}
	}
}
