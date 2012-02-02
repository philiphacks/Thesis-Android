package be.pds.timer;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.Toast;
import android.util.Log;
import java.util.HashMap;

import be.pds.thesis.*;

public class TimerComponent extends AndroidComponent {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> result;
	private AndroidComponent trigger;

	public void setTrigger(AndroidComponent comp) {
		this.trigger = comp;
	}


	public TimerComponent(Activity a) {
		super(a);

		setupTimer();
	}

	public void action(HashMap<String, Object> properties) {
		setupTimer();
	}

	private void setupTimer() {
	    Log.i(TAG, "setupTimer()");
	    final AndroidComponent myself = this;
		new CountDownTimer(5000, 1000) {
			@Override
			public void onFinish() {
				for (AndroidAction a : actions) {
					if (a instanceof CallComponentAction) {
						((CallComponentAction)a).setCalledComponent(trigger);
					} else if (a instanceof UseComponentResultAction) {
						((UseComponentResultAction)a).setCalledComponent(trigger);
						((UseComponentResultAction)a).setResult(result);
					}
					a.execute();
					if (a instanceof CallComponentAction) {
						if (a.getProperties().containsKey("resultType")) {
							Log.i(TAG, "Saving result.");
							result = new HashMap<String, Object>();
							result.put("resultType", (String)a.getProperties().get("resultType"));
							result.put("result", a.getProperties().get("result"));
						}
					}
				}
				setupTimer();
			}

			@Override
			public void onTick(long millisUntilFinished) {
				Toast.makeText(parent.getBaseContext(), "Tick.", Toast.LENGTH_SHORT).show();
			}
			
		}.start();
	}
}
