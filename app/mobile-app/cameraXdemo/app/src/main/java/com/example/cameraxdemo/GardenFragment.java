package com.example.cameraxdemo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class GardenFragment extends Fragment{

    private RecyclerView rcvPlant;
    private PlantAdapter plantAdapter;
    private List<String> addedClasses;
    private List<String> addedPaths;
    private MainActivity mainActivity;
    private DataGarden dataGarden;

    public GardenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_garden, container, false);
        mainActivity = (MainActivity) getActivity();
        addedPaths = new ArrayList<>();
        addedClasses = new ArrayList<>();
        addedClasses = mainActivity.getAddedClasses();
        addedPaths = mainActivity.getAddedPaths();
        dataGarden = new DataGarden();
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if(addedClasses != null)
            addPlant();
        rcvPlant = (RecyclerView) view.findViewById(R.id.rcv_garden);
        plantAdapter = new PlantAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvPlant.setLayoutManager(linearLayoutManager);

        plantAdapter.setData(getListPlant());
        plantAdapter.addContext(mainActivity);
        rcvPlant.setAdapter(plantAdapter);
    }

    private List<Plant> getListPlant() {
        List<Plant> plants = dataGarden.getList();
        return plants;
    }

    private void addPlant() {
        for (int i = 0; i < addedClasses.size(); i++){
            Plant plant = LabelManager.getPlantByName(addedClasses.get(i));
            if (plant != null){
                plant.setThumbnail_path(addedPaths.get(i));
                dataGarden.addPlant(plant);
            }
        }
    }
}