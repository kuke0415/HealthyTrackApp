package com.example.healthytrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    private TextView dateText;
    private Button waterButton, exerciseButton, sleepButton;
    private boolean waterChecked = false, exerciseChecked = false, sleepChecked = false;
    private AppDatabase db;
    private String currentDate;
    private Button historyButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateText = findViewById(R.id.date_text);
        waterButton = findViewById(R.id.btn_water);
        exerciseButton = findViewById(R.id.btn_exercise);
        sleepButton = findViewById(R.id.btn_sleep);

        db = AppDatabase.getInstance(this);

        currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        dateText.setText("今天: " + currentDate);

        loadData();

        waterButton.setOnClickListener(v -> {
            waterChecked = !waterChecked;
            updateButtonStates();
            saveData();
        });

        exerciseButton.setOnClickListener(v -> {
            exerciseChecked = !exerciseChecked;
            updateButtonStates();
            saveData();
        });

        sleepButton.setOnClickListener(v -> {
            sleepChecked = !sleepChecked;
            updateButtonStates();
            saveData();
        });
        historyButton = findViewById(R.id.btn_history);

        historyButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
        Button chartButton = findViewById(R.id.btn_chart);
        chartButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChartActivity.class);
            startActivity(intent);
        });


    }

    private void updateButtonStates() {
        waterButton.setText(waterChecked ? "已喝水" : "喝水");
        exerciseButton.setText(exerciseChecked ? "已锻炼" : "锻炼");
        sleepButton.setText(sleepChecked ? "已早睡" : "早睡");
    }

    private void loadData() {
        new Thread(() -> {
            HealthLog log = db.healthLogDao().getLogByDate(currentDate);
            if (log != null) {
                waterChecked = log.water;
                exerciseChecked = log.exercise;
                sleepChecked = log.sleep;
            }
            runOnUiThread(this::updateButtonStates);
        }).start();
    }

    private void saveData() {
        new Thread(() -> {
            HealthLog log = new HealthLog();
            log.date = currentDate;
            log.water = waterChecked;
            log.exercise = exerciseChecked;
            log.sleep = sleepChecked;
            db.healthLogDao().insertOrUpdate(log);
        }).start();
    }
    private void scheduleDailyReminder() {
        Intent intent = new Intent(this, ReminderReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8); // 设置每天早上8点
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        );
    }

}
