package be.pds.geosms;

public class GeoSMSActivity extends Activity {

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        setupmySms();
        setupmyGeo();
    }

	
	private EditText editPhoneNr;
	private EditText editMessage;
	private Button sendMessage;
	
	private void setupmySms() {    
	    editPhoneNr = (EditText) findViewById(R.id.txtPhoneNo);
	    editMessage = (EditText) findViewById(R.id.txtMessage);
	    sendMessage = (Button) findViewById(R.id.btnSendSMS);
	    
		// Found a trigger.. it is a Geo Component
		// We should probably send an SMS with the Geo data.
		// However, this will be processed in the Geo component.
	}
	
	private void sendSMS(String phoneNumber, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";
		
		PendingIntent sentPI = PendingIntent.getBroadcast(this, 0,
				new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
				new Intent(DELIVERED), 0);
		
		//---when the SMS has been sent---
		registerReceiver(new BroadcastReceiver() {
	
			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
					case Activity.RESULT_OK:
						Toast.makeText(getBaseContext(), "SMS sent",
								Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						Toast.makeText(getBaseContext(), "Generic failure", 
								Toast.LENGTH_SHORT).show();
					    break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						Toast.makeText(getBaseContext(), "No service", 
					    		Toast.LENGTH_SHORT).show();
					    break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						Toast.makeText(getBaseContext(), "Null PDU", 
								Toast.LENGTH_SHORT).show();
					    break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						Toast.makeText(getBaseContext(), "Radio off", 
								Toast.LENGTH_SHORT).show();
					    break;
					default:
						Toast.makeText(getBaseContext(), "Fail.", 
								Toast.LENGTH_SHORT).show();
						break;
				}
			}
			
		}, new IntentFilter(SENT));
		
		//---when the SMS has been delivered---
		registerReceiver(new BroadcastReceiver() {
	
			@Override
			public void onReceive(Context context, Intent intent) {
				switch (getResultCode()) {
					case Activity.RESULT_OK:
						Toast.makeText(getBaseContext(), "SMS delivered",
								Toast.LENGTH_SHORT).show();
						break;
					case Activity.RESULT_CANCELED:
						Toast.makeText(getBaseContext(), "SMS not delivered",
								Toast.LENGTH_SHORT).show();
						break;
				}
			}
			
		}, new IntentFilter(DELIVERED));
		
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	
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
	String location = "I am at " + loc.toString();
	sendSMS(editPhoneNr.getText().toString(), location);
	}
	
}
