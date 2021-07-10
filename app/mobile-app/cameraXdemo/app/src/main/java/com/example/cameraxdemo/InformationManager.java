package com.example.cameraxdemo;

public class InformationManager {
    public static Information getInformation(String name){
        Information information = null;
        switch (name){
            case "Cây xương rồng":
                int [] images1 = {R.drawable.xuuongrong1, R.drawable.xuongrong2, R.drawable.xuongrong3, R.drawable.xuongrong4};
                information = new Information(R.string.xuongtong_mota, R.string.xuongrong_ynghia, R.string.xuongrong_cachtrong, images1);
                break;
            case "Hoa trang":
                int [] images2 = {R.drawable.trang1, R.drawable.trang2, R.drawable.trang3, R.drawable.trang4};
                information = new Information(R.string.trang_mota, R.string.trang_ynghia, R.string.trang_cachtrong, images2);
                break;
            case "Hoa chuông vàng":
                int [] images3 = {R.drawable.chuongvang1, R.drawable.chuongvang2, R.drawable.chuongvang3, R.drawable.chuongvang4};
                information = new Information(R.string.chuongvang_mota, R.string.chuongvang_ynghia, R.string.chuongvang_cachtrong, images3);
                break;
            case "Hoa brassavola trắng":
                int [] images4 = {R.drawable.lantrang1, R.drawable.lantrang2, R.drawable.lantrang3, R.drawable.lantrang4};
                information = new Information(R.string.hoalan_mota, R.string.hoalan_ynghia, R.string.hoalan_cachtrong, images4);
                break;
            case "Hoa brassavola vàng":
                int [] images5 = {R.drawable.lanvang1, R.drawable.lanvang2, R.drawable.lanvang3, R.drawable.lanvang4};
                information = new Information(R.string.hoalan_mota, R.string.hoalan_ynghia, R.string.hoalan_cachtrong, images5);
                break;
            case "Hoa sứ đỏ":
                int [] images6 = {R.drawable.su1, R.drawable.su2, R.drawable.su3, R.drawable.su4};
                information = new Information(R.string.su_mota, R.string.su_ynghia, R.string.su_cachtrong, images6);
                break;
        }
        return information;
    }
}
