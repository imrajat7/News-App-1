package com.example.android.newsapp1;

public class News {
    /** Title of the news */
    private String mTitle;

    /**  Author of the news */
    private String mAuthor;

    /**  Section of the news */
    private String mSection;

    /**  Date of the news */
    private String mDate;

    /**
     *  Url of the site
     */
    private String mUrl;

    News(String title, String author, String section, String date, String url){
        mTitle = title;
        mAuthor = author;
        mSection = section;
        mDate = date;
        mUrl = url;
    }

    /**
     * return the title of the news
     */
    public String getTtitle(){
        return mTitle;
    }

    /**
     * return the author of the news
     */
    public String getAuthor(){
        return mAuthor;
    }

    /**
     * return the section of the news
     */
    public String getSection(){
        return mSection;
    }

    /**
     * return the date of the news
     */
    public String getDdate(){
        return mDate;
    }

    /**
     * return the URL of the news
     */
    public String getUrl(){
        return mUrl;
    }
}
