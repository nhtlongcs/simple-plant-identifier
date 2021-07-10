package com.example.cameraxdemo;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

public class ReminderFragment extends Fragment {

    private RecyclerView rcvReminder;
    private ReminderAdapter reminderAdapter;
    private List<String> addedClasses;
    private List<String> addedPaths;
    private MainActivity mainActivity;
    private DataReminder dataReminder;
    private LinearLayout completeNotification;

    public ReminderFragment() {
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
        dataReminder = new DataReminder();
        return inflater.inflate(R.layout.fragment_reminder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rcvReminder = (RecyclerView) view.findViewById(R.id.rcv_reminder);
        completeNotification = (LinearLayout) view.findViewById(R.id.complete_notification);
        completeNotification.setVisibility(View.GONE);
        reminderAdapter = new ReminderAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvReminder.setLayoutManager(linearLayoutManager);
        reminderAdapter.setData(getListReminder());
        rcvReminder.setAdapter(reminderAdapter);
        reminderAdapter.setDataReminder(completeNotification);
    }

    private List<Reminder> getListReminder(){
        List<Reminder> reminders = dataReminder.getList();
        return reminders;
    }
}