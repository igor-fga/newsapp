package com.example.ifgan.newsapp;

import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;
import java.util.List;

/**
 * Created by ifgan on 07/08/2017.
 */

public class NewsService extends AsyncTask<URL, Void, List<Article>>{

    /**
     * URL to query the Guardian dataset for section Tecnology information
     */
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/technology?api-key=test";

    private AsyncTaskDelegate delegate = null;

    //Responsavel por responder a requisição após a execução
    //Esse responsável é o AsyncTaskDelegate
    public NewsService(AsyncTaskDelegate responder){
        this.delegate = responder;
    }

    @Override
    protected List<Article> doInBackground(URL... params) {

        if (GUARDIAN_REQUEST_URL == null) {
            return null;
        }

        List<Article> articles = QueryUtils.fetchNewsData(GUARDIAN_REQUEST_URL);

        return articles;
    }

    @Override
    protected void onPostExecute(List<Article> articles) {
        super.onPostExecute(articles);
        if(delegate != null)
            delegate.processFinish(articles);
    }
}
