package com.rahulxyz.movieslook.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by raul_Will on 7/16/2017.
 */

 class MovieDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "moviesLook.db";
    private static final int DATABASE_VERSION = 19;

    public MovieDbHelper(Context c){
        super(c, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String POPULAR = "CREATE TABLE "+ MovieContract.MovieEntry.TABLE_NAME_POPULAR+"(" +
                MovieContract.MovieEntry._ID+" INTEGER, " +
                MovieContract.MovieEntry.MOVIE_ID+" TEXT PRIMARY KEY, " +
                MovieContract.MovieEntry.TITLE+" TEXT, " +
                MovieContract.MovieEntry.POSTER_PATH+" TEXT, " +
                MovieContract.MovieEntry.PLOT+" TEXT, " +
                MovieContract.MovieEntry.RELEASE_DATE+" TEXT, " +
                MovieContract.MovieEntry.USER_RATING+" TEXT," +
                " UNIQUE (" + MovieContract.MovieEntry.MOVIE_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(POPULAR);

        String RATING = "CREATE TABLE "+ MovieContract.MovieEntry.TABLE_NAME_RATING+"(" +
                MovieContract.MovieEntry._ID+" INTEGER, " +
                MovieContract.MovieEntry.MOVIE_ID+" TEXT PRIMARY KEY, " +
                MovieContract.MovieEntry.TITLE+" TEXT, " +
                MovieContract.MovieEntry.POSTER_PATH+" TEXT, " +
                MovieContract.MovieEntry.PLOT+" TEXT, " +
                MovieContract.MovieEntry.RELEASE_DATE+" TEXT, " +
                MovieContract.MovieEntry.USER_RATING+" TEXT," +
                " UNIQUE (" + MovieContract.MovieEntry.MOVIE_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(RATING);

        String FAVOURITE = "CREATE TABLE "+ MovieContract.MovieEntry.TABLE_NAME_FAVOURITE+"(" +
                MovieContract.MovieEntry._ID+" INTEGER, " +
                MovieContract.MovieEntry.MOVIE_ID+" TEXT PRIMARY KEY, " +
                MovieContract.MovieEntry.TITLE+" TEXT, " +
                MovieContract.MovieEntry.POSTER_PATH+" TEXT, " +
                MovieContract.MovieEntry.PLOT+" TEXT, " +
                MovieContract.MovieEntry.RELEASE_DATE+" TEXT, " +
                MovieContract.MovieEntry.USER_RATING+" TEXT," +
                " UNIQUE (" + MovieContract.MovieEntry.MOVIE_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(FAVOURITE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String POP = "DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME_POPULAR;
        String RAT = "DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME_RATING;
        String FAV = "DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME_FAVOURITE;
        db.execSQL(POP);
        db.execSQL(RAT);
        db.execSQL(FAV);
        onCreate(db);
    }
}
