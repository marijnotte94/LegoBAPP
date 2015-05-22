package com.share2pley.share2pleyapp.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Photo {
	private static final String JSON_FILENAME = "filename";
	
	private String mFilename;
	
	public Photo(String filename) {
		mFilename = filename;
	}
	
	public Photo(JSONObject json) throws JSONException {
		mFilename = json.getString(JSON_FILENAME);
	}
	
	public JSONObject toJSJON() throws JSONException {
		JSONObject json = new JSONObject();
		json.put(JSON_FILENAME, mFilename);
		return json;
	}
	
	public String getFileName() {
		return mFilename;
	}
}
