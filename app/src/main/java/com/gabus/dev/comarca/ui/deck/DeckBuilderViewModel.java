package com.gabus.dev.comarca.ui.deck;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gabus.dev.comarca.domain.model.Card;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DeckBuilderViewModel extends ViewModel {

    private static final int MAX_DECK_SIZE = 8;

    private final MutableLiveData<List<Card>> _currentDeck = new MutableLiveData<>();
    public final LiveData<List<Card>> currentDeck = _currentDeck;

    private final MutableLiveData<List<Card>> _availableCards = new MutableLiveData<>();
    public final LiveData<List<Card>> availableCards = _availableCards;

    private final MutableLiveData<Boolean> _deckFull = new MutableLiveData<>(false);
    public final LiveData<Boolean> deckFull = _deckFull;

    private final MutableLiveData<Boolean> _deckSaved = new MutableLiveData<>(false);
    public final LiveData<Boolean> deckSaved = _deckSaved;

    @Inject
    public DeckBuilderViewModel() {
    }

    public void setDeck(List<Card> deck) {
        _currentDeck.setValue(new ArrayList<>(deck));
        updateDeckFullStatus();
    }

    public void setAvailableCards(List<Card> cards) {
        _availableCards.setValue(new ArrayList<>(cards));
    }

    public void addCardToDeck(Card card) {
        List<Card> currentDeck = _currentDeck.getValue();
        if (currentDeck == null || currentDeck.size() >= MAX_DECK_SIZE) {
            return;
        }

        List<Card> updatedDeck = new ArrayList<>(currentDeck);
        updatedDeck.add(card);
        _currentDeck.setValue(updatedDeck);

        List<Card> availableCards = _availableCards.getValue();
        if (availableCards != null) {
            List<Card> updatedAvailable = new ArrayList<>(availableCards);
            updatedAvailable.remove(card);
            _availableCards.setValue(updatedAvailable);
        }

        updateDeckFullStatus();
    }

    public void removeCardFromDeck(Card card) {
        List<Card> currentDeck = _currentDeck.getValue();
        if (currentDeck == null) return;

        List<Card> updatedDeck = new ArrayList<>(currentDeck);
        updatedDeck.remove(card);
        _currentDeck.setValue(updatedDeck);

        List<Card> availableCards = _availableCards.getValue();
        if (availableCards != null) {
            List<Card> updatedAvailable = new ArrayList<>(availableCards);
            updatedAvailable.add(card);
            _availableCards.setValue(updatedAvailable);
        }

        updateDeckFullStatus();
    }

    private void updateDeckFullStatus() {
        List<Card> currentDeck = _currentDeck.getValue();
        _deckFull.setValue(currentDeck != null && currentDeck.size() >= MAX_DECK_SIZE);
    }

    public List<Card> getCurrentDeck() {
        return _currentDeck.getValue();
    }

    public void saveDeck() {
        _deckSaved.setValue(true);
    }
}
