package com.hippotang;


public class Cell {
    private States state;
    private boolean open;
    private boolean hasMine;
    private int number;

    public int row;
    public int col;

    public Cell() {
        this.state = States.BLANK;
        this.open = false;
        this.hasMine = false;
    }

    public Cell(States state, int row, int col) {
        this.state = state;
        this.open = false;
        this.hasMine = false;
        this.row = row;
        this.col = col;
    }

    public Cell(States state, boolean hasMine, int row, int col) {
        this.state = state;
        this.open = false;
        this.hasMine = hasMine;
        this.row = row;
        this.col = col;
    }

    public States getState() {
        return state;
    }

    public int getNumber() {
        return number;
    }

    public boolean getHasMine() {
        return hasMine;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setState(States state) {
        this.state = state;
    }

    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public void makeOpen() {
        this.open = true;
    }

    public void setNumber(int n) {
        this.number = n;
    }

}
