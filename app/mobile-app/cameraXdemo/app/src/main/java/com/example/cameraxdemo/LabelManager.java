package com.example.cameraxdemo;

public class LabelManager {
    static public Plant getPlantByClass(String name) {
        Plant plant = null;
        switch (name) {
            case "cayhoavanglon":
                plant = new Plant("", "Hoa chuông vàng", "Tabebuia aurea", "Nhiều nắng"
                        , "Mỗi 1-2 ngày", "Mỗi 4 tháng");
                break;
            case "cayhoadonho":
                plant = new Plant(""
                        , "Hoa trang", "Ixora coccinea", "Nhiều nắng"
                        , "Mỗi 3-4 ngày", "Mỗi 2 tháng");
                break;
            case "cayxuongrong":
                plant = new Plant(""
                        , "Cây xương rồng", "Euphorbia antiquorum", "Nửa nắng"
                        , "Mỗi 6-10 ngày", "Mỗi 6 tháng");
                break;
            case "cayhoatrang":
                plant = new Plant("", "Hoa brassavola trắng", "Brassavola ", "Nhiều nắng"
                        , "Mỗi 1-2 ngày", "Mỗi 1 tháng");
                break;
            case "cayhoavangnho":
                plant = new Plant("", "Hoa brassavola vàng", "Brassavola", "Nhiều nắng"
                        , "Mỗi ngày", "Mỗi 1 tháng");
                break;
            case "cayhoadolon":
                plant = new Plant("", "Hoa sứ đỏ", "Plumeria", "Nhiều nắng"
                        , "Mỗi ngày", "Mỗi 20 ngày");
                break;
        }
        return plant;
    }

    static public Plant getPlantByName(String name) {
        Plant plant = null;
        switch (name) {
            case "Hoa chuông vàng":
                plant = new Plant("", "Hoa chuông vàng", "Tabebuia aurea", "Nhiều nắng"
                        , "Mỗi 1-2 ngày", "Mỗi 4 tháng");
                break;
            case "Hoa trang":
                plant = new Plant(""
                        , "Hoa trang", "Ixora coccinea", "Nhiều nắng"
                        , "Mỗi 3-4 ngày", "Mỗi 2 tháng");
                break;
            case "Cây xương rồng":
                plant = new Plant(""
                        , "Cây xương rồng", "Euphorbia antiquorum", "Nửa nắng"
                        , "Mỗi 6-10 ngày", "Mỗi 6 tháng");
                break;
            case "Hoa brassavola trắng":
                plant = new Plant("", "Hoa brassavola trắng", "Brassavola ", "Nhiều nắng"
                        , "Mỗi 1-2 ngày", "Mỗi 1 tháng");
                break;
            case "Hoa brassavola vàng":
                plant = new Plant("", "Hoa brassavola vàng", "Brassavola", "Nhiều nắng"
                        , "Mỗi ngày", "Mỗi 1 tháng");
                break;
            case "Hoa sứ đỏ":
                plant = new Plant("", "Hoa sứ đỏ", "Plumeria", "Nhiều nắng"
                        , "Mỗi ngày", "Mỗi 20 ngày");
                break;
        }
        return plant;
    }
}
