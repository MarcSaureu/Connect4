package com.example.connect4.Logic;

public class Player {

    private static final char PLAYER1 = '1';
    private static final char PLAYER2 = '2';

    private final char id;

    private Player(char id) {
        this.id = id;
    }

    public static Player player1() {
        Player Player1 = new Player(PLAYER1);
        return Player1;
    }

    public static Player player2() {
        Player Player2 = new Player(PLAYER2);
        return Player2;
    }

    public boolean isPlayer1() {
        return this.id == PLAYER1;
    }

    public boolean isPlayer2() {
        return  this.id == PLAYER2;
    }

    public char getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Player "
                + id
                ;
    }
}