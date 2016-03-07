package com.akaver.puzzle44;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by akaver on 07/03/16.
 */
public class Puzzle44Engine {
    private static final String TAG = "Puzzle44Engine";

    private int[][] gameBoard = new int[4][4];
    private int moveCount = 0;

    public Puzzle44Engine(){
        randomizeBoard();
    }

    public int[][] getBoard(){
        return gameBoard;
    }

    public void setBoard(int[][] gameBoard){
        this.gameBoard = gameBoard;
    }

    public void randomizeBoard(){
        List<Integer> items = new ArrayList<Integer>();
        Random random = new Random();

        for (int itemNo=1; itemNo<=16; itemNo++){
            items.add(itemNo);
        }

        for (int x=0; x<=3; x++){
            for(int y=0; y<=3; y++){
                int itemNo = random.nextInt(items.size());
                gameBoard[y][x] = items.get(itemNo);
                items.remove(itemNo);

            }
        }

        moveCount = 0;
    }

    public boolean isGameOver(){

        return false;
    }

    public void makeMove(int x, int y){

    }

    public int getMoves(){
        return moveCount;
    }

    public void setMove(int moveCount){
        this.moveCount = moveCount;
    }
}
