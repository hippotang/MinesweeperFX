package com.hippotang;

import java.util.Random;
import java.util.ArrayList;

public class Board {
    private Cell[][] board;
    private int numMines;
    public boolean gameOver;
    public int rows;
    public int cols;

    public Board(Level level) {
        switch (level) {
            case BEGINNER:
                initBoard(8,8, 10);
                rows = 8;
                cols = 8;
                this.numMines = 10;
                break;
            case INTERMEDIATE:
                initBoard(16,16, 40);
                this.numMines = 40;
                rows = 16;
                cols = 16;
                break;
            case EXPERT:
                initBoard(16,32, 99);
                this.numMines = 99;
                rows = 16;
                cols = 32;
                break;
            default:
                initBoard(8,8, 10);
                this.numMines = 10;
                rows = 8;
                cols = 8;
                break;
        }
        this.gameOver = false;
    }

    private void initBoard(int rows, int cols, int numMines) {
        board = new Cell[rows][cols];
        int[] mineLocs = getRandomLocations(rows*cols);
        // first sets all cells to "blank" and with hasMine = false
        for (int i = 0; i<rows; i++) {
            for (int j = 0; j<cols; j++) {
                board[i][j] = new Cell(States.BLANK, i, j);
            }
        }

        for (int i = 0; i<numMines; i++) {
            board[mineLocs[i]/cols][mineLocs[i]%cols].setHasMine(true);
        }

        for (int i = 0; i<rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j].setNumber(numMinesInSurroundingSquares(board[i][j]));
            }
        }
    }

    public void openingMove(Cell c) {
        boolean bombIsMoved = false;
        // if mine is clicked, move the mine to the top left corner
        // if the top left corner is occupied, move it to the cell on the left
        if (c.getHasMine()) {
            for (int i = 0; i<board.length; i++) {
                for (int j = 0; j<board[0].length; j++) {
                    if (!board[i][j].getHasMine() && !bombIsMoved) {
                        board[i][j].setHasMine(true);
                        c.setHasMine(false);
                        bombIsMoved = true;
                        System.out.println("bomb is moved to " + "(" + i + ", " + j + ")");
                        break;
                    }
                    c.setNumber(numMinesInSurroundingSquares(c));
                }
            }
        }

        for (int i = 0; i<rows; i++) {
            for (int j = 0; j<cols; j++) {
                board[i][j].setNumber(numMinesInSurroundingSquares(board[i][j]));
            }
        }

        if (c.getNumber() == 0) {
            this.openAnyResultingSquares(c);
        }

    }

    public void openAnyResultingSquares(Cell c) {
        int row = c.row, col = c.col;
        int[] xPositions = {row - 1, row -1, row - 1, row, row, row + 1, row + 1, row + 1};
        int[] yPositions = {col-1, col, col+1, col-1, col+1, col-1, col, col+1};
        ArrayList<Cell> cellsToOpen = new ArrayList<Cell>();

        for (int i = 0; i<8; i++) {
            if (checkIndex2(xPositions[i], yPositions[i])) {
                if (board[xPositions[i]][yPositions[i]].getNumber() == 0
                        && !board[xPositions[i]][yPositions[i]].isOpen()){
                    cellsToOpen.add(board[xPositions[i]][yPositions[i]]);
                }
                if (board[xPositions[i]][yPositions[i]].getState() != States.FLAGGED) {
                    board[xPositions[i]][yPositions[i]].makeOpen();
                }
                System.out.println(board[xPositions[i]][yPositions[i]].isOpen());
            }
        }

        for (Cell c2: cellsToOpen) {
           System.out.println(c2.row + ", " + c2.col);
           c2.makeOpen();
           openAnyResultingSquares(c2);
        }
    }

    public int getNumRemainingMines() {
        int count = 0;
        for (Cell[] a: board) {
            for (Cell c: a) {
                if (c.getHasMine()) {
                    count++;
                }
            }
        }
        return count;
    }

    public Cell getCell(int x, int y) {
        return board[x][y];
    }

    private static int[] getRandomLocations(int nums) {
        int a, b, temp;
        Random rnd = new Random();

        // init
        int[] randomLocs = new int[nums];
        for (int i = 0; i<nums; i++) {
            randomLocs[i] = i;
        }

        // shuffle
        for (int i = 0; i<nums; i++) {
            a = rnd.nextInt(nums);
            b = rnd.nextInt(nums);
            temp = randomLocs[a];
            randomLocs[a] = randomLocs[b];
            randomLocs[b] = temp;
        }

        return randomLocs;
    }

    private int numMinesInSurroundingSquares(Cell c) {
        int count = 0;
        int row = c.row, col = c.col;
        return checkIndex(row-1, col-1) + checkIndex(row-1, col) + checkIndex(row-1, col+1) +
                checkIndex(row, col-1) + checkIndex(row, col+1) +
                checkIndex(row+1, col-1) + checkIndex(row+1, col) + checkIndex(row+1, col+1);

    }

    // returns int
    private int checkIndex(int row, int col) {
        try {
            boolean temp = board[row][col].getHasMine();
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }

        if (board[row][col].getHasMine()) {
            return 1;
        }
        return 0;
    }

    // returns boolean
    private boolean checkIndex2(int row, int col) {
        try {
            boolean temp = board[row][col].getHasMine();
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

        return true;
    }

}
