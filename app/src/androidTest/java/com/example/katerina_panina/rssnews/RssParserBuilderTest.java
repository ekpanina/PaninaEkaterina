package com.example.katerina_panina.rssnews;

import android.test.AndroidTestCase;

/**
 * Rss parser builder test class.
 *
 * @author Ekaterina Panina panina.k.a@gmail.com
 */
public class RssParserBuilderTest extends AndroidTestCase {

    /**
     * Tests getting input stream function.
     */
    public void testGettingInputStream() {
        RssParserBuilder rssParserBuilder = new RssParserBuilder();
        rssParserBuilder.setNewsUrl(null);
        assertNull(rssParserBuilder.getInputStream());
    }

    /**
     * Tests setting parser function.
     */
    public void testSettingParser() {
        RssParserBuilder rssParserBuilder = new RssParserBuilder();
        assertNull(rssParserBuilder.setParser(null));
    }
}
