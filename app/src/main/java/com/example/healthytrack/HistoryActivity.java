package com.example.healthytrack;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import android.widget.Button;


public class HistoryActivity extends AppCompatActivity {

    private AppDatabase db;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recycler_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db = AppDatabase.getInstance(this);
        Button backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(v -> finish());




        loadHistory();

    }

    private void loadHistory() {
        new Thread(() -> {
            List<HealthLog> logs = db.healthLogDao().getAllLogs();
            runOnUiThread(() -> recyclerView.setAdapter(new HistoryAdapter(logs)));
        }).start();
    }
}
