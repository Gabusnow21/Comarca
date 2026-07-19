package com.gabus.dev.comarca.domain.model;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private int maxHP;
    private int currentHP;
    private int maxMana;
    private int currentMana;
    private final List<Card> deck;
    private final List<Card> hand;
    private final List<Card> discardPile;

    public Player(int maxHP, int maxMana) {
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.maxMana = maxMana;
        this.currentMana = maxMana;
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        this.discardPile = new ArrayList<>();
    }

    public int getMaxHP() { return maxHP; }
    public int getCurrentHP() { return currentHP; }
    public int getMaxMana() { return maxMana; }
    public int getCurrentMana() { return currentMana; }
    public List<Card> getDeck() { return deck; }
    public List<Card> getHand() { return hand; }
    public List<Card> getDiscardPile() { return discardPile; }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(0, damage);
        currentHP = Math.max(0, currentHP - actualDamage);
    }

    public void heal(int amount) {
        currentHP = Math.min(maxHP, currentHP + amount);
    }

    public boolean spendMana(int cost) {
        if (currentMana >= cost) {
            currentMana -= cost;
            return true;
        }
        return false;
    }

    public void restoreMana() {
        currentMana = maxMana;
    }

    public boolean isAlive() {
        return currentHP > 0;
    }

    public void setDeck(List<Card> cards) {
        deck.clear();
        deck.addAll(cards);
    }
}
