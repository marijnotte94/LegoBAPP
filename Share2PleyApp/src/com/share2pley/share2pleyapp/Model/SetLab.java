package com.share2pley.share2pleyapp.Model;

import java.util.ArrayList;


//Lab with all sets
public class SetLab {

	private Set set1; 
	private Set set2; 
	private Set set3;
	private Set set4;
	private Set set5;
	private Set set6;
	private Set set7;
	private Set set8;
	private Set set9;
	private Set set10;
	private Set set11;
	private Set set12;
	private Set set13;
	private Set set14;
	private Set set15;
	private Set set16;
	
	private ArrayList<Set> sets;
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
	
	public Set getSet(int index){
		return sets.get(index); 
	}
	
	public ArrayList<Set> getSetList() {
		return sets;
	}

}
