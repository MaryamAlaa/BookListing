package com.example.youssefalaa.booklistingapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by youssef alaa on 30/05/2017.
 */

public class ListActivity extends AppCompatActivity {

    private ListView bookList;
    private String keyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        bookList = (ListView) findViewById(R.id.books_list);
        keyWord = getIntent().getExtras().getString("text");
        new BookTask().execute(keyWord);
    }


    private class BookTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String reviewJsonStr = null;
            try {
                String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
                BOOK_BASE_URL += keyWord;

                final String APP_ID = "key";

                Uri uri = Uri.parse(BOOK_BASE_URL).buildUpon()
                        .appendQueryParameter(APP_ID, getString(R.string.api_key)).build();

                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
                    return null;
                }
                reviewJsonStr = buffer.toString();
            } catch (IOException e) {
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                    }
                }
            }
            try {
                return reviewJsonStr;
            } catch (Exception e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            List<Book> list;
            list = getParsedList(s);

                BookAdapter adapter = new BookAdapter(ListActivity.this, list);
                bookList.setAdapter(adapter);
            if(list.size()==0) {
                Toast.makeText(getApplication(), "There is no information about this searsh", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<Book> getParsedList(String s) {
        List<Book> list = new ArrayList<>();
        try {
            JSONObject rootObject = new JSONObject(s);
            JSONArray itemsArray = rootObject.optJSONArray("items");
            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject subObject = itemsArray.getJSONObject(i);
                JSONObject volumeObject = subObject.optJSONObject("volumeInfo");
                String title = volumeObject.optString("title");
                String publisher = volumeObject.optString("publisher");
                String publishedDate = volumeObject.optString("publishedDate");
                String description = volumeObject.optString("description");
                JSONArray authorArray = volumeObject.optJSONArray("authors");
                String author = "";
                for (int j = 0; j < authorArray.length(); j++) {
                    author = authorArray.optString(0);
                }
                list.add(new Book("title: " + title, "publisher: " + publisher, "publishedDate: " + publishedDate, "description: " + description, "author: " + author));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}

