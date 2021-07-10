package com.example.cameraxdemo;

import java.util.ArrayList;
import java.util.List;

public class DataGarden {
    private List<Plant> mPlants;

    public DataGarden() {
        mPlants = new ArrayList<>();
        mPlants.add(new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210709_110215.jpg"
                , "Cây xương rồng", "Euphorbia antiquorum", "Nửa nắng"
                , "Mỗi 6-10 ngày", "Mỗi 6 tháng"));
        mPlants.add(new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210709_110136.jpg"
                , "Hoa chuông vàng", "Tabebuia aurea", "Nhiều nắng"
                , "Mỗi 1-2 ngày", "Mỗi 4 tháng"));
        mPlants.add(new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210709_110021.jpg"
                , "Hoa trang", "Ixora coccinea", "Nhiều nắng"
                , "Mỗi 3-4 ngày", "Mỗi 2 tháng"));
        mPlants.add(new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210710_121207.jpg"
                , "Hoa sứ đỏ", "Plumeria", "Nhiều nắng"
                , "Mỗi ngày", "Mỗi 20 ngày"));
        mPlants.add(new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210710_121229.jpg"
                , "Hoa brassavola vàng", "Brassavola", "Nhiều nắng"
                , "Mỗi ngày", "Mỗi 1 tháng"));
        mPlants.add(new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210710_121249.jpg"
                , "Hoa brassavola trắng", "Brassavola ", "Nhiều nắng"
                , "Mỗi 1-2 ngày", "Mỗi 1 tháng"));
    }

    public List<Plant> getList() {
        return mPlants;
    }

    public void addPlant(Plant plant){
        mPlants.add(plant);
    }
}
