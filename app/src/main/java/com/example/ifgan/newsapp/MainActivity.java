package com.example.ifgan.newsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AsyncTaskDelegate {

    @Override
    public void processFinish(Object output) {
        if(output != null){
            //Recupero a lista retornada pelo asynctask
            List<Article> articles = (List<Article>) output;

            // Find a reference to the {@link ListView} in the layout
            ListView newsListView = (ListView) findViewById(R.id.list);

            final ArticleAdapter adapter = new ArticleAdapter(MainActivity.this, articles);

            newsListView.setAdapter(adapter);

        }else{
            Toast.makeText(this, "Falha na conexão", Toast.LENGTH_LONG).show();
        }
    }
}
