package com.weihuan.buzzbuzz.Fragments;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weihuan.buzzbuzz.R;
import com.weihuan.buzzbuzz.network.ImageDownloader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<String> dataSet = new ArrayList<>();
    private ArrayList<String> imageSet = new ArrayList<>();
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private ImageDownloader imageDownloader;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.layout_recipe_title);
            imageView = (ImageView) v.findViewById(R.id.cocktailImage);
        }
    }

    public ListAdapter(ArrayList<String> data, ArrayList<String> image) {
        dataSet.clear();
        dataSet.addAll(data);
        imageSet.clear();
        imageSet.addAll(image);
        imageDownloader = new ImageDownloader();
        try {
            bitmaps = imageDownloader.execute(imageSet).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(dataSet.get(position));
        holder.imageView.setImageBitmap(bitmaps.get(position));
//        try {
//            URL url = new URL(imageSet.get(position));
//            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
//            holder.imageView.setImageBitmap(bitmap);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}
