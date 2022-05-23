package com.example.calendar;

import static com.example.calendar.CalendarUtils.daysInMonthArray;
import static com.example.calendar.CalendarUtils.monthYearFromDate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements  CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        iniWidgets();

        setWeekView();
    }
    // find it id
    private void iniWidgets() {
        calendarRecycleView = findViewById(R.id.calendarRecycleView);
        monthYearText = findViewById(R.id.monthYearTextView);
    }

    private void setWeekView() {
        monthYearText.setText(monthYearFromDate( CalendarUtils.selectedDate));
        ArrayList<LocalDate> days = CalendarUtils.daysInWeekArray( CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecycleView.setLayoutManager(layoutManager);
        calendarRecycleView.setAdapter(calendarAdapter);
    }



    @Override
    public void onItemClick(int position, String dayText) {

            String message = "selected Date "+ dayText + " "+ monthYearFromDate(CalendarUtils.selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();

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
    }

}