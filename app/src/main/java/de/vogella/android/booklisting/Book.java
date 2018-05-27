package de.vogella.android.booklisting;

/**
 * Created by User on 27/05/2018.
 */

public class Book {

    private String mTitle ;
    private String mAuthor;
    private String mPublishDate;
    private int mPageCount;



    public Book (String title,String author,String date,int pages){

        mTitle = title;
        mAuthor = author;
        mPublishDate = date;
        mPageCount = pages;

    }

    public String getTitle(){
        return mTitle;
    }
    public String getAuthor(){
        return mAuthor;
    }
    public String getDate(){
        return mPublishDate;
    }
    public int getPages(){
        return mPageCount;
    }



}
