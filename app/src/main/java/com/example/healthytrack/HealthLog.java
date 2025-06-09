package com.example.healthytrack;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class HealthLog {

    @PrimaryKey
    @NonNull
    public String date;

    public boolean water;
    public boolean exercise;
    public boolean sleep;

    public HealthLog() {}
}
