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

public class RecipesRecyclerAdapter extends RecyclerView.Adapter<RecipesRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Recipe> recipes = new ArrayList<>();
    private OnRecipeListener mOnRecipeListener;


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        public TextView category;
        OnRecipeListener onRecipeListener;
        public ViewHolder(View v, OnRecipeListener onRecipeListener) {
            super(v);
            textView = (TextView) v.findViewById(R.id.layout_recipe_title);
            imageView = (ImageView) v.findViewById(R.id.cocktailImage);
            category = v.findViewById(R.id.category);
            this.onRecipeListener = onRecipeListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }

    public RecipesRecyclerAdapter(List<Recipe> newRecipes, OnRecipeListener onRecipeListener) {

        recipes.clear();
        recipes.addAll(newRecipes);
        this.mOnRecipeListener = onRecipeListener;

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
    public RecipesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mOnRecipeListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        holder.textView.setText(currentRecipe.getRecipeName());
        Picasso.get().load(currentRecipe.getImage()).into(holder.imageView);
        holder.category.setText(currentRecipe.getCategory());
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

    public interface OnRecipeListener {
        void onRecipeClick(int position);
    }

}
