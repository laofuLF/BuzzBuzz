package com.weihuan.buzzbuzz.db;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.weihuan.buzzbuzz.R;

import java.util.ArrayList;

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
    @SerializedName("strIngredient1")
    private String ingredient1;
    @SerializedName("strIngredient2")
    private String ingredient2;
    @SerializedName("strIngredient3")
    private String ingredient3;
    @SerializedName("strIngredient4")
    private String ingredient4;
    @SerializedName("strIngredient5")
    private String ingredient5;
    @SerializedName("strIngredient6")
    private String ingredient6;
    @SerializedName("strIngredient7")
    private String ingredient7;
    @SerializedName("strIngredient8")
    private String ingredient8;
    @SerializedName("strIngredient9")
    private String ingredient9;
    @SerializedName("strIngredient10")
    private String ingredient10;
    @SerializedName("strMeasure1")
    private String measurement1;
    @SerializedName("strMeasure2")
    private String measurement2;
    @SerializedName("strMeasure3")
    private String measurement3;
    @SerializedName("strMeasure4")
    private String measurement4;
    @SerializedName("strMeasure5")
    private String measurement5;
    @SerializedName("strMeasure6")
    private String measurement6;
    @SerializedName("strMeasure7")
    private String measurement7;
    @SerializedName("strMeasure8")
    private String measurement8;
    @SerializedName("strMeasure9")
    private String measurement9;
    @SerializedName("strMeasure10")
    private String measurement10;

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

    public Recipe() {

    }


//    public Recipe(int id, String recipeName, String glass, String image,
//                  String instructions, String category, String ingredients, String measurements) {
//        this.id = id;
//        this.recipeName = recipeName;
//        this.glass = glass;
//        this.image = image;
//        this.instructions = instructions;
//        this.category = category;
//
//    }

    public Recipe(Parcel in) {
        this.id = in.readInt();
        this.recipeName = in.readString();
        this.glass = in.readString();
        this.image = in.readString();
        this.instructions = in.readString();
        this.category = in.readString();
    }

    public ArrayList<String> getAllIngredients(){
        ArrayList<String> allIngredients = new ArrayList<>();
        if (ingredient1 != null) {
            allIngredients.add(ingredient1);
        }
        if (ingredient2 != null) {
            allIngredients.add(ingredient2);
        }
        if (ingredient3 != null) {
            allIngredients.add(ingredient3);
        }
        if (ingredient4 != null) {
            allIngredients.add(ingredient4);
        }
        if (ingredient5 != null) {
            allIngredients.add(ingredient5);
        }
        if (ingredient6 != null) {
            allIngredients.add(ingredient6);
        }
        if (ingredient7 != null) {
            allIngredients.add(ingredient7);
        }
        if (ingredient8 != null) {
            allIngredients.add(ingredient8);
        }
        if (ingredient9 != null) {
            allIngredients.add(ingredient9);
        }
        if (ingredient10 != null) {
            allIngredients.add(ingredient10);
        }
        return allIngredients;
    }

    public ArrayList<String> getAllMeasurements() {
        ArrayList<String> allMeasurements = new ArrayList<>();
        if (measurement1 != null) {
            allMeasurements.add(measurement1);
        }
        if (measurement2 != null) {
            allMeasurements.add(measurement2);
        }
        if (measurement3 != null) {
            allMeasurements.add(measurement3);
        }
        if (measurement4 != null) {
            allMeasurements.add(measurement4);
        }
        if (measurement5 != null) {
            allMeasurements.add(measurement5);
        }
        if (measurement6 != null) {
            allMeasurements.add(measurement6);
        }
        if (measurement7 != null) {
            allMeasurements.add(measurement7);
        }
        if (measurement8 != null) {
            allMeasurements.add(measurement8);
        }
        if (measurement9 != null) {
            allMeasurements.add(measurement9);
        }
        if (measurement10 != null) {
            allMeasurements.add(measurement10);
        }
        return allMeasurements;
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

    public void setCategory(String category) {
        this.category = category;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }



    public String getIngredient1() {
        return ingredient1;
    }

    public void setIngredient1(String ingredient1) {
        this.ingredient1 = ingredient1;
    }

    public String getIngredient2() {
        return ingredient2;
    }

    public void setIngredient2(String ingredient2) {
        this.ingredient2 = ingredient2;
    }

    public String getIngredient3() {
        return ingredient3;
    }

    public void setIngredient3(String ingredient3) {
        this.ingredient3 = ingredient3;
    }

    public String getIngredient4() {
        return ingredient4;
    }

    public void setIngredient4(String ingredient4) {
        this.ingredient4 = ingredient4;
    }

    public String getIngredient5() {
        return ingredient5;
    }

    public void setIngredient5(String ingredient5) {
        this.ingredient5 = ingredient5;
    }

    public String getIngredient6() {
        return ingredient6;
    }

    public void setIngredient6(String ingredient6) {
        this.ingredient6 = ingredient6;
    }

    public String getIngredient7() {
        return ingredient7;
    }

    public void setIngredient7(String ingredient7) {
        this.ingredient7 = ingredient7;
    }

    public String getIngredient8() {
        return ingredient8;
    }

    public void setIngredient8(String ingredient8) {
        this.ingredient8 = ingredient8;
    }

    public String getIngredient9() {
        return ingredient9;
    }

    public void setIngredient9(String ingredient9) {
        this.ingredient9 = ingredient9;
    }

    public String getIngredient10() {
        return ingredient10;
    }

    public void setIngredient10(String ingredient10) {
        this.ingredient10 = ingredient10;
    }

    public String getMeasurement1() {
        return measurement1;
    }

    public void setMeasurement1(String measurement1) {
        this.measurement1 = measurement1;
    }

    public String getMeasurement2() {
        return measurement2;
    }

    public void setMeasurement2(String measurement2) {
        this.measurement2 = measurement2;
    }

    public String getMeasurement3() {
        return measurement3;
    }

    public void setMeasurement3(String measurement3) {
        this.measurement3 = measurement3;
    }

    public String getMeasurement4() {
        return measurement4;
    }

    public void setMeasurement4(String measurement4) {
        this.measurement4 = measurement4;
    }

    public String getMeasurement5() {
        return measurement5;
    }

    public void setMeasurement5(String measurement5) {
        this.measurement5 = measurement5;
    }

    public String getMeasurement6() {
        return measurement6;
    }

    public void setMeasurement6(String measurement6) {
        this.measurement6 = measurement6;
    }

    public String getMeasurement7() {
        return measurement7;
    }

    public void setMeasurement7(String measurement7) {
        this.measurement7 = measurement7;
    }

    public String getMeasurement8() {
        return measurement8;
    }

    public void setMeasurement8(String measurement8) {
        this.measurement8 = measurement8;
    }

    public String getMeasurement9() {
        return measurement9;
    }

    public void setMeasurement9(String measurement9) {
        this.measurement9 = measurement9;
    }

    public String getMeasurement10() {
        return measurement10;
    }

    public void setMeasurement10(String measurement10) {
        this.measurement10 = measurement10;
    }







}
