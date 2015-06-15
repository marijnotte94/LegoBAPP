package com.share2pley.share2pleyapp;

import java.io.IOException;
import java.util.ArrayList;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.share2pley.share2pleyapp.Model.DatabaseSetHelper;
import com.share2pley.share2pleyapp.Model.SetLab;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class SetPagerFragment extends Fragment {
	public static final String ARG_PAGE = "page";
	private int mPageNumber;

	public static SetPagerFragment create(int pageNumber, Context context) {
		SetPagerFragment fragment = new SetPagerFragment();
		DBHelper mDBHelper = new DBHelper(context);
		ArrayList<DatabaseSetHelper> sets = mDBHelper.getSetData();
		ArrayList<DatabaseSetHelper> resSets = new ArrayList<DatabaseSetHelper>();
		for (int i = 0; i < sets.size(); i++) {
			if (sets.get(i).getSolved() == 0) {
				resSets.add(sets.get(i));
			}
		}
		int index = resSets.get(pageNumber).getIndex();
		int res = pageNumber;
		for (int i = 0; i < index; i++) {
			if (sets.get(i).getSolved() == 1) {
				res++;
			}
		}
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, res);
		fragment.setArguments(args);
		return fragment;
	}

	public SetPagerFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mPageNumber = getArguments().getInt(ARG_PAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.fragment_choose_set, container, false);
		setViews(rootView);
		setChooseViewButton(rootView);
		setInstructionsViewButton(rootView);
		return rootView;
	}

	public void setViews(View rootView) {
		TextView nameView = (TextView) rootView
				.findViewById(R.id.choose_set_textView_description);
		nameView.setText(SetLab.get(getActivity()).getRealString(mPageNumber));
		TextView typeView = (TextView) rootView
				.findViewById(R.id.choose_set_textView_type);
		typeView.setText(SetLab.get(getActivity()).getType(mPageNumber));
		TextView ageView = (TextView) rootView
				.findViewById(R.id.choose_set_textView_age);
		ageView.setText(SetLab.get(getActivity()).getAge(mPageNumber));
		ImageView imageView = (ImageView) rootView
				.findViewById(R.id.imageView_chooseSet);
		imageView.setImageResource(R.drawable.atdp403221_6);
		imageView.setImageResource(SetLab.get(getActivity())
				.getSet(mPageNumber).getModelImageResource(mPageNumber));
	}

	public void setChooseViewButton(View rootView) {
		Button chooseButton = (Button) rootView
				.findViewById(R.id.fragment_choose_set_chooseButton);
		chooseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ClearActivity.class);
				i.putExtra(ARG_PAGE, mPageNumber);
				startActivity(i);
				getActivity().finish();
			}
		});
	}

	public void setInstructionsViewButton(View rootView) {
		Button instructionsButton = (Button) rootView
				.findViewById(R.id.fragment_choose_set_instructionsButton);
		instructionsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startPdf(mPageNumber);
				} catch (ActivityNotFoundException e) {
					Toast.makeText(getActivity(), "No PDF reader installed.",
							100).show();
				}
			}
		});
	}

	private void startPdf(int pageNumber) {
		AssetManager assetManager = getActivity().getAssets();
		try {
			String[] files = assetManager.list("");
			Intent intent = new Intent(Intent.ACTION_VIEW);
			String[] array = getResources().getStringArray(R.array.assetsArray);
			intent.setDataAndType(
					Uri.parse("file://" + getActivity().getFilesDir()
							+ Uri.parse(array[pageNumber])), "application/pdf");
			startActivity(intent);
		} catch (IOException e) {
			Toast.makeText(getActivity(), "It is not working", 100);
		}
	}
}
