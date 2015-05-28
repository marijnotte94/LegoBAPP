package com.share2pley.share2pleyapp.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.share2pley.share2pleyapp.R;


public class Brick {

	private int mSource;
	private int mAmount;
	//private Bitmap bm;
	
	
	public Brick(int source, int amount, Context context){
		mSource = source;
		mAmount = amount;
		//bm = BitmapFactory.decodeResource(context.getResources(), source);
	}
	
	public int getAmount(){
		return mAmount;
	}
	
	public int getSource(){
		return mSource;
	}
	
	public void setAmount(int newamount){
		mAmount = newamount;
	}
	
	public String toString(){
		return mAmount + "x";
	}
	

	
	
	

	
	
}
