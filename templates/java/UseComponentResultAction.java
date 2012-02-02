package be.pds.thesis;

import java.util.HashMap;
import android.util.Log;
import android.location.Location;
import android.widget.Toast;

public class UseComponentResultAction extends AndroidAction {

	private final String GEO = "geo";
	private final String TAG = getClass().toString();
	private AndroidComponent component;
	private HashMap<String, Object> result;

	public UseComponentResultAction() {
		super();
	}

	public UseComponentResultAction(HashMap<String, Object> properties) {
		super(properties);
	}

	public void setCalledComponent(AndroidComponent a) {
		this.component = a;
	}

	public void setResult(HashMap<String, Object> result) {
		this.result = result;
	}

	public void execute() {
		Log.i(TAG, "[UseComponentResultAction] executing...");
		String message = "";
		if (((String) result.get("resultType")).equals(GEO)) {
			Location loc = (Location) result.get("result");
			message = "I am at location (" + loc.getLongitude() + ", " + loc.getLatitude() + ")";
		}
		Toast.makeText(this.component.getParent().getBaseContext(), message,
							Toast.LENGTH_SHORT).show();
	}
}
