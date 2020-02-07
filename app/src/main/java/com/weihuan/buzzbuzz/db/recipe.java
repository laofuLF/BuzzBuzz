package com.weihuan.buzzbuzz.db;

public class recipe {

    public static final String TABLE_NAME = "recipes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_RECIPE_NAME = "recipeName";
    public static final String COLUMN_GLASS = "glass";
    public static final String COLUMN_IMAGE = "image";


    private int id;
    private String recipeName;
    private String glass;
    private String image;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY"
                    + COLUMN_RECIPE_NAME + " TEXT,"
                    + COLUMN_GLASS + " TEXT,"
                    + COLUMN_IMAGE + " TEXT"
                    + ")";

    public recipe(){
    }

    public recipe(int id, String recipeName, String glass, String image) {
        this.id = id;
        this.recipeName = recipeName;
        this.glass = glass;
        this.image = image;
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
}
