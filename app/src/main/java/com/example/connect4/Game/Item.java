package com.example.connect4.Game;

public class Item {

    private String title;
    private String description;
    private String temps;

    public Item() {
        super();
    }

    public Item(String title, String description, String temps) {
        super();
        this.title = title;
        this.description = description;
        this.temps = temps;
    }

    public Item(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTemps() {
        return temps;
    }

}
