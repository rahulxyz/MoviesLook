package com.rahulxyz.movieslook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private MovieCard mCard;
    private TextView title,releaseDate,rating,plot;
    private ImageView poster;
    private NetworkUtils networkUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_beautiful);

        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            int position = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
             mCard = ImageAdpater.movieCard[position];
        }
        networkUtils= new NetworkUtils(this);

       title =(TextView)findViewById(R.id.title_b);
       releaseDate =(TextView)findViewById(R.id.releaseDate_b);
       rating =(TextView)findViewById(R.id.rating_b);
       plot =(TextView)findViewById(R.id.plot_b);
       poster = (ImageView)findViewById(R.id.poster_b);


        setUp();
    }

    private void setUp(){
        title.setText(mCard.title);
        releaseDate.setText(mCard.releaseDate);
        rating.setText(mCard.userRating);
        plot.setText(mCard.plot);
        if(mCard.poster_path != null) {
            String urlString = networkUtils.buildPosterUrl(mCard.poster_path, Screen.sizeMax).toString();
            poster.setMaxHeight(Screen.height/3);
            Picasso.with(this).load(urlString).into(poster);
        }
    }
}
