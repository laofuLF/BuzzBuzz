package com.weihuan.buzzbuzz.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
        db.execSQL(recipe.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + recipe.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }


    public void insertNote(int id, String recipeName, String recipeGlass, String recipeImage) {

        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        // input all values of this piece of data
        values.put(recipe.COLUMN_ID, id);
        values.put(recipe.COLUMN_RECIPE_NAME, recipeName);
        values.put(recipe.COLUMN_GLASS, recipeGlass);
        values.put(recipe.COLUMN_IMAGE, recipeImage);

        db.insert(recipe.TABLE_NAME, null, values);
        db.close();
    }


    public recipe getNote(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(recipe.TABLE_NAME,
                new String[]{recipe.COLUMN_ID, recipe.COLUMN_RECIPE_NAME, recipe.COLUMN_GLASS, recipe.COLUMN_IMAGE},
                recipe.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null,null,null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        // prepare the recipe object
        recipe output = new recipe(
                cursor.getInt(cursor.getColumnIndex(recipe.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(recipe.COLUMN_RECIPE_NAME)),
                cursor.getString(cursor.getColumnIndex(recipe.COLUMN_GLASS)),
                cursor.getString(cursor.getColumnIndex(recipe.COLUMN_IMAGE))
        );

        cursor.close();
        return output;
    }


    public List<recipe> getAllRecipes() {
        List<recipe> recipes = new ArrayList<>();

        // Select All
        String selectQuery = "SELECT * FROM " + recipe.TABLE_NAME + "ORDER BY " +
                recipe.COLUMN_ID;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // loop through all rows and add to the list
        if (cursor.moveToFirst()) {
            do {
                recipe current = new recipe();
                current.setId(cursor.getInt(cursor.getColumnIndex(recipe.COLUMN_ID)));
                current.setRecipeName(cursor.getString(cursor.getColumnIndex(recipe.COLUMN_RECIPE_NAME)));
                current.setGlass(cursor.getString(cursor.getColumnIndex(recipe.COLUMN_GLASS)));
                current.setImage(cursor.getString(cursor.getColumnIndex(recipe.COLUMN_IMAGE)));
            } while (cursor.moveToNext());
        }

        db.close();

        return recipes;
    }


    public int getRecipesCount() {
        String countQuery = "SELECT * FROM " + recipe.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }


    public void deleteRecipe(recipe recipeData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(recipe.TABLE_NAME, recipe.COLUMN_ID + " = ?",
                new String[]{String.valueOf(recipeData.getId())});

        db.close();
    }


}
