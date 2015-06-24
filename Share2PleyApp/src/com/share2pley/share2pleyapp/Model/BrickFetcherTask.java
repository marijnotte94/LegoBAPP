package com.share2pley.share2pleyapp.Model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.share2pley.share2pleyapp.ClearActivity;

@SuppressWarnings("deprecation")
public class BrickFetcherTask extends AsyncTask<String, String, Void> {
	private final Context mContext;
	private final Activity mActivity;
	private final int mPageNumber;
	private final ProgressDialog mLoadingDialog;
	public static final String ARG_PAGE = "page";

	public BrickFetcherTask(Activity activity, Context context, int pageNumber) {
		mContext = context.getApplicationContext();
		mPageNumber = pageNumber;
		mActivity = activity;
		mLoadingDialog = new ProgressDialog(activity);
	}

	@Override
	protected void onPreExecute() {
		mLoadingDialog.show();
		super.onPreExecute();
	}

	@Override
	/**
	 * Fetch the bricks from an URL
	 * Is opens a connection and starts a XML parser to read the values
	 * The list will also be shortened, to contain just 30 values.
	 */
	protected Void doInBackground(String... uri) {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response;
		try {
			HttpGet request = new HttpGet(uri[0]);
			response = client.execute(request);
			InputStream in = response.getEntity().getContent();
			XMLPullParserHandler parser = new XMLPullParserHandler();
			List<Brick> bricks = parser.parseBricks(in);
			List<Brick> resList = getLessBricks(bricks);
			SetLab.get(mContext).getSet(mPageNumber).setBricks(resList);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	/**
	 * After all the bricks are fetched, start new clearActivity
	 */
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Intent i = new Intent(mContext, ClearActivity.class);
		i.putExtra(ARG_PAGE, mPageNumber);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		mLoadingDialog.dismiss();
		mContext.startActivity(i);
		mActivity.finish();
	}

	/**
	 * This method is used to shorter the original brick list. It picks 30
	 * values from
	 * 
	 * @param bricks
	 *            and puts them in a new list.
	 * @return this list
	 */
	public List<Brick> getLessBricks(List<Brick> bricks) {
		List<Brick> resList = new ArrayList<Brick>();
		for (int i = 0; i < 30; i++) {
			Random dice = new Random();
			int n = dice.nextInt(bricks.size());
			Brick brick = bricks.get(n);
			brick.setBitmapFromUrl(brick.getSource());
			resList.add(brick);
			bricks.remove(brick);
		}
		return resList;
	}
}
