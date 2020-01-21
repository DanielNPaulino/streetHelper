package com.example.streethelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CheckReportsDB extends AppCompatActivity {

    private DatabaseHandler objectDatabaseHandler;
    private RecyclerView objectRecyclerView;
    private RVAdapter objectRvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_reports_db);

        try {
            objectRecyclerView=findViewById(R.id.imagesRV);
            objectDatabaseHandler=new DatabaseHandler(this);
        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Method that calls the view in CheckReportsDB to load from DB.
     * @param view
     */
    public void getData(View view){
        try {
           objectRvAdapter = new RVAdapter(objectDatabaseHandler.getAllImagesData());
           objectRecyclerView.setHasFixedSize(true);

           objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
           objectRecyclerView.setAdapter(objectRvAdapter);
        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
