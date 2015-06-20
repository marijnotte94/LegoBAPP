package com.share2pley.share2pleyapp.Model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class HttpAsyncTask extends AsyncTask<String, String, String> {
	private TextView t;
	private final Context mContext;
	private final String result = "fail";
	public final String TAG = "BricksetFetchr";

	public HttpAsyncTask(Context context) {
		mContext = context;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(String... uri) {
		String[] notUsed = { "query", "theme", "subtheme", "setNumber", "year",
				"wanted", "orderBy", "pageSize", "pageSize", "pageNumber",
				"userName" };
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		String responseString = null;
		try {
			HttpPost request = new HttpPost(uri[0]);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					13);
			nameValuePairs.add(new BasicNameValuePair("apiKey",
					"hTsg-Vmye-dO8e"));
			nameValuePairs
					.add(new BasicNameValuePair("userHash", "ec7fnW1{M4"));
			nameValuePairs.add(new BasicNameValuePair("owned", "true"));
			for (int i = 0; i < notUsed.length; i++) {
				nameValuePairs.add(new BasicNameValuePair(notUsed[i], ""));
			}
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = client.execute(request);
			InputStream in = response.getEntity().getContent();
			XMLPullParserHandler parser = new XMLPullParserHandler();
			List<PrototypeSet> sets = parser.parse(in);
			PrototypeSetLab.get(mContext).setAllSets(sets);
		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return "japie";
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}
}
