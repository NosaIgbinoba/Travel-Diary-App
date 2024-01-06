package com.travel.traveldiary;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.travel.traveldiary.Entries.DatabaseHelper;
import com.travel.traveldiary.Entries.EntryModel;

import java.util.Calendar;
import java.util.concurrent.Executors;

public class TravelEntry_Add extends AppCompatActivity {

    MaterialToolbar materialToolbar;
    TextInputEditText location, date, activity, note;
    Button addBtn;
    private int mYear, mMonth, mDay;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_entry_add);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Adding Entry..");
        progressDialog.setCancelable(false);

        activity = findViewById(R.id.activity);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);

        addBtn = findViewById(R.id.addBtn);

        DatabaseHelper databaseHelper = DatabaseHelper.getDB(this);


        note = findViewById(R.id.note);
        note.setLines(5);
        note.setGravity(Gravity.START);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(TravelEntry_Add.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthofYear, int dayOfMonth) {
                        date.setText(dayOfMonth + "-" + (monthofYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String loc = location.getText().toString();
                String dat = date.getText().toString();
                String act = activity.getText().toString();
                String not = note.getText().toString();
                if (TextUtils.isEmpty(loc)) {
                    location.setError("Location can not be empty !");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(dat)) {
                    date.setError("Date can not be empty !");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(act)) {
                    activity.setError("Activity can not be empty !");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(not)) {
                    note.setError("Note can not be empty !");
                    progressDialog.dismiss();
                } else {

                    Executors.newSingleThreadExecutor().execute(() -> {
                        databaseHelper.entryDAO().addEntry(new EntryModel(loc, dat, not, act));

                        runOnUiThread(() -> {
                            Toast.makeText(TravelEntry_Add.this, "This Entry has been added", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        });
                    });
                }
            }
        });
    }


}