package com.example.calendar;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import  java.util.Calendar;


public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String DATABASE_NAME = "EVENT_DATABASE";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "EVENT_TABLE";
    private static final String COUNTER = "Counter";


    private static final String ID_FIELD = "id";
    private static final String NAME_FIELD = "name";
    private static final String DATE_FIELD = "date";
    private static final String TIME_FIELD = "time";
    private static final String DELETED = "deleted";


    @SuppressLint("SimpleDateFormat")
    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");

    public SQLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static SQLiteManager instanceOfDataBase(Context context){
        if(sqLiteManager ==null){
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(NAME_FIELD)
                .append(" TEXT, ")
                .append(DATE_FIELD)
                .append(" DATE, ")
                .append(TIME_FIELD)
                .append(" DATETIME) ");
        db.execSQL(sql.toString());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addEventToDatabase(Event event){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD,event.getId());
        contentValues.put(NAME_FIELD,event.getName());
        contentValues.put(DATE_FIELD,event.getDate().toString());
        contentValues.put(TIME_FIELD,event.getTime().toString());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        Toast.makeText(event, "saved", Toast.LENGTH_SHORT).show();
    }
    public void populateNoteListArray(LocalDate date){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //:TODO fix this bug
        try (Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME+ " WHERE " +DATE_FIELD + " = " + date , null)) {

            if(result.getCount()!=0){
                while (result.moveToNext()){
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String eventDate = result.getString(3);
                    String time = result.getString(4);
                    LocalDate eventDateFormatted = convertToLocalDateViaInstant(getDateFromString(eventDate));
                    LocalTime eventTime = convertToLocalTimeViaInstant(getDateFromString(time));
                    Event event = new Event(id,name,eventDateFormatted,eventTime);
                    Event.eventsList.add(event);
                }
            }
        }

    }

    public void updateEventInDB(Event event){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD,event.getId());
        contentValues.put(NAME_FIELD,event.getName());
        contentValues.put(DATE_FIELD,event.getDate().toString());
        contentValues.put(TIME_FIELD,event.getTime().toString());

        sqLiteDatabase.update(TABLE_NAME,contentValues, ID_FIELD + " =? ",new String[]{String.valueOf(event.getId())});
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }
    public LocalTime convertToLocalTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalTime();
    }

    private String getStringFromDate(Date date){
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string){
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }

}
