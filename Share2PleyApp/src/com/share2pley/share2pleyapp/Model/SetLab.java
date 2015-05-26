package com.share2pley.share2pleyapp.Model;

import java.util.ArrayList;


//Lab with all sets
public class SetLab {

	private static Set set1; 
	private static Set set2; 
	private static Set set3;
	private static Set set4;
	private static Set set5;
	private static Set set6;
	private static Set set7;
	private static Set set8;
	private static Set set9;
	private static Set set10;
	private static Set set11;
	private static Set set12;
	private static Set set13;
	private static Set set14;
	private static Set set15;
	private static Set set16;
	
	private static ArrayList<Set> sets;
	private static SetLab sSetLab;
	
	
	private SetLab(){
		sets =  new ArrayList<Set>();
		
		set1 = new Set(4,4,65,33,33,11,12,12,12);
		set2 = new Set(4,4,65,33,33,11,12,12,12);
		set3 = new Set(4,4,65,33,33,11,12,12,12);
		set4 = new Set(4,4,65,33,33,11,12,12,12);
		set5 = new Set(4,4,65,33,33,11,12,12,12);
		set6 = new Set(4,4,65,33,33,11,12,12,12);
		set7 = new Set(4,4,65,33,33,11,12,12,12);
		set8 = new Set(4,4,65,33,33,11,12,12,12);
		set9 = new Set(4,4,65,33,33,11,12,12,12);
		set10 = new Set(4,4,65,33,33,11,12,12,12);
		set11 = new Set(4,4,65,33,33,11,12,12,12);
		set12 = new Set(4,4,65,33,33,11,12,12,12);
		set13 = new Set(4,4,65,33,33,11,12,12,12);
		set14 = new Set(4,4,65,33,33,11,12,12,12);
		set15 = new Set(4,4,65,33,33,11,12,12,12);
		//correct, dont change!
		set16 = new Set(6,19,13,9,19,171,204,48,4);
		
		sets.add(set1);
		sets.add(set2);
		sets.add(set3);
		sets.add(set4);
		sets.add(set5);
		sets.add(set6);
		sets.add(set7);
		sets.add(set8);
		sets.add(set9);
		sets.add(set10);
		sets.add(set11);
		sets.add(set12);
		sets.add(set13);
		sets.add(set14);
		sets.add(set15);
		sets.add(set16);
	}

	public static SetLab get(){
		if (sSetLab == null){
			sSetLab = new SetLab();
		}
		return sSetLab;
	}
	
	public static Set getSet(int index){
		return sets.get(index); 
	}
	
	public static int getAmount(){
		return sets.size();
	}

}
