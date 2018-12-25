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

import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.deepankmehta.newsapp.BuildConfig.API_KEY;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Empty constructor
     */
    private QueryUtils() {
    }

    /**
     * Method that fetches news
     */
    public static List<News> fetchNews(String newsCategory) {
        // Creates a url object
        String requestUrl = "https://api.nytimes.com/svc/topstories/v2/%1$s.json?api-key=%2$s";
        String fullUrl = String.format(requestUrl, newsCategory, API_KEY);
        URL url = createUrl(fullUrl);

        // Empty string object to hold the parsed JSON response
        String jsonResponse = "";

        // Performs HTTP request to the above created URL
        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request for the search criteria");
        }

        // Extracts information from the JSON response for each news item
        List<News> news = QueryUtils.extractFeatures(jsonResponse);
        assert news != null;
        for (int i = 0; i < news.size(); i++) {
            News currentNews = news.get(i);
            String imgUrl = currentNews.getThumbnailUrl();
            if (!imgUrl.isEmpty()) {
                try {
                    currentNews.setBitmap(BitmapFactory.decodeStream((InputStream)
                            new URL(imgUrl).getContent()));
                } catch (MalformedURLException e) {
                    Log.e(LOG_TAG, "Problem parsing the thumbnail for the news");
                } catch (IOException e) {
                    Log.e(LOG_TAG, "IOException has occured");
                }
            }
        }
        return news;
    }

    /**
     * Returns new URL object from the given string url
     */
    private static URL createUrl(String stringUrl) {
        // Empty object to hold the parsed URL from the stringUrl
        URL url = null;

        // Parse valid url
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL");
        }
        return url;
    }

    /**
     * Extract data from the parsed JSON response
     */
    private static List<News> extractFeatures(String newsJSON) {

        // If no data found from the HTTP request
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        // Initialize list of strings to hold the extracted news item
        List<News> newsList = new ArrayList<>();

        // Traverse the JSON response
        try {
            // Create JSON object from response
            JSONObject baseJSONResponse = new JSONObject(newsJSON);

            // Extract
            JSONArray contentResults = baseJSONResponse.getJSONArray("results");
            for (int i = 0; i < contentResults.length(); i++) {
                JSONObject currentStory = contentResults.getJSONObject(i);
                String contentTitle = currentStory.getString("title");
                String contentDate = currentStory.getString("published_date");

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.ENGLISH);

                Date date;
                SimpleDateFormat readable = new SimpleDateFormat("EEE, d MMM yyyy");
                String formattedDate = "";

                try {
                    date = dateFormat.parse(contentDate);
                    formattedDate = readable.format(date);
                } catch (ParseException e) {
                    Log.e(LOG_TAG, "Error parsing date");
                }

                String contentUrl = currentStory.getString("url");
                JSONArray contentMultimedia = currentStory.getJSONArray("multimedia");
                String imgUrl = "";
                if (contentMultimedia.length() > 0 && contentMultimedia.get(0) != null) {
                    JSONObject thumbnail = contentMultimedia.getJSONObject(2);
                    imgUrl = thumbnail.getString("url");
                }
                newsList.add(new News(contentTitle, formattedDate, contentUrl, imgUrl));
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON results");
        }
        return newsList;
    }

    /**
     * Make an HTTP request
     */
    private static String makeHTTPRequest(URL url) throws IOException {
        // Hold the parsed JSON response
        String jsonResponse = "";

        // Return if url is null
        if (url == null) {
            return jsonResponse;
        }

        // Initialize HTTP connection
        HttpURLConnection urlConnection = null;

        // Initialize InputStream to hold json response from request
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error while connecting, Error code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving news results");
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Converts inputStream into a String
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();

            }
        }

        return output.toString();
    }
}
