package be.pds.collaborative;

import be.pds.thesis.*;

import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

public class ChatActivity extends Activity {

	private ChatComponent chat;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
			chat = new ChatComponent(this);
			ScrollView chatBox = (ScrollView) this.findViewById(R.id.scrollChat);
			LayoutType childLayout = (LayoutType) this.findViewById(R.id.linearLayout1);

    }


	
}
