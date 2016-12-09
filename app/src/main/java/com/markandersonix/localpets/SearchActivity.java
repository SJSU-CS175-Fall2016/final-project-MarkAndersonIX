package com.markandersonix.localpets;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.type_spinner) Spinner typeSpinner;
    @BindView(R.id.breed_spinner) Spinner breedSpinner;
    @BindView(R.id.sex_spinner) Spinner sexSpinner;
    @BindView(R.id.size_spinner) Spinner sizeSpinner;
    @BindView(R.id.age_spinner) Spinner ageSpinner;
    @BindView(R.id.zip_edittext) EditText zipEditText;
    @BindView(R.id.search_button) Button searchButton;
    HashMap<String,String> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        options = new HashMap<>();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.e("typeSpinner:",typeSpinner.getSelectedItem().toString());
                if(!typeSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("animal", typeSpinner.getSelectedItem().toString().toLowerCase());
                }
                if(!breedSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("breed", breedSpinner.getSelectedItem().toString().toLowerCase());
                }
                if(!sexSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("sex", sexSpinner.getSelectedItem().toString().toLowerCase());
                }
                if(!sizeSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("size", sizeSpinner.getSelectedItem().toString().toLowerCase());
                }
                if(!ageSpinner.getSelectedItem().toString().equals("Any")) {
                    options.put("age", ageSpinner.getSelectedItem().toString().toLowerCase());
                }

                Intent resultIntent = new Intent();
                resultIntent.putExtra("options",options); //return map of options to main
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
