package com.example.streethelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    private static String DATABASE_NAME = "streetDB.db";
    private static int DATABASE_VERSION = 2;
    private static String createTableQuery = "create table imageInfo (imageName TEXT" + ", typeOfProblem TEXT" +  ", image BLOB)";

    private ByteArrayOutputStream objectByteArrayOutPutStream;
    private byte[] imageInBytes;


    /**
     * Constructor for database handler.
     * @param context
     */
    public DatabaseHandler (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    /**
     * On create method for database handler.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(createTableQuery);
            Toast.makeText(context, "Table created successfully on database", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     * Method that adds the content of imageview who recieves the photo and the content of the
     * edittext who recieves the location from map.
     * @param objectModelClass
     */
    public void storeImage(ModelClass objectModelClass){
        try {
            SQLiteDatabase objectSQLiteDatabase = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = objectModelClass.getImage();

            objectByteArrayOutPutStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutPutStream);

            imageInBytes = objectByteArrayOutPutStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();

            //getters for imagename ,type of problem and image
            objectContentValues.put("imageName", objectModelClass.getImageName());
            objectContentValues.put("typeOfProblem", objectModelClass.getTypeOfProblem());
            objectContentValues.put("image", imageInBytes);


            //setter of imagename type of problem and image to table
            long checkIfQueryRuns = objectSQLiteDatabase.insert("imageInfo", null, objectContentValues);

            if (checkIfQueryRuns != -1){
                Toast.makeText(context, "Data added to the Database", Toast.LENGTH_SHORT).show();
                objectSQLiteDatabase.close();
            }
            else {
                Toast.makeText(context, "Fails to add Data", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ArrayList to recieve the reports saved on the database, to be called in the getData method
     * used to show all database information.
     * @return
     */
    public ArrayList<ModelClass> getAllImagesData() {
        try {
            SQLiteDatabase objectSqliteDatabase = this.getReadableDatabase();
            ArrayList<ModelClass> objectModelClassList = new ArrayList<>();

            Cursor objectCursor = objectSqliteDatabase.rawQuery("select * from imageInfo", null);
            if (objectCursor.getCount() != 0)
            {
                while (objectCursor.moveToNext())
                {
                    String typeOfProblem = objectCursor.getString(0);
                    String locationProblem = objectCursor.getString(1);
                    byte [] imageBytes=objectCursor.getBlob(2);

                    Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                    objectModelClassList.add(new ModelClass(typeOfProblem,locationProblem,objectBitmap));
                }
                return objectModelClassList;
            }
            else
            {
                Toast.makeText(context, "No values found in Database", Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
}

