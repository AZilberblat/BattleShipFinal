package com.avrahamzilberblat.battleshipfinal;

import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import static com.avrahamzilberblat.battleshipfinal.Constants.TABLE_NAME_Easy;
import static com.avrahamzilberblat.battleshipfinal.Constants.TABLE_NAME_Hard;
import static com.avrahamzilberblat.battleshipfinal.Constants.TABLE_NAME_Normal;


public class SQliteFragment extends Fragment {
    private static final String TAG = "ListDataActivity";
    private PlayerDetails  tempPlayerDetails;
    private DatabaseHelper mDatabaseHelper;
    private DatabaseHelper sDatabaseHelper;
    private DatabaseHelper dDatabaseHelper;

    private TextView EasyLevelText;
    private TextView NormalLevelText;
    private TextView HardLevelText;
    DecimalFormat df = new DecimalFormat("#.##");
    private ListView easyListView;
    private ListView hardListView;
    private ListView normalListView;

    /**
     * Inflate the Fragmen_sqlite XML and view the Players with three functions(Level)
     * Each function has ListView,Adapter and ItemListener
     * @param inflater
     * @param parent
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        super.onCreateView(inflater,parent,savedInstanceState);

        View v = inflater.inflate(R.layout.fragment_sqlite, parent, false);
        easyListView = (ListView)v.findViewById(R.id.listViewSQLEasy);
        normalListView = (ListView)v.findViewById(R.id.listViewSQLNormal);
        hardListView = (ListView)v.findViewById(R.id.listViewSQLHard);

        mDatabaseHelper = new DatabaseHelper(getActivity(),TABLE_NAME_Easy);
        sDatabaseHelper = new DatabaseHelper(getActivity(),TABLE_NAME_Normal);
        dDatabaseHelper = new DatabaseHelper(getActivity(),TABLE_NAME_Hard);

        EasyLevelText= (TextView) v.findViewById(R.id.EasyLevelText);
        NormalLevelText=(TextView) v.findViewById(R.id.NormalLevelText);
        HardLevelText=(TextView) v.findViewById(R.id.HardLevelText);

        populateListViewEasy();
        populateListViewNormal();
        populateListViewHard();
    return v;
    }
    /**
     *get the data and append to a list
     */
    private void populateListViewEasy() {
        EasyLevelText.setText("Easy");
        EasyLevelText.setTextColor(Color.LTGRAY);

        Cursor data = mDatabaseHelper.getData(TABLE_NAME_Easy);
        data.moveToFirst();
        ArrayList<PlayerDetails> listData = new ArrayList<>();
        for (int i = 0; i < Constants.TOP_TEN_HIGH_RATIO && data.moveToNext(); ++i) {
            String str = String.format("%1.2f", data.getDouble(2));
            Double lat=data.getDouble(3);
            Double longi=data.getDouble(4);
            Location targetLocation = new Location("");//provider name is unnecessary
            targetLocation.setLatitude(lat);
            targetLocation.setLongitude(longi);
            tempPlayerDetails=new PlayerDetails(data.getString(1),Double.valueOf(str),targetLocation);
            listData.add(tempPlayerDetails);


    }
        data.close();
        Collections.sort(listData, new SortPlayersByRatio());

        ListAdapter adapterEasy = new ArrayAdapter<>(getActivity(),android.R.layout.simple_expandable_list_item_1, listData);
        easyListView.setAdapter(adapterEasy);
        easyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = easyListView.getItemAtPosition(position);
                PlayerDetails temp = (PlayerDetails) o; //As you are using Default String Adapter
                Object mFragment;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewside,new MapFragment(temp) ).commit();

            }
        });

    }
    /**
     *get the data and append to a list
     */
    public void populateListViewNormal() {
        NormalLevelText.setText("Normal");
        NormalLevelText.setTextColor(Color.LTGRAY);



        Cursor data = sDatabaseHelper.getData(TABLE_NAME_Normal);
        ArrayList<PlayerDetails> listDataNormal = new ArrayList<>();
        for (int i = 0; i < Constants.TOP_TEN_HIGH_RATIO && data.moveToNext(); ++i) {
            String str = String.format("%1.2f", data.getDouble(2));
            Double lat=data.getDouble(3);
            Double longi=data.getDouble(4);
            Location targetLocation = new Location("");//provider name is unnecessary
            targetLocation.setLatitude(lat);
            targetLocation.setLongitude(longi);

            tempPlayerDetails=new PlayerDetails(data.getString(1),Double.valueOf(str),targetLocation);
            listDataNormal.add(tempPlayerDetails);
        }
        data.close();
        Collections.sort(listDataNormal, new SortPlayersByRatio());

        ListAdapter adapterNormal = new ArrayAdapter<>(getActivity(),android.R.layout.simple_expandable_list_item_1, listDataNormal);
        normalListView.setAdapter(adapterNormal);
        normalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = normalListView.getItemAtPosition(position);
                PlayerDetails temp = (PlayerDetails) o; //As you are using Default String Adapter
                Object mFragment;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewside,new MapFragment(temp) ).commit();

            }
        });

    }

    /**
     *get the data and append to a list
     */
    public void populateListViewHard() {
        HardLevelText.setText("Hard");
        HardLevelText.setTextColor(Color.LTGRAY);

        Cursor data = dDatabaseHelper.getData(TABLE_NAME_Hard);
        ArrayList<PlayerDetails> listDataHard = new ArrayList<>();
        for (int i = 0; i < Constants.TOP_TEN_HIGH_RATIO && data.moveToNext(); ++i) {
            String str = String.format("%1.2f", data.getDouble(2));
            Double lat=data.getDouble(3);
            Double longi=data.getDouble(4);
            Location targetLocation = new Location("");//provider name is unnecessary
            targetLocation.setLatitude(lat);
            targetLocation.setLongitude(longi);
            tempPlayerDetails=new PlayerDetails(data.getString(1),Double.valueOf(str),targetLocation);
            listDataHard.add(tempPlayerDetails);

        }

        data.close();

        Collections.sort(listDataHard, new SortPlayersByRatio());

        ListAdapter adapterHard = new ArrayAdapter<>(getActivity(),android.R.layout.simple_expandable_list_item_1, listDataHard);

        hardListView.setAdapter(adapterHard);

        /**
         * Click listener which opens Map with Players Location
         */
        hardListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = hardListView.getItemAtPosition(position);
                PlayerDetails temp = (PlayerDetails) o; //As you are using Default String Adapter
                Object mFragment;
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.viewside,new MapFragment(temp) ).commit();

            }
        });

    }
}