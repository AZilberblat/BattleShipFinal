package com.avrahamzilberblat.battleshipfinal;


import android.util.Log;

import java.util.ArrayList;



public class gameBoard {
    private int rows;
    private int[][] boardStatus=null;//0 indicates empty spot and 1 indicates theres a ship in the cell and 2 indicates this cell have been selected



    private int numOfShips;
    public ArrayList<String> getItems() {
        return items;
    }
    public void setItems(ArrayList<String> items) {
        this.items = items;
    }
    private ArrayList<String> items=new ArrayList<>();

    public ArrayList<Ship> getShipsOnBoard() {
        return shipsOnBoard;
    }

    public ArrayList<Ship> shipsOnBoard=new ArrayList<>();







    public gameBoard(int rows, int ships) {
        int r1,r2,direction;

        this.rows = rows;
        this.numOfShips = ships;
        this.boardStatus = new int[this.rows][this.rows];





        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.rows; j++) {
                boardStatus[i][j] = 0;
                items.add(i+"-"+j);
            }

        }


        switch (this.numOfShips) {
            case 1:
                shipsOnBoard.add(new Ship(1));
                do{

                    r1 = (int)(Math.random()*rows);
                    r2 = (int)(Math.random()*rows);
                    direction=(int)(Math.random()*4);
                }while (canPlace(shipsOnBoard.get(0),r1,r2,direction)==false);
                place(shipsOnBoard.get(0),r1,r2,direction);
                shipsOnBoard.get(0).setShipLocationObBoard(r1+"-"+r2);
                break;


            case 3:
                shipsOnBoard.add(new Ship(1));
                shipsOnBoard.add(new Ship(2));
                shipsOnBoard.add(new Ship(3));

                do{

                    r1 = (int)(Math.random()*rows);
                    r2 = (int)(Math.random()*rows);
                    direction=(int)(Math.random()*4);
                }while (canPlace(shipsOnBoard.get(0),r1,r2,direction)==false);
                place(shipsOnBoard.get(0),r1,r2,direction);
                shipsOnBoard.get(0).setShipLocationObBoard(r1+"-"+r2);
                do{

                    r1 = (int)(Math.random()*rows);
                    r2 = (int)(Math.random()*rows);
                    direction=(int)(Math.random()*4);
                }while (canPlace(shipsOnBoard.get(1),r1,r2,direction)==false);
                place(shipsOnBoard.get(1),r1,r2,direction);
                shipsOnBoard.get(1).setShipLocationObBoard(r1+"-"+r2);
                do{

                    r1 = (int)(Math.random()*rows);
                    r2 = (int)(Math.random()*rows);
                    direction=(int)(Math.random()*4);
                }while (canPlace(shipsOnBoard.get(2),r1,r2,direction)==false);
                place(shipsOnBoard.get(2),r1,r2,direction);
                shipsOnBoard.get(2).setShipLocationObBoard(r1+"-"+r2);

                break;

            case 4:
                shipsOnBoard.add(new Ship(1));
                shipsOnBoard.add(new Ship(1));
                shipsOnBoard.add(new Ship(2));
                shipsOnBoard.add(new Ship(3));

                do{

                    r1 = (int)(Math.random()*rows);
                    r2 = (int)(Math.random()*rows);
                    direction=(int)(Math.random()*4);
                }while (canPlace(shipsOnBoard.get(0),r1,r2,direction)==false);
                place(shipsOnBoard.get(0),r1,r2,direction);
                shipsOnBoard.get(0).setShipLocationObBoard(r1+"-"+r2);

                do{
                r1 = (int)(Math.random()*rows);
                r2 = (int)(Math.random()*rows);
                direction=(int)(Math.random()*4);
                }while (canPlace(shipsOnBoard.get(1),r1,r2,direction)==false);
                place(shipsOnBoard.get(1),r1,r2,direction);
                shipsOnBoard.get(1).setShipLocationObBoard(r1+"-"+r2);

                do{

                    r1 = (int)(Math.random()*rows);
                    r2 = (int)(Math.random()*rows);
                    direction=(int)(Math.random()*4);
                }while (canPlace(shipsOnBoard.get(2),r1,r2,direction)==false);
                place(shipsOnBoard.get(2),r1,r2,direction);
                shipsOnBoard.get(2).setShipLocationObBoard(r1+"-"+r2);

                do{

                    r1 = (int)(Math.random()*rows);
                    r2 = (int)(Math.random()*rows);
                    direction=(int)(Math.random()*4);
                }while (canPlace(shipsOnBoard.get(3),r1,r2,direction)==false);
                place(shipsOnBoard.get(3),r1,r2,direction);
                shipsOnBoard.get(3).setShipLocationObBoard(r1+"-"+r2);

                break;
        }


    }




    public int getNumOfShips() {
        return numOfShips;
    }

    public int getRows() {
        return rows;
    }

    public void setCell(){}

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int[][] getBoardStatus() {
        return boardStatus;
    }

    public void setBoardStatus(int[][] boardStatus) {
        this.boardStatus = boardStatus;
    }

    public void place(Ship ship, int row, int col, int direction) {
        int size = ship.getSize();
        switch (direction) {
            case 0: // North
                for (int  i = row; i >= row - (size - 1); i--) {
                    getBoardStatus()[i][col] = 1;
                    ship.getShipLocationObBoard().add(i+"-"+col);
                }
                break;

            case 1: // East
                for (int i = col; i <= col + (size - 1); i++){
                    getBoardStatus()[row][i] = 1;
                    ship.getShipLocationObBoard().add(row+"-"+i);
                }
                break;

            case 2: // South
                for (int i = row; i <= row + (size - 1); i++){
                    getBoardStatus()[i][col] = 1;
                    ship.getShipLocationObBoard().add(i+"-"+col);
                }
                break;

            default: // West
                for (int i = col; i >= col - (size - 1); i--){
                    getBoardStatus()[row][i] = 1;
                    ship.getShipLocationObBoard().add(row+"-"+i);
                }
                break;
        }
    }

    public boolean canPlace(Ship ship, int row, int col, int direction) {
        int size = ship.getSize();
        boolean thereIsRoom = true;
        switch (direction) {
            case 0: // North
                if (row - (size - 1) < 0)
                    thereIsRoom = false;
                else
                    for (int  i = row; i >= row - (size - 1) && thereIsRoom; i--)
                        thereIsRoom = thereIsRoom & (getBoardStatus()[i][col] == 0);
                break;

            case 1: // East
                if (col + (size - 1) >= col)
                    thereIsRoom = false;
                else
                    for (int i = col; i <= col + (size - 1) && thereIsRoom; i++)
                        thereIsRoom = thereIsRoom & (getBoardStatus()[row][i] == 0);
                break;

            case 2: // South
                if (row + (size - 1) >= rows)
                    thereIsRoom = false;
                else
                    for (int i = row; i <= row + (size - 1) && thereIsRoom; i++)
                        thereIsRoom  = thereIsRoom & (getBoardStatus()[i][col] == 0);
                break;

            default: // West
                if (col - (size - 1) < 0)
                    thereIsRoom = false;
                else
                    for (int i = col; i >= col - (size - 1) && thereIsRoom; i--)
                        thereIsRoom = thereIsRoom & (getBoardStatus()[row][i] == 0);
                break;
        }
        return thereIsRoom;
    }
    public void findShip(String loc){
        for (int i=0; i<shipsOnBoard.size();i++){
            for (int j=0;j<shipsOnBoard.get(i).getSize();j++){
                if(loc.equals(shipsOnBoard.get(i).getShipLocationObBoard().get(j)));
                shipsOnBoard.get(i).setHit(true);

            }
        }
    }
}
