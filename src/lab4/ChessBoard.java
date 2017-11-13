package lab4;

import java.util.Scanner;

/**
 *
 * @author Luong Nguyen
 */
public class ChessBoard {
    private int board[][];
    private int count;
    
    public ChessBoard(int size) {
        board = new int[size][size];
        count = 1;
    }
    
    // put a tile on the remaining space of a 2x2 board
    public void putTile(int tr, int tc, int dr, int dc) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                if (tr + i != dr || tc + j != dc) {
                    board[i + tr][j + tc] = count;
                }
            }
        }
        count++;
    }
    
    public void titleBoard(int tr, int tc, int dr, int dc, int size) {
        if (size == 2) { // base case
            putTile(tr, tc, dr, dc);
        } else {
            int[] dTiles = getDefectiveTiles(tr, tc, dr, dc, size);
        
            titleBoard(tr, tc, dTiles[0], dTiles[1], size/2);
            titleBoard(tr, tc + size/2, dTiles[2], dTiles[3], size / 2);
            titleBoard(tr + size/2, tc, dTiles[4], dTiles[5], size / 2);
            titleBoard(tr + size/2, tc + size/2, dTiles[6], dTiles[7], size / 2);
        }
    }
    
    /*
    * given an initial defective tile on a board
    * return an array of [row, col, row, col...] of defective tiles from 4 quarters
    */
    public int[] getDefectiveTiles(int tr, int tc, int dr, int dc, int size) {
        int mr = tr + size / 2;
        int mc = tc + size / 2;
        int[] dTiles = new int[]{mr-1, mc-1, mr-1, mc, mr, mc-1, mr, mc};
        
        if (dr < mr) {
            if (dc < mc) {
                putTile(mr-1, mc-1, mr-1, mc-1);
                dTiles[0] = dr;
                dTiles[1] = dc;
            }
            else {
                putTile(mr-1, mc-1, mr-1, mc);
                dTiles[2] = dr;
                dTiles[3] = dc;
            }
        } else {
            if (dc < mc) {
                putTile(mr-1, mc-1, mr, mc-1);
                dTiles[4] = dr;
                dTiles[5] = dc;
            }
            else {
                putTile(mr-1, mc-1, mr, mc);
                dTiles[6] = dr;
                dTiles[7] = dc;
            }
        }
        return dTiles;
    }
    
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print("\t" + board[i][j]);
            }
            System.out.println("");
        }
    }
    
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter k from 0 to 6: ");
        int k = reader.nextInt();
        int size = (int) Math.pow(2, k);
        System.out.println("Enter location of defect");
        int dr = reader.nextInt();
        int dc = reader.nextInt();

        ChessBoard board = new ChessBoard(size);
        board.titleBoard(0, 0, dr, dc, size);
        board.printBoard();
    }
}
