package be.pds.thesis;

import java.util.HashMap;
import java.util.Iterator;
import java.lang.ClassNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ChangeActivityAction extends AndroidAction {

	private final String TAG = getClass().toString();
	private Activity caller;

	public ChangeActivityAction() {
		super();
	}

	public ChangeActivityAction(HashMap<String, Object> properties) {
		super(properties);
	}

	public void setCallingActivity(Activity caller) {
		this.caller = caller;
	}

	public void execute() {
		Log.i(TAG, "[ChangeActivityAction] executing...");
		try {
			Intent i = new Intent(this.caller, 
						(Class.forName((String) properties.get("class"))));
			HashMap<String, String> data = (HashMap<String, String>) properties.get("data");
			if (data != null) {
				Bundle b = new Bundle();
				Iterator iterator = data.keySet().iterator();
				while (iterator.hasNext()) {
					String value = (String) iterator.next();
					b.putString(value, data.get(value));
				}
				i.putExtras(b);
			}

			this.caller.startActivity(i);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
