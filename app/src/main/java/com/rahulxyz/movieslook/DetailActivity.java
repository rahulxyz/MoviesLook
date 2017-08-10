package com.rahulxyz.movieslook;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.rahulxyz.movieslook.database.MovieContract;
import com.rahulxyz.movieslook.databinding.ActivityDetailBinding;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity implements ListAdapter.ListItemClickListener, LoaderManager.LoaderCallbacks<Cursor>{

    private int mPosition, mActiveLoaderId;
    private ActivityDetailBinding mBinding;
    private ListAdapter listAdapter;
    private ReviewAdapter reviewAdapter;
    private String mMovieId;
    private Boolean like= false;
    private MovieCard movieCard;

    public final String LIST_POSITION = "list_position";
    private int[] mActivePosition ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding= DataBindingUtil.setContentView(this,R.layout.activity_detail);

        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            mPosition = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
            mActiveLoaderId = intent.getIntExtra(getString(R.string.activeloader),0);
        }
        listAdapter= new ListAdapter(this, this);
        reviewAdapter = new ReviewAdapter(this);

        if(savedInstanceState==null){
            getSupportLoaderManager().initLoader(mActiveLoaderId,null,DetailActivity.this);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(MainActivity.ACTIVE_LOADER,mActiveLoaderId);
        outState.putIntArray(LIST_POSITION,
                new int[]{mBinding.detailScroll.getScrollX(),mBinding.detailScroll.getScrollY()});
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mActiveLoaderId = savedInstanceState.getInt(MainActivity.ACTIVE_LOADER);
        mActivePosition = savedInstanceState.getIntArray(LIST_POSITION);
        getSupportLoaderManager().restartLoader(mActiveLoaderId,null,this);
    }


    @Override
 public Loader<Cursor> onCreateLoader(int id, Bundle args) {
     Uri activeUri;
     switch (id) {
         case MainActivity.POPULAR_LOADER_ID:
             activeUri = MovieContract.MovieEntry.CONTENT_URI_POPULAR;
             break;
         case MainActivity.RATING_LOADER_ID:
             activeUri = MovieContract.MovieEntry.CONTENT_URI_RATING;
             break;
         case MainActivity.FAVOURITE_LOADER_ID:
             activeUri = MovieContract.MovieEntry.CONTENT_URI_FAVOURITE;
             break;
         default: throw new UnsupportedOperationException("Unknown Uri At Query ");
     }

     final Uri finalActiveUri = activeUri;
     return new AsyncTaskLoader<Cursor>(this) {

         Cursor cursor=null;
         @Override
         protected void onStartLoading() {
             if(cursor!= null)
                 deliverResult(cursor);
             else
                 forceLoad();
         }

         @Override
         public Cursor loadInBackground() {
             try{
                 cursor = getContentResolver().query(
                         finalActiveUri,
                         null,
                         null,
                         null,
                         MovieContract.MovieEntry._ID);
             }catch (Exception e){
                 e.printStackTrace();
             }

             return cursor;
         }

         @Override
         public void deliverResult(Cursor data) {
             cursor= data;
             super.deliverResult(data);
         }
     };
 }

    public void setScrollPosition(){
        if(mActivePosition!=null){
            mBinding.detailScroll.post(new Runnable() {
                @Override
                public void run() {
                    mBinding.detailScroll.scrollTo(mActivePosition[0],mActivePosition[1]);
                }
            });
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(data==null){
            Log.d(MainActivity.tag,"Error");
            //showing default view
        }else{
            getDataFromCursor(data);
            setDataToDisplay();
            setScrollPosition();
            new TrailerData().execute();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private void getDataFromCursor(Cursor cursor){
        try {
            if (cursor == null && !cursor.moveToFirst())
                return;
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        cursor.moveToPosition(mPosition);
        movieCard = new MovieCard();
        movieCard.movieId=cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.MOVIE_ID));
        movieCard.poster_path = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.POSTER_PATH));
        movieCard.title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.TITLE));
        movieCard.releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.RELEASE_DATE));
        movieCard.userRating = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.USER_RATING));
        movieCard.plot = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.PLOT));
        cursor.close();
    }

    private void setDataToDisplay(){

        mMovieId = movieCard.movieId;
        mBinding.secondary.title.setText(movieCard.title);
        mBinding.primary.releaseDate.setText(movieCard.releaseDate);
        mBinding.primary.rating.setText(movieCard.userRating);
        mBinding.secondary.plot.setText(movieCard.plot);

        //set star
        new CheckFavourite().execute();
        final String poster_path= movieCard.poster_path;
        if(poster_path != null) {
            final String urlString = NetworkUtils.buildPosterUrl(this, poster_path,Screen.sizeMax).toString();
            mBinding.primary.poster.setMaxHeight(Screen.height / 3);
            Picasso.with(this).load(urlString).into(mBinding.primary.poster);
            Picasso.with(this)
                    .load(urlString)
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mBinding.primary.poster, new Callback() {
                        @Override
                        public void onSuccess() {
                            //Log.d(MainActivity.tag,"cache successfully found");
                        }

                        @Override
                        public void onError() {
                            //try again online
                            Picasso.with(getBaseContext()).load(urlString).into(mBinding.primary.poster);
                        }
                    });
        }


    }


    private class TrailerData extends AsyncTask<Void,Void,URL[]>{

        @Override
        protected void onPreExecute() {
            mBinding.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected URL[] doInBackground(Void... params) {

            URL urlVideo= NetworkUtils.buildVideoUrlWithMovieId(DetailActivity.this,mMovieId);
            String videoJson=null;
            try{
                videoJson= NetworkUtils.getResponseFromHttpUrl(urlVideo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(videoJson==null)
                return null;

            //parse
            URL[] videoUrl=null;
            try {
                videoUrl= MovieParser.getVideoUrl(DetailActivity.this,videoJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return videoUrl;
        }

        @Override
        protected void onPostExecute(URL[] videoUrl) {

            if(videoUrl!=null) {
                listAdapter.swapUrl(videoUrl);
                mBinding.secondary.trailerRecycler.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
                mBinding.secondary.trailerRecycler.setAdapter(listAdapter);
            }
            new ReviewData().execute(mMovieId);
        }
    }

    private class ReviewData extends AsyncTask<String,Void,String[]>{

        @Override
        protected String[] doInBackground(String... params) {
            URL urlReview =NetworkUtils.buildReviewUrlWithMovieId(DetailActivity.this, mMovieId);
            String reviewJson=null;
            try {
                reviewJson = NetworkUtils.getResponseFromHttpUrl(urlReview);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //parse
            if(reviewJson==null)
                return null;

            String[] review=null;
            try {
                review = MovieParser.getReviewList(DetailActivity.this,reviewJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return review;
        }

        @Override
        protected void onPostExecute(String[] data) {
            mBinding.progressBar.setVisibility(View.INVISIBLE);
            if(data!=null) {
                reviewAdapter.swapReview(data);
                mBinding.secondary.reviewRecycler.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
                mBinding.secondary.reviewRecycler.setAdapter(reviewAdapter);
            }
        }
    }

    @Override
    public void onListItemClick(int clickedItem) {
        URL url =  ListAdapter.mTrailer[clickedItem];
        openYoutube(url);
    }

    private void openYoutube(URL url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
        startActivity(intent);
    }

    public void favouriteIt(View view){
        ImageView starView= mBinding.primary.star;

        if(like){
            //remove it from favorite
            getContentResolver().delete(MovieContract.MovieEntry.CONTENT_URI_FAVOURITE.buildUpon().appendPath(mMovieId).build(),
                    null,
                    null);
            //make star dark
            starView.setImageResource(R.drawable.ic_starunlike);
        }else{
            //insert it into favourite

            getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI_FAVOURITE,
                    getContentValue());
            //make star bright
            starView.setImageResource(R.drawable.ic_starlike);
        }
        //change like value
        new CheckFavourite().execute();
    }


    private ContentValues getContentValue(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MovieContract.MovieEntry.MOVIE_ID, movieCard.movieId);
        contentValues.put(MovieContract.MovieEntry.TITLE, movieCard.title);
        contentValues.put(MovieContract.MovieEntry.POSTER_PATH, movieCard.poster_path);
        contentValues.put(MovieContract.MovieEntry.PLOT, movieCard.plot);
        contentValues.put(MovieContract.MovieEntry.RELEASE_DATE, movieCard.releaseDate);
        contentValues.put(MovieContract.MovieEntry.USER_RATING, movieCard.userRating);

        return contentValues;
    }


    private class CheckFavourite extends AsyncTask<Void,Void,Cursor>{
        @Override
        protected Cursor doInBackground(Void... params) {
            Cursor cursor=null;
            Uri uri = MovieContract.MovieEntry.CONTENT_URI_FAVOURITE.buildUpon().appendPath(mMovieId).build();
            try{
                cursor = getContentResolver().query(
                        uri,
                        null,
                        null,
                        null,
                        MovieContract.MovieEntry._ID);
            }catch (Exception e){
                e.printStackTrace();
            }

            return cursor;
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if(cursor!=null && !cursor.moveToFirst()) {
                setStar(false);
            }
            else {
                try {
                    cursor.moveToFirst();
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                setStar(true);
            }
            cursor.close();
        }
    }

    private void setStar(Boolean temp){
        if(temp) {
            like = temp;
            mBinding.primary.star.setImageResource(R.drawable.ic_starlike);
        }
        else {
            like= temp;
            mBinding.primary.star.setImageResource(R.drawable.ic_starunlike);
        }

    }

}
