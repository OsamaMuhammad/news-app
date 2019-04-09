package com.example.newsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_info);
        Intent intent=getIntent();
        String headline=intent.getStringExtra("headline");
        String description=intent.getStringExtra("description");
        String imageUrl=intent.getStringExtra("imageUrl");

        ImageView imageView=(ImageView)findViewById(R.id.news_thumbnail);
        Picasso.get().load(imageUrl).into(imageView);
        TextView headlineTextView=(TextView)findViewById(R.id.news_headline);
        headlineTextView.setText(headline);
        TextView descriptionTextView=(TextView)findViewById(R.id.news_descrition);
        descriptionTextView.setText(description);


    }
}
