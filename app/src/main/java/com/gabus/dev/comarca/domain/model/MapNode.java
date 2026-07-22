package com.gabus.dev.comarca.domain.model;

public class MapNode {

    public enum NodeType {
        COMBAT,
        TREASURE,
        REST,
        BOSS,
        START
    }

    private final int id;
    private final NodeType type;
    private final String name;
    private boolean completed;
    private boolean available;
    private final int positionX;
    private final int positionY;

    public MapNode(int id, NodeType type, String name, boolean completed, boolean available,
                   int positionX, int positionY) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.completed = completed;
        this.available = available;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getId() { return id; }
    public NodeType getType() { return type; }
    public String getName() { return name; }
    public boolean isCompleted() { return completed; }
    public boolean isAvailable() { return available; }
    public int getPositionX() { return positionX; }
    public int getPositionY() { return positionY; }

    public void setCompleted(boolean completed) { this.completed = completed; }
    public void setAvailable(boolean available) { this.available = available; }
}
