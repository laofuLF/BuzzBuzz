package com.weihuan.buzzbuzz.db;

import com.google.gson.annotations.SerializedName;

public class Recipe {

    public static final String TABLE_NAME = "recipes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "recipeName";
    public static final String COLUMN_GLASS = "glass";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_INSTRUCTIONS = "instructions";
    public static final String COLUMN_CATEGORY = "category";

    @SerializedName("idDrink")
    private int id;
    @SerializedName("strDrink")
    private String recipeName;
    @SerializedName("strGlass")
    private String glass;
    @SerializedName("strDrinkThumb")
    private String image;
    @SerializedName("strInstructions")
    private String instructions;
    @SerializedName("strCategory")
    private String category;
//    @SerializedName("strIngredient1")
//    private String ingredient1;
//    @SerializedName("strIngredient2")
//    private String ingredient2;
//    @SerializedName("strIngredient3")
//    private String ingredient3;
//    @SerializedName("strIngredient4")
//    private String ingredient4;
//    @SerializedName("strIngredient5")
//    private String ingredient5;




    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY, "
                    + COLUMN_RECIPE_NAME + " TEXT, "
                    + COLUMN_GLASS + " TEXT, "
                    + COLUMN_IMAGE + " TEXT, "
                    + COLUMN_INSTRUCTIONS + " TEXT, "
                    + COLUMN_CATEGORY + " TEXT)";

    public static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public Recipe(){
    }

    public Recipe(int id, String recipeName, String glass, String image, String instructions, String category) {
        this.id = id;
        this.recipeName = recipeName;
        this.glass = glass;
        this.image = image;
        this.instructions = instructions;
        this.category = category;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String name) {
        this.recipeName = name;
    }

    public String getGlass() {
        return glass;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public String getInstructions() {
        return instructions;
    }
}
