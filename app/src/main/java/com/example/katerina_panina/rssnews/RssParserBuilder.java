package com.example.katerina_panina.rssnews;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Rss parser builder.
 *
 * @author Ekaterina Panina panina.k.a@gmail.com
 */
public class RssParserBuilder {

    private String mNewsUrl;

    /**
     * Gets {@link InputStream} object for parser.
     *
     * @return {@link InputStream} input stream.
     */
    InputStream getInputStream() {
        InputStream inputStream = null;
        URL feedUrl;
        try {
            feedUrl = new URL(mNewsUrl);
            inputStream = feedUrl.openConnection().getInputStream();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * Initiates parser.
     *
     * @param aNewsUrl url for given source news.
     */
    XmlPullParser setParser(String aNewsUrl) {
        XmlPullParserFactory factory;
        XmlPullParser parser;

        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(false);
            parser = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        }

        mNewsUrl = aNewsUrl;
        try {
            parser.setInput(getInputStream(), null);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
        return parser;
    }

    /**
     * For tests purposes only. Sets news URL.
     *
     * @param aNewsUrl News URL.
     */
    void setNewsUrl(String aNewsUrl) {
        mNewsUrl = aNewsUrl;
    }
}
