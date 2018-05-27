package de.vogella.android.booklisting;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {

    private static final String BOOK_API_REQUEST = "https://www.googleapis.com/books/v1/volumes?q=%22football%22&printType=books&orderBy=newest&maxResults=10";
    public static final String LOG_TAG = BooksActivity.class.getName();
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        ListView booksListView = (ListView) findViewById(R.id.lists);
        booksListView.setAdapter(mAdapter);

        BookAsyncTask task = new BookAsyncTask();
        task.execute();

    }



    private class BookAsyncTask extends AsyncTask<URL, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(URL... urls) {

            ArrayList<Book> books = BookService.fetchBooksData(BOOK_API_REQUEST);

            return books;
        }

        /**
         * Update the screen with the given earthquake (which was the result of the
         * {@link BookAsyncTask}).
         */
        @Override
        protected void onPostExecute(List<Book> book) {
            mAdapter.clear();

            if(book !=null && !book.isEmpty()){
                mAdapter.addAll(book);}

        }

    }
}
