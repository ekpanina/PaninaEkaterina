package com.example.katerina_panina.rssnews;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Rss parser.
 *
 * @author Ekaterina Panina panina.k.a@gmail.com
 */
public class RssParser extends RssParserBuilder {

    /**
     * Publish date node.
     */
    private static final String DATE = "pubDate";

    /**
     * Decription node.
     */
    private static final String DESCRIPTION = "description";

    /**
     * Link node.
     */
    private static final String LINK = "link";

    /**
     * Title node.
     */
    private static final String TITLE = "title";

    /**
     * Item node.
     */
    private static final String ITEM = "item";

    /**
     * Channel node.
     */
    private static final String CHANNEL = "channel";

    private String mNewsUrl1;
    private String mNewsUrl2;
    private String mSourceUrl1;
    private String mSourceUrl2;
    private URL mFeedUrl;
    private XmlPullParser mParser;

    /**
     * Constructor.
     *
     * @param aNewsUrl1 url for the first news source.
     * @param aNewsUrl2 url for the second news source.
     * @param aSourceUrl1 source text for the first source news.
     * @param aSourceUrl2 source text for the second source news.
     */
    public RssParser(String aNewsUrl1, String aNewsUrl2, String aSourceUrl1,
                     String aSourceUrl2) {
        mNewsUrl1 = aNewsUrl1;
        mNewsUrl2 = aNewsUrl2;
        mSourceUrl1 = aSourceUrl1;
        mSourceUrl2 = aSourceUrl2;
    }

    /**
     * Parses news from given news sources.
     *
     * @param aNewsUrl url for given source news.
     * @param aSourceUrl source text for the first source news.
     * @return List of news.
     */
    private List<NewsPiece> parseNewsUrl(String aNewsUrl, String aSourceUrl) {
        List<NewsPiece> messages = null;

        mParser = setParser(aNewsUrl);
        if (mParser != null) {
            NewsPiece currentMessage = null;
            boolean done = false;

            int eventType = 0;
            try {
                eventType = mParser.getEventType();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
                return null;
            }

            while (eventType != XmlPullParser.END_DOCUMENT && !done) {

                String name = null;
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        messages = new ArrayList<>();
                        break;
                    case XmlPullParser.START_TAG:
                        name = mParser.getName();
                        if (name.equalsIgnoreCase(ITEM)) {
                            currentMessage = new NewsPiece();
                        } else if (currentMessage != null) {
                            if (name.equalsIgnoreCase(DESCRIPTION)) {
                                try {
                                    currentMessage.setDescription(mParser.nextText());
                                } catch (XmlPullParserException e) {
                                    e.printStackTrace();
                                    return null;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            } else if (name.equalsIgnoreCase(DATE)) {
                                try {
                                    currentMessage.setDate(mParser.nextText());
                                } catch (XmlPullParserException e) {
                                    e.printStackTrace();
                                    return null;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            } else if (name.equalsIgnoreCase(TITLE)) {
                                try {
                                    currentMessage.setTitle(mParser.nextText() + " " + aSourceUrl);
                                } catch (XmlPullParserException e) {
                                    e.printStackTrace();
                                    return null;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    return null;
                                }
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = mParser.getName();
                        if (name.equalsIgnoreCase(ITEM) && currentMessage != null) {
                            messages.add(currentMessage);
                        } else if (name.equalsIgnoreCase(CHANNEL)) {
                            done = true;
                        }
                        break;
                }
                try {
                    eventType = mParser.next();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return messages;
    }

    /**
     * Concatenates two arrays of news into one big source array.
     *
     * @return {@link ArrayList} array of news from two sources.
     */
    public ArrayList<NewsPiece> parse() {
        ArrayList<NewsPiece> news = new ArrayList<>();
        final List<NewsPiece> Url1News = parseNewsUrl(mNewsUrl1, mSourceUrl1);
        final List<NewsPiece> Url2News = parseNewsUrl(mNewsUrl2, mSourceUrl2);

        if (Url1News != null) {
            news.addAll(Url1News);
        }
        if (Url2News != null) {
            news.addAll(Url2News);
        }
        return news;
    }
}