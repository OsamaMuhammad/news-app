package com.example.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static String LOG_TAG=QueryUtils.class.getSimpleName();

    public static List<News> fetchNewsData(String urlString){
        URL url=createUrl(urlString);
        String jsonResponse=null;
        try {
            jsonResponse=makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG,"Error closing iput stream");
        }

        return extractDataFromJson(jsonResponse);

    }

    public static URL createUrl(String urlString) {
        URL myURL=null;
        try {
            myURL=new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG,"Error with creating Url");
        }
        return myURL;
    }

    public static String makeHttpRequest(URL myUrl) throws IOException {
        HttpURLConnection urlConnection=null;
        String jsonRespone=null;
        InputStream myStream=null;

        try {

            urlConnection = (HttpURLConnection) myUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(1500);
            urlConnection.setConnectTimeout(1000);
            if (urlConnection.getResponseCode() == 200) {
                myStream = urlConnection.getInputStream();
                jsonRespone = readFromStream(myStream);
            }
        }
        catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving JSON results",e);
        }
        finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if(myStream!=null){
                myStream.close();
            }
        }


        return jsonRespone;
    }

    public static String readFromStream(InputStream myStream) throws IOException {
        StringBuilder output=null;
        if (myStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(myStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            output = new StringBuilder();
            String string = reader.readLine();
            while (string != null) {
                output.append(string);
                string = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<News> extractDataFromJson(String json){

        if(json==null || json.isEmpty()){
            return null;
        }

        ArrayList<News> news=new ArrayList<>();
        try {
            JSONObject newsObject=new JSONObject(json);
            JSONArray newsArray= newsObject.getJSONArray("articles");

            if(newsArray.length()>0) {
                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject newsInfo = newsArray.getJSONObject(i);
                    String title = newsInfo.getString("title");
                    String description = newsInfo.getString("description");
                    String urlToImage = newsInfo.getString("urlToImage");
                    String datePublished = newsInfo.getString("publishedAt");
                    news.add(new News(title, description, urlToImage, datePublished));

                }
            }

        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        return news;
    }
}
