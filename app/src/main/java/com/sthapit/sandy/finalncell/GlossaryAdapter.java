package com.sthapit.sandy.finalncell;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Dell on 12/8/2015.
 */
public class GlossaryAdapter extends RecyclerView.Adapter<GlossaryAdapter.ViewHolder> {

    private static ArrayList<GlossaryItem> dataSet;

    public GlossaryAdapter(ArrayList<GlossaryItem> item) {

        dataSet = item;
    }


    @Override
    public GlossaryAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
// create a new view
        View itemLayoutView = LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.glossary_item_list, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(GlossaryAdapter.ViewHolder viewHolder, int i) {

        GlossaryItem fp = dataSet.get(i);

        viewHolder.tvName.setText(fp.getName());
        viewHolder.tvNepali.setText(fp.getName_nepali());
        viewHolder.feed = fp;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    // inner class to hold a reference to each item of RecyclerView
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvNepali;
        public GlossaryItem feed;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);

            tvName = (TextView) itemLayoutView.findViewById(R.id.tv_glossary_top);
            tvNepali = (TextView) itemLayoutView.findViewById(R.id.tv_glossary_mid);

        }
    }
}
