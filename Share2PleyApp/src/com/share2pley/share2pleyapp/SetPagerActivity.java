package com.share2pley.share2pleyapp;

import java.util.ArrayList;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

/**
 * 
 * @author richard
 * This class provides the swiping between the sets, so to choose the proper set.
 */
public class SetPagerActivity extends FragmentActivity {
	private static final int NUM_PAGES = 12;

	private ViewPager mViewPager;
	private ArrayList<Set> mSets;
	private PagerAdapter mPagerAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager_set);

		//Initiate Viewpager and PagerAdapter
		mViewPager = (ViewPager)findViewById(R.id.pager);
		mPagerAdapter = new SetPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mPagerAdapter);
		//mSets = SetLab.get().getSetList();

		FragmentManager fm = getSupportFragmentManager();
	}

	private class SetPagerAdapter extends FragmentStatePagerAdapter {

		public SetPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
		}

		@Override
		public Fragment getItem(int pos) {
			return SetPagerFragment.create(pos);
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
}

