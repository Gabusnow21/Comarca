package com.gabus.dev.comarca.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gabus.dev.comarca.data.dao.CardDao;
import com.gabus.dev.comarca.data.dao.FactionDao;
import com.gabus.dev.comarca.data.db.ComarcaDatabase;
import com.gabus.dev.comarca.data.entity.CardEntity;
import com.gabus.dev.comarca.data.entity.FactionEntity;

import java.util.List;

public class CardRepository {

    private final FactionDao factionDao;
    private final CardDao cardDao;

    public CardRepository(Application application) {
        ComarcaDatabase db = ComarcaDatabase.getDatabase(application);
        factionDao = db.factionDao();
        cardDao = db.cardDao();
    }

    // Faction operations
    public LiveData<List<FactionEntity>> getAllFactions() {
        return factionDao.getAllFactions();
    }

    public LiveData<FactionEntity> getFactionById(long factionId) {
        return factionDao.getFactionById(factionId);
    }

    public FactionEntity getFactionByName(String name) {
        return factionDao.getFactionByName(name);
    }

    // Card operations
    public LiveData<List<CardEntity>> getAllCards() {
        return cardDao.getAllCards();
    }

    public LiveData<CardEntity> getCardById(long cardId) {
        return cardDao.getCardById(cardId);
    }

    public LiveData<List<CardEntity>> getCardsByFaction(long factionId) {
        return cardDao.getCardsByFaction(factionId);
    }

    public List<CardEntity> getCardsByFactionSync(long factionId) {
        return cardDao.getCardsByFactionSync(factionId);
    }

    public CardEntity getCardByIdSync(long cardId) {
        return cardDao.getCardByIdSync(cardId);
    }

    public List<CardEntity> getRandomCards(int count) {
        return cardDao.getRandomCards(count);
    }

    public List<CardEntity> getRandomCardsByFaction(long factionId, int count) {
        return cardDao.getRandomCardsByFaction(factionId, count);
    }

    public void insertCard(CardEntity card) {
        ComarcaDatabase.databaseWriteExecutor.execute(() -> cardDao.insertCard(card));
    }

    public void insertAllCards(List<CardEntity> cards) {
        ComarcaDatabase.databaseWriteExecutor.execute(() -> cardDao.insertAllCards(cards));
    }
}
