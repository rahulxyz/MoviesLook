package com.rahulxyz.movieslook.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rahulxyz.movieslook.MainActivity;

/**
 * Created by raul_Will on 7/16/2017.
 */

public class MovieProvider extends ContentProvider {

    private MovieDbHelper movieDbHelper;
    private static final int POPULAR = 500;
    private static final int POPULAR_WITH_ID= 501;
    private static final int RATING = 600;
    private static final int RATING_WITH_ID = 601;
    private static final int FAVOURITE =700;
    private static final int FAVOURITE_WITH_ID =701;

    private static final UriMatcher sUriMatcher = builUri();

    private static UriMatcher builUri(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI( MovieContract.AUTHORITY, MovieContract.PATH_POPULAR, POPULAR);
        uriMatcher.addURI( MovieContract.AUTHORITY, MovieContract.PATH_POPULAR+"/#", POPULAR_WITH_ID);

        uriMatcher.addURI( MovieContract.AUTHORITY, MovieContract.PATH_RATING, RATING);
        uriMatcher.addURI( MovieContract.AUTHORITY, MovieContract.PATH_RATING+"/#", RATING_WITH_ID);

        uriMatcher.addURI( MovieContract.AUTHORITY, MovieContract.PATH_FAVOURITE, FAVOURITE);
        uriMatcher.addURI( MovieContract.AUTHORITY, MovieContract.PATH_FAVOURITE+"/#", FAVOURITE_WITH_ID);

        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        Context context= getContext();
        movieDbHelper= new MovieDbHelper(context);
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int rowsInserted = 0;
        String current_table;

        switch(sUriMatcher.match(uri)) {

            case POPULAR:
                current_table = MovieContract.MovieEntry.TABLE_NAME_POPULAR;
                break;

            case RATING:
                current_table = MovieContract.MovieEntry.TABLE_NAME_RATING;
                break;

            default:
                return super.bulkInsert(uri, values);
        }

        db.beginTransaction();
        try {
            for (ContentValues value : values) {
                long id = db.insert(current_table,
                        null,
                        value);
                if(id !=-1)
                    rowsInserted++;
            }
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

        if(rowsInserted>0){
            try {
                getContext().getContentResolver().notifyChange(uri, null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return rowsInserted;


    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase db= movieDbHelper.getReadableDatabase();
        int id= sUriMatcher.match(uri);
        Cursor cursor;
        String movieId,mSelection;
        String[] mSelectionArgs;
        switch (id){

            case POPULAR:
                cursor = db.query(MovieContract.MovieEntry.TABLE_NAME_POPULAR,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case POPULAR_WITH_ID:
                movieId= uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.MOVIE_ID+"=?";
                mSelectionArgs = new String[]{movieId};
                cursor = db.query(MovieContract.MovieEntry.TABLE_NAME_POPULAR,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case RATING:
                cursor = db.query(MovieContract.MovieEntry.TABLE_NAME_RATING,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case RATING_WITH_ID:
                movieId= uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.MOVIE_ID+"=?";
                mSelectionArgs = new String[]{movieId};
                cursor = db.query(MovieContract.MovieEntry.TABLE_NAME_RATING,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case FAVOURITE:
                cursor = db.query(MovieContract.MovieEntry.TABLE_NAME_FAVOURITE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case FAVOURITE_WITH_ID:
                movieId= uri.getPathSegments().get(1);
                mSelection = MovieContract.MovieEntry.MOVIE_ID+"=?";
                mSelectionArgs = new String[]{movieId};
                cursor = db.query(MovieContract.MovieEntry.TABLE_NAME_FAVOURITE,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:throw new UnsupportedOperationException("Unknown Uri in query: "+uri);
        }
        try {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }catch (Exception e){
            e.printStackTrace();
        }

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db= movieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match){

            case FAVOURITE:
                long id = db .insert(MovieContract.MovieEntry.TABLE_NAME_FAVOURITE, null, values);

                if(id >0){
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI_FAVOURITE, id);
                }else
                    throw new SQLException("Failed to insert row into "+ uri);
                break;

            default: throw new UnsupportedOperationException("Unknown Uri "+ uri);
        }

        //notifies any database and UI accordingly that changes have occured
        try {
            getContext().getContentResolver().notifyChange(uri, null);
        }catch (Exception e){
            e.printStackTrace();
        }

        return returnUri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String movieId,mSelection;
        String[] mSelectionArgs;
        SQLiteDatabase db = movieDbHelper.getWritableDatabase();
        int match= sUriMatcher.match(uri);
        int rowsDeleted= 0;
        movieId= uri.getPathSegments().get(1);
        mSelection = MovieContract.MovieEntry.MOVIE_ID+"=?";
        mSelectionArgs = new String[]{movieId};
        switch (match){
            case FAVOURITE_WITH_ID:
                try {
                    rowsDeleted =db.delete(MovieContract.MovieEntry.TABLE_NAME_FAVOURITE,
                            mSelection,
                            mSelectionArgs);
                }catch(Exception e){
                    e.printStackTrace();
                    Log.d(MainActivity.tag,"delete error");
                }
                break;
            default: throw new UnsupportedOperationException("Unknown Uri while delete "+uri);
        }

        if(rowsDeleted!=0) {
            try {
                getContext().getContentResolver().notifyChange(uri, null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }

        return rowsDeleted;
    }


    //not required
    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
     /*   int match = sUriMatcher.match(uri);
        int taskUpdated;
        switch (match){
            case MOVIESLOOK_WITH_ID:
                String id = uri.getPathSegments().get(1);
                taskUpdated = movieDbHelper.getWritableDatabase().update(
                        MovieContract.MovieEntry.TABLE_NAME,
                        values,
                        MovieContract.MovieEntry.MOVIE_ID+"=?",
                        new String[]{id}
                );
                //Log.d(MainActivity.tag,"updated "+taskUpdated);
                break;
            default: throw new UnsupportedOperationException("Unknown Uri "+uri);
        }
        if(taskUpdated!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return taskUpdated;*/
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
