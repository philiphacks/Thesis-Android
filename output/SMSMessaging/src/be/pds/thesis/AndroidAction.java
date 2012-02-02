package be.pds.thesis;

import java.util.HashMap;

public abstract class AndroidAction {
	
	protected HashMap<String, Object> properties;

	public AndroidAction() {
		this.properties = new HashMap<String, Object>();
	}

	public AndroidAction(HashMap<String, Object> properties) {
		this.properties = properties;
	}
	
	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}

	abstract public void execute();

}
