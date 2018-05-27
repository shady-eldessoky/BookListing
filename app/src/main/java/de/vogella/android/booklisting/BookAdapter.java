package de.vogella.android.booklisting;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by User on 27/05/2018.
 */

public class BookAdapter extends ArrayAdapter<Book> {




    public BookAdapter(Activity context, ArrayList<Book> books) {

        super(context, 0, books);

    }


    @Override              // 1                          listView
    public View getView(int position, View convertView, ViewGroup parent) {
        // listItemView ("one" , "lutti")

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.book_list_item, parent, false);
        }


        Book currentBook = getItem(position);

        // Find the TextView with view ID magnitude

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentBook.getTitle());


        TextView author = (TextView) listItemView.findViewById(R.id.author);
        author.setText(currentBook.getAuthor());




        TextView date = (TextView) listItemView.findViewById(R.id.dates);
        date.setText(currentBook.getDate());


        TextView pages = (TextView) listItemView.findViewById(R.id.pages);
        pages.setText(currentBook.getPages()+"");

        return listItemView;
    }
}
