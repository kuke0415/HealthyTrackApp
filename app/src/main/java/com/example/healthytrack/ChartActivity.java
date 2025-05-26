package com.example.healthytrack;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Color;

public class ChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        barChart = findViewById(R.id.bar_chart);
        db = AppDatabase.getInstance(this);

        loadChartData();
    }

    private void loadChartData() {
        new Thread(() -> {
            List<HealthLog> logs = db.healthLogDao().getLast7Logs();
            List<BarEntry> entries = new ArrayList<>();
            List<String> labels = new ArrayList<>();

            for (int i = 0; i < logs.size(); i++) {
                HealthLog log = logs.get(i);
                int score = 0;
                if (log.water) score++;
                if (log.exercise) score++;
                if (log.sleep) score++;
                entries.add(new BarEntry(i, score));
                labels.add(log.date.substring(5)); // 只显示 MM-DD
            }

            runOnUiThread(() -> {
                BarDataSet dataSet = new BarDataSet(entries, "健康打卡得分 (最高3分)");
                dataSet.setColor(Color.BLUE);  // 导入 android.graphics.Color

                BarData barData = new BarData(dataSet);
                barChart.setData(barData);

                XAxis xAxis = barChart.getXAxis();
                xAxis.setGranularity(1f);
                xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

                barChart.getDescription().setEnabled(false);
                barChart.animateY(1000);
                barChart.invalidate();
            });
        }).start();
    }
}
