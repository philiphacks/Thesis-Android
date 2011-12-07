package be.pds.triggertest;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GeoComponent {

	private Activity parent;
	private final String TAG = getClass().toString();
	private SMSComponent isTriggerOf;

	public void isTriggerOf(SMSComponent sms) {
		this.isTriggerOf = sms;
	}

	public GeoComponent(Activity a) {
		this.parent = a;
		setupGeo();
	}


	private void setupmyGeo() {

		// Acquire a reference to the system Location Manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
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
		// A Geo component can never have a trigger.. It can only be a trigger of another component
			// this component is the trigger of another component.
			// What does this mean for a geocomponent?
			// Check the type of isTriggerOf()
			// If it is "SMS", probably use Geo to send an SMS.
				// We found that this Geo Component is the trigger of an SMS component
				// Find phone number and send the location
				String location = "I am at location (" + loc.getLongitude() + ", " + loc.getLatitude() + ")";
				this.isTriggerOf.action(this.isTriggerOf.geteditPhoneNr().getText().toString(), location);
	}
}
