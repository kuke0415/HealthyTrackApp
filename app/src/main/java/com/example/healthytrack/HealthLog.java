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

    public HealthLog() {} // 必须有无参构造函数，Room 用它创建对象
}
