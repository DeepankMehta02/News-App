/*
 * Copyright 2018 Deepank Mehta. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * You may not use this file; except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * Distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * Limitations under the License.
 */

package com.deepankmehta.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>> {

    /**
     * Adapter that hold the contents
     */
    private NewsAdapter adapter;

    /**
     * Loader ID
     */
    private static final int LOADER_ID = 1;

    /**
     * News category
     */
    private String newsCategory;

    /**
     * Progressbar
     */
    private ProgressBar progressBar;

    /**
     * String key for the news category
     */
    private static final String NEWS_CATEGORY = "CATEGORY";

    /**
     * Empty constructor
     */
    public ContentFragment() {

    }

    /**
     * Creating values for fragments
     */
    public static ContentFragment newInstance(String newsCategory) {
        // Base fragment to reuse
        ContentFragment fragment = new ContentFragment();

        // Initialize bundle
        Bundle bundle = new Bundle(1);

        // String url parameter to pass args when recreating Content Fragment
        bundle.putString(NEWS_CATEGORY, newsCategory);

        // Save args to the fragment
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        // Get the passed in args in the fragment
        if (getArguments() != null) {
            newsCategory = getArguments().getString(NEWS_CATEGORY);
        }
    }

    /**
     * Creates the fragment UI view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstamce) {
        // Inflate the UI from the fragment
        final View mainView = inflater.inflate(R.layout.recycler_activity, viewGroup, false);

        // Creates the adapter
        adapter = new NewsAdapter(getContext(), new ArrayList<News>());

        // Reference to the recycler view
        RecyclerView recyclerView = mainView.findViewById(R.id.recycler_view);

        // Set the layout for the recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        // Sets the adapter
        recyclerView.setAdapter(adapter);

        // Reference to the progressbar
        progressBar = mainView.findViewById(R.id.progress_bar);

        // Reference to the empty text view
        TextView emptyTextView = mainView.findViewById(R.id.empty_text_view);

        // Checks the internet connectivity
        if (getArguments() != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                // If there is a network connection available, then fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    LoaderManager mLoaderManager = getLoaderManager();
                    mLoaderManager.initLoader(LOADER_ID, null, this);

                } else {
                    // Otherwise, display the error
                    progressBar.setVisibility(View.GONE);
                    emptyTextView.setText("No Internet Conenction");
                }
            }
        }
        return mainView;

    }

    /**
     * onCreateLoader method
     */
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
        return new NewsLoader(getActivity(), newsCategory);
    }

    /**
     * onLoadFinished method
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        progressBar.setVisibility(View.GONE);
        adapter.clear();
        if (news != null && !news.isEmpty()) {
            adapter.addAll(news);
        }
    }

    /**
     * onLoaderReset method
     */
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        adapter.clear();
    }
}
