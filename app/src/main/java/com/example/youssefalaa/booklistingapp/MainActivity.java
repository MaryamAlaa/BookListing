package com.example.youssefalaa.booklistingapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText searchText;
    private Button searchButton;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        searchText = (EditText) findViewById(R.id.search_edit_text);
        searchButton = (Button) findViewById(R.id.search_button);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isConnected(MainActivity.this)) {
                    text = searchText.getText().toString().trim();
                    Intent intent = new Intent(MainActivity.this, ListActivity.class);
                    intent.putExtra("text", text);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplication(), "There is no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
