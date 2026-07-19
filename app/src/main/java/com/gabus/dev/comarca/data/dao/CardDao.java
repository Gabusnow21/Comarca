package com.gabus.dev.comarca.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;

import com.gabus.dev.comarca.data.entity.CardEntity;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards ORDER BY id ASC")
    LiveData<List<CardEntity>> getAllCards();

    @Query("SELECT * FROM cards WHERE id = :cardId")
    LiveData<CardEntity> getCardById(long cardId);

    @Query("SELECT * FROM cards WHERE factionId = :factionId ORDER BY id ASC")
    LiveData<List<CardEntity>> getCardsByFaction(long factionId);

    @Query("SELECT * FROM cards WHERE factionId = :factionId ORDER BY id ASC")
    List<CardEntity> getCardsByFactionSync(long factionId);

    @Query("SELECT * FROM cards WHERE id = :cardId LIMIT 1")
    CardEntity getCardByIdSync(long cardId);

    @Query("SELECT * FROM cards ORDER BY RANDOM() LIMIT :count")
    List<CardEntity> getRandomCards(int count);

    @Query("SELECT * FROM cards WHERE factionId = :factionId ORDER BY RANDOM() LIMIT :count")
    List<CardEntity> getRandomCardsByFaction(long factionId, int count);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertCard(CardEntity card);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllCards(List<CardEntity> cards);

    @Update
    void updateCard(CardEntity card);

    @Delete
    void deleteCard(CardEntity card);

    @Query("DELETE FROM cards")
    void deleteAllCards();
}
