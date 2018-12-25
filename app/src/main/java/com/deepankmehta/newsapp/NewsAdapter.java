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
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.CardViewHolder> {

    /**
     * Variables
     */
    private List<News> mNewsList;
    private Context mContext;

    /**
     * Creates new adapter to display list of news content
     */
    NewsAdapter(Context context, List<News> news) {
        this.mNewsList = news;
        this.mContext = context;
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        // Variables
        TextView newsTitle;
        TextView newsDate;
        ImageView newsImage;
        CardView contentCard;

        CardViewHolder(View itemView) {
            super(itemView);

            newsTitle = itemView.findViewById(R.id.title_text_view);
            newsDate = itemView.findViewById(R.id.date_text_view);
            newsImage = itemView.findViewById(R.id.image);
            contentCard = itemView.findViewById(R.id.content_card);
        }
    }

    /**
     * This method is called when there is no View Group to recycle
     */
    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.content_card, parent, false);
        return new CardViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final CardViewHolder cardViewHolder, int position) {
        // Get the current news item
        final News currentItem = mNewsList.get(position);
        cardViewHolder.newsTitle.setText(currentItem.getTitle());
        cardViewHolder.newsDate.setText(currentItem.getDate());

        cardViewHolder.contentCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(currentItem.getUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                if (webIntent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(webIntent);
                }
            }
        });

        // Check whether or not the current news item has a thumbnail or not
        if (currentItem.getBitmap() == null) {
            cardViewHolder.newsImage.setScaleType(ImageView.ScaleType.CENTER);
            cardViewHolder.newsImage.setImageResource(R.drawable.no_thumbnail);
        } else {
            cardViewHolder.newsImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            cardViewHolder.newsImage.setImageBitmap(currentItem.getBitmap());
        }

    }

    /**
     * Tells the recycler view the total number of items in the data set
     */
    @Override
    public int getItemCount() {
        return mNewsList.size();
    }

    /**
     * Clear the adaptr's data set
     */
    public void clear() {
        mNewsList = new ArrayList<>();
    }

    /**
     * Adds all the data to the adapter's data set
     */
    public void addAll(List<News> data) {
        for (int i = 0; i < data.size(); i++) {
            News contentNews = data.get(i);
            mNewsList.add(contentNews);
            notifyDataSetChanged();
        }
    }

}
