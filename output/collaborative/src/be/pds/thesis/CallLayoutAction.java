package be.pds.thesis;

import java.util.HashMap;
import android.view.View;
import android.util.Log;

public class CallLayoutAction extends AndroidAction {

	public enum Type { TRIGGER_BUTTON };
	private final String TAG = getClass().toString();
	private View view;
	private Type type;

	public CallLayoutAction() {
		super();
	}

	public CallLayoutAction(HashMap<String, Object> properties) {
		super(properties);
	}

	public void setLayoutComponent(View v, Type t) {
		this.view = v;
		this.type = t;
	}

	public void execute() {
		Log.i(TAG, "[CallLayoutAction] executing...");
		if (this.type == Type.TRIGGER_BUTTON) {
			Log.i(TAG, "[CallLayoutAction] triggering button...");
		}
	}
		
}
