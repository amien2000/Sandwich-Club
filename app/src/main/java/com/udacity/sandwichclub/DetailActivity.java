//Program       : Nano Degree
//Course        : Android Developer
//Project Title : Sandwich Club
//Student Name  : Abdul Razak Husin
//Email         : husinabdulrazak@gmail.com

package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }
        //Get the string array with element content in JSON format.
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        //Select the array element based on position from intent
        String json = sandwiches[position];
        //Pass the array element content which is in JSON format to JsonUtils for parsing
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }
        //Set toolbar title
        setTitle(sandwich.getMainName());

        populateUI(sandwich);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //Populate ImageView with image URL processed by Picasso API.
        final ImageView ingredientsIv = findViewById(R.id.image_iv);
           Picasso.get()
                    .load(sandwich.getImage())
                    .into(ingredientsIv, new Callback() {
            //Respond to url link error.
            @Override
            public void onSuccess() {
                Log.i("DetailActivity","****SUCCESSFUL****");
            }
            @Override
            public void onError(Exception e) {
                e.printStackTrace();
                Log.e("DetailActivity","****IMAGE NOT AVAILABLE****");
                //Provide and default image if there is no image generated.
                ingredientsIv.setImageResource(R.drawable.internet_error);
            }
        });

        //Populate sandwich name TextView with string from Sandwich object.
        String mainName = sandwich.getMainName();
        TextView mainNameTextView = (TextView) findViewById(R.id.sandwich_name_tv);
        mainNameTextView.setText(mainName);

        //Populate aka TextView with list from Sandwich object.
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        TextView alsoKnownAsTextView = (TextView) findViewById(R.id.aka_tv);
        //Format the text display according to cases having no input string, having one string
        //and having multiple strings.
        switch (alsoKnownAs.size()) {
            case 0:
                alsoKnownAsTextView.setText(" - - -");
                break;
            case 1:
                alsoKnownAsTextView.setText(alsoKnownAs.get(0));
                break;
            default:
                for (int i = 0; i < alsoKnownAs.size()-1; i++) {
                    alsoKnownAsTextView.append((i+1)+". "+alsoKnownAs.get(i)+"\n");
                }
                //Format the last element (without \n) so that there is no extra spacing below the TV.
                alsoKnownAsTextView.append((alsoKnownAs.size())+". "+alsoKnownAs.get(alsoKnownAs.size()-1));
                break;
        }

        //Populate placeOfOrigin TextView with string from Sandwich object.
        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        TextView placeOfOriginTextView = (TextView) findViewById((R.id.place_of_origin_tv));
        //Format the text display according to cases having no input String and having one.
        switch (placeOfOrigin){
            case "":
                placeOfOriginTextView.setText(" - - -");
                break;
             default:
                 placeOfOriginTextView.setText(placeOfOrigin);
                 break;
        }

        //Populate description TextView with text from Sandwich object.
        String description = sandwich.getDescription();
        TextView descriptionTextView = (TextView) findViewById(R.id.description_tv);
        descriptionTextView.setText(description);

        //Populate ingredients TextView with list from Sandwich object.
        List<String> ingredients = sandwich.getIngredients();
        TextView ingredientsTextView = (TextView) findViewById(R.id.ingredients_tv);
        //Format list so that each each element is shown in separate lines.
        for (int j =0; j< ingredients.size()-1; j++) {
            ingredientsTextView.append((j+1)+". "+ingredients.get(j)+"\n");
        }
        //Format the last element (without \n) so that there is no extra spacing below the TV.
        ingredientsTextView.append((ingredients.size())+". "+ingredients.get(ingredients.size()-1));

    }
}
