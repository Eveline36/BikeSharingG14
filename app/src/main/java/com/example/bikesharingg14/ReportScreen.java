package com.example.bikesharingg14;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.io.FileOutputStream;

public class ReportScreen extends AppCompatActivity {

    private Spinner spinnerReportType;
    private EditText editTextDescription;
    private Button btnSubmitReport;
    private String selectedReportType;
    private String reportDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_screen);


        // Initialize views
        spinnerReportType = findViewById(R.id.spinnerReportType);
        editTextDescription = findViewById(R.id.editTextDescription);
        btnSubmitReport = findViewById(R.id.btnSubmitReport);

        // Set up Spinner with report types
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.report_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerReportType.setAdapter(adapter);

        // Listen for Spinner item selection
        spinnerReportType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedReportType = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        // Handle the Submit Button click
        btnSubmitReport.setOnClickListener(v -> submitReport());
    }

    private void submitReport() {
        // Get the description text
        reportDescription = editTextDescription.getText().toString().trim();
        Intent intent = getIntent();
        String bikeString = intent.getStringExtra("bikeString");
        // Create the report string with "bikeString: reportType: description" format
        String reportString = bikeString + ": "+ selectedReportType +": "+ reportDescription;
        saveReport(reportString);
    }

    private void saveReport(String report) {
        String filename = "reports.txt";
        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream = openFileOutput(filename, Context.MODE_APPEND);
            //This opens or creates a file with the given filename
            // MODE_APPEND-> add data to the end of the file (file already exists)
            // If the file doesn't exist, a new one will be created.
            outputStream.write(report.getBytes());
            // Converts the file Contents from text (like words and sentences) into bytes.
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Report Submitted: \n" + report, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ReportScreen.this,MainActivity.class);
        startActivity(intent);
    }

}
