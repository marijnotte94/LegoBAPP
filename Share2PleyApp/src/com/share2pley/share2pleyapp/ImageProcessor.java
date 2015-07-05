package com.share2pley.share2pleyapp;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

public class ImageProcessor {
	private ImageProcessor mImageProcessor;
	private static int dif = 0;
	public static final String EXTRA_PHOTO_FILENAME = "com.share2pley.share2pleyapp.photo_filename";
	static float orH = 0;
	static float phH = 0;
	private static Bitmap onView;
	protected ImageProcessor(){}

	public ImageProcessor getInstance(){
		if (mImageProcessor == null) {
			mImageProcessor = new ImageProcessor();
		}
		return mImageProcessor;
	}

	public static boolean isCorrectBrick(Bitmap original, Bitmap photo){
		original = original.copy(Config.ARGB_8888, true);
		onView = photo;
		photo = photo.copy(Config.ARGB_8888, true);
		photo = Bitmap.createBitmap(photo, (photo.getWidth()/2) - 125, (photo.getHeight()/2) - 125, (photo.getWidth() / 2) + 125, (photo.getHeight() / 2) + 125);

		Log.d("heightoriginal", original.getHeight()+"");
		Log.d("heightphoto", photo.getHeight()+"");
		Log.d("widthoriginal", original.getWidth()+"");
		Log.d("widthphoto", photo.getWidth()+"");

		for(int i = 0; i<original.getHeight(); i++){
			for(int j = 0; j<original.getWidth(); j++){
				int pixel = original.getPixel(i,j);
				int Rp = (pixel >> 16) & 0xff;
				int Gp = (pixel >> 8) & 0xff;
				int Bp = pixel & 0xff;
				if(Rp == 0 && Gp == 0 && Bp == 0){
					original.setPixel(i, j, Color.WHITE);
				}
			}
		}
		
		int white = 0;
		
		Log.d("isCorrectBrick","tralalal");
		for(int i = 0; i < original.getHeight(); i++){
			for (int j = 0; j<original.getWidth(); j++){
				int orPix = original.getPixel(i,j);
				int phPix = photo.getPixel(i,j);
				int orR = Color.red(orPix);
				int orG = Color.green(orPix);
				int orB = Color.blue(orPix);
				int phR = Color.red(phPix);
				int phG = Color.green(phPix);
				int phB = Color.blue(phPix);

				float[] orHSV = new float[3];
				float[] phHSV = new float[3];
				Color.RGBToHSV(orR, orG, orB, orHSV);
				Color.RGBToHSV(phR, phG, phB, phHSV);
//				Log.d("orAlpha",orHSV+"");
//				Log.d("phAlpha",phHSV+"");
				if(orPix != -1){
					if(Math.abs(orHSV[0] - phHSV[0]) > 50){
						dif++;
						orH = orH + orHSV[0];
						phH = phH + phHSV[0];
					}
				}
				else{
					white++;
				}
				
				//					Log.d("oralpha", orHSV[0]+"");
				//					Log.d("phalpha", phHSV[0]+"");
				//				int Rphoto = (phPix >> 16) & 0xff;
				//				int Gphoto = (phPix >> 8) & 0xff;
				//				int Bphoto = phPix & 0xff;
				//				int Roriginal = (orPix >> 16) & 0xff;
				//				int Goriginal = (orPix >> 8) & 0xff;
				//				int Boriginal = orPix & 0xff;
				//				int Rdiff = Math.abs(Rphoto - Roriginal);
				//				int Gdiff = Math.abs(Gphoto - Goriginal);
				//				int Bdiff = Math.abs(Bphoto - Boriginal);
				//				if(otherPixel(Rdiff,Gdiff,Bdiff))
				//					dif++;
			}	
		}
		orH = orH / dif;
		phH = phH / dif;
		Log.d("white",white+"");
		Log.d("diff",dif+"");
		Log.d("tot",original.getHeight() * original.getWidth() + "");
		if(dif < 4000){
			return true;
		}
		return false;
	}

	public static int getDif(){
		return dif;
	}

	public static boolean otherPixel(int Rdiff, int Gdiff, int Bdiff){
		int tot = 0;
		if (Rdiff > 100){
			tot++;
		}
		if (Gdiff > 100){
			tot++;
		}
		if (Bdiff > 100){
			tot++;
		}

		if(tot > 1){
			return true;
		}
		return false;
	}

	public static boolean CorrectColor(Bitmap original, Bitmap image){
		int iWidth = image.getWidth();
		int iHeight = image.getHeight();
		int oWidth = original.getWidth();
		int oHeight = original.getHeight();
		int Ro = 0; int Go = 0; int Bo = 0; int Ri = 0; int Gi = 0; int Bi = 0;
		int pixel;
		for (int x = 0; x<iWidth; x++){
			for(int y = 0; y<iHeight; y++){
				pixel = image.getPixel(x,y);
				Ri += Color.red(pixel);
				Gi += Color.green(pixel);
				Bi += Color.blue(pixel);
			} 
		}

		for (int x = 0; x<oWidth; x++){
			for(int y = 0; y<oHeight; y++){
				pixel = original.getPixel(x,y);
				Ro += Color.red(pixel);
				Go += Color.green(pixel);
				Bo += Color.blue(pixel);
			} 
		}

		Ri =  Ri / (iWidth * iHeight);
		Gi =  Gi / (iWidth * iHeight);
		Bi =  Bi / (iWidth * iHeight);
		Ro =  Ro / (oWidth * oHeight);
		Go =  Go / (oWidth * oHeight);
		Bo =  Bo / (oWidth * oHeight);

		Log.d("Ri",Ri+"");
		Log.d("Gi",Gi+"");
		Log.d("Bi",Bi+"");
		Log.d("Ro",Ro+"");
		Log.d("Go",Go+"");
		Log.d("Bo",Bo+"");

		return true;
	}



	public static byte[] bmToByte(Bitmap bp){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bp.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return byteArray;
	}

	public static void reset(){
		dif = 0;
	}
	
	public static Bitmap getView(){
		return onView;
	}

}



