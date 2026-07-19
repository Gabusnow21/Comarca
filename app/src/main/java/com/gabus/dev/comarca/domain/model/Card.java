package com.gabus.dev.comarca.domain.model;

public class Card {

    private final long id;
    private final String name;
    private final long factionId;
    private final int manaCost;
    private final int attack;
    private final int defense;
    private final String description;
    private final String ability;
    private final int iconRes;

    public Card(long id, String name, long factionId, int manaCost, int attack, int defense,
                String description, String ability, int iconRes) {
        this.id = id;
        this.name = name;
        this.factionId = factionId;
        this.manaCost = manaCost;
        this.attack = attack;
        this.defense = defense;
        this.description = description;
        this.ability = ability;
        this.iconRes = iconRes;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public long getFactionId() { return factionId; }
    public int getManaCost() { return manaCost; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public String getDescription() { return description; }
    public String getAbility() { return ability; }
    public int getIconRes() { return iconRes; }
}
