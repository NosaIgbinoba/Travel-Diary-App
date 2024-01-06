package com.travel.traveldiary.Entries;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.travel.traveldiary.R;
import com.travel.traveldiary.TravelEntry_Detail;

import java.util.List;

public class EntryRecyclerViewAdapter extends RecyclerView.Adapter<EntryRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<EntryModel> arrayList;

    public EntryRecyclerViewAdapter(Context context, List<EntryModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.entry_data, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.activity.setText(arrayList.get(position).getActivity());
        holder.date.setText(arrayList.get(position).getDate());
        holder.location.setText(arrayList.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView location, date, activity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            location = itemView.findViewById(R.id.location);
            date=itemView.findViewById(R.id.date);
            activity=itemView.findViewById(R.id.activity);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, TravelEntry_Detail.class);
                intent.putExtra("ID", arrayList.get(getAdapterPosition()).getId());
                intent.putExtra("LOC", location.getText().toString());
                context.startActivity(intent);
            });
        }
    }
}
