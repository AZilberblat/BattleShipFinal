package com.avrahamzilberblat.battleshipfinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Comparator;

import static com.avrahamzilberblat.battleshipfinal.gameActivity.counterOpponent;
import static com.avrahamzilberblat.battleshipfinal.gameActivity.playerTurn;

public class GridViewCustomAdapterCom extends BaseAdapter {
    static Activity mActivity;
    ArrayList<Button> buttonArrayList;
    private int shipSize;
    private com.avrahamzilberblat.battleshipfinal.gameBoard gameBoard;
    Context context;

    private static LayoutInflater inflater = null;

    public GridViewCustomAdapterCom(Activity activity, gameBoard gameBoard,int shipSize,Context context){
        buttonArrayList = new ArrayList<>();
        mActivity = activity;
        this.shipSize=shipSize;
        this.gameBoard = gameBoard;
        this.context=context;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public final int getCount() {

        return gameBoard.getItems().size();

    }

    @Override
    public final Object getItem(int position) {
        return gameBoard.getItems().get(position);
    }

    @Override
    public final long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = null;
        v = inflater.inflate(R.layout.item, null);
        Button tv = v.findViewById(R.id.button);
        tv.setText(gameBoard.getItems().get(position));
        String text= (String) tv.getText();
        buttonArrayList.add(tv);

        return v;
    }


    public void doRandom() {
        int randIndex,row,column;
        String text;
        if (playerTurn == false) {


            do{
                randIndex=(int)(Math.random()*(buttonArrayList.size()));
                text = buttonArrayList.get(randIndex).getText().toString();
                column = Character.getNumericValue(text.charAt(2));
                row = Character.getNumericValue(text.charAt(0));
            }while(gameBoard.getBoardStatus()[row][column]==2 || (gameBoard.getBoardStatus()[row][column]!=1 && gameBoard.getBoardStatus()[row][column]!=0 && gameBoard.getBoardStatus()[row][column]!=2));
            Button b = buttonArrayList.get(randIndex);
            buttonArrayList.remove(randIndex);
            if (gameBoard.getBoardStatus()[row][column] == 1) {
                gameBoard.findShip(text);
                b.setBackgroundResource(R.drawable.explosion);
                AnimationDrawable anim = (AnimationDrawable) b.getBackground();
                anim.start();
                playerTurn = true;
                counterOpponent++;
                gameBoard.getBoardStatus()[row][column] = 2;
                if (counterOpponent == getShipSize()) {
                    Intent intent = new Intent(context,GameOver.class);
                    intent.putExtra("text", "The Computer Won");
                    intent.putExtra("numOfButtons", this.gameBoard.getRows());
                    intent.putExtra("numOfShips",this.gameBoard.getNumOfShips());
                    intent.putExtra("shipCounter",counterOpponent);
                    context.startActivity(intent);
                }



            } else if(gameBoard.getBoardStatus()[row][column] == 0) {
                b.setBackgroundResource(R.drawable.movie);
                AnimationDrawable anim1 = (AnimationDrawable) b.getBackground();
                anim1.start();
                playerTurn = true;
                gameBoard.getBoardStatus()[row][column] = 2;
            }
        }
    }

    public void SensorKillShip() {
        int index = GetShipSpot();
        String text = buttonArrayList.get(index).getText().toString();
        gameBoard.findShip(text);
        buttonArrayList.get(index).setBackgroundResource(R.drawable.explosion);
        int column = Character.getNumericValue(text.charAt(2));
        int row = Character.getNumericValue(text.charAt(0));
        buttonArrayList.get(index).setBackgroundResource(R.drawable.explosion);
        AnimationDrawable anim = (AnimationDrawable) buttonArrayList.get(index).getBackground();
        anim.start();
        gameBoard.getBoardStatus()[row][column] = 2;
        counterOpponent++;
        if (counterOpponent == getShipSize()) {
            Intent intent = new Intent(context, GameOver.class);
            intent.putExtra("text", "The Computer Won");
            intent.putExtra("numOfButtons", this.gameBoard.getRows());
            intent.putExtra("numOfShips", this.gameBoard.getNumOfShips());
            intent.putExtra("shipCounter", counterOpponent);
            context.startActivity(intent);
        }
    }


    public int GetShipSpot(){
        Button b;
        int row,col;
        String text;
        for (int i =0; i<buttonArrayList.size();i++){
            text = buttonArrayList.get(i).getText().toString();
            col = Character.getNumericValue(text.charAt(2));
            row = Character.getNumericValue(text.charAt(0));
            if(gameBoard.getBoardStatus()[row][col]==1)
                return i;

        }
        return 0;
    }

    public void MoveShip(){
        int r1,r2,direction;
        for(int i=0;i<gameBoard.getShipsOnBoard().size();i++){
            if(gameBoard.getShipsOnBoard().get(i).isHit()==false){
                do{
                    r1 = (int)(Math.random()*gameBoard.getRows());
                    r2 = (int)(Math.random()*gameBoard.getRows());
                    direction=(int)(Math.random()*4);
                }while (gameBoard.canPlace(gameBoard.shipsOnBoard.get(i),r1,r2,direction)==false);
                gameBoard.place(gameBoard.shipsOnBoard.get(i),r1,r2,direction);
                gameBoard.shipsOnBoard.get(i).setShipLocationObBoard(r1+"-"+r2);

                break;
            }
        }

    }


    public int getShipSize() {
        return shipSize;
    }

    public void setShipSize(int shipSize) {
        this.shipSize = shipSize;
    }
}
