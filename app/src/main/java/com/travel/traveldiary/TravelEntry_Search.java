package com.travel.traveldiary;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.travel.traveldiary.Entries.DatabaseHelper;
import com.travel.traveldiary.Entries.EntryModel;
import com.travel.traveldiary.Entries.EntryRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class TravelEntry_Search extends AppCompatActivity {

    RecyclerView recyclerView;
    EntryRecyclerViewAdapter recyclerViewAdapter;
    MaterialToolbar materialToolbar;
    List<EntryModel> arrayList;
    TextInputEditText travelentry;

    static DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_entry_search);

        materialToolbar = findViewById(R.id.toolBar);
        materialToolbar.setNavigationOnClickListener(view -> {
            finish();
        });

        databaseHelper = DatabaseHelper.getDB(this);

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        travelentry = findViewById(R.id.travelentry);

        fetchTravelEntries();

        travelentry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String newText = travelentry.getText().toString().toLowerCase();
                List<EntryModel> filteredStudents = new ArrayList<>();
                arrayList = databaseHelper.entryDAO().getEntriesByLocation(newText);
                for (EntryModel entry : arrayList) {
                    if (entry.getLocation().toLowerCase().contains(newText)) {
                        filteredStudents.add(entry);
                    }
                }
                showAll(filteredStudents);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (travelentry.getText().toString().isEmpty()){
                    fetchTravelEntries();
                }
            }
        });

    }

    private void fetchTravelEntries() {
        arrayList.clear();
        Executors.newSingleThreadExecutor().execute(() -> {
            List<EntryModel> entryModels = databaseHelper.entryDAO().getAllEntries();

            runOnUiThread(() -> {
                showAll(entryModels);
            });
        });
    }

    public void showAll(List<EntryModel> arrayList) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TravelEntry_Search.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerViewAdapter = new EntryRecyclerViewAdapter(TravelEntry_Search.this, arrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }


}