package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class EvenEditActivity extends AppCompatActivity {

    private EditText edtEventName;
    private TextView eventDateTextView, eventTimeTextView;
    private LocalTime time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_edit);
        iniWidgets();
        time = LocalTime.now();
        eventDateTextView.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        eventTimeTextView.setText("Date: " + CalendarUtils.formattedTime(time));
    }

    private void iniWidgets() {
        edtEventName = (EditText) findViewById(R.id.edtEvenName);
        eventDateTextView = (TextView) findViewById(R.id.eventDateTextView);
        eventTimeTextView = (TextView) findViewById(R.id.eventTimeTextView);
    }

    public void saveEventAction(View view) {
        String eventName = edtEventName.getText().toString();
        Event newEvent = new Event(eventName,CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}