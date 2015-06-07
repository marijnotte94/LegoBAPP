package com.share2pley.share2pleyapp;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.share2pley.share2pleyapp.Model.DatabaseSetHelper;

/**
 * 
 * @author Richard Vink - 4233867. This class provides the swiping between the
 *         sets, so to choose the proper set.
 */
public class SetPagerActivity extends FragmentActivity {
	private static final int NUM_PAGES = 16;
	private ViewPager mViewPager;
	private PagerAdapter mPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager_set);

		// Initiate Viewpager and PagerAdapter
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mPagerAdapter = new SetPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		// mSets = SetLab.get().getSetList();
	}

	private class SetPagerAdapter extends FragmentPagerAdapter {
		private final DBHelper mDBHelper;
		private final ArrayList<DatabaseSetHelper> mSets;

		public SetPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			mDBHelper = new DBHelper(getBaseContext());
			mSets = mDBHelper.getSetData(0);
		}

		@Override
		public Fragment getItem(int pos) {
			return SetPagerFragment.create(pos);
		}

		@Override
		public int getCount() {
			return mSets.size();
		}

		@Override
		public int getItemPosition(Object object) {
			// refresh all fragments when data set changed
			return PagerAdapter.POSITION_NONE;
		}
	}
}
