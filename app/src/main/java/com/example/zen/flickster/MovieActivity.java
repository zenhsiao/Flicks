package com.example.zen.flickster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.zen.flickster.adapters.MovieArrayAdapter;
import com.example.zen.flickster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    ArrayList<Movie> movies;//有其他的movie model嗎？
    MovieArrayAdapter movieAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        lvItems = (ListView) findViewById(R.id.lvMovies);//找出listview的意思？ 為何不用像下面兩個一樣要先new?
        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(this, movies);//this是指MovieActivity？
        lvItems.setAdapter(movieAdapter);

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

        AsyncHttpClient client = new AsyncHttpClient();

        //看起來在定義方法 是何時會呼叫？new client的時候嗎？
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray movieJsonResults = null;

                try {
                    movieJsonResults = response.getJSONArray("results");
                    //movies = Movie.fromJSONArray(movieJsonResults); why這樣會無法出現資料？
                    movies.addAll(Movie.fromJSONArray(movieJsonResults));
                    movieAdapter.notifyDataSetChanged();
                    Log.d("DEBUG", movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);

        setupListViewListener();
    }

    private void setupListViewListener(){
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {

                        // first parameter is the context, second is the class of the activity to launch
                        Intent i = new Intent(MovieActivity.this, DetailActivity.class);
                        // put "extras" into the bundle for access in the second activity
                        Bundle bundle = new Bundle();
                        int y = (int) id;
                        Movie movie = movies.get(y);

                        bundle.putString("image", movie.getBackdropPath());
                        bundle.putString("title", movie.getOriginalTitle());
                        bundle.putDouble("rating", movie.getVote_average());
                        bundle.putString("overview", movie.getOverview());

                        i.putExtras(bundle);
                        // brings up the second activity
                        startActivity(i);
                    }
                });
    }
}
