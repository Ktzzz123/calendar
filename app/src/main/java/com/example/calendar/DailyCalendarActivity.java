package com.example.calendar;

import static com.example.calendar.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DailyCalendarActivity extends AppCompatActivity {

    private TextView monthDayTextView;
    private TextView dayOfWeekTextView;
    private ListView hourListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_calendar);
        initWidgets();
        loadFromDBtoMemory();
    }


    private void initWidgets() {
        monthDayTextView = findViewById(R.id.monthDayTextView);
        dayOfWeekTextView = findViewById(R.id.dayOfWeekTextView);
        hourListView = findViewById(R.id.hourListView);
    }
    private void loadFromDBtoMemory() {
        SQLiteManager sqLiteManager =  SQLiteManager.instanceOfDataBase(this);
        sqLiteManager.populateNoteListArray(selectedDate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setDayView();
        
    }

    private void setDayView() {
        monthDayTextView.setText(CalendarUtils.monthDayFromDate(selectedDate));
        String dayOfWeek = selectedDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault());
        dayOfWeekTextView.setText(dayOfWeek);
        setHourAdapter();
    }

    private void setHourAdapter() {
        HourAdapter hourAdapter = new HourAdapter(getApplicationContext(),hourEventList());
        hourListView.setAdapter(hourAdapter);
    }

    private ArrayList<HourEvent> hourEventList() {
        ArrayList<HourEvent> list = new ArrayList<>();
        for(int hour =0; hour <24;hour ++){
            LocalTime time = LocalTime.of(hour,0);
            ArrayList<Event>events = Event.eventsForDateAndTime(selectedDate,time);
            HourEvent hourEvent = new HourEvent(time, events);
            list.add(hourEvent);
        }
        return list;
    }

    public void previousDayAction(View view) {
        CalendarUtils.selectedDate =  CalendarUtils.selectedDate.minusDays(1);
        setDayView();
    }

    public void nextDayAction(View view) {
        CalendarUtils.selectedDate =  CalendarUtils.selectedDate.plusDays(1);
        setDayView();
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, DailyCalendarActivity.class));

    }
}