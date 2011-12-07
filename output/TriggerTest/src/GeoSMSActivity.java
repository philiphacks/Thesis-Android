package be.pds.triggertest;

public class GeoSMSActivity extends Activity {

	private SMSComponent mySms;
	private GeoComponent myGeo;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

			mySms = new SMSComponent(this);
				mySms.setTrigger(myGeo);
			myGeo = new GeoComponent(this);
				myGeo.isTriggerOf(mySms);
    }

	
}
