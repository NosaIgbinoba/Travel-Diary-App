package com.travel.traveldiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.travel.traveldiary.Entries.DatabaseHelper;
import com.travel.traveldiary.Entries.EntryModel;

import java.util.concurrent.Executors;

public class TravelEntry_Detail extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    int id;
    TextInputEditText location, date, note, activity;
    TextView data;
    static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_entry_detail);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        databaseHelper = DatabaseHelper.getDB(this);

        data = findViewById(R.id.data);
        activity = findViewById(R.id.activity);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        note = findViewById(R.id.note);

        note.setLines(5);
        note.setGravity(Gravity.START);

        changeStatus(false);

        if (getIntent().hasExtra("ID")
                && getIntent().hasExtra("LOC")
        ) {
            id = getIntent().getIntExtra("ID",0);
            data.setText((data.getText().toString()+getIntent().getStringExtra("LOC")));
        }

        if (id != 0){
            getTravelEntry(id);
        }

    }
    private void getTravelEntry(int id) {
        Executors.newSingleThreadExecutor().execute(() -> {
            EntryModel entry = databaseHelper.entryDAO().getEntry(id);

            runOnUiThread(() -> {
                note.setText(entry.getNote());
                activity.setText(entry.getActivity());
                date.setText(entry.getDate());
                location.setText(entry.getLocation());
            });
        });
    }

    public void editUpdate(View view) {
        Intent intent = new Intent(TravelEntry_Detail.this, TravelEntry_Update.class);
        intent.putExtra("ID", id);
        startActivity(intent);
        finish();
    }
    public void changeStatus(boolean val) {
        location.setEnabled(val);
        date.setEnabled(val);
        activity.setEnabled(val);
        note.setEnabled(val);
    }
}