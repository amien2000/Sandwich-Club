//Program       : Nano Degree
//Course        : Android Developer
//Project Title : Sandwich Club
//Student Name  : Abdul Razak Husin
//Email         : husinabdulrazak@gmail.com

package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        //Create new sandwich instance/ object
        Sandwich sandwich = new Sandwich();

        //Parse the required JSON string for the selected sandwich element from DetailActivity.
        try{
            JSONObject rootJson = new JSONObject(json);
            String image = rootJson.getString("image");
            JSONObject name = rootJson.getJSONObject("name");
            String mainName= name.getString("mainName");
            JSONArray alsoKnownAsJSONArray =name.getJSONArray("alsoKnownAs");
            //Convert JSONArray format to ArrayList format
            List<String> alsoKnownAs = new ArrayList<String>();
            if (alsoKnownAsJSONArray != null){
                for(int i=0; i< alsoKnownAsJSONArray.length(); i++){
                    alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
                }
            }
            String placeOfOrigin = rootJson.getString("placeOfOrigin");
            String description = rootJson.getString("description");
            JSONArray ingredientsJSONArray = rootJson.getJSONArray("ingredients");
            //Convert JSONArray format to ArrayList format
            List<String> ingredients = new ArrayList<String>();
            if (ingredientsJSONArray != null){
                for(int j=0; j<ingredientsJSONArray.length(); j++){
                    ingredients.add(ingredientsJSONArray.getString(j));
                }
            }
            //Update the sandwich instance/ object.
            sandwich.setImage(image);
            sandwich.setMainName(mainName);
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setPlaceOfOrigin(placeOfOrigin);
            sandwich.setDescription(description);
            sandwich.setIngredients(ingredients);

            return sandwich;

        }catch(JSONException e){
            e.printStackTrace();
        }

        return null;
    }
}
