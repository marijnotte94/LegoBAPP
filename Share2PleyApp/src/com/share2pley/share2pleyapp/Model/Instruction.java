package com.share2pley.share2pleyapp.Model;

import android.content.res.ColorStateList;

public class Instruction {

	private String color;
	private int amount;
	
	
	public Instruction(String mColor, int mAmount){
		color = mColor;
		amount = mAmount;
	}
	
	public String toString(){
		return "Put " + amount + " " + color + " pieces in the bag";
	}
	
	public int getAmount(){
		return amount;
	}
	
	public void setAmount(int newamount){
		amount = newamount;
	}
	
	public String getColor(){
		return color;
	}

	public int setForeGround(String color) {
		if(color.equals("Red")){
			return 0xFFff0000;
		}
		else if(color.equals("Green")){
			return 0xFF008000;
		}
		else if(color.equals("Grey")){
			return 0xFF808080;
		}
		else if(color.equals("Yellow")){
			return 0xFFFFFF00;
		}
		else if(color.equals("Gold")){
			return 0xFFFFD700;
		}
		else if(color.equals("Black")){
			return 0xFF000000;
		}
		else if(color.equals("Blue")){
			return 0xFF0000FF;
		}
		else if(color.equals("White")){
			return 0xFFFFFFFF;
		}
		else{
			return 0xFF106097;
		}
	
	}

	public int setBackGround(String color) {
		if(color.equals("Yellow") || color.equals("White")){
			return 0xFF000000;
		}
		else{
			return 0xFFFFFFFF;
		}
	}
}
