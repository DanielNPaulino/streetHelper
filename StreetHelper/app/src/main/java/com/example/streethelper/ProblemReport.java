package com.example.streethelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ProblemReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Toolbar toolBar;
    private Spinner spinner;
    private static final String[] paths = {"Street Holes", "Streetlight Out", "Street Sign Missing / Damaged", "Thrash Bin Spilled", "Other Problems"};

    private ImageView picImgView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int PICK_IMAGE = 100;
    private static final int PICK_MAP_POINT_REQUEST = 999;

    Uri galleryImageUri;

    private Button buttonSubmit;
    String problem;
    TextView location;

    private Button buttonLocation;
    private EditText locationTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_report);

        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProblemReport.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        picImgView = findViewById(R.id.pictureImgView);
        //button id finder
        buttonSubmit = findViewById(R.id.buttonSubmit);

        location = findViewById(R.id.locationTextView);

        buttonLocation = findViewById(R.id.locationButton);
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPointOnMap();
            }
        });

        //listener
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent (ProblemReport.this, ReportHistory.class);
                String loca = locationTxt.getText().toString();
                sendIntent.putExtra("loca", loca);
                sendIntent.putExtra("problem", problem);
                startActivity(sendIntent);
            }
        });
    }

    private void pickPointOnMap() {
        Intent pickPointIntent = new Intent(this, MapsActivity.class);
        startActivityForResult(pickPointIntent, PICK_MAP_POINT_REQUEST);
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

        startActivityForResult(imageTakeIntent,REQUEST_IMAGE_CAPTURE);

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

        //camera result
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            picImgView.setImageBitmap(imageBitmap);
        }

        //gallery result
        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            galleryImageUri = data.getData();
            picImgView.setImageURI(galleryImageUri);
        }

        //location result
        if (requestCode == PICK_MAP_POINT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                LatLng latLng = (LatLng) data.getParcelableExtra("picked_point");
                locationTxt = findViewById(R.id.recieveLocation);
                locationTxt.setText(latLng.latitude + "" + latLng.longitude);

                Toast.makeText(this, "Point Chosen: " + latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();
            }
        }
    }
}
