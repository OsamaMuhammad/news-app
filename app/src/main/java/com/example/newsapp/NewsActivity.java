package com.example.newsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    NewsAdapter newsAdapter=null;

    // https://newsapi.org/v2/everything?sources=ign&apiKey=7c7a95126a3f44b8bcef06d3d7ffdf81"
    final static String mUrl="https://newsapi.org/v2/everything";
    ListView newsList=null;
    List<News> news=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        newsList=(ListView)findViewById(R.id.list);

        NewsAsyncTask myTask=new NewsAsyncTask();
        myTask.execute(mUrl);

        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentNews=news.get(position);
                Intent intent=new Intent(getApplicationContext(),NewsInfo.class);
                intent.putExtra("headline",currentNews.getNewsHeadline());
                intent.putExtra("description",currentNews.getDescription());
                intent.putExtra("imageUrl",currentNews.getImageUrl());
                startActivity(intent);
            }
        });

    }

    private void updateUi(List<News> news){

        newsList=(ListView)findViewById(R.id.list);

        newsAdapter=new NewsAdapter(this,R.layout.list_item,news);
        newsList.setAdapter(newsAdapter);

    }

    private class NewsAsyncTask extends AsyncTask<String,Void, List<News>>{
        @Override
        protected List<News> doInBackground(String[] url) {
            SharedPreferences sharedPref= PreferenceManager.getDefaultSharedPreferences(NewsActivity.this);
            String sources=sharedPref.getString(getString(R.string.settings_news_source_key),getString(R.string.settings_news_source_default));
            Uri baseUri=Uri.parse(url[0]);
            Uri.Builder uriBuilder=baseUri.buildUpon();
            uriBuilder.appendQueryParameter("sources",sources);
            uriBuilder.appendQueryParameter("apiKey","7c7a95126a3f44b8bcef06d3d7ffdf81");
            Log.v("ssaaa",uriBuilder.toString());
            news=new ArrayList<>();
            news=QueryUtils.fetchNewsData(uriBuilder.toString());
            return news;
        }

        @Override
        protected void onPostExecute(List<News> news) {
            if(news==null)
                return;
            updateUi(news);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_settings){
            Intent settingsIntent=new Intent(this,SettingsActivity.class);
             startActivity(settingsIntent);
            return true;
        }

       return super.onOptionsItemSelected(item);
    }
}
