package com.example.bikesharingg14;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class Bike_RecyclerViewAdapter extends RecyclerView.Adapter <Bike_RecyclerViewAdapter.BikeViewHolder> {
    Context context;
    ArrayList<BikeModel> bikeModels;
    private final BikeRecyclerInterface recyclerInterface;

    public Bike_RecyclerViewAdapter(Context context, ArrayList<BikeModel> bikeModels,BikeRecyclerInterface recyclerInterface){
        this.context = context;
        this.bikeModels = bikeModels;
        this.recyclerInterface = recyclerInterface;
    }

    @NonNull
    @Override
    public Bike_RecyclerViewAdapter.BikeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.bike_recycler_row,parent,false);
        return new Bike_RecyclerViewAdapter.BikeViewHolder(view,recyclerInterface);
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
        Button findbikebutton;
        ImageView BikeIcon;
        TextView WalkDist, RangeEst;

        public BikeViewHolder(@NonNull View itemView,BikeRecyclerInterface recyclerInterface) {
            super(itemView);
            BikeIcon = itemView.findViewById(R.id.bikeicon);
            WalkDist = itemView.findViewById(R.id.walkduration);
            RangeEst = itemView.findViewById(R.id.rangeleft);

            findbikebutton = itemView.findViewById(R.id.findbikebutton);
            findbikebutton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if(recyclerInterface!=null){
                        int pos = getAdapterPosition();

                        if(pos!=RecyclerView.NO_POSITION){
                            recyclerInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }
}
