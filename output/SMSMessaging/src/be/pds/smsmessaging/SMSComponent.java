package be.pds.smsmessaging;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import be.pds.thesis.*;

public class SMSComponent extends AndroidComponent {

	private final String TAG = getClass().getName();
	private HashMap<String, Object> properties;
	private EditText editPhoneNr;
	private EditText editMessage;
	private Button sendMessage;

	public SMSComponent(Activity a) {
		super(a);
		editPhoneNr = (EditText) this.parent.findViewById(R.id.txtPhoneNo);
		editMessage = (EditText) this.parent.findViewById(R.id.txtMessage);
		sendMessage = (Button) this.parent.findViewById(R.id.btnSendSMS);
		setupSMS();
	}

	public EditText geteditPhoneNr() {
		return editPhoneNr;
	}
	public EditText geteditMessage() {
		return editMessage;
	}
	public Button getsendMessage() {
		return sendMessage;
	}

	public void action(HashMap<String, Object> properties) {
		Log.i(TAG, "[SMSComponent] sending SMS to " + (String)properties.get("phoneNr"));
		sendSMS((String)properties.get("phoneNr"), (String)properties.get("message"));
	}

	private void setupSMS() {
		final AndroidComponent myself = this;
			properties = new HashMap<String, Object>();
					sendMessage.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
									// TODO: make a class out of this?
									properties.put("message", editMessage.getText().toString());
									// TODO: make a class out of this?
									properties.put("phoneNr", editPhoneNr.getText().toString());
									CallComponentAction cca = new CallComponentAction(properties);
									cca.setCalledComponent(myself);
									cca.execute();
						}
					});
	}

	private void sendSMS(String phoneNumber, String message) {
		String SENT = "SMS_SENT";
		String DELIVERED = "SMS_DELIVERED";

		PendingIntent sentPI = PendingIntent.getBroadcast(this.parent, 0,
				new Intent(SENT), 0);
		PendingIntent deliveredPI = PendingIntent.getBroadcast(this.parent, 0,
				new Intent(DELIVERED), 0);

		//---when the SMS has been sent---
		this.parent.registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context arg0, Intent arg1) {
				switch (getResultCode()) {
					case Activity.RESULT_OK:
						Toast.makeText(parent.getBaseContext(), "SMS sent",
								Toast.LENGTH_SHORT).show();
						break;
					case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
						Toast.makeText(parent.getBaseContext(), "Generic failure", 
								Toast.LENGTH_SHORT).show();
					    break;
					case SmsManager.RESULT_ERROR_NO_SERVICE:
						Toast.makeText(parent.getBaseContext(), "No service", 
					    		Toast.LENGTH_SHORT).show();
					    break;
					case SmsManager.RESULT_ERROR_NULL_PDU:
						Toast.makeText(parent.getBaseContext(), "Null PDU", 
								Toast.LENGTH_SHORT).show();
					    break;
					case SmsManager.RESULT_ERROR_RADIO_OFF:
						Toast.makeText(parent.getBaseContext(), "Radio off", 
								Toast.LENGTH_SHORT).show();
					    break;
					default:
						Toast.makeText(parent.getBaseContext(), "Fail.", 
								Toast.LENGTH_SHORT).show();
						break;
				}
			}
			
		}, new IntentFilter(SENT));

		//---when the SMS has been delivered---
		this.parent.registerReceiver(new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				switch (getResultCode()) {
					case Activity.RESULT_OK:
						Toast.makeText(parent.getBaseContext(), "SMS delivered",
								Toast.LENGTH_SHORT).show();
						break;
					case Activity.RESULT_CANCELED:
						Toast.makeText(parent.getBaseContext(), "SMS not delivered",
								Toast.LENGTH_SHORT).show();
						break;
				}
			}
			
		}, new IntentFilter(DELIVERED));

		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
	}
}