package com.example.newsapp;

public class News {
    String mNewsHeasdline;
    String mDescription;
    String mImageUrl;
    String mDatePublished;


    public News(String newsHeasdline, String description,String imageUrl,String datePublished) {
        mNewsHeasdline = newsHeasdline;
        mDescription = description;
        mImageUrl=imageUrl;
        mDatePublished=datePublished;
    }

    public String getNewsHeadline() {
        return mNewsHeasdline;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getmDatePublished() {
        return mDatePublished;
    }
}
