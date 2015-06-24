package com.share2pley.share2pleyapp.Model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * 
 * @author Richard Vink - 4233867
 * 
 */
public class XMLPullParserHandler {

	List<Set> mSets;
	List<Brick> mBricks;

	private Set mSet;
	private Brick mBrick;
	private String mText;

	public XMLPullParserHandler() {
		mSets = new ArrayList<Set>();
		mBricks = new ArrayList<Brick>();
	}

	public List<Set> getSets() {
		return mSets;
	}

	/**
	 * Reads the inputstream xml, and creates the bricks from it
	 * 
	 * @param in
	 * @return
	 */
	public List<Brick> parseBricks(InputStream in) {
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;
		mBricks = new ArrayList<Brick>();
		int setXmlCount = 0;

		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);

			parser = factory.newPullParser();
			parser.setInput(in, null);

			int eventType = parser.getEventType();
			while (setXmlCount < 2) {
				String tagName = parser.getName();
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if (tagName.equalsIgnoreCase("parts")) {
						setXmlCount++;
					} else if (tagName.equalsIgnoreCase("part")) {
						mBrick = new Brick();
					}
					break;

				case XmlPullParser.TEXT:
					mText = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagName.equalsIgnoreCase("part")) {
						if (!mBrick.getSource().equals(
								"http://img.rebrickable.com/img/ni.png")) {
							mBricks.add(mBrick);
						}
					} else if (tagName.equalsIgnoreCase("qty")) {
						mBrick.setQuantity(Integer.parseInt(mText));
					} else if (tagName.equalsIgnoreCase("part_name")) {
						mBrick.setName(mText);
					} else if (tagName.equalsIgnoreCase("part_id")) {
						mBrick.setId(mText);
					} else if (tagName.equalsIgnoreCase("color_name")) {
						mBrick.setColor(mText);
					} else if (tagName.equalsIgnoreCase("element_id")) {
						if (Integer.parseInt(mText) == -1) {
						} else {
							mBrick.setId(mText);
						}
					} else if (tagName.equalsIgnoreCase("part_img_url")) {
						String text = "http:" + mText;
						mBrick.setSource(text);
					} else if (tagName.equalsIgnoreCase("parts")) {
						setXmlCount++;
					} else {
						break;
					}
					break;

				default:
					break;
				}
				if (setXmlCount < 2) {
					eventType = parser.next();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mBricks;
	}

	/**
	 * Reads the inputstream xml, and creates the sets from it
	 * 
	 * @param in
	 * @return
	 */
	public List<Set> parseSets(InputStream in) {
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
						mSet = new Set();
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
					} else if (tagName.equalsIgnoreCase("numberVariant")) {
						mSet.setIdAddition(Integer.parseInt(mText));
					} else if (tagName.equalsIgnoreCase("name")) {
						mSet.setName(mText);
					} else if (tagName.equalsIgnoreCase("theme")) {
						mSet.setTheme(mText);
					} else if (tagName.equalsIgnoreCase("pieces")) {
						mSet.setPieces(Integer.parseInt(mText));
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

	public List<String> parseInstructions(InputStream in) {
		XmlPullParserFactory factory = null;
		XmlPullParser parser = null;
		ArrayList<String> instructionsList = new ArrayList<String>();

		try {
			factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);

			parser = factory.newPullParser();
			parser.setInput(in, null);
			String instruction;

			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tagName = parser.getName();
				switch (eventType) {

				case XmlPullParser.TEXT:
					mText = parser.getText();
					break;

				case XmlPullParser.END_TAG:
					if (tagName.equalsIgnoreCase("URL")) {
						instructionsList.add(mText);
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
		return instructionsList;
	}
}
