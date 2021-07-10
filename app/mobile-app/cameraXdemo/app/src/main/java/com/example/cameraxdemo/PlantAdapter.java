package com.example.cameraxdemo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlantAdapter extends RecyclerView.Adapter<PlantAdapter.PlantViewHolder> {

    private List<Plant> mPlants;
    private Context context;

    public void setData(List<Plant> plants){
        this.mPlants = plants;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_garden, parent, false);
        return new PlantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant plant = mPlants.get(position);
        if(plant == null)
            return;
        Bitmap myBitmap = BitmapFactory.decodeFile(plant.getThumbnail_path());
        holder.viewThumbnail.setImageBitmap(myBitmap);
        holder.viewPlantName.setText(plant.getPlant_name());
        holder.viewPlantScientificName.setText(plant.getPlant_scientific_name());
        holder.viewPlantSun.setText(plant.getPlant_sun());
        holder.viewPlantWater.setText(plant.getPlant_water());
        holder.viewPlantFertilizer.setText(plant.getPlant_fertilizer());
        holder.viewGardenCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShowPlantDetail.class);
                intent.putExtra("name", plant.getPlant_name());
                intent.putExtra("path", plant.getThumbnail_path());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mPlants != null){
            return mPlants.size();
        }
        return 0;
    }

    public class PlantViewHolder extends RecyclerView.ViewHolder{
        private ImageView viewThumbnail;
        private TextView viewPlantName;
        private TextView viewPlantScientificName;
        private TextView viewPlantSun;
        private TextView viewPlantWater;
        private TextView viewPlantFertilizer;
        private CardView viewGardenCard;

        public PlantViewHolder(@NonNull View itemView) {
            super(itemView);
            viewThumbnail = itemView.findViewById(R.id.thumbnail);
            viewPlantName = itemView.findViewById(R.id.plant_name);
            viewPlantScientificName = itemView.findViewById(R.id.plant_scienctific_name);
            viewPlantSun = itemView.findViewById(R.id.plant_sun);
            viewPlantWater = itemView.findViewById(R.id.plant_water);
            viewPlantFertilizer = itemView.findViewById(R.id.plant_fertilizer);
            viewGardenCard = (CardView) itemView.findViewById(R.id.garden_card);
        }
    }

    public void addContext(Context context){
        this.context = context;
    }
}
