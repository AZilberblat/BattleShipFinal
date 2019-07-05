package com.avrahamzilberblat.battleshipfinal;

import java.util.ArrayList;

public class Ship {
    private int Size;
    private boolean isAlive;

    public boolean isHit() {
        return isHit;
    }

    public void setHit(boolean hit) {
        isHit = hit;
    }

    private boolean isHit;

    public ArrayList<String> getShipLocationObBoard() {
        return shipLocationObBoard;
    }

    public void setShipLocationObBoard(String loc) {
        shipLocationObBoard.add(loc);
    }

    private ArrayList<String> shipLocationObBoard;

    public Ship(int size){
        this.Size=size;
        this.isAlive=true;
        this.isHit=false;
        shipLocationObBoard=new ArrayList<>();
    }

    public int getSize() {
        return Size;
    }

    public void setSize(int size) {
        Size = size;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
