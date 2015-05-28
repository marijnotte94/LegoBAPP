package com.share2pley.share2pleyapp.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.share2pley.share2pleyapp.R;

public class Set {

	private String name;

	private ArrayList<Brick> bricks;
	private Context mContext;

	public Set(String mName, Context context){
		name = mName;
		mContext = context;
		bricks = new ArrayList<Brick>();
		addBricks();
	}

	//check if there is a next instruction
	public boolean hasNext(int ins){
		if(ins <= bricks.size()-2){
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
		bricks.add(new Brick(source, amount, mContext));
	}

	public Brick getStone(int index) {
		return bricks.get(index);
	}

	public void addBricks(){
		final R.drawable drawableResources = new R.drawable();
		final Class<R.drawable> c = R.drawable.class;
		final Field[] fields = c.getDeclaredFields();

		for (int j = 0, max = fields.length; j < max; j++) {
			final int resourceId;
			try {
				resourceId = fields[j].getInt(drawableResources);
				String resourceName = mContext.getResources().getString(resourceId);
				//Log.d(resourceName, resourceName);
				if(resourceName.contains(name)){
					
					int amount = Character.getNumericValue(resourceName.charAt(resourceName.length()-5));	 
					Log.d("trlala",amount+"");
					addBrick(resourceId, amount);

				}
			} catch (Exception e) {
				continue;
			}
		}
	}

	/*
	//split instruction of one color into multiple instructions of max 10 pieces per instruction (amount is random)
	public ArrayList<Instruction> split(ArrayList<Instruction> ins) {
		ArrayList<Instruction> newins = new ArrayList<Instruction>();
		Random rand = new Random();
		for(Instruction instruction : ins){
			int amount = instruction.getAmount();
			String color = instruction.getColor();
			if(amount > max){
				while(amount > max){
					int newamount = rand.nextInt((max - 1) + 1) + 1;
					newins.add(new Instruction(color, newamount));
					amount = amount - newamount;
				}
				newins.add(new Instruction(color,amount));
			}
			else{
				newins.add(instruction);
			}
		}
		return newins;
	}

	//shuffle all instructions 
	public void randomize(ArrayList<Instruction> ins){
		Instruction other = ins.remove(ins.size()-1);
		Collections.shuffle(ins);
	}


	 */
}
