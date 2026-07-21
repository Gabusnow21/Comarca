package com.gabus.dev.comarca.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.gabus.dev.comarca.data.entity.CardEntity;
import com.gabus.dev.comarca.data.entity.PlayerDeckEntity;
import com.gabus.dev.comarca.data.entity.RunEntity;
import com.gabus.dev.comarca.domain.model.Card;
import com.gabus.dev.comarca.domain.model.Player;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RunManager {

    private final RunRepository runRepository;
    private final DeckRepository deckRepository;
    private final CardRepository cardRepository;

    private RunEntity currentRun;
    private List<Card> currentDeck;

    @Inject
    public RunManager(RunRepository runRepository, DeckRepository deckRepository,
                      CardRepository cardRepository) {
        this.runRepository = runRepository;
        this.deckRepository = deckRepository;
        this.cardRepository = cardRepository;
    }

    public void startNewRun(int maxHP) {
        long runId = runRepository.createNewRun(maxHP);
        currentRun = runRepository.getActiveRunSync();
        currentDeck = new ArrayList<>();
    }

    public void saveRun(Player player, int currentLevel, int gold) {
        if (currentRun == null) {
            currentRun = runRepository.getActiveRunSync();
        }

        if (currentRun != null) {
            currentRun.currentHP = player.getCurrentHP();
            currentRun.maxHP = player.getMaxHP();
            currentRun.currentLevel = currentLevel;
            currentRun.gold = gold;
            currentRun.lastSaveDate = System.currentTimeMillis();
            runRepository.updateRun(currentRun);
        }
    }

    public void saveDeck(List<Card> deck, long runId) {
        if (currentRun == null) return;

        deckRepository.clearDeckForRun(currentRun.id);

        List<PlayerDeckEntity> deckItems = new ArrayList<>();
        for (int i = 0; i < deck.size(); i++) {
            Card card = deck.get(i);
            PlayerDeckEntity entity = new PlayerDeckEntity(card.getId(), currentRun.id, i);
            deckItems.add(entity);
        }

        deckRepository.addMultipleCardsToDeck(deckItems);
    }

    public LiveData<RunEntity> getActiveRun() {
        return runRepository.getActiveRun();
    }

    public RunEntity getActiveRunSync() {
        return runRepository.getActiveRunSync();
    }

    public void endCurrentRun() {
        if (currentRun != null) {
            runRepository.endRun(currentRun.id);
            currentRun = null;
            currentDeck = null;
        }
    }

    public boolean hasActiveRun() {
        RunEntity activeRun = runRepository.getActiveRunSync();
        return activeRun != null && activeRun.isActive;
    }

    public List<Card> getCurrentDeck() {
        return currentDeck;
    }

    public void setCurrentDeck(List<Card> deck) {
        this.currentDeck = new ArrayList<>(deck);
    }

    public RunEntity getCurrentRun() {
        return currentRun;
    }
}
