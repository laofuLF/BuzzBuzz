package com.weihuan.buzzbuzz.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.weihuan.buzzbuzz.R;
import com.weihuan.buzzbuzz.db.Recipe;

import java.util.ArrayList;
import java.util.List;

public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
    private List<Recipe> recipes = new ArrayList<>();
    private RecipesRecyclerAdapter.OnRecipeListener mOnRecipeListener;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;
        RecipesRecyclerAdapter.OnRecipeListener onRecipeListener;
        public ViewHolder(View v, RecipesRecyclerAdapter.OnRecipeListener onRecipeListener) {
            super(v);
            textView = (TextView) v.findViewById(R.id.horizontal_title);
            imageView = (ImageView) v.findViewById(R.id.horizontal_image);
            this.onRecipeListener = onRecipeListener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRecipeListener.onRecipeClick(getAdapterPosition());
        }
    }

    public HorizontalAdapter(List<Recipe> newRecipes, RecipesRecyclerAdapter.OnRecipeListener onRecipeListener) {

        recipes.clear();
        if (newRecipes != null) {
            recipes.addAll(newRecipes);
            this.mOnRecipeListener = onRecipeListener;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);
        ViewHolder vh = new ViewHolder(v, mOnRecipeListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Recipe currentRecipe = recipes.get(position);
        holder.textView.setText(currentRecipe.getRecipeName());
        Picasso.get().load(currentRecipe.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

}
