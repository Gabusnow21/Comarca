package com.gabus.dev.comarca.domain.model;

import java.util.List;

public class CombatState {

    public enum Phase {
        PLAYER_TURN,
        ENEMY_TURN,
        VICTORY,
        DEFEAT
    }

    private Phase currentPhase;
    private Player player;
    private Enemy enemy;
    private int turnCount;
    private boolean isCardPlayed;
    private String lastActionMessage;

    public CombatState(Player player, Enemy enemy) {
        this.currentPhase = Phase.PLAYER_TURN;
        this.player = player;
        this.enemy = enemy;
        this.turnCount = 1;
        this.isCardPlayed = false;
        this.lastActionMessage = "";
    }

    public Phase getCurrentPhase() { return currentPhase; }
    public Player getPlayer() { return player; }
    public Enemy getEnemy() { return enemy; }
    public int getTurnCount() { return turnCount; }
    public boolean isCardPlayed() { return isCardPlayed; }
    public String getLastActionMessage() { return lastActionMessage; }

    public void setPhase(Phase phase) { this.currentPhase = phase; }
    public void setTurnCount(int count) { this.turnCount = count; }
    public void setCardPlayed(boolean played) { this.isCardPlayed = played; }
    public void setLastActionMessage(String message) { this.lastActionMessage = message; }

    public boolean isGameOver() {
        return currentPhase == Phase.VICTORY || currentPhase == Phase.DEFEAT;
    }

    public void checkGameOver() {
        if (!enemy.isAlive()) {
            currentPhase = Phase.VICTORY;
        } else if (!player.isAlive()) {
            currentPhase = Phase.DEFEAT;
        }
    }
}
