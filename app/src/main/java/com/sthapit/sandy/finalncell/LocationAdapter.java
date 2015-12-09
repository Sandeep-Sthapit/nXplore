package com.sthapit.sandy.finalncell;

/**
 * Created by Dell on 11/20/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by venkataprasad on 02-01-2015.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private static ArrayList<LocationItem> dataSet;

    public LocationAdapter(ArrayList<LocationItem> item) {

        dataSet = item;
    }


    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
// create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.location_view, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LocationAdapter.ViewHolder viewHolder, int i) {

        LocationItem fp = dataSet.get(i);

        viewHolder.tvName.setText(fp.getTitle());
        viewHolder.tvLocation.setText(fp.getLocation());
        viewHolder.tvPhone.setText(fp.getPhone());
        viewHolder.iconView.setImageResource(fp.getThumbnail());
        viewHolder.feed = fp;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvLocation;
        public TextView tvPhone;
        public ImageView iconView;

        public LocationItem feed;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView
                    .findViewById(R.id.tv_hotel_name);
            tvLocation = (TextView) itemLayoutView
                    .findViewById(R.id.tv_hotel_location);
            tvPhone = (TextView) itemLayoutView
                    .findViewById(R.id.tv_hotel_phone);
            iconView = (ImageView) itemLayoutView
                    .findViewById(R.id.img_location);

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), CusineActivity.class);
                    intent.putExtra("item_title",feed.getTitle());
                    intent.putExtra("item_image",feed.getThumbnail());
                    v.getContext().startActivity(intent);
                    Toast.makeText(v.getContext(), "Item is: " + feed.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });


        }

    }
}

