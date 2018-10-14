package com.example.android.newsapp1;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String GUARDIAN_URL =
            "http://content.guardianapis.com/search?q=debates&section=politics&show-tags=contributor&api-key=test";

    private int NEWS_LOADER_ID = 1;

    private NewsAdapter mAdapter;

    private TextView mEmptyStateTextView;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = findViewById(R.id.list);


        mProgressBar = findViewById(R.id.progess_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());


        newsListView.setAdapter(mAdapter);


        newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current news that was clicked on
                News currentNews = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        LoaderManager loaderManager = getLoaderManager();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if(isConnected){
            NEWS_LOADER_ID = 0;
            loaderManager.initLoader(NEWS_LOADER_ID,null,this);
        }else {
            mEmptyStateTextView = findViewById(R.id.empty_view);
            newsListView.setEmptyView(mEmptyStateTextView);

            mProgressBar.setVisibility(View.GONE);
        }
    }

    public NewsLoader onCreateLoader(int i, Bundle args) {
        // Create a new loader for the given URL
        return new NewsLoader(this, GUARDIAN_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        // Find a reference to the {@link ListView} in the layout
        ListView newsListView =  findViewById(R.id.list);

        mEmptyStateTextView = findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        mProgressBar.setVisibility(View.GONE);

        // Clear the adapter of previous news data
        mAdapter.clear();
        // If there is a valid list of news then add
        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}