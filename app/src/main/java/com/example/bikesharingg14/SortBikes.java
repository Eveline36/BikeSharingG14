package com.example.bikesharingg14;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.ArrayList;

public class SortBikes extends AppCompatActivity {

    ArrayList<BikeModel> bikes;
    RadioGroup preference = findViewById(R.id.prefSelect);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sort_bikes);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        //not sure if this is correct, but saw somewhere online that it was, and it doesn't flag as an error so...
        bikes = (ArrayList<BikeModel>) bundle.getSerializable("bikes");

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    //we are sorting either on distance or range, not a combination because its easier for me and the user that way
    public void sortTime(View view) {
        //if bikes is empty immediately toss it back
        if (!bikes.isEmpty()) {
            int selection = preference.getCheckedRadioButtonId();

            //do nothing if no sorting preference is selected
            if (selection == -1) {
                return;
            }

            //find out what the selection is and do the sort
            RadioButton checkedOption = findViewById(selection);
            String checkedText = (String) checkedOption.getText();
            if (checkedText.equals("Distance from You")) {
                sortByDist();
            } else {
                sortByRange();
            }
        }
        //automatically return to main after sorting complete, or if bikes is empty
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bikes",(Serializable) bikes);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void sortByDist(){
        //sorts bikes based on each index's '.getDistance()' value
        //only tested it with ints so lmk if it doesn't work
        int low = 0;
        int high = bikes.size()-1;
        while (low<high){
            int max = high;
            int min = low;
            for(int i=low;i<=high;i++) {
                if (bikes.get(i).getDistance()<bikes.get(min).getDistance()) {min=i;}
            }
            if (min!=low) {
                BikeModel temp = bikes.get(low);
                bikes.set(low, bikes.get(min));
                bikes.set(min, temp);
            }
            for(int i=low;i<=high;i++) {
                if (bikes.get(i).getDistance()>bikes.get(max).getDistance()) {max=i;}
            }
            if (max!=high) {
                BikeModel temp = bikes.get(high);
                bikes.set(high, bikes.get(max));
                bikes.set(max, temp);
            }
            low++;
            high--;
        }
    }
    public void sortByRange(){
        //sorts bikes based on each index's '.getDistance()' value
        //only tested it with ints so lmk if it doesn't work
        int low = 0;
        int high = bikes.size()-1;
        while (low<high){
            int max = high;
            int min = low;
            for(int i=low;i<=high;i++) {
                if (bikes.get(i).getRange()<bikes.get(min).getRange()) {min=i;}
            }
            if (min!=low) {
                BikeModel temp = bikes.get(low);
                bikes.set(low, bikes.get(min));
                bikes.set(min, temp);
            }
            for(int i=low;i<=high;i++) {
                if (bikes.get(i).getRange()>bikes.get(max).getRange()) {max=i;}
            }
            if (max!=high) {
                BikeModel temp = bikes.get(high);
                bikes.set(high, bikes.get(max));
                bikes.set(max, temp);
            }
            low++;
            high--;
        }
    }
}