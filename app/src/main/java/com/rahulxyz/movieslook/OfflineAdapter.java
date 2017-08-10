package com.rahulxyz.movieslook;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.rahulxyz.movieslook.database.MovieContract;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;


/**
 * Created by raul_Will on 7/17/2017.
 */

class OfflineAdapter extends BaseAdapter {

    private Cursor mCursor;
    private final Context context;

    public OfflineAdapter(Context context){
        this.context = context;
    }


    @Override
    public int getCount() {
        if(mCursor== null) {
            return 0;
        }else{
            return mCursor.getCount();
        }

    }

    @Override
    public Object getItem(int position) {

        if (!mCursor.moveToPosition(position))
            return null;

        String poster_path = mCursor.getString(
                mCursor.getColumnIndex(
                        MovieContract.MovieEntry.POSTER_PATH));



        return NetworkUtils.buildPosterUrl(context,poster_path, context.getResources().getString(R.string.sizeSmall));
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if(convertView== null) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
        }

        final ImageView imageView= (ImageView) convertView.findViewById(R.id.im_grid_item);
        Picasso.with(context)
                .load(getItem(position).toString())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        //Log.d(MainActivity.tag,"cache successfully found");
                    }

                    @Override
                    public void onError() {
                        // Try again online if cache failed
                        Picasso.with(context).load(getItem(position).toString()).into(imageView);
                    }
                });


        return convertView;
    }

    public void swapCursor(Cursor c){
        if (mCursor == c) {
            return;
        }

        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
    }
}
