package minesweeper_;

import java.lang.Math;
import java.util.Scanner;

public class Minesweeper_ {

    private boolean[][] minemap; //true if mine
    private int[][] board; //number of mines nearby; -num if mine
    private int mines, rows, cols, spacesLeft;
    private String[][] state; //visual board

    private int guesses = 0;
    private boolean alive = true;

    public static void main(String args[]) {
        int r = 10;
        int c = 10;
        int m = 10;
        Minesweeper_ x = new Minesweeper_(r, c, m);
    }

    public Minesweeper_(int initR, int initC, int initM) {
        mines = initM;
        rows = initR;
        cols = initC;
        spacesLeft = rows * cols - mines;
        if (mines > rows * cols) {
            System.out.println("Error: too many mines");
        } else {
            minemap = new boolean[rows][cols];
            board = new int[rows][cols];
            state = new String[rows][cols];
            initBoard();
            play(guesses, alive);
        }
    }

    public void play(int guesses, boolean alive) {

        if (alive && guesses < rows * cols - mines) {
            printBoard();
            alive = guess(getRow(), getCol());
            guesses++;
            spacesLeft--;
            play(guesses, alive);
        }
        if (alive) {
            winner();
        } else {
            loser();
        }
    }

    private void initBoard() {
        int m = mines;
        while (m > 0) {
            int r = (int) (rows * Math.random());
            int c = (int) (cols * Math.random());
            if (!minemap[r][c]) {
                minemap[r][c] = true;
                m--;
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (minemap[i][j]) {
                    for (int y = i - 1; y <= i + 1; y++) {
                        for (int x = j - 1; x <= j + 1; x++) {
                            if (x >= 0 && y >= 0 && x < cols && y < rows) {
                                board[y][x] += 1;
                            }
                        }
                    }
                    board[i][j] = -rows * cols;
                }
                state[i][j] = "[]";
            }
        }
    }

    private void printBoard() {
        System.out.println("\n\n\nSpaces Left: " + spacesLeft + "\n");
        System.out.print("    1  2  3  4  5  6  7  8  9  10 \n");
        for (int r = 0; r < rows; r++) {
            System.out.print(r + 1);
            System.out.print(" ");
            if (r < 9) {
                System.out.print(" ");
            }
            for (int c = 0; c < cols; c++) {
                System.out.print(state[r][c] + " ");
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private boolean guess(int r, int c) {
        if (minemap[r][c]) {
            return false;
        } else {
            state[r][c] = board[r][c] + " ";
            return true;
        }
    }

    private int getRow() {
        Scanner reader = new Scanner(System.in);
        System.out.print("row: ");
        int r = reader.nextInt();
        while (!inBounds(r, rows)) {
            System.out.println("Input row out of bounds.");
            System.out.print("row: ");
            r = reader.nextInt();
        }
        return r - 1;
    }

    private int getCol() {
        Scanner reader = new Scanner(System.in);
        System.out.print("col: ");
        int c = reader.nextInt();
        while (!inBounds(c, cols)) {
            System.out.println("Input col out of bounds.");
            System.out.print("col: ");
            c = reader.nextInt();
        }
        return c - 1;
    }

    private boolean inBounds(int num, int bound) {
        return num >= 1 && num <= bound;
    }

    private void winner() {
        System.out.print("\n\n    1  2  3  4  5  6  7  8  9  10 \n");
        for (int r = 0; r < rows; r++) {
            System.out.print(r + 1);
            System.out.print(" ");
            if (r < 9) {
                System.out.print(" ");
            }
            for (int c = 0; c < cols; c++) {
                if (minemap[r][c]) {
                    System.out.print("* ");
                } else {
                    System.out.print("- ");
                }
            }
            System.out.println("");
        }
        System.out.println("You Win!");
        System.out.println("Exit Program!\n");
        System.exit(0);
    }

    private void loser() {
        System.out.print("\n\n    1  2  3  4  5  6  7  8  9  10 \n");
        for (int r = 0; r < rows; r++) {
            System.out.print(r + 1);
            System.out.print(" ");
            if (r < 9) {
                System.out.print(" ");
            }
            for (int c = 0; c < cols; c++) {
                if (minemap[r][c]) {
                    System.out.print("*  ");
                } else {
                    System.out.print(state[r][c] + " ");
                }
            }
            System.out.println("");
        }
        System.out.println("You Lose!");
        System.out.println("Exit Program!\n");
        System.exit(0);
    }
}
