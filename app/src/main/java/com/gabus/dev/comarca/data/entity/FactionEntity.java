package com.gabus.dev.comarca.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "factions")
public class FactionEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public String colorHex;
    public String description;
    public int iconRes;

    public FactionEntity(String name, String colorHex, String description, int iconRes) {
        this.name = name;
        this.colorHex = colorHex;
        this.description = description;
        this.iconRes = iconRes;
    }
}
