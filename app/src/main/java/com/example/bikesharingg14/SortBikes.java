package com.example.bikesharingg14;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SortBikes extends AppCompatActivity {

    ArrayList<BikeModel> bikes;
    private static final Type BIKE_TYPE = new TypeToken<List<BikeModel>>() {}.getType();
    RadioGroup preference;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sort_bikes);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        preference = findViewById(R.id.prefSelect);

        Intent intent = getIntent();
        String bikeString = intent.getStringExtra("bikes");
        Log.d("Intent Bike String",bikeString);
        //not sure if this is correct, but saw somewhere online that it was, and it doesn't flag as an error so...

        bikes = gson.fromJson(bikeString,BIKE_TYPE);
        Log.d ("Array Output",bikes.toString());

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
        String bikeString = gson.toJson(bikes);
        intent.putExtra("bikes",bikeString);
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
        reverseList();
    }

    private void reverseList() {
        ArrayList<BikeModel> temp = new ArrayList<>();
        for(int i = bikes.size()-1;i>=0;i--){
            temp.add(bikes.get(i));
        }
        bikes = new ArrayList<BikeModel>();
        for (BikeModel bike: temp) {
            bikes.add(bike);
        }
    }
}