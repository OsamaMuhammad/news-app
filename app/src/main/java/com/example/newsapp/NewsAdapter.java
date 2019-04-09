package com.example.newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rootView=convertView;
        if(rootView==null){
            rootView=LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        News currentNews=getItem(position);
        TextView headline=(TextView)rootView.findViewById(R.id.headline);
        headline.setText(currentNews.getNewsHeadline());

        TextView description=(TextView)rootView.findViewById(R.id.description);
        description.setText(currentNews.getDescription());

        ImageView thumbnail=(ImageView)rootView.findViewById(R.id.thumbnail);
        Picasso.get().load(currentNews.mImageUrl).into(thumbnail);

        return rootView;
    }
}
