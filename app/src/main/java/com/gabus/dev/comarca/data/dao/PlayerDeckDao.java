package com.gabus.dev.comarca.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.gabus.dev.comarca.data.entity.PlayerDeckEntity;

import java.util.List;

@Dao
public interface PlayerDeckDao {

    @Query("SELECT * FROM player_deck WHERE runId = :runId ORDER BY deckSlot ASC")
    LiveData<List<PlayerDeckEntity>> getDeckForRun(long runId);

    @Query("SELECT * FROM player_deck WHERE runId = :runId ORDER BY deckSlot ASC")
    List<PlayerDeckEntity> getDeckForRunSync(long runId);

    @Query("SELECT COUNT(*) FROM player_deck WHERE runId = :runId")
    LiveData<Integer> getDeckCountForRun(long runId);

    @Query("SELECT COUNT(*) FROM player_deck WHERE runId = :runId")
    int getDeckCountForRunSync(long runId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertDeckItem(PlayerDeckEntity deckItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllDeckItems(List<PlayerDeckEntity> deckItems);

    @Update
    void updateDeckItem(PlayerDeckEntity deckItem);

    @Delete
    void deleteDeckItem(PlayerDeckEntity deckItem);

    @Query("DELETE FROM player_deck WHERE runId = :runId")
    void clearDeckForRun(long runId);

    @Query("DELETE FROM player_deck")
    void deleteAllDeckItems();
}
