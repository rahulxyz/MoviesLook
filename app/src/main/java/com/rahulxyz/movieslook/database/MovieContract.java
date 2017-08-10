package com.rahulxyz.movieslook.database;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by raul_Will on 7/16/2017.
 */

public class MovieContract {

    public static final String AUTHORITY= "com.rahulxyz.movieslook.database";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    public static final String PATH_POPULAR = "popular";
    public static final String PATH_RATING = "rating";
    public static final String PATH_FAVOURITE = "favourite";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI_POPULAR = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR).build();
        public static final Uri CONTENT_URI_RATING = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RATING).build();
        public static final Uri CONTENT_URI_FAVOURITE= BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVOURITE).build();


        public static final String TABLE_NAME_POPULAR = "popular";
        public static final String TABLE_NAME_RATING = "rating";
        public static final String TABLE_NAME_FAVOURITE = "favourite";

        public static final String MOVIE_ID = "movie_id";
        public static final String TITLE = "title";
        public static final String POSTER_PATH = "poster_path";
        public static final String PLOT = "plot";
        public static final String RELEASE_DATE = "release_date";
        public static final String USER_RATING = "user_rating";

    }
}
