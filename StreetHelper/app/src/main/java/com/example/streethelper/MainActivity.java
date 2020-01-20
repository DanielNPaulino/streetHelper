package com.example.streethelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    public Button buttonReport;
    private Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sets toolbar
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);


        //listener for reportButton
        buttonReport = findViewById(R.id.reportButton);
        buttonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProblemReport.class));
            }
        });
    }

    /**
     * Intent to go to the CheckReportsDB called on the onclick of button "View Reports"
     * @param view
     */
    public void checkReports(View view){
        startActivity(new Intent(this,CheckReportsDB.class));
    }
}
