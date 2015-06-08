package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class ReferenceActivity extends Activity {
	private ImageView mDuplo;
	private ImageView mLegoCity;
	private ImageView mFriends;
	private ImageView mChima;
	private ImageView mStarwars;
	private ImageView mTechnic;
	private ReferenceActivity mReferenceActivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mReferenceActivity = this;
		setContentView(R.layout.activity_references);

		mDuplo = (ImageView) findViewById(R.id.imageView_references_duplo);
		mDuplo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setApp("com.lego.duplo.trains");
			}
		});

		mLegoCity = (ImageView) findViewById(R.id.imageView_references_legoCity);
		mLegoCity.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setApp("com.lego.city.my_city");
			}
		});

		mFriends = (ImageView) findViewById(R.id.imageView_references_friends);
		mFriends.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setApp("com.lego.friends.artmaker");
			}
		});

		mChima = (ImageView) findViewById(R.id.imageView_references_chima);
		mChima.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setApp("com.wb.speedorz");
			}
		});

		mStarwars = (ImageView) findViewById(R.id.imageView_references_starwars);
		mStarwars.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setApp("com.lego.starwars.thenewyodachronicles");
			}
		});

		mTechnic = (ImageView) findViewById(R.id.imageView_references_technic);
		mTechnic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setApp("com.lego.technic.race");
			}
		});

	}

	public void setApp(String appName) {
		try {
			Intent i = mReferenceActivity.getPackageManager()
					.getLaunchIntentForPackage(appName);
			startActivity(i);
			return;
		} catch (NullPointerException e) {
		}
		try {
			startActivity(new Intent(Intent.ACTION_VIEW,
					Uri.parse("market://details?id=" + appName)));
		} catch (android.content.ActivityNotFoundException anfe) {
			try {
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("https://play.google.com/store/apps/details?id="
								+ appName)));
			} catch (ActivityNotFoundException e) {
				Log.e("TAG", "activity not found");
			}
		}
	}

}