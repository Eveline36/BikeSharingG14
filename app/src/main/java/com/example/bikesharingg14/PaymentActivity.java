package com.example.bikesharingg14;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PaymentActivity extends AppCompatActivity {

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
    }

    public void endRide(View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    public void openReceipt(View v){
        Intent intent = new Intent(this,ReceiptActivity.class);
        startActivity(intent);
    }


    //This should be used to call the issue report menu
    public void reportBike(View v){
        //Intent intent = new Intent(this,report.class);
        //startActivity(intent);
    }
}