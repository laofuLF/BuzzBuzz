package com.weihuan.buzzbuzz.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.weihuan.buzzbuzz.R;

public class Recipe implements Parcelable {

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


    public Recipe(int id, String recipeName, String glass, String image, String instructions, String category) {
        this.id = id;
        this.recipeName = recipeName;
        this.glass = glass;
        this.image = image;
        this.instructions = instructions;
        this.category = category;

    }

    public Recipe(Parcel in) {
        this.id = in.readInt();
        this.recipeName = in.readString();
        this.glass = in.readString();
        this.image = in.readString();
        this.instructions = in.readString();
        this.category = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Actual object serialization happens here, write object content to parcel
     * one by one, reading should be done according to its order.
     * flat means how the object should be written
     * @param dest
     * @param flags
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(recipeName);
        dest.writeString(glass);
        dest.writeString(image);
        dest.writeString(instructions);
        dest.writeString(category);
    }

    /**
     * This part is needed for Android to be able to create new objects, individually or as arrays
     */
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            return new Recipe((source));
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }

    };


    @Override
    public boolean equals(Object object) {
        if (object instanceof Recipe) {
            Recipe toCompare = (Recipe) object;
            return (this.recipeName.equalsIgnoreCase((toCompare.getRecipeName())));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (this.getRecipeName().hashCode());
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
