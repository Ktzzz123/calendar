package com.example.calendar;

import static com.example.calendar.CalendarUtils.monthYearFromDate;
import static com.example.calendar.CalendarUtils.selectedDate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements  CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecycleView;
    private ListView eventListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        initWidgets();
        loadFromDBtoMemory(selectedDate);
        setWeekView();

    }


    // find it id
    private void initWidgets() {
        calendarRecycleView = findViewById(R.id.calendarRecycleView);
        monthYearText = findViewById(R.id.monthYearTextView);
        eventListView = findViewById(R.id.eventListView);
    }

    private void loadFromDBtoMemory(LocalDate date) {
        SQLiteManager sqLiteManager =  SQLiteManager.instanceOfDataBase(this);
        sqLiteManager.populateNoteListArray(date);
    }

    public void setWeekView() {
        monthYearText.setText(monthYearFromDate( CalendarUtils.selectedDate));
        //TODO: ehh>?
        ArrayList<LocalDate> days = CalendarUtils.daysInWeekArray( CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecycleView.setLayoutManager(layoutManager);
        calendarRecycleView.setAdapter(calendarAdapter);
        setEventAdapter();

    }
    private void setEventAdapter() {
        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(),Event.eventsForDate(selectedDate));
        eventListView.setAdapter(eventAdapter);

    }


    @Override
    public void onItemClick(int position, LocalDate date) {

            CalendarUtils.selectedDate = date;
            setWeekView();

    }
    public void previousWeekAction(View view) {
        CalendarUtils.selectedDate =  CalendarUtils.selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        CalendarUtils.selectedDate =  CalendarUtils.selectedDate.plusWeeks(1);
        setWeekView();
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, EvenEditActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }


    public void dailyAction(View view)
    {
        startActivity(new Intent(this, DailyCalendarActivity.class));
    }


}