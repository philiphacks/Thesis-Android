package be.pds.multipleactivitiestest;

import be.pds.thesis.*;

import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends Activity {


	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

    			TextView showFirst = (TextView) this.findViewById(R.id.showFirst);
    			Button toActivityTwo = (Button) this.findViewById(R.id.toActivityTwo);


			// We found actions in the gluecomponent
    }


	
}
