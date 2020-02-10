package com.weihuan.buzzbuzz.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImageDownloader extends AsyncTask<ArrayList<String>, Void, ArrayList<Bitmap>> {
    private ArrayList<Bitmap> bitmaps;

    @Override
    protected ArrayList<Bitmap> doInBackground(ArrayList<String>... urls) {
        if (!isCancelled()) {
            bitmaps = new ArrayList<>();
            URL url = null;
            try {
                ArrayList<String> currentList = urls[0];
                for (String currentUrl: currentList){
                    url = new URL(currentUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    bitmaps.add(bitmap);
                }
                return bitmaps;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
