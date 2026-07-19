package com.gabus.dev.comarca.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.gabus.dev.comarca.data.entity.FactionEntity;

import java.util.List;

@Dao
public interface FactionDao {

    @Query("SELECT * FROM factions ORDER BY id ASC")
    LiveData<List<FactionEntity>> getAllFactions();

    @Query("SELECT * FROM factions WHERE id = :factionId")
    LiveData<FactionEntity> getFactionById(long factionId);

    @Query("SELECT * FROM factions WHERE name = :name LIMIT 1")
    FactionEntity getFactionByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertFaction(FactionEntity faction);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllFactions(List<FactionEntity> factions);

    @Update
    void updateFaction(FactionEntity faction);

    @Delete
    void deleteFaction(FactionEntity faction);

    @Query("DELETE FROM factions")
    void deleteAllFactions();
}
