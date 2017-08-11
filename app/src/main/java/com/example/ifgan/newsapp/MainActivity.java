package com.example.ifgan.newsapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskDelegate {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isOnline()) {
            Toast.makeText(MainActivity.this, R.string.disconnect, Toast.LENGTH_SHORT).show();
            return;
        }

        NewsService task = new NewsService(MainActivity.this);
        task.execute();
    }

    @Override
    public void processFinish(Object output) {
        if(output != null){
            //Recupero a lista retornada pelo asynctask
            List<Article> articles = (List<Article>) output;

            // Find a reference to the {@link ListView} in the layout
            ListView newsListView = (ListView) findViewById(R.id.list);

            final ArticleAdapter adapter = new ArticleAdapter(MainActivity.this, articles);

            newsListView.setAdapter(adapter);

            // Set an item click listener on the ListView, which sends an intent to a web browser
            // to open a website with more information about the selected article.
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current article that was clicked on
                    Article currentArticle = adapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri articleUri = Uri.parse(currentArticle.getUrl());

                    // Create a new intent to view the article URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });



        }else{
            Toast.makeText(this, "Falha na conexão", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Check the internet conectivity
     *
     * @return true for online and false for disconnect
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
