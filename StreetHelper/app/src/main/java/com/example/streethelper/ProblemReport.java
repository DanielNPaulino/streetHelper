package com.example.streethelper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProblemReport extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Toolbar toolBar;
    private Spinner spinner;
    private static final String[] typeOfProblem = {"Street Holes", "Streetlight Out", "Street Sign Missing / Damaged", "Thrash Bin Spilled", "Other Problems"};

    private ImageView picImgView;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final int PICK_IMAGE = 100;
    private static final int PICK_MAP_POINT_REQUEST = 999;

    private Button buttonSubmit;
    String problem;

    private Button buttonLocation;
    private EditText locationTxt;

    private DatabaseReference mDatabase;
    private Uri imageFilePath;
    private Bitmap imageToStore;
    DatabaseHandler objectDatabaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_report);
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        try{
            picImgView = findViewById(R.id.pictureImgView);
            objectDatabaseHandler = new DatabaseHandler(this);


        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //set top toolbar
        toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        //spinner default setter using string array typeOfProblem
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ProblemReport.this,
                android.R.layout.simple_spinner_item,typeOfProblem);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        picImgView = findViewById(R.id.pictureImgView);



        //listener for opening Map activity
        buttonLocation = findViewById(R.id.locationButton);
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPointOnMap();
            }
        });

        //listener for submit button that adds location and type of problem to the intent
        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //saves image and location to local database
                storeImage();

                Intent sendIntent = new Intent (ProblemReport.this, ReportHistory.class);
                String loca = locationTxt.getText().toString();
                sendIntent.putExtra("loca", loca);
                sendIntent.putExtra("problem", problem);
                startActivity(sendIntent);
            }
        });
    }

    /**
     * Method that have the intent to call Map activity.
     */
    private void pickPointOnMap() {
        Intent pickPointIntent = new Intent(this, MapsActivity.class);
        startActivityForResult(pickPointIntent, PICK_MAP_POINT_REQUEST);
    }

    /**
     * Method that gets the selected item's text to a global string to be passed to next activity.
     * @param parent
     * @param v
     * @param position
     * @param id
     */
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
     * Method with the intent to access camera.
     * @param view
     */
    public void takePicture(View view) {
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(imageTakeIntent,REQUEST_IMAGE_CAPTURE);

    }

    /**
     * Method with the intent to access Gallery.
     * @param view
     */
    public void openGallery(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,PICK_IMAGE);
    }

    /**
     * Set picture taken to imageview, set picture from gallery to imageview, and sets map point
     * chosen to editText.
     * @param requestCode code to enter each if.
     * @param resultCode code to give confirmation by user.
     * @param data data for intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //camera result
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode==RESULT_OK) {
            Bundle extras = data.getExtras();
            imageToStore = (Bitmap) extras.get("data");
            picImgView.setImageBitmap(imageToStore);
        }

        //gallery result
        if (requestCode==PICK_IMAGE && resultCode==RESULT_OK){
            imageFilePath = data.getData();
            picImgView.setImageURI(imageFilePath);
        }

        //location result
        if (requestCode == PICK_MAP_POINT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                LatLng latLng = (LatLng) data.getParcelableExtra("picked_point");

                String geoLocation = getCityName(latLng);

                locationTxt = findViewById(R.id.recieveLocation);
                locationTxt.setText(geoLocation);

                Toast.makeText(this, "Point Chosen: " + latLng.latitude + " " + latLng.longitude, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Method that gets the address from the latlang given.
     * @param latLng LatLng given.
     * @return myCity
     */
    private String getCityName(LatLng latLng) {
        String myCity = "";
        Geocoder geocoder = new Geocoder(ProblemReport.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude,latLng.longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            myCity = addresses.get(0).getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return myCity;
    }

    /**
     * Method that saves image and location to the local database.
     */
    private void storeImage(){
        try {
            if (!locationTxt.getText().toString().isEmpty() && picImgView.getDrawable() != null && imageToStore != null){
                objectDatabaseHandler.storeImage(new ModelClass(locationTxt.getText().toString(),imageToStore));
            }else 
            {
                Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
            }
            
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
