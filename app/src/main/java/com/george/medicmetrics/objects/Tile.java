package com.george.medicmetrics.objects;

public class Tile {

    private int mId;
    private int mImageId;
    private String mText;

    public Tile(int id, int imageId, String text) {
        mId = id;
        mImageId = imageId;
        mText = text;
    }

    public int getId() {
        return mId;
    }

    public int getImageId() {
        return mImageId;
    }

    public String getText() {
        return mText;
    }
}
