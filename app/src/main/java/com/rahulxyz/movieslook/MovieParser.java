package com.rahulxyz.movieslook;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.rahulxyz.movieslook.database.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by raul_Will on 6/6/2017.
 */

class MovieParser {

    //specify which table to set
    public static void setMovieData(Context context, String jsonData, Uri contentUri) throws JSONException {
        JSONObject entireData = new JSONObject(jsonData);
        JSONArray  results = entireData.getJSONArray(context.getString(R.string.Results));
        JSONObject eachMovie;

        //Log.d(MainActivity.tag,"inside parser "+jsonData);

        ContentValues[] contentValues = new ContentValues[results.length()];
        for(int i=0; i<results.length();i++){
            eachMovie= (JSONObject) results.get(i);
            contentValues[i]= new ContentValues();
            //Log.d(MainActivity.tag,(String) eachMovie.get(context.getString(R.string.Title)));
            contentValues[i].put(MovieContract.MovieEntry.MOVIE_ID, String.valueOf(eachMovie.getInt(context.getString(R.string.jsonMovieId))));
            contentValues[i].put(MovieContract.MovieEntry.TITLE, (String) eachMovie.get(context.getString(R.string.Title)));
            contentValues[i].put(MovieContract.MovieEntry.POSTER_PATH, (String) eachMovie.get(context.getString(R.string.Poster_Path)));
            contentValues[i].put(MovieContract.MovieEntry.PLOT, (String) eachMovie.get(context.getString(R.string.Plot)));
            contentValues[i].put(MovieContract.MovieEntry.RELEASE_DATE, (String) eachMovie.get(context.getString(R.string.Release_Date)));
            contentValues[i].put(MovieContract.MovieEntry.USER_RATING, eachMovie.get(context.getString(R.string.User_Rating)).toString());
        }
        context.getContentResolver().bulkInsert(contentUri, contentValues);

    }

    public static URL[] getVideoUrl(Context context, String jsonData) throws JSONException {
        JSONObject entireData= new JSONObject(jsonData);
        JSONArray results= entireData.getJSONArray(context.getString(R.string.Results));
        JSONObject eachVideo;
        URL[] url = new URL[results.length()];
        for(int i=0;i<results.length();i++){
            eachVideo = (JSONObject) results.get(i);

            url[i] = NetworkUtils.buildTrailerUrl(context, (String) eachVideo.get(context.getString(R.string.key_video_url)));
        }

        return url;
    }

    public static String[] getReviewList(Context context, String jsonData) throws JSONException {
        JSONObject entireData= new JSONObject(jsonData);
        JSONArray results= entireData.getJSONArray(context.getString(R.string.Results));
        JSONObject eachReview;
        String author,content;

        String[] review =new String[results.length()];
        for(int i=0;i<results.length();i++){
            eachReview = (JSONObject) results.get(i);
            author = eachReview.getString(context.getString(R.string.author_review));
            content = eachReview.getString(context.getString(R.string.content_review));
            review[i]=author+":\n\n"+content;
        }

        return review;
    }
}
