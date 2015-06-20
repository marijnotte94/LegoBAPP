package com.share2pley.share2pleyapp.Model;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

public class PrototypeSetLab {
	private List<PrototypeSet> mSets;
	private static PrototypeSetLab sSetLab;
	private final Context mContext;

	private PrototypeSetLab(Context context) {
		mContext = context;
		mSets = new ArrayList<PrototypeSet>();
	}

	public static PrototypeSetLab get(Context context) {
		if (sSetLab == null) {
			sSetLab = new PrototypeSetLab(context);
		}
		return sSetLab;
	}

	public List<PrototypeSet> getSetList() {
		return mSets;
	}

	public int getAmount() {
		return mSets.size();
	}

	public PrototypeSet getSet(int index) {
		return mSets.get(index);
	}

	public void addSet(PrototypeSet set) {
		mSets.add(set);
	}

	public void setAllSets(List<PrototypeSet> sets) {
		mSets = sets;
	}

}
