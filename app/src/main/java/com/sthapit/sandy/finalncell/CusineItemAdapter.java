package com.sthapit.sandy.finalncell;

/**
 * Created by Dell on 11/20/2015.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by venkataprasad on 02-01-2015.
 */
public class CusineItemAdapter extends RecyclerView.Adapter<CusineItemAdapter.ViewHolder> {

    private static ArrayList<Items> dataSet;

    public CusineItemAdapter(ArrayList<Items> item) {

        dataSet = item;
    }


    @Override
    public CusineItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
// create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.cusine_item_view, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CusineItemAdapter.ViewHolder viewHolder, int i) {

        Items fp = dataSet.get(i);

        viewHolder.tvName.setText(fp.getTitle());
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
        public ImageView iconView;
        public Button voteButton;
        public TextView vote;
        public Items feed;
        public boolean liked = false;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.tvName_cusine);
            iconView = (ImageView) itemLayoutView.findViewById(R.id.iconId_cusine);
            voteButton = (Button) itemLayoutView.findViewById(R.id.bt_vote);
            vote = (TextView) itemLayoutView.findViewById(R.id.vote_number);

            voteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!liked) {
                        vote.setText("1");
                        tvName.setTextColor(Color.BLUE);
                        vote.setTextColor(Color.BLUE);
                        voteButton.setBackgroundResource(R.drawable.ic_icon_like);
                        liked = true;
                    } else {
                        vote.setText("0");
                        tvName.setTextColor(Color.DKGRAY);
                        vote.setTextColor(Color.DKGRAY);
                        voteButton.setBackgroundResource(R.drawable.ic_icon_no_like);
                        liked = false;
                    }
                }
            });

            itemLayoutView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), FoodItemActivity.class);
                    intent.putExtra("item_title", feed.getTitle());
                    intent.putExtra("item_image", feed.getThumbnail());
                    v.getContext().startActivity(intent);
                    Toast.makeText(v.getContext(), "Item is: " + feed.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });


        }

    }
}

