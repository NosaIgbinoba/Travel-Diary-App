package com.travel.traveldiary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.travel.traveldiary.Entries.DatabaseHelper;
import com.travel.traveldiary.Entries.EntryModel;

import java.util.concurrent.Executors;

public class TravelEntry_Update extends AppCompatActivity {

    Boolean edi = false;
    Button btnEdit, btnDelete;
    int id;
    MaterialToolbar materialToolbar;
    TextInputEditText location, date, note, activity;
    static DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_entry_update);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        databaseHelper = DatabaseHelper.getDB(this);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);

        activity = findViewById(R.id.activity);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        note = findViewById(R.id.note);

        note.setLines(5);
        note.setGravity(Gravity.START);

        if (getIntent().hasExtra("ID"))
        {
            id = getIntent().getIntExtra("ID",0);
            getTravelEntry(id);
        }

        changeStatus(false);

        btnEdit.setOnClickListener(view -> {
            if (!edi) {
                changeStatus(true);
                btnEdit.setText("Update");
                edi = true;
            } else if (edi) {
                new AlertDialog.Builder(TravelEntry_Update.this)
                        .setTitle("Confirmation")
                        .setMessage("Are you sure you want to Update this ?")
                        .setPositiveButton("Update", (dialogInterface, i) -> {

                            Executors.newSingleThreadExecutor().execute(() -> {
                                databaseHelper.entryDAO().updateEntry(new EntryModel(id, location.getText().toString(), date.getText().toString(), note.getText().toString(), activity.getText().toString()));

                                runOnUiThread(() -> {
                                    Toast.makeText(this, "Entry has been Updated", Toast.LENGTH_SHORT).show();
                                    finish();
                                });
                            });

                            changeStatus(false);
                            edi = false;
                            btnEdit.setText("Edit");
                            finish();
                        })
                        .setNegativeButton("Cancel", (dialogInterface, i) -> {
                        })
                        .show();

                new Handler().postDelayed(() -> {
                    changeStatus(false);
                    edi = false;
                    btnEdit.setText("Edit");
                }, 10000);
            }
        });

        btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(TravelEntry_Update.this)
                    .setTitle("Confirmation")
                    .setMessage("Are you sure you want to Delete this ?")
                    .setPositiveButton("Delete", (dialogInterface, i) -> {

                        Executors.newSingleThreadExecutor().execute(() -> {
                            databaseHelper.entryDAO().deleteEntry(new EntryModel(id, location.getText().toString(), date.getText().toString(), note.getText().toString(), activity.getText().toString()));

                            runOnUiThread(() -> {
                                Toast.makeText(this, "Entry has been deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            });
                        });

                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                        Toast.makeText(this, "Deleting Cancelled", Toast.LENGTH_SHORT).show();
                        changeStatus(false);
                    })
                    .show();

            changeStatus(false);
        });
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

    public void changeStatus(boolean val) {
        location.setEnabled(val);
        date.setEnabled(val);
        activity.setEnabled(val);
        note.setEnabled(val);
    }
}