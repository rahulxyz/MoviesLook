package com.rahulxyz.movieslook;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

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

    private final Context context;

    public NetworkUtils(Context c){
        this.context = c;
    }

    // to build URL of api call
    public URL buildUrl(String sortType) {
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
        Log.d("Movie","url-> "+url);
        return url;
    }


    public URL buildPosterUrl(String relativePath, String size) {

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
        Log.d("Movie","Posterurl-> "+url);
        return url;
    }

    //creates HTTP connection using URL and will receive jsonData
    public String getResponseFromHttpUrl(URL url) throws IOException {
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
