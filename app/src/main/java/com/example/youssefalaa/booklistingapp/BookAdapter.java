package com.example.youssefalaa.booklistingapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by youssef alaa on 30/05/2017.
 */

public class BookAdapter extends BaseAdapter {

    private Context mContext;
    private List<Book> list;


    public BookAdapter(Context mContext, List<Book> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_item, parent, false);
        }

        TextView publisher = (TextView) convertView.findViewById(R.id.book_publisher);
        TextView publishedDate = (TextView) convertView.findViewById(R.id.book_publishedDate);
        TextView description = (TextView) convertView.findViewById(R.id.book_description);
        TextView title = (TextView) convertView.findViewById(R.id.book_title);
        TextView author = (TextView) convertView.findViewById(R.id.book_author);

        title.setText(list.get(position).getTitle());
        author.setText(list.get(position).getAuthor());
        publisher.setText(list.get(position).getPublisher());
        publishedDate.setText(list.get(position).getPublishedDate());
        description.setText(list.get(position).getDescription());

        return convertView;
    }
}
