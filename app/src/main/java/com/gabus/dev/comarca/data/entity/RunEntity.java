package com.gabus.dev.comarca.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "runs")
public class RunEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public int currentLevel;
    public int maxHP;
    public int currentHP;
    public int gold;
    public boolean isActive;
    public long startDate;
    public long lastSaveDate;

    public RunEntity() {
        this.currentLevel = 1;
        this.maxHP = 80;
        this.currentHP = 80;
        this.gold = 0;
        this.isActive = true;
        this.startDate = System.currentTimeMillis();
        this.lastSaveDate = System.currentTimeMillis();
    }

    public RunEntity(int maxHP) {
        this();
        this.maxHP = maxHP;
        this.currentHP = maxHP;
    }
}
