package be.pds.thesis;

import java.util.HashMap;
import android.util.Log;

public class CallComponentAction extends AndroidAction {

	private final String TAG = getClass().toString();
	private AndroidComponent component;

	public CallComponentAction() {
		super();
	}

	public CallComponentAction(HashMap<String, Object> properties) {
		super(properties);
	}

	public void setCalledComponent(AndroidComponent a) {
		this.component = a;
	}

	public void execute() {
		Log.i(TAG, "[CallComponentAction] executing...");
		this.component.action(properties);
	}
	
}
