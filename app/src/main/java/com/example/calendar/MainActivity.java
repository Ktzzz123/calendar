package com.example.calendar;

import static com.example.calendar.CalendarUtils.daysInMonthArray;
import static com.example.calendar.CalendarUtils.monthYearFromDate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{
    private TextView monthYearText;
    private RecyclerView calendarRecycleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void previousMonthAction(View view) {
        CalendarUtils.selectedDate =  CalendarUtils.selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        CalendarUtils.selectedDate =  CalendarUtils.selectedDate.plusMonths(1);
        setMonthView();
    }
    // find it id
    private void iniWidgets() {
        calendarRecycleView = findViewById(R.id.calendarRecycleView);
        monthYearText = findViewById(R.id.monthYearTextView);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate( CalendarUtils.selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray( CalendarUtils.selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
         calendarRecycleView.setLayoutManager(layoutManager);
         calendarRecycleView.setAdapter(calendarAdapter);
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            String message = "selected Date "+ dayText + " "+ monthYearFromDate(CalendarUtils.selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
        else{

            Toast.makeText(this, dayText.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    public void weeklyAction(View view) {

        startActivity(new Intent(this, WeekViewActivity.class));
    }
}