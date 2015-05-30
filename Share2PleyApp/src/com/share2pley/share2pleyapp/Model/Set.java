package com.share2pley.share2pleyapp.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.UUID;

public class Set {
	private UUID mId;

	private int red;
	private int green;
	private int blue;
	private int pink;
	private int yellow;
	private int other;
	private int white;
	private int gold;
	private int grey;
	private int black;

	private ArrayList<Instruction> instructions;
	private final int max = 10;

	public Set(int mWhite, int mGold, int mRed, int mYellow, int mGreen, int mGrey, int mBlack, int mBlue, int mOther){
		mId = UUID.randomUUID();
		white = mWhite;
		gold = mGold;
		red = mRed;
		yellow = mYellow;
		green = mGreen;
		grey = mGrey;
		black = mBlack;
		blue = mBlue;
		other = mOther;

		instructions = new ArrayList<Instruction>();

		instructions.add(new Instruction("White", mWhite));
		instructions.add(new Instruction("Gold", mGold));
		instructions.add(new Instruction("Red", mRed));
		instructions.add(new Instruction("Yellow", mYellow));
		instructions.add(new Instruction("Green", mGreen));
		instructions.add(new Instruction("Grey", mGrey));
		instructions.add(new Instruction("Blue", mBlue));
		instructions.add(new Instruction("Black", mBlack));
		instructions = split(instructions);
		randomize(instructions);
		instructions.add(new Instruction("Other", mOther));
	}

	public Instruction getInstruction(int index){
		return instructions.get(index);
	}

	public ArrayList<Instruction> getIntructions(){
		return instructions;
	}
	
	//check if there is a next instruction
	public boolean hasNext(int ins){
		if(ins <= instructions.size()-2){
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
	
	public UUID getId() {
		return mId;
	}
}
