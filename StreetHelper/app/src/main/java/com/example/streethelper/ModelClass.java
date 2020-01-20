package com.example.streethelper;

import android.graphics.Bitmap;

public class ModelClass {

    private String imageName;
    private String typeOfProblem;
    private Bitmap image;

    public ModelClass(String imageName, String typeOfProblem, Bitmap image) {
        this.imageName = imageName;
        this.typeOfProblem = typeOfProblem;
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTypeOfProblem() {
        return typeOfProblem;
    }

    public void setTypeOfProblem(String typeOfProblem) {
        this.typeOfProblem = typeOfProblem;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
