package com.rahulxyz.movieslook;

import android.content.Context;
import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by raul_Will on 6/6/2017.
 */

class NetworkUtils {

    // to build URL of api call
    public static URL buildUrl(Context context,String sortType) {
        Uri builtUri = Uri.parse(context.getString(R.string.Movie_Base_Url)).buildUpon()
                .appendPath(sortType)
                .appendQueryParameter(context.getString(R.string.Param_Api_key),context.getString(R.string.MyKey))
                .build();

        URL url=null;
        try {
             url =new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static URL buildPosterUrl(Context context,String relativePath, String size) {
        // "/basdbda" -> "basdbda"
        relativePath= relativePath.substring(1,relativePath.length());
        Uri builtUri = Uri.parse(context.getString(R.string.Poster_Base_Url))
                .buildUpon()
                .appendPath(size)
                .appendPath(relativePath)
                .build();

        URL url=null;
        try {
            url=new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //building url of video
    static URL buildVideoUrlWithMovieId(Context context, String id){
        Uri builtUri= Uri.parse(context.getResources().getString(R.string.Movie_Base_Url)).buildUpon()
                .appendPath(id)
                .appendPath(context.getResources().getString(R.string.videos_path))
                .appendQueryParameter(context.getResources().getString(R.string.api_key_query),context.getResources().getString(R.string.MyKey))
                .build();

        URL url = null;
        try{
            url= new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //building url of video
    static URL buildReviewUrlWithMovieId(Context context, String id){
        Uri builtUri= Uri.parse(context.getResources().getString(R.string.Movie_Base_Url)).buildUpon()
                .appendPath(id)
                .appendPath(context.getString(R.string.reviews))
                .appendQueryParameter(context.getResources().getString(R.string.api_key_query),context.getResources().getString(R.string.MyKey))
                .build();

        URL url = null;
        try{
            url= new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //buildng url of specific trailer
    static URL buildTrailerUrl(Context context, String key){
        Uri builtUri= Uri.parse(context.getString(R.string.youtube_base_url)).buildUpon()
                .appendQueryParameter(context.getResources().getString(R.string.youtube_key_query),key)
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    //creates HTTP connection using URL and will receive jsonData
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = urlConnection.getInputStream();
            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");
            boolean hasInput= sc.hasNext();
            if(hasInput){
                return sc.next();
            }else{
                return null;
            }
        }finally{
            urlConnection.disconnect();
        }
    }

}
