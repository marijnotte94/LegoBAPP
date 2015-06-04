package com.share2pley.share2pleyapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class ReferenceActivity extends Activity {
	private ImageView mLegoCity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_references);

		mLegoCity = (ImageView) findViewById(R.id.imageView_references_legoCity);
		mLegoCity.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final String legoCityName = "com.lego.city.my_city";
				Intent i = new Intent("com.lego.city.my_city");
				if (i.equals(null)) {
					try {
						startActivity(new Intent(Intent.ACTION_VIEW, Uri
								.parse("market://details?id=" + legoCityName)));
					} catch (android.content.ActivityNotFoundException anfe) {
						startActivity(new Intent(
								Intent.ACTION_VIEW,
								Uri.parse("https://play.google.com/store/apps/details?id="
										+ legoCityName)));
					}
				} else {
					startActivity(i);
				}
			}
		});

	}
}