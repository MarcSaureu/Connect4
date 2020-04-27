package com.example.connect4.Logic;

public class Board {

    private final int size;
    private final Cell[][] cells;

    Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        generateEmptyBoard();
    }

    private void generateEmptyBoard() {
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                cells[i][j] = new Cell('#');
            }
        }
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    Position occupyCell (int column, Player player) {
        if (canPlayCol(column)){
            int row = firstEmptyRow(column);
            this.cells[row][column] = new Cell(player);
            return new Position(row,column);
        }
        return null;
    }

    boolean hasValidMoves() {
        for(int i=0; i < this.size; i++){
            if (canPlayCol(i)){
                return true;
            }
        }
        return false;
    }

    public boolean canPlayCol(int col) {
        boolean canplay =false;
        int i = 0;
        while( i < this.size) {
            if (this.cells[i][col].isEmpty()){
                canplay = true;
                break;
            }
            i++;
        }
        return col < this.size && col >= 0 && canplay;
    }

    int firstEmptyRow(int column) {
        for (int i = 0 ; i <= this.size && canPlayCol(column); i++) {
            if (this.cells[i][column].isEmpty()){
                return i;
            }
        }
        return -1;
    }

    int maxConnected(Position position) {
        if (!cells[position.getRow()][position.getColumn()].isEmpty()) {
            int max = 0;
            for (int i = 0; i < Direction.ALL.length; i++) {
                Position one = update(position, Direction.ALL[i]);
                Position two = update(position, Direction.ALL[i].invert());
                max = Math.max(max, Position.pathLength(one, two));
            }
            return max;
        }
        return 0;
    }

    private Position update(Position position, Direction direction) {
        while (isValid(position.move(direction)) && player(position).isEqualTo(player(position.move(direction)))){
            position = position.move(direction);
        }
        return position;
    }

    private Cell player(Position position) {
        return this.cells[position.getRow()][position.getColumn()];
    }

    private boolean isValid(Position move) {
        return move.getRow() < size && move.getRow() >= 0 && move.getColumn()< size && move.getColumn()>=0;
    }

}
