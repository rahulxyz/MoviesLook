package com.rahulxyz.movieslook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by raul_Will on 7/21/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private final Context context;
    private String[] mReview;
    private TextView reviewText;

    public ReviewAdapter(Context context){
        this.context= context ;
    }

    public void swapReview(String[] data){
        mReview = data;
    }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
            reviewText.setText(mReview[position]);
    }

    @Override
    public int getItemCount() {
        if(mReview==null)
            return 0;
        else
            return mReview.length;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        public ReviewViewHolder(View itemView) {
            super(itemView);

            reviewText= (TextView) itemView.findViewById(R.id.review_tv);
        }
    }
}
