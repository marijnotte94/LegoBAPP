package com.share2pley.share2pleyapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.share2pley.share2pleyapp.Model.BrickFetcherTask;
import com.share2pley.share2pleyapp.Model.InstructionsFetcherTask;
import com.share2pley.share2pleyapp.Model.SetLab;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class SetPagerFragment extends Fragment {
	public static final String ARG_PAGE = "page";
	private int mPageNumber;
	private final Context mContext;
	private static final String ENDPOINT = "http://brickset.com/api/v2.asmx/";
	private static final String METHOD_GET_INSTRUCTIONS = "getInstructions";
	private static final String BRICKSSITE = "http://rebrickable.com/api/";
	private static final String REBRICKABLE_API_KEY = "VOVpbl4rpN";
	private static final String METHOD_GET_SET_PARTS = "get_set_parts";

	/**
	 * 
	 * @param pageNumber
	 * @param context
	 * @return
	 */
	public static SetPagerFragment create(int pageNumber, Context context) {
		SetPagerFragment fragment = new SetPagerFragment(context);
		// DBHelper mDBHelper = new DBHelper(context);
		// ArrayList<DatabaseSetHelper> sets = mDBHelper.getSetData();
		// ArrayList<DatabaseSetHelper> resSets = new
		// ArrayList<DatabaseSetHelper>();
		// for (int i = 0; i < sets.size(); i++) {
		// if (sets.get(i).getSolved() == 0) {
		// resSets.add(sets.get(i));
		// }
		// }
		// int index = resSets.get(pageNumber).getIndex();
		// int res = pageNumber;
		// for (int i = 0; i < index; i++) {
		// if (sets.get(i).getSolved() == 1) {
		// res++;
		// }
		// }
		// Bundle args = new Bundle();
		// args.putInt(ARG_PAGE, res);
		// fragment.setArguments(args);
		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNumber);
		fragment.setArguments(args);
		return fragment;
	}

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	public SetPagerFragment(Context context) {
		mContext = context;
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

	/**
	 * Sets all the Views
	 * 
	 * @param rootView
	 *            used for setting up the views.
	 */
	public void setViews(View rootView) {
		TextView nameView = (TextView) rootView
				.findViewById(R.id.choose_set_textView_description);
		// nameView.setText(SetLab.get(getActivity()).getRealString(mPageNumber));
		nameView.setText(SetLab.get(mContext).getSet(mPageNumber)
				.getNameDescription());
		TextView typeView = (TextView) rootView
				.findViewById(R.id.choose_set_textView_type);
		typeView.setText(SetLab.get(getActivity()).getSet(mPageNumber)
				.getTheme());
		// TextView ageView = (TextView) rootView
		// .findViewById(R.id.choose_set_textView_age);
		// ageView.setText(SetLab.get(getActivity()).getAge(mPageNumber));
		ImageView imageView = (ImageView) rootView
				.findViewById(R.id.imageView_chooseSet);
		imageView.setImageBitmap(SetLab.get(getActivity()).getSet(mPageNumber)
				.getBitmap());
		// imageView.setImageResource(SetLab.get(getActivity())
		// .getSet(mPageNumber).getModelImageResource(mP));
	}

	/**
	 * This method puts a listener to the choose button
	 * 
	 * @param rootView
	 *            used for finding the chooseButton
	 */
	public void setChooseViewButton(View rootView) {
		Button chooseButton = (Button) rootView
				.findViewById(R.id.fragment_choose_set_chooseButton);
		chooseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getBricks();
			}
		});
	}

	/**
	 * Adds bricks to corresponding set Loading all bitmaps and starting
	 * activity from Task
	 */
	public void getBricks() {
		String res = SetLab.get(getActivity()).getSet(mPageNumber).getFullId();
		String url = BRICKSSITE + METHOD_GET_SET_PARTS + "?key="
				+ REBRICKABLE_API_KEY + "&format=" + "xml" + "&set=" + res;
		BrickFetcherTask brickTask = new BrickFetcherTask(getActivity(),
				mContext, mPageNumber);
		brickTask.execute(url);
	}

	public void setInstructionsViewButton(View rootView) {
		Button instructionsButton = (Button) rootView
				.findViewById(R.id.fragment_choose_set_instructionsButton);
		instructionsButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String url = Uri.parse(ENDPOINT).buildUpon()
						.appendEncodedPath(METHOD_GET_INSTRUCTIONS).build()
						.toString();
				int setId = SetLab.get(mContext).getSet(mPageNumber).getId();
				int pageNumber = mPageNumber;
				ProgressDialog dialog = new ProgressDialog(getActivity());
				FragmentManager fm = getFragmentManager();
				InstructionsFetcherTask instructionsTask = new InstructionsFetcherTask(
						setId, pageNumber, mContext, dialog, fm);
				dialog.show();
				instructionsTask.execute(url);
			}
		});
	}
}
