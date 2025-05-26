package com.example.healthytrack;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private final List<HealthLog> historyList;

    public HistoryAdapter(List<HealthLog> historyList) {
        this.historyList = historyList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.item_text);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HealthLog log = historyList.get(position);
        String text = log.date + ": "
                + (log.water ? "喝水✓ " : "喝水× ")
                + (log.exercise ? "锻炼✓ " : "锻炼× ")
                + (log.sleep ? "早睡✓" : "早睡×");
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
}
