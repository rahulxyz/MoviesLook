package com.rahulxyz.movieslook;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rahulxyz.movieslook.database.MovieContract;


import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, LoaderManager.LoaderCallbacks<Cursor>{


    private TextView tv_grid_error;
    private GridView gridView;
    private ProgressBar progressBar;
    public static final String tag="moviesLook23";
    private OfflineAdapter offlineAdapter;

    public static final int POPULAR_LOADER_ID= 4;
    public static final int RATING_LOADER_ID = 5;
    public static final int FAVOURITE_LOADER_ID=6;

    public static final String ACTIVE_LOADER = "active_loader";
    public static final String LIST_POSITION = "list_position";
    private int mActiveLoaderId;
    private int mActiveGridPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        tv_grid_error= (TextView) findViewById(R.id.tv_grid_error);
        gridView = (GridView) findViewById(R.id.grid_main);
        gridView.setOnItemClickListener(this);




        Screen.setScreenResolution(this);
        gridView.setColumnWidth(Screen.width/5);
        offlineAdapter = new OfflineAdapter(this);

        if(savedInstanceState==null){
            new MovieSync().execute(POPULAR_LOADER_ID);
        }
    }


    private void showError(int loaderId){
        switch (loaderId){
            case POPULAR_LOADER_ID: tv_grid_error.setText(this.getString(R.string.network_error));
                break;
            case RATING_LOADER_ID:  tv_grid_error.setText(this.getString(R.string.network_error));
                break;
            case FAVOURITE_LOADER_ID: tv_grid_error.setText(R.string.favourite_error);
                break;
            default:throw new UnsupportedOperationException("Unknown Error");
        }

        tv_grid_error.setVisibility(View.VISIBLE);
        gridView.setVisibility(View.INVISIBLE);
    }

    private void showGridData(){
        tv_grid_error.setVisibility(View.INVISIBLE);
        gridView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT,position);
        intent.putExtra(getString(R.string.activeloader),mActiveLoaderId);
        startActivity(intent);
    }

    //downloads database from network and adds to database and initialises loader
    private class MovieSync extends AsyncTask<Integer, Void,Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            //getting url of whole jsonData
            URL activeURL;
            Uri activeUri;
            switch (params[0]){
                case POPULAR_LOADER_ID:
                    activeURL = NetworkUtils.buildUrl(MainActivity.this,getBaseContext().getResources().getString(R.string.Popularity_Url_Path));
                    activeUri = MovieContract.MovieEntry.CONTENT_URI_POPULAR;
                    break;
                case RATING_LOADER_ID:
                    activeURL = NetworkUtils.buildUrl(MainActivity.this,getBaseContext().getResources().getString(R.string.Rating_Url_Path));
                    activeUri = MovieContract.MovieEntry.CONTENT_URI_RATING;
                    break;
                default: throw new UnsupportedOperationException("Unknown Loader Id "+params[0]);
            }
            try {
                //to get entire json database of movie from url
                String jsonData= NetworkUtils.getResponseFromHttpUrl(activeURL);

                //set movie database into database
                MovieParser.setMovieData(MainActivity.this,jsonData, activeUri);

            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }
            return params[0];

        }


        @Override
        protected void onPostExecute(Integer Active_Loader_ID) {
            progressBar.setVisibility(View.INVISIBLE);
            //if offline database available showOfflineData else showError
            showOfflineData(Active_Loader_ID);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ACTIVE_LOADER,mActiveLoaderId);
        outState.putInt(LIST_POSITION, gridView.getFirstVisiblePosition());

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mActiveLoaderId = savedInstanceState.getInt(ACTIVE_LOADER);
        mActiveGridPosition = savedInstanceState.getInt(LIST_POSITION);
        getSupportLoaderManager().restartLoader(mActiveLoaderId,null,this);
    }


    public void setScrollPosition(){
        gridView.setSelection(mActiveGridPosition);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mActiveLoaderId== FAVOURITE_LOADER_ID)
            getSupportLoaderManager().restartLoader(mActiveLoaderId,null,this);
    }

    private void showOfflineData(int loaderId){
        mActiveLoaderId = loaderId;
            getSupportLoaderManager().restartLoader(loaderId,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int clickedItem = item.getItemId();
        switch(clickedItem){
            case R.id.action_sortByPopularity:
                new MovieSync().execute(POPULAR_LOADER_ID);
                return true;
            case R.id.action_sortByRating:
                new MovieSync().execute(RATING_LOADER_ID);
                return true;
            case R.id.action_sortByFavourite:
                showOfflineData(FAVOURITE_LOADER_ID);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri activeUri=null;
        switch (id) {
            case POPULAR_LOADER_ID:
                activeUri = MovieContract.MovieEntry.CONTENT_URI_POPULAR;
                break;
            case RATING_LOADER_ID:
                activeUri = MovieContract.MovieEntry.CONTENT_URI_RATING;
                break;
            case FAVOURITE_LOADER_ID:
                activeUri = MovieContract.MovieEntry.CONTENT_URI_FAVOURITE;
                break;
            default: throw new UnsupportedOperationException("Unknown Uri At Query ");
        }

        final Uri finalActiveUri = activeUri;
        return new AsyncTaskLoader<Cursor>(this) {

            Cursor mCursor = null;

            @Override
            protected void onStartLoading() {
                if(mCursor!= null) {
                    //deliver any previous loaded database immediately
                    deliverResult(mCursor);
                }
                else {
                    forceLoad();
                }
            }

            @Override
            public Cursor loadInBackground() {
                try{
                     return getContentResolver().query(
                            finalActiveUri,
                            null,
                            null,
                            null,
                            MovieContract.MovieEntry._ID);

                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }

            }

            public void deliverResult(Cursor newCursor){
                mCursor = newCursor;
                super.deliverResult(mCursor);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data == null) {
            return;
        }

        if(data.getCount()>0){
            showGridData();
            offlineAdapter.swapCursor(data);
            gridView.setAdapter(offlineAdapter);
            setScrollPosition();
        }else{
            showError(mActiveLoaderId);
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }



}
