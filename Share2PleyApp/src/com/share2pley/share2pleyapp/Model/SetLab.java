package com.share2pley.share2pleyapp.Model;

import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;

import com.share2pley.share2pleyapp.R;



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

		set1 = new Set("Speelhuis");

		final R.drawable drawableResources = new R.drawable();
		final Class<R.drawable> c = R.drawable.class;
		final Field[] fields = c.getDeclaredFields();

		for (int i = 0, max = fields.length; i < max; i++) {
			final int resourceId;
			try {
		        resourceId = fields[i].getInt(drawableResources);
		    } catch (Exception e) {
		        continue;
		    }
			
			if(resourceId >=0x7f020044 && resourceId <= 0x7f020077){
				set1.addStone(resourceId,1);
			}

		}


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
