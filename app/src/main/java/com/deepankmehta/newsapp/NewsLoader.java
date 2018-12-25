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
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /**
     * Variables
     */
    private String newsCategory;
    private List<News> mData;

    public NewsLoader(Context context, String newsCategory) {
        super(context);
        this.newsCategory = newsCategory;
    }

    /**
     * onStartLoading method
     */
    @Override
    protected void onStartLoading() {
        if (mData != null) {
            deliverResult(mData);
        } else {
            forceLoad();
        }
    }

    /**
     * loadInBackground method
     */
    @Override
    public List<News> loadInBackground() {
        if (newsCategory == null) {
            return null;
        }
        return QueryUtils.fetchNews(newsCategory);
    }

    /**
     * deliverResult method
     */
    @Override
    public void deliverResult(List<News> data) {
        mData = data;
        super.deliverResult(data);
    }
}
