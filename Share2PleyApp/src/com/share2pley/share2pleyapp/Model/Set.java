package com.share2pley.share2pleyapp.Model;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.share2pley.share2pleyapp.R;

/**
 * 
 * @author Marijn Otte - ?
 * 
 */
public class Set {
	private final int mIndex;
	private final ArrayList<Brick> mBricks;
	private final Context mContext;
	private final TypedArray mImgs;
	private final TypedArray mFigurines;
	private final TypedArray mProcesses;
	private TypedArray mSetsArray;

	public Set(int setNumber, Context context) {
		mContext = context;
		mIndex = setNumber;
		mBricks = new ArrayList<Brick>();

		mImgs = mContext.getResources().obtainTypedArray(R.array.models_imgs);
		mContext.getResources().obtainTypedArray(R.array.models_imgs).recycle();
		mFigurines = mContext.getResources().obtainTypedArray(R.array.figurines_imgs);
		mContext.getResources().obtainTypedArray(R.array.figurines_imgs).recycle();
		mProcesses = mContext.getResources().obtainTypedArray(R.array.processes_imgs);
		mContext.getResources().obtainTypedArray(R.array.processes_imgs).recycle();
		
		// set the mSetsArray to the proper set
		mSetsArray = getModelArrayResource(mIndex);

		addBricks();
	}

	// check if there is a next instruction
	public boolean hasNext(int ins) {
		if (ins <= mBricks.size() - 2) {
			return true;
		}
		return false;
	}

	// check if there is a previous instruction
	public boolean hasPrevious(int ins) {
		if (ins == 0) {
			return false;
		}
		return true;
	}

	public void addBrick(int source, int amount) {
		mBricks.add(new Brick(source, amount, mContext));
	}

	public Brick getBrick(int index) {
		return mBricks.get(index);
	}

	/**
	 * This method used String.split to split the amount of the filename It
	 * combines the amount with the brick picture to add each brick
	 */
	public void addBricks() {
		for (int i = 0; i < mSetsArray.length(); i++) {
			String resource = mContext.getResources().getResourceName(
					mSetsArray.getResourceId(i, -1));
			String splitted[] = resource.split("_");
			String amountString = splitted[1];
			int amount = Integer.parseInt(amountString);
			addBrick(mSetsArray.getResourceId(i, -1), amount);
		}
	}

	public int getAmountBricks() {
		return mBricks.size();
	}

	public int getModelImageResource(int index) {
		return mImgs.getResourceId(index, -1);
	}

	public int getFigurineImageResource(int index) {
		return mFigurines.getResourceId(index / 2, -1);
	}
	
	public Drawable getProgressImageResource(int index) {
		return mContext.getResources().getDrawable(mProcesses.getResourceId(index,-1));
	}

	public TypedArray getModelArrayResource(int mSetIndex) {
		switch (mSetIndex) {
		case 0:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.speelhuis_imgs);
			mContext.getResources().obtainTypedArray(R.array.speelhuis_imgs)
					.recycle();
			break;
		case 1:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.hetgrotebos_imgs);
			mContext.getResources().obtainTypedArray(R.array.hetgrotebos_imgs)
					.recycle();
			break;
		case 2:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.fietswinkel_imgs);
			mContext.getResources().obtainTypedArray(R.array.fietswinkel_imgs)
					.recycle();
			break;
		case 3:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.speelgoedwinkel_imgs);
			mContext.getResources()
					.obtainTypedArray(R.array.speelgoedwinkel_imgs).recycle();
			break;
		case 4:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.jungle_imgs);
			mContext.getResources().obtainTypedArray(R.array.jungle_imgs)
					.recycle();
			break;
		case 5:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.emma_imgs);
			mContext.getResources().obtainTypedArray(R.array.emma_imgs)
					.recycle();
			break;
		case 6:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.helikopter_imgs);
			mContext.getResources().obtainTypedArray(R.array.helikopter_imgs)
					.recycle();
			break;
		case 7:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.sloopterrein_imgs);
			mContext.getResources().obtainTypedArray(R.array.sloopterrein_imgs)
					.recycle();
			break;
		case 8:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.vuurleeuw_imgs);
			mContext.getResources().obtainTypedArray(R.array.vuurleeuw_imgs)
					.recycle();
			break;
		case 9:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.drilklauw);
			mContext.getResources().obtainTypedArray(R.array.drilklauw)
					.recycle();
			break;
		case 10:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.tie_imgs);
			mContext.getResources().obtainTypedArray(R.array.tie_imgs)
					.recycle();
			break;
		case 11:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.atdp_imgs);
			mContext.getResources().obtainTypedArray(R.array.atdp_imgs)
					.recycle();
			break;
		case 12:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.craftingkist_imgs);
			mContext.getResources().obtainTypedArray(R.array.craftingkist_imgs)
					.recycle();
			break;
		case 13:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.enderdraak_imgs);
			mContext.getResources().obtainTypedArray(R.array.enderdraak_imgs)
					.recycle();
			break;
		case 14:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.bouwploeg_imgs);
			mContext.getResources().obtainTypedArray(R.array.bouwploeg_imgs)
					.recycle();
			break;
		case 15:
			mSetsArray = mContext.getResources().obtainTypedArray(
					R.array.offroader_imgs);
			mContext.getResources().obtainTypedArray(R.array.offroader_imgs)
					.recycle();
			break;
		}
		return mSetsArray;
	}

	@Override
	public String toString() {
		return mIndex + "";
	}
}
