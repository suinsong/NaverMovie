package com.bit.navermovie;

import android.database.DatabaseUtils;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bit.navermovie.databinding.ActivityMovieBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class MovieActivity extends AppCompatActivity {

    ActivityMovieBinding movieBinding;
    List<NaverMovieVO> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieBinding = DataBindingUtil.setContentView(this, R.layout.activity_movie);
        movieBinding.btnMovieSearch.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String movieSearch = movieBinding.txtMovieSearch.getText().toString();
                if (movieSearch.isEmpty()) {
                    Toast.makeText(MovieActivity.this, "검색어를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;

                }

                NaverSearchAsync naverSearchAsyne = new NaverSearchAsync();
                naverSearchAsyne.execute();
            }
        });
    }

    class NaverSearchAsync extends AsyncTask<Integer, Integer, Void> {

        //async모드를 이옹해서 naversearch()메서드 호출
        @Override
        protected Void doInBackground(Integer... integers) {

            naverSearch();

            return null;
        }
    }

    public void naverSearch() {
        String movieSearch = movieBinding.txtMovieSearch.getText().toString();
        try {
            String queryString = URLEncoder.encode(movieSearch, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json";
            apiURL += "?query=" + queryString;
            apiURL += "&display=20";

            URL url = new URL(apiURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setRequestProperty("X-Naver-Client-Id", NaverSecret.NAVER_CLIENT_ID);
            connection.setRequestProperty("X-Naver-Client-Secret", NaverSecret.NAVER_CLIENT_SECRET);

            //데이터를 보내달라고 물어봄
            int responseCode = connection.getResponseCode();
            BufferedReader buffer;
            if (responseCode == 200) {
                buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            } else {
                return;
            }
            String inputLine;
            String jsonString = new String(); //string을 이어받기 위해서 String을 객체로 생성

            while ((inputLine = buffer.readLine()) != null) {
                jsonString += inputLine;
            }

            buffer.close(); //네트워크 종료


            //네이버로부터 받은 json구조의 String을 json object로 변경해서 사용
            JSONObject resJSON = new JSONObject(jsonString);

            //json object로부터 items이하의 jsontree을 추출
            JSONArray items = resJSON.getJSONArray("items");
            movies = new ArrayList<NaverMovieVO>();
            for (int i = 0; i < items.length(); i++) {
                //하부 tree를 하나씩 추출
                JSONObject item = items.getJSONObject(i);

                NaverMovieVO vo = new NaverMovieVO();
                vo.setTitle(item.getString("title"));
                vo.setLink(item.getString("link"));
                vo.setImage(item.getString("image"));
                vo.setDirector(item.getString("director"));
                vo.setActor(item.getString("actor"));
                vo.setPubDate(item.getString("pubDate"));
                vo.setSubtitle(item.getString("subtitle"));
                vo.setUserRating(item.getString("userRating"));


            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


}
