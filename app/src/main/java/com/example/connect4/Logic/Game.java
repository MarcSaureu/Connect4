package com.example.connect4.Logic;

import java.io.Serializable;
import java.util.Date;

public class Game implements Serializable {

    private final Board board;
    private final int connectToWin;
    private Status status;
    private boolean hasWinner;
    private Player turn;
    private long startTime;
    private long gameTime;

    public Game(int size, int connectToWin) {
        this.board = new Board(size);
        this.connectToWin = connectToWin;
        this.hasWinner = false;
        this.turn = Player.player1();
        this.status = Status.PLAYER1_PLAYS;

        Date date = new Date();
        this.startTime = date.getTime();

        this.gameTime = 25;

    }

    public Integer playOpponent () {

        int size = this.board.getSize();
        Integer[] pos = new Integer[size];

        if (board.hasValidMoves()){
            for (int j = 0; j < size; j++){
                boolean i = this.board.canPlayCol(j);
                if (i == false){
                    pos[j] = null;
                } else {
                    pos[j] = j;
                }
            }
            int random = (int)(Math.random() * size);
            while (pos[random] == null){
                random = (int)(Math.random() * size);
            }
            return pos[random];
        } else {
            return null;
        }
    }

    public void toggleTurn() {
        if (this.status == Status.PLAYER1_PLAYS){
            this.status = Status.PLAYER2_PLAYS;
            this.turn = Player.player2();
        } else {
            this.status = Status.PLAYER1_PLAYS;
            this.turn = Player.player1();
        }
    }

    public void manageTime() {
        Date actualDate = new Date();
        long actualTime = actualDate.getTime();
        long timePassed = (actualTime - this.startTime)/1000;//Mira el temps que ha passat en segons

        if (timePassed > this.gameTime) {
            this.status = Status.TIMEOVER;
            this.hasWinner = true;
        }
    }

    public boolean checkForFinish () {
        return status != Status.PLAYER1_PLAYS && status != Status.PLAYER2_PLAYS;
    }

    public Position drop(int col){
        boolean playableRow = this.board.canPlayCol(col);

        if (playableRow) {
            Position occupedPos = this.board.occupyCell(col, this.turn);

            if (this.board.maxConnected(occupedPos) >= this.connectToWin) {
                if (this.turn.isPlayer1()) {
                    this.status = Status.PLAYER1_WINS;
                } else if (this.turn.isPlayer2()) {
                    this.status = Status.PLAYER2_WINS;
                }
                this.hasWinner = true;
            } else if (!this.board.hasValidMoves()) {
                this.status = Status.DRAW;
            } else {
                toggleTurn();
            }
            return occupedPos;
        } else {
            return null;
        }
    }


    public long getGameTime() {
        return gameTime;
    }

    public long getStartTime() {
        return startTime;
    }

    public Board getBoard() {
        return board;
    }

    public int getConnectToWin() {
        return connectToWin;
    }

    public Status getStatus() {
        return status;
    }

    public boolean itHasWinner() {
        return hasWinner;
    }

    public Player getTurn() {
        return turn;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setHasWinner(boolean hasWinner) {
        this.hasWinner = hasWinner;
    }

}
