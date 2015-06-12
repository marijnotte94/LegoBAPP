package com.share2pley.share2pleyapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
		int index = sets.get(pageNumber).getIndex();
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
				copyReadAssets();
			}
		});
	}

	private void copyReadAssets() {
		AssetManager assetManager = getActivity().getAssets();

		InputStream in = null;
		OutputStream out = null;
		File file = new File(getActivity().getFilesDir(), "speelhuis.pdf");
		try {
			in = assetManager.open("speelhuis.pdf");
			out = getActivity().openFileOutput(file.getName(),
					Context.MODE_WORLD_READABLE);

			copyFile(in, out);
			in.close();
			in = null;
			out.flush();
			out.close();
			out = null;
		} catch (Exception e) {
			Log.e("tag", e.getMessage());
		}

		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(
				Uri.parse("file://" + getActivity().getFilesDir()
						+ "/speelhuis.pdf"), "application/pdf");

		startActivity(intent);
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

}
