package com.optimind_jp.dott_eat_client;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * Created by hugh on 2016-08-26.
 */

public class DishViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView countryName;
    public ImageView countryPhoto;
    public TextView dishPrice;
    private boolean isSelected = false;
    private View.OnClickListener listener;

    public DishViewHolder(View itemView, View.OnClickListener listener) {
        super(itemView);
        countryName = (TextView) itemView.findViewById(R.id.dishName);
        countryPhoto = (ImageView) itemView.findViewById(R.id.dishImage);
        dishPrice = (TextView) itemView.findViewById(R.id.dishPriceView);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        //Toast.makeText(view.getContext(), "Clicked Position = " + getPosition(), Toast.LENGTH_SHORT).show();
        isSelected= !isSelected;
        updateView(view);
        listener.onClick(view);
    }

    private void updateView(View view){
        if(isSelected){
            view.setAlpha(0.5f);
            view.setTag("SELECTED");
        }else {
            view.setAlpha(1f);
            view.setTag("NOT SELECTED");
        }
    }

    public static boolean isSelected(View view){
        Object tag = view.getTag();
        if(tag!=null){
            return tag=="SELECTED";
        }else{
            return false;
        }
    }
}