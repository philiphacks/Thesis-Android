package be.pds.thesis;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.Semaphore;

import net.tootallnate.websocket.WebSocketClient;
import net.tootallnate.websocket.drafts.Draft_10;
import android.app.Activity;
import android.util.Log;
import be.pds.collaborative.ChatActivity;
import be.pds.collaborative.ChatComponent;
import be.pds.collaborative.DropboxItemActivity;
import be.pds.collaborative.MainActivity;
import be.pds.thesis.User.Role;

import com.google.gson.Gson;

public class ServerConnection {

	private final String TAG = getClass().getName();
	private static final String CHAT_MESSAGE = "chat";
	private static final String DROPBOX_NOTE = "dropbox";
	private static final String AUTH_MESSAGE = "authed";
	private static final String SESSION_MESSAGE = "users";
	private static final String ERROR_MESSAGE = "error";
	private static final String GENERIC_CHILD_MESSAGE = "genchild";
	private final Semaphore available = new Semaphore(1, true);
	
	private static ServerConnection INSTANCE;
	
	private static WebSocketClient cc;
	private static boolean connected;
	private static boolean authed;
	private static String host;
	private static String port;
	private static User user;
	
	// Represent the different components that need updates from the server
	// e.g. "chat" -> ChatComponent
	//		"comment" -> DropboxComponent
	//		"text" -> GoalsComponent etc.
	private static HashMap<String, Activity> components;

	private ServerConnection() { }
		
	public static synchronized ServerConnection getInstance() {
		if (null == INSTANCE) {
			System.setProperty("java.net.preferIPv6Addresses", "false");
			INSTANCE = new ServerConnection();
			host = "10.0.2.2";
			port = "3000";
			connected = false;
			authed = false;
			components = new HashMap<String, Activity>();
		}

		return INSTANCE;
	}

	public void connect() {
		String location = "ws://" + host + ":" + port;
		try {
			cc = new WebSocketClient(new URI(location), new Draft_10()) {

				public void onMessage(String message) {
					// Parse message (JSON) and execute command
					JsonObj m = new Gson().fromJson(message, JsonObj.class);
					if (m == null) {
						return;
					}
					Log.i(TAG, "GOT MESSAGE: " + message);
					String type = m.getMessage().getType();
					
					if (type.equals(AUTH_MESSAGE)) {
						authed = true;
						user = new User(m.getMessage().getUser(), Role.ADMIN);
						Log.i(TAG, "Authentication successful.");
						available.release();						
					} else if (type.equals(CHAT_MESSAGE)) {
						Log.i(TAG, "Got new chat message");
						String msg = m.getMessage().getUser() + ": " + m.getMessage().getMessage() + "\n";
						ChatComponent comp = ((ChatActivity) components.get(type)).getComponent();
						comp.onMessage(msg);
					} else if (type.equals(DROPBOX_NOTE)) {
						Log.i(TAG, "Got new dropbox message");
						DropboxItemActivity comp = (DropboxItemActivity) components.get(type);
						comp.addNote(m.getMessage().getUser(), m.getMessage().getMessage());
					} else if (type.equals(SESSION_MESSAGE)) {
						Log.i(TAG, "Got new session info");
						MainActivity comp = (MainActivity) components.get(type);
						if (comp != null) {
							comp.setConnectedUsers(m.getMessage().getMessage() + "\n");
						}
					} else if (type.equals(ERROR_MESSAGE)) {
						Log.i(TAG, "Got new error message");
					} else if (type.equals(GENERIC_CHILD_MESSAGE)) {
						String msg = m.getMessage().getMessage() + "\n";
						GenericActivity comp = (GenericActivity) components.get(m.getMessage().getGenericType());
						comp.onMessage(m.getMessage().getUser(), msg);
					} else {
						Log.i(TAG, "Got new generic note with type " + type);
						try {
							String msg = m.getMessage().getMessage() + "\n";
							AndroidComponent comp = ((GenericActivity) components.get(type)).getComponent();
							comp.onMessage(msg);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
					}
					Log.i(TAG, message);
				}

				public void onOpen() {
					ServerConnection.connected = true;
					Log.i("ServerConnection", "Connected to server!");
				}

				public void onClose() {
					ServerConnection.connected = false;
					Log.i("ServerConnection", "Disconnected from server!");
				}

				public void onError(Exception ex) {
					ServerConnection.connected = false;
					Log.i(TAG, "Connection refused.. Got error");
					//ex.printStackTrace();
				}
			};

			cc.connect();
		} catch (URISyntaxException ex) { }
	}

	public void sendMessage(String message) {
		if (ServerConnection.connected) {
			try {
				Log.i("ServerConnection", "Sending new message: " + message);
				cc.send(message);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void quit() {
		String message = "{\"quit\" : { \"user\" : \"phil\" } }";
		sendMessage(message);
		authed = false;
	}
	
	public void addComponent(String name, Activity component) {
		components.put(name, component);
	}
	
	public void setHost(String h) {
		ServerConnection.host = h;
	}

	public void setPort(String p) {
		ServerConnection.port = p;
	}
	
	public String getUser() {
		return user.getUser();
	}
	
	public boolean isAdminConnection() {
		return user.isAdmin();
	}

	public boolean isConnected() {
		return ServerConnection.connected;
	}

	public boolean checkServerProperty(String property) {
		if (property.equals("LoginProperty")) {
			sendMessage("{\"authed\" : { \"user\" : \"" + user + "\" } }");
			try {
				available.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Log.i("ServerConnection", "returning " + authed);
			return authed;
		}
		return false;
	}
	
	public void getLastMessages() {
		sendMessage("{ \"lastMessages\" : { \"user\" : \"" + user + "\" } }");
	}
	
	public void addDropboxNote(String file, String note) {
		String json = "{\"addFileNote\" : { \"file\" : \"" + file + "\", \"user\" : \"" + user + "\", \"text\" : \"" + note + "\" } }";
		sendMessage(json);
	}
	
	public void getDropboxNotes(String file) {
		sendMessage("{ \"getFileNotes\" : { \"file\" : \"" + file + "\" } }");
	}
	
	public void addGenericNote(HashMap<String, Object> properties) {
		// The hashmap should contain the following keys:
		// type, parent, text
		String type = (String)properties.get("type");
		String parent = (String)properties.get("parent");
		String text = (String)properties.get("text");
		String json = "{\"addGenericNote\" : { \"type\" : \"" + type + "\", \"parent\" : \"" + parent + "\", \"user\" : \"" + user + "\", \"text\" : \"" + text + "\" } }";
		sendMessage(json);
	}
	
	public void addGenericNote(String type, String parent, String text) {
		String json = "{\"addGenericNote\" : { \"type\" : \"" + type + "\", \"parent\" : \"" + parent + "\", \"user\" : \"" + user + "\", \"text\" : \"" + text + "\" } }";
		sendMessage(json);
	}
	
	public void getGenericNotes(HashMap<String, Object> properties) {
		String type = (String)properties.get("type");
		String parent = (String)properties.get("parent");
		
		String json = "{\"getGenericNotes\" : { \"type\" : \"" + type + "\", \"parent\" : \"" + parent + "\" } }";
		sendMessage(json);
	}
	
	public void getGenericNotes(String type, String parent) {
		String json = "{\"getGenericNotes\" : { \"type\" : \"" + type + "\", \"parent\" : \"" + parent + "\" } }";
		sendMessage(json);
	}
	
	public void getConnectedUsers() {
		String json = "{\"getConnectedUsers\" : { \"type\" : \"users\" } }";
		sendMessage(json);
	}
	
	public static class JsonObj {
		private Message message;
		
		public void setMessage(Message m) {
			this.message = m;
		}
		
		public Message getMessage() {
			return this.message;
		}
		
		public static class Message {
			private String type;
			private String genericType;
			private String file;
			private String message;
			private String user;

			public String getType() {
				return this.type;
			}
			
			public void setType(String type) {
				this.type = type;
			}
			
			public String getGenericType() {
				return this.genericType;
			}
			
			public void setGenericType(String type) {
				this.genericType = type;
			}
			
			public String getMessage() {
				return this.message;
			}

			public void setMessage(String message) {
				this.message = message;
			}

			public String getUser() {
				return this.user;
			}
			
			public void setUser(String user) {
				this.user = user;
			}
			
			public String getFile() {
				return this.file;
			}
			
			public void setFile(String file) {
				this.file = file;
			}
		}
	}
	
	public String removeSpaces(String s) {
		StringTokenizer st = new StringTokenizer(s," ",false);
		String t="";
		while (st.hasMoreElements()) t += st.nextElement();
		return t;
	}
}
