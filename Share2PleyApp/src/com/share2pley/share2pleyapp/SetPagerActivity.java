package com.share2pley.share2pleyapp;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.share2pley.share2pleyapp.Model.Set;
import com.share2pley.share2pleyapp.Model.SetLab;

/**
 * 
 * @author Richard Vink - 4233867. This class provides the swiping between the
 *         sets, so to choose the proper set.
 */
public class SetPagerActivity extends FragmentActivity {
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
	}

	private class SetPagerAdapter extends FragmentPagerAdapter {
		// private final DBHelper mDBHelper;
		// private final ArrayList<DatabaseSetHelper> mSets;
		// private final ArrayList<DatabaseSetHelper> mSetsChoosed;
		private final List<Set> mSets;

		public SetPagerAdapter(FragmentManager fragmentManager) {
			super(fragmentManager);
			mSets = SetLab.get(getBaseContext()).getSetList();
			// mDBHelper = new DBHelper(getBaseContext());
			// mSets = mDBHelper.getSetData();
			// mSetsChoosed = new ArrayList<DatabaseSetHelper>();
			// for (DatabaseSetHelper set : mSets) {
			// if (set.getSolved() == 0) {
			// mSetsChoosed.add(set);
			// }
			// }
		}

		@Override
		public Fragment getItem(int pos) {
			return SetPagerFragment.create(pos, getBaseContext());
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
