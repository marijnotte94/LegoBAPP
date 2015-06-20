package com.share2pley.share2pleyapp.Model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class PrototypeSet {
	private String name;
	private int id;
	private String description;
	private Bitmap setImage;
	private int setNumber;
	private String theme;
	private String instructionsURL;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bitmap getSetImage() {
		return setImage;
	}

	public void setSetImage(Bitmap setImage) {
		this.setImage = setImage;
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

}
