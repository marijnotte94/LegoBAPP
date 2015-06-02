package com.share2pley.share2pleyapp.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;

import com.share2pley.share2pleyapp.R;

public class Set {
	public String mName;
	private ArrayList<Brick> mBricks;
	private Context mContext;
	private TypedArray mImgs;
	private TypedArray mFigurines;

	public Set(String name, Context context){
		mName = name;
		mContext = context;
		mBricks = new ArrayList<Brick>();
		mImgs = context.getResources().obtainTypedArray(R.array.models_imgs);
		context.getResources().obtainTypedArray(R.array.models_imgs).recycle();
		mFigurines = context.getResources().obtainTypedArray(R.array.figurines_imgs);
		context.getResources().obtainTypedArray(R.array.figurines_imgs).recycle();
		addBricks();
	}

	//check if there is a next instruction
	public boolean hasNext(int ins){
		if(ins <= mBricks.size()-2){
			return true;
		}
		return false;
	}
	//check if there is a previous instruction
	public boolean hasPrevious(int ins){
		if(ins == 0){
			return false;
		}
		return true;
	}

	public void addBrick(int source, int amount){
		mBricks.add(new Brick(source, amount, mContext));
	}

	public Brick getBrick(int index) {
		return mBricks.get(index);
	}

	public void addBricks(){
		for (int i = 0; i < mImgs.length(); i++) {
			addBrick(getModelImageResource(i),3);
		}
	
	
	
//		final R.drawable drawableResources = new R.drawable();
//		final Class<R.drawable> c = R.drawable.class;
//		final Field[] fields = c.getDeclaredFields();
//
//		for (int j = 0, max = fields.length; j < max; j++) {
//			final int resourceId;
//			try {
//				resourceId = fields[j].getInt(drawableResources);
//				String resourceName = mContext.getResources().getString(resourceId);
//				if(resourceName.contains(mName)){
//					int amount = Character.getNumericValue(resourceName.charAt(resourceName.length()-5));	 
//					addBrick(resourceId, amount);
//				}
//			} catch (Exception e) {
//				continue;
//			}
//		}
	}
	
	public int getAmountBricks(){
		return mBricks.size();
	}
	
	public int getModelImageResource(int index) {
		return mImgs.getResourceId(index, -1);
	}
	
	public int getFigurineImageResource(int index) {
		return mFigurines.getResourceId(index/2, -1);
	}
}
