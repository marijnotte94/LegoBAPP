package com.share2pley.share2pleyapp.Model;

import android.content.res.ColorStateList;

public class Brick {

	private int source;
	private int amount;
	
	
	public Brick(int mSource, int mAmount){
		source = mSource;
		amount = mAmount;
	}
	
	public int getAmount(){
		return amount;
	}
	
	public int getSource(){
		return source;
	}
	
	public void setAmount(int newamount){
		amount = newamount;
	}
	
	public String toString(){
		return amount + "x";
	}
	
	
	

	
	
}
