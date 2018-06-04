package de.vogella.android.booklisting;

/**
 * Created by User on 27/05/2018.
 */

public class Book {

    private String mTitle;
    private String mAuthor;
    private String mPublishDate;
    private String mImageUrl;
    private String mBookUrl;


    public Book(String title, String author, String date, String image, String url) {

        mTitle = title;
        mAuthor = author;
        mPublishDate = date;
        mImageUrl = image;
        mBookUrl = url;


    }

    public String getTitle() {
        return mTitle;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getDate() {
        return mPublishDate;
    }

    public String getImage() {
        return mImageUrl;
    }

    public String getUrl() {
        return mBookUrl;
    }


}
