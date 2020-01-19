package com.example.streethelper;

public class ReportItem {
    private int mImageResource;
    private String mText1;
    private String mText2;

    /**
     * Constructor for ReportItem.
     * @param imageResource
     * @param text1
     * @param text2
     */
    public ReportItem(int imageResource, String text1, String text2){
        mImageResource=imageResource;
        mText1=text1;
        mText2=text2;
    }

    //Getters for image and texts
    public int getImageResource(){
        return mImageResource;
    }

    public String getText1(){
        return mText1;
    }

    public String getText2(){
        return mText2;
    }
}
