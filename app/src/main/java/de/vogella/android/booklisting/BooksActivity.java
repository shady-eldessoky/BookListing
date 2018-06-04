package de.vogella.android.booklisting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.app.LoaderManager.LoaderCallbacks;
import android.widget.Toast;
;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BooksActivity extends AppCompatActivity {
    public static final String LOG_TAG = BooksActivity.class.getName();

    private static String BOOK_API_REQUEST = "https://www.googleapis.com/books/v1/volumes";
    private BookAdapter mAdapter;
    private TextView emptyStateTextView;
    private EditText topic;
    private String searchTopic;
    Uri.Builder uriBuilder;
    SharedPreferences sharedPrefs;
    View loadeIndecator;
    TextView bookShelve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        bookShelve =(TextView)findViewById(R.id.book_shelve);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/ASMAN.TTF");
        bookShelve.setTypeface(typeface);
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        topic = (EditText) findViewById(R.id.editText);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        ListView booksListView = (ListView) findViewById(R.id.lists);
        emptyStateTextView = (TextView) findViewById(R.id.empty_view);
        booksListView.setEmptyView(emptyStateTextView);
        booksListView.setAdapter(mAdapter);

        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Book currentBook = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
        loadeIndecator = findViewById(R.id.loading_indicator);
        loadeIndecator.setVisibility(View.GONE);

    }


    public void subOrder(View view) {
        loadeIndecator.setVisibility(View.VISIBLE);
        searchTopic = topic.getText().toString();

        ConnectivityManager connect = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connect.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (TextUtils.isEmpty(searchTopic)) {
                Toast.makeText(this, "please write Topic's name", Toast.LENGTH_SHORT).show();
                loadeIndecator.setVisibility(View.GONE);
                emptyStateTextView.setText("no_books_found");
            } else {
                BookAsyncTask task = new BookAsyncTask();
                task.execute();
            }
        } else {
            loadeIndecator.setVisibility(View.GONE);
            emptyStateTextView.setText("no_internet_connectivity");
        }

    }


    private class BookAsyncTask extends AsyncTask<URL, Void, List<Book>> {
        @Override
        protected List<Book> doInBackground(URL... params) {
            Log.i(LOG_TAG, "loadInBackground : called");


            String maxResult = sharedPrefs.getString(
                    getString(R.string.settings_max_result_key),
                    getString(R.string.settings_max_result_default));

            String orderBy = sharedPrefs.getString(
                    getString(R.string.settings_order_by_key),
                    getString(R.string.settings_order_by_default)
            );

            Uri baseUri = Uri.parse(BOOK_API_REQUEST);
            uriBuilder = baseUri.buildUpon();
            uriBuilder.appendQueryParameter("printType", "books");
            uriBuilder.appendQueryParameter("q", searchTopic);
            uriBuilder.appendQueryParameter("orderBy", orderBy);
            uriBuilder.appendQueryParameter("maxResults", maxResult);

            if (uriBuilder.toString() == null) {
                return null;
            }

            List<Book> books = BookService.fetchBooksData(uriBuilder.toString());
            return books;
        }

        @Override
        protected void onPostExecute(List<Book> book) {
            Log.i(LOG_TAG, "onLoadFinnish : called");
            searchTopic = topic.getText().toString();

            mAdapter.clear();

            if (book != null && !book.isEmpty()) {
                mAdapter.addAll(book);
            }
            loadeIndecator.setVisibility(View.GONE);
            emptyStateTextView.setText("no_books_found");
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
