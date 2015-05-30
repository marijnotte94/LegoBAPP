package com.share2pley.share2pleyapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SetPagerFragment extends Fragment {
	
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	
	public static SetPagerFragment create(int pageNumber) {
		SetPagerFragment fragment = new SetPagerFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}
	
	public SetPagerFragment(){}
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
	}
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_choose_set, container, false);
		TextView textView = (TextView)rootView.findViewById(R.id.choose_set_textView_description);
		
		return rootView;
	}

}
