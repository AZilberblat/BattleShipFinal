package com.avrahamzilberblat.battleshipfinal;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;



public class GridViewCustomAdapter extends BaseAdapter {

    static Activity mActivity;
    private com.avrahamzilberblat.battleshipfinal.gameBoard gameBoard;
    private View.OnClickListener listener;
    private static LayoutInflater inflater = null;

    public GridViewCustomAdapter(Activity activity, gameBoard gameBoard,View.OnClickListener listener) {
        mActivity = activity;
        this.gameBoard = gameBoard;
        this.listener = listener;
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
        tv.setOnClickListener(this.listener);


        return v;


    }
}