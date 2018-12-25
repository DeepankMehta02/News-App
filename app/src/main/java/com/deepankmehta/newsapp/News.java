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

import android.graphics.Bitmap;

public class News {

    /**
     * News title
     */
    private String mTitle;

    /**
     * News date
     */
    private String mDate;

    /**
     * News url
     */
    private String mUrl;

    /**
     * News thumbnail url
     */
    private String mThumbnailUrl;

    /**
     * News thumbnail bitmap
     */
    private Bitmap mBitmap;

    /**
     * Constructor
     */
    public News(String title, String date, String url, String thumbnailUrl) {
        this.mTitle = title;
        this.mDate = date;
        this.mUrl = url;
        this.mThumbnailUrl = thumbnailUrl;
    }

    /**
     * Getter methods
     */
    public String getTitle() {
        return this.mTitle;
    }

    public String getDate() {
        return this.mDate;
    }

    public String getUrl() {
        return this.mUrl;
    }

    public String getThumbnailUrl() {
        return this.mThumbnailUrl;
    }

    Bitmap getBitmap() {
        return this.mBitmap;
    }

    void setBitmap(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
