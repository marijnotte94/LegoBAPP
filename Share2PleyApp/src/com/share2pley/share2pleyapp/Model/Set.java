package com.share2pley.share2pleyapp.Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

/**
 * 
 * @author Richard - 4233867
 * 
 */
public class Set {
	private String name;
	private String full_id;
	private int id;
	private int id_addition;
	private String description;
	private Bitmap setImage;
	private int setNumber;
	private String theme;
	private String instructionsURL;
	private int pieces;
	private List<Brick> mBricks;
	private List<String> mInstructions;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdAddition() {
		return id_addition;
	}

	public void setIdAddition(int id_addition) {
		this.id_addition = id_addition;
	}

	/**
	 * If full id is not set, return the full id using setnumber and id_addition
	 * else get full_id
	 * 
	 * @return full_id
	 */
	public String getFullId() {
		if (full_id == null) {
			return setNumber + "-" + id_addition;
		} else
			return full_id;
	}

	public void setFullId(String id) {
		this.full_id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bitmap getSetImage() {
		return setImage;
	}

	public int getSetNumber() {
		return setNumber;
	}

	public void setSetNumber(int setNumber) {
		this.setNumber = setNumber;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getInstructionsURL() {
		return instructionsURL;
	}

	public void setInstructionsURL(String instructionsURL) {
		this.instructionsURL = instructionsURL;
	}

	public String getNameDescription() {
		return getSetNumber() + " : " + getName();
	}

	/**
	 * Sets the image URL source to a bitmap of the set
	 * 
	 * @param src
	 *            the source of the set image
	 */
	public void setBitmapFromUrl(String src) {
		try {
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			setImage = myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The set images were 90 degrees rotated This method is used to rotate the
	 * images right
	 * 
	 * @return the rotated bitmap
	 */
	public Bitmap getBitmap() {
		Matrix matrix = new Matrix();
		matrix.postRotate(90);
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(setImage,
				setImage.getWidth(), setImage.getHeight(), true);
		Bitmap rotatedBitmap = Bitmap
				.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(),
						scaledBitmap.getHeight(), matrix, true);
		return rotatedBitmap;
	}

	public List<Brick> getBricks() {
		return mBricks;
	}

	public void setBricks(List<Brick> bricks) {
		mBricks = bricks;
	}

	public int getPieces() {
		return pieces;
	}

	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	public List<String> getInstructions() {
		return mInstructions;
	}

	public void setInstructions(List<String> instructions) {
		mInstructions = instructions;
	}
}
