package com.gabus.dev.comarca.ui.reward;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabus.dev.comarca.data.entity.CardEntity;
import com.gabus.dev.comarca.domain.usecase.LootUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RewardViewModel extends ViewModel {

    private final LootUseCase lootUseCase;

    private final MutableLiveData<List<CardEntity>> _lootChoices = new MutableLiveData<>();
    public final LiveData<List<CardEntity>> lootChoices = _lootChoices;

    private final MutableLiveData<Integer> _goldEarned = new MutableLiveData<>(0);
    public final LiveData<Integer> goldEarned = _goldEarned;

    private final MutableLiveData<CardEntity> _selectedCard = new MutableLiveData<>();
    public final LiveData<CardEntity> selectedCard = _selectedCard;

    private final MutableLiveData<Boolean> _cardSelectionComplete = new MutableLiveData<>(false);
    public final LiveData<Boolean> cardSelectionComplete = _cardSelectionComplete;

    private long currentRunId;

    @Inject
    public RewardViewModel(LootUseCase lootUseCase) {
        this.lootUseCase = lootUseCase;
    }

    public void setRunId(long runId) {
        this.currentRunId = runId;
    }

    public void generateLoot(int enemyLevel, boolean isBoss) {
        int gold = lootUseCase.calculateGoldReward(enemyLevel, isBoss);
        _goldEarned.setValue(gold);

        List<CardEntity> choices = lootUseCase.generateLootChoices(3);
        _lootChoices.setValue(choices);
    }

    public void selectCard(CardEntity card) {
        _selectedCard.setValue(card);
        _cardSelectionComplete.setValue(true);
    }

    public CardEntity getSelectedCard() {
        return _selectedCard.getValue();
    }
}
