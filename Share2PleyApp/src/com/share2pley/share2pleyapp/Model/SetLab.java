package com.share2pley.share2pleyapp.Model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class SetLab {
	private List<Set> mSets;
	private static SetLab sSetLab;

	private SetLab(Context context) {
		mSets = new ArrayList<Set>();
	}

	/**
	 * Makes sure there is only 1 setlab for the given context
	 * 
	 * @param context
	 * @return
	 */
	public static SetLab get(Context context) {
		if (sSetLab == null) {
			sSetLab = new SetLab(context);
		}
		return sSetLab;
	}

	public List<Set> getSetList() {
		return mSets;
	}

	public int getAmount() {
		return mSets.size();
	}

	public Set getSet(int index) {
		return mSets.get(index);
	}

	public void addSet(Set set) {
		mSets.add(set);
	}

	public void setAllSets(List<Set> sets) {
		mSets = sets;
	}

	public void removeSet(Set set) {
		mSets.remove(set);
	}

}

