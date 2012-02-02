package be.pds.foursquaretest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import android.app.Activity;
import android.location.Location;
import android.widget.TextView;
import android.util.Log;

import be.pds.thesis.AndroidComponent;

public class FoursquareComponent extends AndroidComponent {

	private final String TAG = getClass().toString();
	private final String API_URL = "https://api.foursquare.com/v2";
	private final String VENUE_CATEGORY = "/venues/search";
	private final String OAUTH_TOKEN = "oauth_token=ZR14WJ2YK3FPF2MXGWN5UYKF4HH0U0TZLWXKVZA0RDXKAV0K";
	private AndroidComponent isTriggerOf;

	public void isTriggerOf(AndroidComponent comp) {
		this.isTriggerOf = comp;
	}
	
	public FoursquareComponent(Activity a) {
		super(a);
		setupFoursquare();
	}

			private TextView showText;

	private void setupFoursquare() {
	    	showText = (TextView) this.parent.findViewById(R.id.showText);
	}

	@Override
	public void action(HashMap<String, Object> properties) {
		properties.put("return", getVenues((Location)properties.get("location")));
	}

	private String getVenues(Location loc) {
		String venues = "";
		if (loc == null) {
			return venues;
		}
		String location = loc.getLongitude() + "," + loc.getLatitude();
		Log.i(TAG, "Getting venues for " + location);
		try {
			URL url = new URL(API_URL + VENUE_CATEGORY + "?ll=" + location + "&" + OAUTH_TOKEN);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			
			urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            
            venues = streamToString(urlConnection.getInputStream());
            Log.i(TAG, venues);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return venues;
	}
	
	private String streamToString(InputStream is) throws IOException {		
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[1024];
			
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		}
		
		return "";
	}
}
