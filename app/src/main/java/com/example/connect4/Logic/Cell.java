package com.example.connect4.Logic;

public class Cell {

    private static final char PLAYER1 = '1';
    private static final char PLAYER2 = '2';
    private static final char EMPTY = '#';

    private final char state;

    public Cell(char state) {
        if (state == EMPTY) {
            this.state = EMPTY;
        } else {
            this.state = 'N';
        }
    }

    public Cell(Player player) {
        this.state = player.getId();
    }

    public boolean isEqualTo(Cell other) {
        if(other == null){
            return false;
        }
        return this.state == other.state;
    }

    public boolean isPlayer1() {
        return this.state == PLAYER1;
    }

    public boolean isPlayer2() {
        return  this.state == PLAYER2;
    }

    public boolean isEmpty() {
        return this.state == EMPTY;
    }

}