package com.travel.traveldiary.Entries;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Entry")
public class EntryModel {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "location")
    private String location;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "activity")
    private String activity;

    @Ignore
    public EntryModel() {
    }

    public EntryModel(int id, String location, String date, String note, String activity) {
        this.id = id;
        this.location = location;
        this.date = date;
        this.note = note;
        this.activity = activity;
    }

    @Ignore
    public EntryModel(String location, String date, String note, String activity) {
        this.location = location;
        this.date = date;
        this.note = note;
        this.activity = activity;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
