package com.example.healthytrack;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;


@Dao
public interface HealthLogDao {

    @Query("SELECT * FROM HealthLog WHERE date = :date LIMIT 1")
    HealthLog getLogByDate(String date);

    @Query("SELECT * FROM HealthLog ORDER BY date DESC")
    List<HealthLog> getAllLogs();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrUpdate(HealthLog log);
    @Query("SELECT * FROM HealthLog ORDER BY date DESC LIMIT 7")
    List<HealthLog> getLast7Logs();

}
