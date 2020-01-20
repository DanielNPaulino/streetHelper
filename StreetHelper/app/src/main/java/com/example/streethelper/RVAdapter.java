package com.example.streethelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass> {

    ArrayList<ModelClass> objectModelClassList;

    /**
     * Instance of arraylist Model class.
     * @param objectModelClassList
     */
    public RVAdapter(ArrayList<ModelClass> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }

    /**
     * ViewHolder Builder.
     * @param parent
     * @param viewType
     * @return recyclerview viewholder
     */
    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolderClass(LayoutInflater.from(parent.getContext()).inflate(R.layout.check_item, parent, false));
    }

    /**
     * Sets current position item in the recyclerview using getter and setter methods.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {
        ModelClass objectModelClass = objectModelClassList.get(position);
        holder.typeOfProblem.setText(objectModelClass.getTypeOfProblem());
        holder.locationProblem.setText(objectModelClass.getImageName());
        holder.problemImage.setImageBitmap(objectModelClass.getImage());
    }

    /**
     * Returns current list size.
     * @return
     */
    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    /**
     * Inner Class for setting correspondence between the views that will store the values
     * and the variables.
     */
    public static class RVViewHolderClass extends RecyclerView.ViewHolder
    {

        TextView typeOfProblem;
        TextView locationProblem;
        ImageView problemImage;
        public RVViewHolderClass(@NonNull View itemView) {
            super(itemView);
            typeOfProblem=itemView.findViewById(R.id.typeOfProblemTxtview);
            locationProblem=itemView.findViewById(R.id.locationProblemTxtview);
            problemImage=itemView.findViewById(R.id.problemImageView);

        }
    }
}
