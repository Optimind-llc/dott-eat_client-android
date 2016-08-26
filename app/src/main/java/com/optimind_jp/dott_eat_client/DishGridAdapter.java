package com.optimind_jp.dott_eat_client;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.optimind_jp.dott_eat_client.models.Dish;

import java.util.ArrayList;

/**
 * Created by hugh on 2016-08-25.
 */

public class DishGridAdapter extends ArrayAdapter {
    private Context context;
    private int layoutResourceId;
    private ArrayList<Dish> data = new ArrayList();


    public DishGridAdapter(Context context, int layoutResourceId, ArrayList data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;

    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.imageTitle = (TextView) row.findViewById(R.id.dishName);
            holder.image = (ImageView) row.findViewById(R.id.image);
            holder.price = (TextView) row.findViewById(R.id.dishPriceView);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        Drawable fallback = ContextCompat.getDrawable(context, R.drawable.powered_by_google_dark);
        Dish item = data.get(position);
        holder.imageTitle.setText(item.name);
        holder.price.setText(item.getPriceString());
        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(fallback)
                .showImageOnFail(fallback)
                .showImageOnLoading(fallback).build();

        imageLoader.displayImage(item.imageURL.toString(), holder.image, options);
        return row;
    }

    static class ViewHolder {
        TextView imageTitle;
        ImageView image;
        TextView price;
    }
}
