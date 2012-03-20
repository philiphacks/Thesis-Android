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

public class DropboxActivity extends Activity {

	private DropboxComponent dropbox;

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dropbox);
			dropbox = new DropboxComponent(this);
			Button dropboxButton = (Button) this.findViewById(R.id.dropboxButton);
			Button dirContentButton = (Button) this.findViewById(R.id.dirContentButton);
			TextView contentText = (TextView) this.findViewById(R.id.contentText);
			ListView dirContent = (ListView) this.findViewById(R.id.dirContent);

    }


	
}
