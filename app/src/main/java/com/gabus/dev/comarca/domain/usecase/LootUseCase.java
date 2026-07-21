package com.gabus.dev.comarca.domain.usecase;

import com.gabus.dev.comarca.data.entity.CardEntity;
import com.gabus.dev.comarca.data.repository.CardRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class LootUseCase {

    private final CardRepository cardRepository;

    @Inject
    public LootUseCase(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<CardEntity> generateLootChoices(int count) {
        return cardRepository.getRandomCards(count);
    }

    public List<CardEntity> generateLootChoicesByFaction(long factionId, int count) {
        return cardRepository.getRandomCardsByFaction(factionId, count);
    }

    public int calculateGoldReward(int enemyLevel, boolean isBoss) {
        int baseGold = 10 + (enemyLevel * 5);
        if (isBoss) {
            baseGold *= 3;
        }
        return baseGold;
    }
}
