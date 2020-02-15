package com.weihuan.buzzbuzz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "recipe_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Recipe.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(Recipe.DELETE_TABLE);

        // Create tables again
        onCreate(db);
    }


    public void insertRecipe(Recipe recipe) {

        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        int id = recipe.getId();
        String recipeName = recipe.getRecipeName();
        String recipeGlass = recipe.getGlass();
        String recipeImage = recipe.getImage();
        String instructions = recipe.getInstructions();
        String category = recipe.getCategory();

        // input all values of this piece of data
        values.put(Recipe.COLUMN_ID, id);
        values.put(Recipe.COLUMN_RECIPE_NAME, recipeName);
        values.put(Recipe.COLUMN_GLASS, recipeGlass);
        values.put(Recipe.COLUMN_IMAGE, recipeImage);
        values.put(Recipe.COLUMN_INSTRUCTIONS, instructions);
        values.put(Recipe.COLUMN_CATEGORY, category);

        db.insert(Recipe.TABLE_NAME, null, values);
        db.close();
    }


    public Recipe getRecipe(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(Recipe.TABLE_NAME,
                new String[]{Recipe.COLUMN_ID, Recipe.COLUMN_RECIPE_NAME, Recipe.COLUMN_GLASS, Recipe.COLUMN_IMAGE},
                Recipe.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        // prepare the Recipe object
        Recipe output = new Recipe(
                cursor.getInt(cursor.getColumnIndex(Recipe.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_RECIPE_NAME)),
                cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_GLASS)),
                cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_IMAGE)),
                cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_INSTRUCTIONS)),
                cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_CATEGORY))
        );

        cursor.close();
        return output;
    }


    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        // Select All
        String selectQuery = "SELECT * FROM " + Recipe.TABLE_NAME + " ORDER BY " +
                Recipe.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                Recipe current = new Recipe();
                current.setId(cursor.getInt(cursor.getColumnIndex(Recipe.COLUMN_ID)));
                current.setRecipeName(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_RECIPE_NAME)));
                current.setGlass(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_GLASS)));
                current.setImage(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_IMAGE)));
                recipes.add(current);
            } while (cursor.moveToNext());
        }

        db.close();

        return recipes;
    }


    public int getRecipesCount() {
        String countQuery = "SELECT * FROM " + Recipe.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }


    public void deleteRecipe(Recipe recipeData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Recipe.TABLE_NAME, Recipe.COLUMN_ID + " = ?",
                new String[]{String.valueOf(recipeData.getId())});

        db.close();
    }


    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(Recipe.DELETE_TABLE);
        db.close();
    }

}
