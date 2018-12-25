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
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NewsCategoryAdapter extends FragmentPagerAdapter {

    private Resources mResources;

    NewsCategoryAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        mResources = context.getResources();
    }

    @Override
    public Fragment getItem(int position) {
        // Url to query the API
        String category;

        // Different fragments for different sections
        Fragment fragment;

        switch (position) {
            case 0:
                // Url for home news section
                category = "world";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 1:
                // Url for home news section
                category = "technology";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 2:
                // Url for home news section
                category = "sports";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 3:
                // Url for home news section
                category = "business";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 4:
                // Url for home news section
                category = "travel";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 5:
                // Url for home news section
                category = "politics";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 6:
                // Url for home news section
                category = "health";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 7:
                // Url for home news section
                category = "movies";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 8:
                // Url for home news section
                category = "science";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            case 9:
                // Url for home news section
                category = "automobiles";
                fragment = ContentFragment.newInstance(category);
                return fragment;

            default:
                return null;
        }
    }

    /**
     * Tells the adapter the total number of fragments views
     */
    @Override
    public int getCount() {
        return 10;
    }

    /**
     * Sets the tabs title
     */
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "World";
            case 1:
                return "Technology";
            case 2:
                return "Sports";
            case 3:
                return "Business";
            case 4:
                return "Travel";
            case 5:
                return "Politics";
            case 6:
                return "Health";
            case 7:
                return "Movies";
            case 8:
                return "Science";
            case 9:
                return "Automobiles";
            default:
                return null;
        }
    }
}
