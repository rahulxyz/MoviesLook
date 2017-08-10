package com.rahulxyz.movieslook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.net.URL;

/**
 * Created by raul_Will on 7/21/2017.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    public static URL[] mTrailer;
    private final Context context;
    private TextView trailer;
    private  final ListItemClickListener mOnClickListener;

    public ListAdapter(Context context,ListItemClickListener clickListener){
        this.context = context;
        this.mOnClickListener = clickListener;
    }

    public void swapUrl(URL[] url){
        mTrailer=url;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.trailer_item,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        int pos = position+1;
        String defaultText= "Trailer "+pos;
        trailer.setText(defaultText);
    }

    @Override
    public int getItemCount() {
        if(mTrailer == null) {
            return 0;
        }else{
            return mTrailer.length;
        }
    }

    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ListViewHolder(View itemView) {
            super(itemView);
            trailer = (TextView) itemView.findViewById(R.id.trailer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedHere= getAdapterPosition();
            mOnClickListener.onListItemClick(clickedHere);
        }
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItem);
    }
}
