package com.gabus.dev.comarca.domain.model;

public class Enemy {

    private final String name;
    private int maxHP;
    private int currentHP;
    private final int attackPower;
    private final int defense;
    private final EnemyPattern pattern;

    public enum EnemyPattern {
        AGGRESSIVE,
        DEFENSIVE,
        BALANCED,
        BERSERKER
    }

    public Enemy(String name, int maxHP, int attackPower, int defense, EnemyPattern pattern) {
        this.name = name;
        this.maxHP = maxHP;
        this.currentHP = maxHP;
        this.attackPower = attackPower;
        this.defense = defense;
        this.pattern = pattern;
    }

    public String getName() { return name; }
    public int getMaxHP() { return maxHP; }
    public int getCurrentHP() { return currentHP; }
    public int getAttackPower() { return attackPower; }
    public int getDefense() { return defense; }
    public EnemyPattern getPattern() { return pattern; }

    public void takeDamage(int damage) {
        int actualDamage = Math.max(1, damage - defense);
        currentHP = Math.max(0, currentHP - actualDamage);
    }

    public boolean isAlive() {
        return currentHP > 0;
    }
}
