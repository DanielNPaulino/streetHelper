package com.example.streethelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class ReportHistory extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<ReportItem> itemList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_history);

        //intent that recieves String extras from previous activity
        Intent intent = getIntent();
        String newProblem = intent.getStringExtra("problem");
        String newLoca = intent.getStringExtra("loca");

        buildRecyclerView();
        insertItem(R.drawable.logo, newProblem, newLoca);

    }

    /**
     * Method responsible for defining the recycler view that is going to hold the items in the ArrayList
     * Creation of the layoutManager and RecyclerAdapter for the recycler view and assign them to the recycler view.
     */
    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ItemAdapter(itemList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Method that inserts the image and strings to the Cardview.
     * @param imageView
     * @param type
     * @param location
     */
    private void insertItem(int imageView, String type, String location) {
        itemList.add(new ReportItem(R.drawable.logo, type, location));
        mAdapter.notifyDataSetChanged();
    }
}
