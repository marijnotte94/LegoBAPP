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

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.share2pley.share2pleyapp.DBHelper;
import com.share2pley.share2pleyapp.NameActivity;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
@SuppressWarnings("deprecation")
public class SetFetcherTask extends AsyncTask<String, String, Void> {
	private final Context mContext;
	private final Activity mActivity;
	private final ProgressDialog mLoadingDialog;
	public final String TAG = "BricksetFetchr";

	public SetFetcherTask(Activity activity, ProgressDialog loadingDialog,
			Context context) {
		mActivity = activity;
		mContext = context;
		mLoadingDialog = loadingDialog;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... uri) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		try {
			HttpPost request = new HttpPost(uri[0]);
			List<NameValuePair> nameValuePairs = setNameValuePairs();
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = client.execute(request);
			InputStream in = response.getEntity().getContent();
			XMLPullParserHandler parser = new XMLPullParserHandler();
			List<Set> sets = parser.parseSets(in);
			setSetLabSets(sets);
		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		if (mLoadingDialog.isShowing()) {
			Intent i = new Intent(mContext, NameActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			mContext.startActivity(i);
			mLoadingDialog.dismiss();
		}
		super.onPostExecute(result);
	}

	/**
	 * Adds all parameters to use for the HTTP POST connection
	 * 
	 * @param uri
	 */
	public List<NameValuePair> setNameValuePairs() {
		String[] notUsed = { "query", "theme", "subtheme", "setNumber", "year",
				"wanted", "orderBy", "pageSize", "pageSize", "pageNumber",
				"userName" };
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(13);
		nameValuePairs.add(new BasicNameValuePair("apiKey", "hTsg-Vmye-dO8e"));
		nameValuePairs.add(new BasicNameValuePair("userHash", "ec7fnW1{M4"));
		nameValuePairs.add(new BasicNameValuePair("owned", "true"));
		for (int i = 0; i < notUsed.length; i++) {
			nameValuePairs.add(new BasicNameValuePair(notUsed[i], ""));
		}
		return nameValuePairs;
	}

	/**
	 * Checks in the local database, if a set is the same as in the fetched set
	 * list, do not add it to SetLab. Else add them to SetLab
	 * 
	 * @param sets
	 */
	public void setSetLabSets(List<Set> sets) {
		List<Set> returnSets = sets;
		DBHelper dbHelper = new DBHelper(mContext);
		if (dbHelper.setsNumberOfRows() > 0) {
			ArrayList<Set> deletionList = new ArrayList<Set>();
			for (int i = 0; i < sets.size(); i++) {
				Set deleteSet = sets.get(i);
				for (Set s : dbHelper.getSetData()) {
					if (s.getFullId().equals(deleteSet.getFullId())) {
						deletionList.add(deleteSet);
					}
				}
			}
			for (int i = 0; i < deletionList.size(); i++) {
				returnSets.remove(deletionList.get(i));
			}
			SetLab.get(mContext).setAllSets(returnSets);
		} else {
			SetLab.get(mContext).setAllSets(sets);
		}
	}
}
