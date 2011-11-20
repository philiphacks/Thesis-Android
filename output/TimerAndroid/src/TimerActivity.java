package be.pds.timer;

public class TimerActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupmyTimer();
        setupmyGeo();
    }

	
	private final String TAG = getClass().getName();
	private Location lastLocation;
	
	
	private void setupmyTimer() { 
	
	    Log.i(TAG, "setupTimer()");
		new CountDownTimer(5000, 1000) {
			@Override
			public void onFinish() {
				if (lastLocation != null) {
					String lon = String.valueOf(lastLocation.getLongitude());
					String lat = String.valueOf(lastLocation.getLatitude());
					String msg = "Last known location: (" + lon + ", " + lat + ")";
					Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getBaseContext(), "No location found.", Toast.LENGTH_LONG).show();
				}
				setupmyTimer();
			}
	
			@Override
			public void onTick(long millisUntilFinished) {
				Toast.makeText(getBaseContext(), "Tick.", Toast.LENGTH_SHORT).show();
			}
			
		}.start();
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
	    	lastLocation = loc;
	
	
	}
	
}
