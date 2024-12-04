package com.example.bikesharingg14;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaymentActivity extends AppCompatActivity {
    TextView rateInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        rateInfo = findViewById(R.id.rateInfo);
    }

    public void endRide(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openReceipt(View v){
        if(rateInfo.getVisibility()==View.INVISIBLE) {
            rateInfo.setVisibility(View.VISIBLE);
        }
        else{
            rateInfo.setVisibility(View.INVISIBLE);
        }
    }


    //This should be used to call the issue report menu
    public void reportBike(View v){
        Intent inputIntent = getIntent();
        String bikeString = null;
        if(inputIntent!=null){
            bikeString = inputIntent.getStringExtra("bikeString");
        }
        else{
            Log.d("Activity Transfer","Input Intent to Payment is null");
        }
        Intent intent = new Intent(this, ReportScreen.class);
        if(bikeString!=null&&!bikeString.isEmpty()) {
            intent.putExtra("reportBike", bikeString);
        }
        else{
            Log.d("Activity Transfer","toReportBikeString is null");
        }
        startActivity(intent);
    }
}