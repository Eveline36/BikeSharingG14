package com.example.bikesharingg14;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Bike_RecyclerViewAdapter extends RecyclerView.Adapter <Bike_RecyclerViewAdapter.BikeViewHolder> {
    Context context;
    ArrayList<BikeModel> bikeModels;

    public Bike_RecyclerViewAdapter(Context context, ArrayList<BikeModel> bikeModels){
        this.context = context;
        this.bikeModels = bikeModels;
    }

    @NonNull
    @Override
    public Bike_RecyclerViewAdapter.BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bike_recycler_row,parent,false);
        return new Bike_RecyclerViewAdapter.BikeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Bike_RecyclerViewAdapter.BikeViewHolder holder, int position) {
        holder.WalkDist.setText(bikeModels.get(position).getDistance() + "");

        int tempMaxRange = BikeModel.BIKE_MAX_RANGE;
        int tempRange = bikeModels.get(position).getRange();

        holder.RangeEst.setText(tempRange+"");
        holder.BikeIcon.setImageResource(bikeModels.get(position).getImageResource());

    }

    @Override
    public int getItemCount() {
        return bikeModels.size();
    }
    public static class BikeViewHolder extends RecyclerView.ViewHolder {

        ImageView BikeIcon;
        TextView WalkDist, RangeEst;

        public BikeViewHolder(@NonNull View itemView) {
            super(itemView);
            BikeIcon = itemView.findViewById(R.id.bikeicon);
            WalkDist = itemView.findViewById(R.id.walkduration);
            RangeEst = itemView.findViewById(R.id.rangeleft);
        }
    }
}
