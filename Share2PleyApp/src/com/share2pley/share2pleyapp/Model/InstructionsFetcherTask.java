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

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.share2pley.share2pleyapp.InstructionsDialog;

@SuppressWarnings("deprecation")
public class InstructionsFetcherTask extends AsyncTask<String, String, Void> {

	private final int mSetId;
	private final int mPageNumber;
	private final Context mContext;
	private final ProgressDialog mProgressDialog;
	private int mAmount;
	private final FragmentManager fm;

	public InstructionsFetcherTask(int setId, int pageNumber, Context context,
			ProgressDialog dialog, FragmentManager fm) {
		mSetId = setId;
		mContext = context;
		mPageNumber = pageNumber;
		mProgressDialog = dialog;
		this.fm = fm;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
	}

	@Override
	protected Void doInBackground(String... params) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		try {
			HttpPost request = new HttpPost(params[0]);
			List<NameValuePair> nameValuePairs = setNameValuePairs();
			request.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			response = client.execute(request);
			InputStream in = response.getEntity().getContent();
			XMLPullParserHandler parser = new XMLPullParserHandler();
			List<String> instructions = parser.parseInstructions(in);
			mAmount = instructions.size();
			SetLab.get(mContext).getSet(mPageNumber)
					.setInstructions(instructions);

		} catch (ClientProtocolException e) {

		} catch (IOException e) {

		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		InstructionsDialog iDialog = new InstructionsDialog();
		Bundle bundle = new Bundle();
		bundle.putInt("PAGE", mPageNumber);
		bundle.putInt("AMOUNT", mAmount);
		iDialog.setArguments(bundle);
		iDialog.show(fm, "Dialog fragment");
	}

	/**
	 * Adds all parameters to use for the HTTP POST connection
	 * 
	 * @param uri
	 */
	public List<NameValuePair> setNameValuePairs() {
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		nameValuePairs.add(new BasicNameValuePair("apiKey", "hTsg-Vmye-dO8e"));
		nameValuePairs.add(new BasicNameValuePair("setID", mSetId + ""));
		return nameValuePairs;
	}

}
