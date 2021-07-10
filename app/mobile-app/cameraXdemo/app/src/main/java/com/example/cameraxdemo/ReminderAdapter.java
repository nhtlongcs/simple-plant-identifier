package com.example.cameraxdemo;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.RemiderViewHolder>{
    private List<Reminder> mReminders;
    private LinearLayout completeNotification;

    public void setData(List<Reminder> reminders){
        this.mReminders = reminders;
        notifyDataSetChanged();
    }

    public void setDataReminder(LinearLayout dataReminder){
        this.completeNotification = dataReminder;
    }

    @NonNull
    @Override
    public RemiderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reminder, parent, false);
        return new RemiderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemiderViewHolder holder, int position) {
        Resources res = holder.itemView.getContext().getResources();
        Reminder reminder = mReminders.get(position);
        if(reminder == null)
            return;

        Bitmap myBitmap = BitmapFactory.decodeFile(reminder.getPlant().getThumbnail_path());
        holder.viewThumbnail.setImageBitmap(myBitmap);
        holder.viewIcon.setImageDrawable(res.getDrawable(reminder.getIcon()));
        holder.viewPlantNam.setText(" "+reminder.getPlant().getPlant_name());
        holder.viewDescription.setText(reminder.getDescription());
        if (holder.checkBox.isChecked())
            holder.checkBox.setChecked(false);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    mReminders.remove(position);
                    notifyDataSetChanged();
                    if(mReminders.size() == 0){
                        completeNotification.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mReminders != null){
            return mReminders.size();
        }
        return 0;
    }

    public class RemiderViewHolder extends RecyclerView.ViewHolder{
        private TextView viewPlantNam;
        private ImageView viewIcon;
        private ImageView viewThumbnail;
        private TextView viewDescription;
        private CheckBox checkBox;
        private CardView reminderCard;

        public RemiderViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPlantNam = (TextView) itemView.findViewById(R.id.plant_name);
            viewIcon = (ImageView) itemView.findViewById(R.id.icon_action);
            viewThumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            viewDescription = (TextView) itemView.findViewById(R.id.description);
            checkBox = (CheckBox) itemView.findViewById(R.id.check);
            reminderCard = (CardView) itemView.findViewById(R.id.reminder_card);
        }
    }
}
