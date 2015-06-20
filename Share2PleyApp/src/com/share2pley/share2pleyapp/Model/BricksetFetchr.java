//package com.share2pley.share2pleyapp.Model;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.List;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//
//import android.net.Uri;
//
//public class BricksetFetchr {
//	public static final String TAG = "BricksetFetchr";
//
//	private static final String HOST = "brickset.com";
//	private static final String ENDPOINT = "http://brickset.com/api/v2.asmx/";
//	private static final String API_KEY = "hTsg-Vmye-dO8e";
//	private static final String METHOD_GET_SETS = "getSets";
//
//	byte[] getUrlBytes(String urlSpec) throws IOException {
//		URL url = new URL(urlSpec);
//		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//		try {
//			ByteArrayOutputStream out = new ByteArrayOutputStream();
//			InputStream in = connection.getInputStream();
//
//			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
//				return null;
//			}
//
//			int bytesRead = 0;
//			byte[] buffer = new byte[1024];
//			while ((bytesRead = in.read(buffer)) > 0) {
//				out.write(buffer, 0, bytesRead);
//			}
//			out.close();
//			return out.toByteArray();
//		} finally {
//			connection.disconnect();
//		}
//	}
//
//	public String getUrl(String urlSpec) throws IOException {
//		return new String(getUrlBytes(urlSpec));
//	}
//
//	public void setSets() {
//		String url = Uri.parse(ENDPOINT).buildUpon()
//				.appendEncodedPath(METHOD_GET_SETS)
//				.appendQueryParameter("apiKey", API_KEY).build().toString();
//		DefaultHttpClient client = new DefaultHttpClient();
//		HttpGet request = new HttpGet(url);
//		HttpResponse response;
//		try {
//			response = client.execute(request);
//			InputStream in = response.getEntity().getContent();
//			XMLPullParserHandler parser = new XMLPullParserHandler();
//			List<PrototypeSet> sets = parser.parse(in);
//			for (PrototypeSet p : sets) {
//				PrototypeSetLab.get().addSet(p);
//			}
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
// }
