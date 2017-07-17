package com.rahulxyz.movieslook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by raul_Will on 6/6/2017.
 */

class ImageAdpater extends BaseAdapter {
    private final Context context;
    public static MovieCard[] movieCard;

    public ImageAdpater(Context c){
        this.context = c;
    }

    public void setMovieList(MovieCard[] card){
        movieCard = card;
    }


    @Override
    public int getCount() {
        int count;
        if(movieCard == null) {
            count=0;
        }else{
            count =movieCard.length;
        }

        return count;
    }

    @Override
    public Object getItem(int position) {
        return movieCard[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ImageView imageView;

        //we are inflating new view
        if(convertView == null){
            convertView = inflater.inflate(R.layout.grid_item,parent,false);
        }

        NetworkUtils networkUtils = new NetworkUtils(context);
        imageView = (ImageView) convertView.findViewById(R.id.im_grid_item);

        //if poster_path== null don't query url
        if(movieCard[position].poster_path != null) {
            String posterUrlString = networkUtils.buildPosterUrl(
                    movieCard[position].poster_path, context.getString(R.string.sizeSmall))
                    .toString();
            imageView.setAdjustViewBounds(true);
            Picasso.with(context).load(posterUrlString).into(imageView);
        }else{
            imageView.setImageResource(R.drawable.noimage);

        }
        return convertView;
    }

}
