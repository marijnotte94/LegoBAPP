package com.share2pley.share2pleyapp.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.util.Log;

import com.share2pley.share2pleyapp.Model.Brick;

public class Set {

	private String name;

	private ArrayList<Brick> bricks;

	public Set(String mName){
		mName = name;
		bricks = new ArrayList<Brick>();
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
	
	public void addStone(int source, int amount){
		bricks.add(new Brick(source, amount));
	}

	public Brick getStone(int index) {
		return bricks.get(index);
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
