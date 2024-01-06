package com.travel.traveldiary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.travel.traveldiary.Entries.DatabaseHelper;
import com.travel.traveldiary.Entries.EntryModel;
import com.travel.traveldiary.Entries.EntryRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<EntryModel> arrayList;
    EntryRecyclerViewAdapter entryRecyclerViewAdapter;
    ProgressDialog progressDialog;
    MaterialToolbar toolbar;
    SwipeRefreshLayout swipeRefresh;
    static DatabaseHelper databaseHelper;
    private static final int REQUEST_CODE_EDIT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Fetching Entries..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        swipeRefresh = findViewById(R.id.swipeRefresh);
        databaseHelper = DatabaseHelper.getDB(this);

        toolbar = findViewById(R.id.toolBar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.search) {
                startActivity(new Intent(MainActivity.this, TravelEntry_Search.class));
            }
            return true;
        });

        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);

        fetchTravelEntries();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                fetchTravelEntries();
                swipeRefresh.setRefreshing(false);
            }
        });
    }

    private void fetchTravelEntries() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<EntryModel> entryModels = databaseHelper.entryDAO().getAllEntries();

            runOnUiThread(() -> {
                showAll(entryModels);
            });
        });
    }

    public void addTravel(View view) {
        startActivity(new Intent(MainActivity.this, TravelEntry_Add.class));
    }

    private void showAll(List<EntryModel> entries) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        entryRecyclerViewAdapter = new EntryRecyclerViewAdapter(MainActivity.this, entries);
        recyclerView.setAdapter(entryRecyclerViewAdapter);
        progressDialog.dismiss();
    }
}