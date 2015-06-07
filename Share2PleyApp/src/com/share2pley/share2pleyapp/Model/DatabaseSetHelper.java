package com.share2pley.share2pleyapp.Model;

public class DatabaseSetHelper {

	private int mIndex;
	private int mSolved;

	public DatabaseSetHelper(int index, int solved) {
		mIndex = index;
		mSolved = solved;
	}

	public int getIndex() {
		return mIndex;
	}

	public int getSolved() {
		return mSolved;
	}

	public void setIndex(int index) {
		mIndex = index;
	}

	public void setSolved(int solved) {
		mSolved = solved;
	}

	@Override
	public String toString() {
		return mIndex + " " + mSolved;
	}

}
