package com.example.westoncampbellinventoryapp;

public class InventoryItem {
    private long itemId;
    private String itemName;
    private String itemDescription;

    public InventoryItem() {}

    public InventoryItem(long id, String name, String description) {
        itemId = id;
        itemName = name;
        itemDescription = description;
    }

    public long getId() {
        return itemId;
    }

    public void setId(long id) {
        this.itemId = id;
    }

    public String getName() {
        return itemName;
    }

    public void setName(String name) {
        this.itemName = name;
    }

    public String getDescription() {
        return itemDescription;
    }

    public void setDescription(String description) {
        this.itemDescription = description;
    }
}
