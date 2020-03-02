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

    public void resetTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(Recipe.DELETE_TABLE);
        db.execSQL(Recipe.CREATE_TABLE);
        db.close();

    }


    public void insertRecipes(List<Recipe> recipes) {
        for (Recipe currentRecipe: recipes) {
            insertRecipe(currentRecipe);
        }
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
        String ingredient1 = recipe.getIngredient1();
        String ingredient2 = recipe.getIngredient2();
        String ingredient3 = recipe.getIngredient3();
        String ingredient4 = recipe.getIngredient4();
        String ingredient5 = recipe.getIngredient5();
        String ingredient6 = recipe.getIngredient6();
        String ingredient7 = recipe.getIngredient7();
        String ingredient8 = recipe.getIngredient8();
        String ingredient9 = recipe.getIngredient9();
        String ingredient10 = recipe.getIngredient10();
        String ingredient11 = recipe.getIngredient11();
        String ingredient12 = recipe.getIngredient12();
        String ingredient13 = recipe.getIngredient13();
        String ingredient14 = recipe.getIngredient14();
        String ingredient15 = recipe.getIngredient15();
        String measurement1 = recipe.getMeasurement1();
        String measurement2 = recipe.getMeasurement2();
        String measurement3 = recipe.getMeasurement3();
        String measurement4 = recipe.getMeasurement4();
        String measurement5 = recipe.getMeasurement5();
        String measurement6 = recipe.getMeasurement6();
        String measurement7 = recipe.getMeasurement7();
        String measurement8 = recipe.getMeasurement8();
        String measurement9 = recipe.getMeasurement9();
        String measurement10 = recipe.getMeasurement10();
        String measurement11 = recipe.getMeasurement11();
        String measurement12 = recipe.getMeasurement12();
        String measurement13 = recipe.getMeasurement13();
        String measurement14 = recipe.getMeasurement14();
        String measurement15 = recipe.getMeasurement15();

        // input all values of this piece of data
        values.put(Recipe.COLUMN_ID, id);
        values.put(Recipe.COLUMN_RECIPE_NAME, recipeName);
        values.put(Recipe.COLUMN_GLASS, recipeGlass);
        values.put(Recipe.COLUMN_IMAGE, recipeImage);
        values.put(Recipe.COLUMN_INSTRUCTIONS, instructions);
        values.put(Recipe.COLUMN_CATEGORY, category);
        values.put(Recipe.INGREDIENT1, ingredient1);
        values.put(Recipe.INGREDIENT2, ingredient2);
        values.put(Recipe.INGREDIENT3, ingredient3);
        values.put(Recipe.INGREDIENT4, ingredient4);
        values.put(Recipe.INGREDIENT5, ingredient5);
        values.put(Recipe.INGREDIENT6, ingredient6);
        values.put(Recipe.INGREDIENT7, ingredient7);
        values.put(Recipe.INGREDIENT8, ingredient8);
        values.put(Recipe.INGREDIENT9, ingredient9);
        values.put(Recipe.INGREDIENT10, ingredient10);
        values.put(Recipe.INGREDIENT11, ingredient11);
        values.put(Recipe.INGREDIENT12, ingredient12);
        values.put(Recipe.INGREDIENT13, ingredient13);
        values.put(Recipe.INGREDIENT14, ingredient14);
        values.put(Recipe.INGREDIENT15, ingredient15);
        values.put(Recipe.MEASUREMENT1, measurement1);
        values.put(Recipe.MEASUREMENT2, measurement2);
        values.put(Recipe.MEASUREMENT3, measurement3);
        values.put(Recipe.MEASUREMENT4, measurement4);
        values.put(Recipe.MEASUREMENT5, measurement5);
        values.put(Recipe.MEASUREMENT6, measurement6);
        values.put(Recipe.MEASUREMENT7, measurement7);
        values.put(Recipe.MEASUREMENT8, measurement8);
        values.put(Recipe.MEASUREMENT9, measurement9);
        values.put(Recipe.MEASUREMENT10, measurement10);
        values.put(Recipe.MEASUREMENT11, measurement11);
        values.put(Recipe.MEASUREMENT12, measurement12);
        values.put(Recipe.MEASUREMENT13, measurement13);
        values.put(Recipe.MEASUREMENT14, measurement14);
        values.put(Recipe.MEASUREMENT15, measurement15);


        db.insert(Recipe.TABLE_NAME, null, values);
        db.close();
    }


//    public Recipe getRecipe(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.query(Recipe.TABLE_NAME,
//                new String[]{Recipe.COLUMN_ID, Recipe.COLUMN_RECIPE_NAME, Recipe.COLUMN_GLASS, Recipe.COLUMN_IMAGE},
//                Recipe.COLUMN_ID + "=?",
//                new String[]{String.valueOf(id)}, null, null,null,null);
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//        }
//
//        // prepare the Recipe object
//        Recipe output = new Recipe();
//        output.setId(cursor.getInt(cursor.getColumnIndex(Recipe.COLUMN_ID)));
//        output.setRecipeName(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_RECIPE_NAME)));
//        output.setGlass(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_GLASS)));
//        output.setImage(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_IMAGE)));
//        output.setInstructions(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_INSTRUCTIONS)));
//        output.setCategory(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_CATEGORY)));
//
//        cursor.close();
//        return output;
//    }


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
                current.setCategory((cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_CATEGORY))));
                current.setInstructions(cursor.getString(cursor.getColumnIndex(Recipe.COLUMN_INSTRUCTIONS)));

                current.setIngredient1(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT1)));
                current.setIngredient2(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT2)));
                current.setIngredient3(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT3)));
                current.setIngredient4(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT4)));
                current.setIngredient5(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT5)));
                current.setIngredient6(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT6)));
                current.setIngredient7(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT7)));
                current.setIngredient8(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT8)));
                current.setIngredient9(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT9)));
                current.setIngredient10(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT10)));
                current.setIngredient11(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT11)));
                current.setIngredient12(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT12)));
                current.setIngredient13(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT13)));
                current.setIngredient14(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT14)));
                current.setIngredient15(cursor.getString(cursor.getColumnIndex(Recipe.INGREDIENT15)));

                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement2(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));
                current.setMeasurement1(cursor.getString(cursor.getColumnIndex(Recipe.MEASUREMENT1)));

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
