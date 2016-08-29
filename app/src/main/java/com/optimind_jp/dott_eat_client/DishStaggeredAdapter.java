package com.optimind_jp.dott_eat_client;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.optimind_jp.dott_eat_client.models.Dish;

import java.util.List;

/**
 * Created by hugh on 2016-08-26.
 */

public class DishStaggeredAdapter  extends RecyclerView.Adapter<DishViewHolder> {

    private List<Dish> itemList;
    private Context context;
    View layoutView;
    Drawable fallback;
    View.OnClickListener listener;


    public DishStaggeredAdapter(Context context, List<Dish> itemList, View.OnClickListener listener) {
        this.itemList = itemList;
        this.context = context;
        this.listener = listener;
        fallback=  ContextCompat.getDrawable(context, R.drawable.powered_by_google_dark);
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dish_list, null);
        DishViewHolder rcv = new DishViewHolder(layoutView);
        rcv.setListener(this.listener);
        return rcv;
    }

    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {

        holder.countryName.setText(itemList.get(position).name);
        holder.dishPrice.setText(itemList.get(position).getPriceString());
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(fallback)
                .showImageOnFail(fallback)
                .showImageOnLoading(fallback).build();
        imageLoader.displayImage(itemList.get(position).imageURL, holder.countryPhoto, options);

    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
    public static void addBadgeCount(Context context, LayerDrawable icon){


    }


}
