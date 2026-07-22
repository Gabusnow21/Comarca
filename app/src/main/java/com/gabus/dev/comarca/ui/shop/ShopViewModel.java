package com.gabus.dev.comarca.ui.shop;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabus.dev.comarca.domain.model.Card;
import com.gabus.dev.comarca.domain.usecase.ShopUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ShopViewModel extends ViewModel {

    private final ShopUseCase shopUseCase;

    private final MutableLiveData<List<Card>> _deckCards = new MutableLiveData<>();
    public final LiveData<List<Card>> deckCards = _deckCards;

    private final MutableLiveData<Integer> _gold = new MutableLiveData<>(0);
    public final LiveData<Integer> gold = _gold;

    private final MutableLiveData<Boolean> _upgradeComplete = new MutableLiveData<>(false);
    public final LiveData<Boolean> upgradeComplete = _upgradeComplete;

    @Inject
    public ShopViewModel(ShopUseCase shopUseCase) {
        this.shopUseCase = shopUseCase;
    }

    public void setDeck(List<Card> deck, int gold) {
        _deckCards.setValue(new ArrayList<>(deck));
        _gold.setValue(gold);
    }

    public boolean upgradeAttack(Card card) {
        int cost = shopUseCase.getUpgradeAttackCost(card.getAttack());
        if (!shopUseCase.canAfford(_gold.getValue(), cost)) {
            return false;
        }

        Card upgradedCard = shopUseCase.upgradeAttack(card);
        updateCardInDeck(card, upgradedCard);
        _gold.setValue(_gold.getValue() - cost);
        return true;
    }

    public boolean upgradeDefense(Card card) {
        int cost = shopUseCase.getUpgradeDefenseCost(card.getDefense());
        if (!shopUseCase.canAfford(_gold.getValue(), cost)) {
            return false;
        }

        Card upgradedCard = shopUseCase.upgradeDefense(card);
        updateCardInDeck(card, upgradedCard);
        _gold.setValue(_gold.getValue() - cost);
        return true;
    }

    public boolean reduceMana(Card card) {
        int cost = shopUseCase.getReduceManaCost(card.getManaCost());
        if (cost == Integer.MAX_VALUE || !shopUseCase.canAfford(_gold.getValue(), cost)) {
            return false;
        }

        Card upgradedCard = shopUseCase.reduceManaCost(card);
        updateCardInDeck(card, upgradedCard);
        _gold.setValue(_gold.getValue() - cost);
        return true;
    }

    private void updateCardInDeck(Card oldCard, Card newCard) {
        List<Card> currentDeck = _deckCards.getValue();
        if (currentDeck == null) return;

        List<Card> updatedDeck = new ArrayList<>(currentDeck);
        int index = -1;
        for (int i = 0; i < updatedDeck.size(); i++) {
            if (updatedDeck.get(i).getId() == oldCard.getId()) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            updatedDeck.set(index, newCard);
            _deckCards.setValue(updatedDeck);
        }
    }

    public void finishShopping() {
        _upgradeComplete.setValue(true);
    }
}
