package com.example.zen.flickster;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.zen.flickster.R.id.tvImage;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        ImageView Image = (ImageView) findViewById(tvImage);
        TextView Title = (TextView) findViewById(R.id.tvTitle);
        TextView Overview = (TextView) findViewById(R.id.tvOverview);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.tvRating);

        String mImagePath;
        String mTitle;
        double mRating;
        String mTextOverview;

        Bundle bundle = getIntent().getExtras();
        mImagePath = bundle.getString("image");
        mTitle = bundle.getString("title");
        mRating = bundle.getDouble("rating") / 2;
        float y = (float) mRating;
        mTextOverview = bundle.getString("overview");

        Title.setText(mTitle);
        ratingBar.setRating(y);
        Overview.setText(mTextOverview);
        Picasso.with(this).load(mImagePath).fit().centerCrop().placeholder(R.drawable.coming).into(Image);
    }


    public void onSubmit(View v) {
        this.finish(); // closes the activity
    }


}
