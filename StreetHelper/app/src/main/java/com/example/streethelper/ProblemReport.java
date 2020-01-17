package com.example.streethelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Toolbar toolBar;
    private Spinner spinner;
    private static final String[] paths = {"Street Holes", "Streetlight Out", "Street Sign Missing / Damaged", "Thrash Bin Spilled", "Other Problems"};

    private ImageView picImgView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int PICK_IMAGE = 100;

    Uri imageUri;

    private Button buttonSubmit;
    String problem;
    TextView location;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_report);

        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProblemReport.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        picImgView = findViewById(R.id.pictureImgView);
        //button id finder
        buttonSubmit = findViewById(R.id.buttonSubmit);

        location = findViewById(R.id.locationTextView);

        //listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent (ProblemReport.this, ReportHistory.class);
                String loca = location.getText().toString();
                sendIntent.putExtra("loca", loca);
                sendIntent.putExtra("problem", problem);
                startActivity(sendIntent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        String problemType = spinner.getSelectedItem().toString();
        problem = problemType;



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    /**
     * method with the intent to access camera.
     * @param view
     */
    public void takePicture(View view) {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(imageTakeIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    public void openGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,PICK_IMAGE);
    }

    /**
     * set picture taken to imageview
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picImgView.setImageBitmap(imageBitmap);
        }

        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            imageUri = data.getData();
            picImgView.setImageURI(imageUri);
        }
    }

}
