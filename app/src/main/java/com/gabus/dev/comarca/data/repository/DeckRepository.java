package com.gabus.dev.comarca.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gabus.dev.comarca.data.dao.PlayerDeckDao;
import com.gabus.dev.comarca.data.db.ComarcaDatabase;
import com.gabus.dev.comarca.data.entity.PlayerDeckEntity;

import java.util.List;

public class DeckRepository {

    private final PlayerDeckDao playerDeckDao;

    public DeckRepository(Application application) {
        ComarcaDatabase db = ComarcaDatabase.getDatabase(application);
        playerDeckDao = db.playerDeckDao();
    }

    public LiveData<List<PlayerDeckEntity>> getDeckForRun(long runId) {
        return playerDeckDao.getDeckForRun(runId);
    }

    public List<PlayerDeckEntity> getDeckForRunSync(long runId) {
        return playerDeckDao.getDeckForRunSync(runId);
    }

    public LiveData<Integer> getDeckCountForRun(long runId) {
        return playerDeckDao.getDeckCountForRun(runId);
    }

    public int getDeckCountForRunSync(long runId) {
        return playerDeckDao.getDeckCountForRunSync(runId);
    }

    public void addCardToDeck(PlayerDeckEntity deckItem) {
        ComarcaDatabase.databaseWriteExecutor.execute(() ->
            playerDeckDao.insertDeckItem(deckItem)
        );
    }

    public void addMultipleCardsToDeck(List<PlayerDeckEntity> deckItems) {
        ComarcaDatabase.databaseWriteExecutor.execute(() ->
            playerDeckDao.insertAllDeckItems(deckItems)
        );
    }

    public void removeCardFromDeck(PlayerDeckEntity deckItem) {
        ComarcaDatabase.databaseWriteExecutor.execute(() ->
            playerDeckDao.deleteDeckItem(deckItem)
        );
    }

    public void clearDeckForRun(long runId) {
        ComarcaDatabase.databaseWriteExecutor.execute(() ->
            playerDeckDao.clearDeckForRun(runId)
        );
    }

    public void updateDeckItem(PlayerDeckEntity deckItem) {
        ComarcaDatabase.databaseWriteExecutor.execute(() ->
            playerDeckDao.updateDeckItem(deckItem)
        );
    }
}
