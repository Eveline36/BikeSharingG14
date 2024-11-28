package com.example.bikesharingg14;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.MapFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize map fragment
        Fragment fragment = new MapsFragment();

        // Open map fragment
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.frame_layout,fragment)
                .commit();

        // Initialize Bottom Sheet
        FrameLayout framelayout = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(framelayout);

        //Set Bottom Sheet peek height
        sheetBehavior.setPeekHeight(200);
        //Set Bottom Sheet to default collapse
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }
}