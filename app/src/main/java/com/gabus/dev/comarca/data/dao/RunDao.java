package com.gabus.dev.comarca.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.gabus.dev.comarca.data.entity.RunEntity;

import java.util.List;

@Dao
public interface RunDao {

    @Query("SELECT * FROM runs WHERE isActive = 1 LIMIT 1")
    LiveData<RunEntity> getActiveRun();

    @Query("SELECT * FROM runs WHERE isActive = 1 LIMIT 1")
    RunEntity getActiveRunSync();

    @Query("SELECT * FROM runs ORDER BY id DESC LIMIT :limit")
    LiveData<List<RunEntity>> getRecentRuns(int limit);

    @Query("SELECT * FROM runs WHERE id = :runId")
    LiveData<RunEntity> getRunById(long runId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertRun(RunEntity run);

    @Update
    void updateRun(RunEntity run);

    @Delete
    void deleteRun(RunEntity run);

    @Query("UPDATE runs SET isActive = 0 WHERE id = :runId")
    void endRun(long runId);

    @Query("UPDATE runs SET isActive = 0")
    void endAllActiveRuns();
}
