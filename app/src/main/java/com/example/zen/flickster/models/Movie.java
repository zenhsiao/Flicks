package com.example.zen.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Zen on 2017/2/14.
 */

public class Movie {

    //for movie adapter使用？
    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w780/%s",backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    //一定要和API要接的json的key（名稱）,value格式相符？
    String posterPath;
    String backdropPath;
    String originalTitle;
    String overview;
    Double vote_average;

    //為何要加throws JSONException才可以?是什麼意思？ 可以使用jsonObject.getString是內建功能嗎？
    public Movie(JSONObject jsonObject) throws JSONException {
        this.posterPath = jsonObject.getString("poster_path");
        this.backdropPath = jsonObject.getString("backdrop_path");
        this.originalTitle = jsonObject.getString("original_title");
        this.overview = jsonObject.getString("overview");
        this.vote_average = jsonObject.getDouble("vote_average");
    }

    //why use static? 因為是針對整個class使用 不是針對某個實例變數使用？ why can use getJSONObject?
    public static ArrayList<Movie> fromJSONArray(JSONArray array){
        ArrayList<Movie> results = new ArrayList<>();

        for( int x = 0; x < array.length(); x++){
            try {
                results.add(new Movie(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
