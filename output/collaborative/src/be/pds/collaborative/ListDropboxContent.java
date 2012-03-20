package be.pds.collaborative;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;

public class ListDropboxContent extends AsyncTask<Void, Long, Boolean> {

	private final String TAG = getClass().getName();
	
	private Context context;
	private DropboxAPI<?> api;
	private String path;
	
	private ArrayList<String> directoryContent;
	private ListView content;
	
	private String errorMessage;
	
	public ListDropboxContent(Context context, DropboxAPI<?> api, String path, ListView dirContent) {
		this.context = context.getApplicationContext();
		this.api = api;
		this.path = path;
		this.content = dirContent;
		this.directoryContent = new ArrayList<String>();
	}
	
	@Override
	protected Boolean doInBackground(Void... params) {
		try {
			Log.i("TAG", "Getting directory content of " + path);
			Entry dirent = api.metadata("/", 1000, null, true, null);

			if (!dirent.isDir || dirent.contents == null) {
				errorMessage = "File or empty directory";
				return false;
			}
			
			for (Entry e: dirent.contents) {
				//TODO: recursion
				if (!e.isDeleted) {
					if (e.isDir) {
						// Go into directory
						for (Entry en : e.contents) {
							if (!en.isDir) {
								Log.i(TAG, "Found file: " + en.fileName());
							}
						}
					} else {
						// e is a file
						directoryContent.add(e.fileName());
						//Log.i(TAG, "Found file: " + e.fileName());
					}
				}
			}
			return true;
		} catch (DropboxException e) {
			errorMessage = e.getMessage();
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (result) {
			String[] values = (String[])directoryContent.toArray(new String[directoryContent.size()]);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context,
					android.R.layout.simple_list_item_1, values);
			content.setAdapter(adapter);
		} else {
			showToast(errorMessage);
		}
	}
	
	private void showToast(String msg) {
		Toast error = Toast.makeText(context, msg, Toast.LENGTH_LONG);
		error.show();
	}
	
}
