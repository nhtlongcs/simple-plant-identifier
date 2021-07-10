package com.example.cameraxdemo;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DataReminder {
    private List<Reminder> mReminders;

    public DataReminder() {
        mReminders = new ArrayList<>();
        Plant plant1 = new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210709_110215.jpg"
                , "Cây xương rồng", "Euphorbia antiquorum", "Nửa nắng"
                , "Mỗi 6-10 ngày", "Mỗi 6 tháng");

        Plant plant2 = new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210709_110136.jpg"
                , "Hoa chuông vàng", "Tabebuia aurea", "Nhiều nắng"
                , "Mỗi 1-2 ngày", "Mỗi 4 tháng");

        Plant plant3 = new Plant("/storage/emulated/0/miui/gallery/cloud/owner/thunbnails/IMG_20210709_110021.jpg"
                , "Hoa trang", "Ixora coccinea", "Nhiều nắng"
                , "Mỗi 3-4 ngày", "Mỗi 2 tháng");

        mReminders.add(new Reminder(plant1, 1, "today", "Lần cuối tưới ngày 27/6/2021"));
        mReminders.add(new Reminder(plant2, 2, "today", "Lần cuối bón ngày 10/4/2021"));
        mReminders.add(new Reminder(plant3, 1, "today", "Lần cuối tưới ngày 5/7/2021"));
        mReminders.add(new Reminder(plant2, 1, "today", "Lần cuối tưới ngày 2/7/2021"));
        mReminders.add(new Reminder(plant2, 0, "today", "Lần cuối phơi nắng ngày 10/3/2021"));
    }

    public List<Reminder> getList() {
        return mReminders;
    }

    public void addReminder(Reminder reminder){
        mReminders.add(reminder);
    }

    public void removeReminder(int index){
        if (mReminders != null)
        {
            if(mReminders.size() != 0){
                mReminders.remove(index);
            }
        }
    }
}
