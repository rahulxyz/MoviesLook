package com.rahulxyz.movieslook;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ImageAdpater imageAdpater;
    private TextView tv_grid_error;
    private GridView gridView;
    private ProgressBar progressBar;
    private NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        tv_grid_error= (TextView) findViewById(R.id.tv_grid_error);
        gridView = (GridView) findViewById(R.id.grid_main);
        gridView.setOnItemClickListener(this);

        networkUtils = new NetworkUtils(this);
        Screen.setScreenResolution(this);
        gridView.setColumnWidth(Screen.width/5);

        new MovieSync().execute(this.getString(R.string.Popularity_Url_Path));
        imageAdpater = new ImageAdpater(this);
    }



    private void showError(){
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
        startActivity(intent);
    }


    private class MovieSync extends AsyncTask<String,Void,MovieCard[]>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieCard[] doInBackground(String... params) {
            //getting url of whole jsonData
            URL url = networkUtils.buildUrl(params[0]);

            try {
                //to get entire json data of movie from url
                String jsonData= networkUtils.getResponseFromHttpUrl(url);
                //converts jsonData into readable array
                MovieCard[] m= MovieParser.getMovieData(MainActivity.this,jsonData);
                return m;

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(MovieCard[] movieCard) {
            progressBar.setVisibility(View.INVISIBLE);
            if(movieCard != null) {
                imageAdpater.setMovieList(movieCard);
                gridView.setAdapter(imageAdpater);
                showGridData();
            }else
            {
                showError();
            }
        }

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
                new MovieSync().execute(this.getString(R.string.Popularity_Url_Path));
                imageAdpater = new ImageAdpater(this);
                return true;
            case R.id.action_sortByRating:
                new MovieSync().execute(this.getString(R.string.Rating_Url_Path));
                imageAdpater = new ImageAdpater(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
