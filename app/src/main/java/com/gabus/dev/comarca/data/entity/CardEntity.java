package com.gabus.dev.comarca.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "cards",
    foreignKeys = @ForeignKey(
        entity = FactionEntity.class,
        parentColumns = "id",
        childColumns = "factionId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = @Index("factionId")
)
public class CardEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String name;
    public long factionId;
    public int manaCost;
    public int attack;
    public int defense;
    public String description;
    public String ability;
    public int iconRes;

    public CardEntity(String name, long factionId, int manaCost, int attack, int defense,
                      String description, String ability, int iconRes) {
        this.name = name;
        this.factionId = factionId;
        this.manaCost = manaCost;
        this.attack = attack;
        this.defense = defense;
        this.description = description;
        this.ability = ability;
        this.iconRes = iconRes;
    }
}
