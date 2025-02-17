package com.example.streethelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private ArrayList<ReportItem> mItemList;

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        /**
         * Constructor for itemViewHolder class.
         * @param itemView
         */
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.reportItemImgView);
            mTextView1 = itemView.findViewById(R.id.reportItemTypeProblem);
            mTextView2 = itemView.findViewById(R.id.reportLocationProblem);
        }
    }

    /**
     * Instance of itemList.
     * @param itemList
     */
    public ItemAdapter(ArrayList<ReportItem> itemList){
        mItemList = itemList;
    }

    /**
     * ViewHolder builder.
     * @param parent
     * @param viewType
     * @return ivh itemViewHolder.
     */
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_item, parent, false);
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }

    /**
     * Sets the current item's Image, text and text2 to the item of CardView.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ReportItem currentItem = mItemList.get(position);
        holder.mImageView.setImageResource(currentItem.getImageResource());
        holder.mTextView1.setText(currentItem.getText1());
        holder.mTextView2.setText(currentItem.getText2());
    }

    /**
     * Returns current size of the ItemList.
     * @return itemList Size
     */
    @Override
    public int getItemCount() {
        return mItemList.size();
    }
}
