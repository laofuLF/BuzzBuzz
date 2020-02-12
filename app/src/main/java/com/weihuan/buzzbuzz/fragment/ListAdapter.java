package com.weihuan.buzzbuzz.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weihuan.buzzbuzz.R;
import com.weihuan.buzzbuzz.db.Recipe;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private Context context;
    private List<Recipe> recipes = new ArrayList<>();

//    private ArrayList<String> dataSet = new ArrayList<>();
////    private ArrayList<String> imageSet = new ArrayList<>();
////    private ArrayList<Bitmap> bitmaps = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;
        public ViewHolder(View v) {
            super(v);
            textView = (TextView) v.findViewById(R.id.layout_recipe_title);
            imageView = (ImageView) v.findViewById(R.id.cocktailImage);
        }
    }

    public ListAdapter(List<Recipe> newRecipes) {

        recipes.clear();
        recipes.addAll(newRecipes);

//        dataSet.clear();
//        dataSet.addAll(data);
//        imageSet.clear();
//        imageSet.addAll(image);
//        imageDownloader = new ImageDownloader();
//        try {
//            bitmaps = imageDownloader.execute(imageSet).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }


    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        holder.textView.setText(currentRecipe.getRecipeName());
        Picasso.get().load(currentRecipe.getImage()).into(holder.imageView);
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
        return recipes.size();
    }

}
