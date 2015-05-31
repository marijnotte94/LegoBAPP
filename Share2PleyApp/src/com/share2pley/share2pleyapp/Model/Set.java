package com.share2pley.share2pleyapp.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.share2pley.share2pleyapp.R;

public class Set {
	public String mName;
	private ArrayList<Brick> mBricks;
	private Context mContext;
	private Bitmap mImage;
	private TypedArray mImgs;

	public Set(String name, Context context){
		mName = name;
		mContext = context;
		mBricks = new ArrayList<Brick>();
		mImgs = context.getResources().obtainTypedArray(R.array.models_imgs);
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

	public Brick getStone(int index) {
		return mBricks.get(index);
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
				if(resourceName.contains(mName)){
					int amount = Character.getNumericValue(resourceName.charAt(resourceName.length()-5));	 
					addBrick(resourceId, amount);
				}
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public int getImageResource(int index) {
		return mImgs.getResourceId(index, -1);
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
<<<<<<< HEAD
		Collections.shuffle(ins);	
	}
	
	public UUID getId() {
		return mId;
=======
		Collections.shuffle(ins);
>>>>>>> 48765fdcb42d9d5e834ac703570545084fe3dd02
	}


	 */
}
