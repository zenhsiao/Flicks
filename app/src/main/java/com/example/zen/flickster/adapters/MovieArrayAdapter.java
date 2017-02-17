package com.example.zen.flickster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zen.flickster.R;
import com.example.zen.flickster.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Zen on 2017/2/14.
 */
//ukyo的是繼承base adapter, 這邊是繼承array adapter 會有差嗎？
public class MovieArrayAdapter extends ArrayAdapter<Movie> {

    // View lookup cache
    private static class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvOverview;
    }

    public MovieArrayAdapter(Context context, List<Movie> movies){
        super(context, android.R.layout.simple_list_item_1,movies);//是什麼意思? super通常是拿parent的method來用？
    }

    // Returns the number of types of Views that will be created by getView(int, View, ViewGroup)
    //why need this one?
    @Override
    public int getViewTypeCount() {
        return 2; //這個ListView有兩種類型的item layout
    }

    // Get the type of View that will be created by getView(int, View, ViewGroup)
    // for the specified item.
    @Override
    public int getItemViewType(int position) {
        // Return an integer here representing the type of View.
        // Note: Integers must be in the range 0 to getViewTypeCount() - 1
        //get the data item for position
        Movie movie = getItem(position);
        if (movie.getVote_average()>= 6d ){
            return 1;
        } else{
            return 0;
        }
    }

    @NonNull
    @Override
    //透過自訂的item layout來產生每個item的View
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for position ??
        Movie movie = getItem(position);
        int type = getItemViewType(position);

        //check the existing view being reused, otherwise inflate the view??
        ViewHolder viewHolder; // view lookup cache stored in tag?
        if (convertView == null){
            // If there's no view to re-use, inflate a brand new view for row?
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            if ( type == 0){
                convertView = inflater.inflate(R.layout.item_movie, parent, false);

            } else if (type == 1){
                convertView = inflater.inflate(R.layout.item_movie_highrate, parent, false);

            } else{
                return null;
            }

            viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
            viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvOverview);

            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }else {
            // View is being recycled, retrieve the viewHolder object from tag ?
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data from the data object via the viewHolder object
        // into the template view.
        if (type == 0){
            viewHolder.tvTitle.setText(movie.getOriginalTitle());
            viewHolder.tvOverview.setText(movie.getOverview());

            //adjust image for different orientation,但怎麼知道要打這些得到orientation?這些指令是什麼意思？我這邊用this是對的嗎？
            if (this.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                Picasso.with(getContext()).load(movie.getPosterPath()).fit().placeholder(R.drawable.coming).into(viewHolder.ivImage);
            }else if (this.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Picasso.with(getContext()).load(movie.getBackdropPath()).fit().placeholder(R.drawable.coming).into(viewHolder.ivImage);
            }
        }else{
            Picasso.with(getContext()).load(movie.getBackdropPath()).fit().centerCrop().placeholder(R.drawable.coming).into(viewHolder.ivImage);
        }

        //return the view
        return convertView;
    }
}
