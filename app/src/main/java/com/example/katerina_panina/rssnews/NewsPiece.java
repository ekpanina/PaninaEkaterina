package com.example.katerina_panina.rssnews;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for describing one element of news.
 *
 * @author Ekaterina Panina panina.k.a@gmail.com
 */
public class NewsPiece {

    static SimpleDateFormat FORMATTER = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z");
    private String mTitle;
    private String mDescription;
    private Date mDate;

    /**
     * Gets news piece title.
     *
     * @return news piece title.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets news piece title.
     *
     * @param aTitle news piece title.
     */
    public void setTitle(String aTitle) {
        this.mTitle = aTitle;
    }

    /**
     * Gets news piece description.
     *
     * @return the mDescription news piece description.
     */
    public String getDescription() {
        return mDescription;
    }

    /**
     * Sets news piece description.
     *
     * @param mDescription news piece description.
     */
    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    /**
     * Gets news piece date published.
     *
     * @return mDate news piece date published.
     */
    public Date getDate() {
        return mDate;
    }

    /**
     * Sets news piece date published.
     *
     * @param aDate news piece date published.
     */
    public void setDate(String aDate) {
        while (!aDate.endsWith("00")){
            aDate += "0";
        }
        try {
            mDate = FORMATTER.parse(aDate.trim());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}