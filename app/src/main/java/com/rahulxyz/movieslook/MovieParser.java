package com.rahulxyz.movieslook;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by raul_Will on 6/6/2017.
 */

class MovieParser {

    public static MovieCard[] getMovieData(Context context, String jsonData) throws JSONException {
        JSONObject entireData = new JSONObject(jsonData);
        JSONArray  results = entireData.getJSONArray(context.getString(R.string.Results));
        JSONObject eachMovie;

        MovieCard[] movieData=new MovieCard[results.length()];
        for(int i=0; i<results.length();i++){
            eachMovie= (JSONObject) results.get(i);
            movieData[i]= new MovieCard();


            movieData[i].title=(String) eachMovie.get(context.getString(R.string.Title));

            Log.d("Movie","Parser posterPath"+eachMovie.get(context.getString(R.string.Poster_Path)));
            movieData[i].poster_path=(String) eachMovie.get(context.getString(R.string.Poster_Path));
            movieData[i].plot= (String) eachMovie.get(context.getString(R.string.Plot));
            movieData[i].releaseDate = (String) eachMovie.get(context.getString(R.string.Release_Date));
            movieData[i].userRating = eachMovie.get(context.getString(R.string.User_Rating)).toString();
        }
    return movieData;
    }
}
