package be.pds.thesis;

import android.app.Activity;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class AndroidComponent {
	
	protected List<AndroidAction> actions;
	protected Activity parent;
	private HashMap<String, Object> result;

	public AndroidComponent() {
		actions = new ArrayList<AndroidAction>();
	}

	public AndroidComponent(Activity parent) {
		actions = new ArrayList<AndroidAction>();
		this.parent = parent;
	}

	public void addAction(AndroidAction a) {
		actions.add(a);
	}

	public Activity getParent() {
		return this.parent;
	}

	abstract public void action(HashMap<String, Object> properties);
	
	abstract public void onMessage(final String message);
}

