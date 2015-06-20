package com.share2pley.share2pleyapp.Model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

public class XMLPullParserHandler {

	List<PrototypeSet> mSets;

	private PrototypeSet mSet;
	private String mText;

	public XMLPullParserHandler() {
		mSets = new ArrayList<PrototypeSet>();
	}

	public List<PrototypeSet> getSets() {
		return mSets;
	}

	public List<PrototypeSet> parse(InputStream in) {
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;

		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);

			parser = factory.newPullParser();
			parser.setInput(in, null);

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tagName = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagName.equalsIgnoreCase("sets")) {
						mSet = new PrototypeSet();
					}
					break;

				case XmlPullParser.TEXT:
					mText = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagName.equalsIgnoreCase("sets")) {
						mSets.add(mSet);
					} else if (tagName.equalsIgnoreCase("setID")) {
						mSet.setId(Integer.parseInt(mText));
					} else if (tagName.equalsIgnoreCase("number")) {
						mSet.setSetNumber(Integer.parseInt(mText));
					} else if (tagName.equalsIgnoreCase("name")) {
						mSet.setName(mText);
					} else if (tagName.equalsIgnoreCase("theme")) {
						mSet.setTheme(mText);
					} else if (tagName.equalsIgnoreCase("imageURL")) {
						mSet.setBitmapFromUrl(mText);
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mSets;
	}
}
