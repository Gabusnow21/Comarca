package com.gabus.dev.comarca.data.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "player_deck",
    foreignKeys = {
        @ForeignKey(
            entity = CardEntity.class,
            parentColumns = "id",
            childColumns = "cardId",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = RunEntity.class,
            parentColumns = "id",
            childColumns = "runId",
            onDelete = ForeignKey.CASCADE
        )
    },
    indices = {
        @Index("cardId"),
        @Index("runId")
    }
)
public class PlayerDeckEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public long cardId;
    public long runId;
    public int deckSlot;

    public PlayerDeckEntity(long cardId, long runId, int deckSlot) {
        this.cardId = cardId;
        this.runId = runId;
        this.deckSlot = deckSlot;
    }
}
