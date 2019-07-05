package com.avrahamzilberblat.battleshipfinal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

@SuppressLint("ValidFragment")
public class MapFragment extends Fragment implements OnMapReadyCallback {

    GoogleMap mGoogleMap;
    MapView mMapView;
    View mView;
    PlayerDetails playerDetails;

    public MapFragment(    PlayerDetails playerDetails) {
        this.playerDetails=playerDetails;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Inflate the layout for this fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_map, container, false);
        return  mView;
    }

    @Override
    public void onViewCreated(@NonNull View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView=(MapView) mView.findViewById(R.id.map);
        if(mMapView!=null)
        {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mGoogleMap= googleMap;
        mapConfig(googleMap);
    }

    /**
     *    this is a method for configuring the Map(for clean code)
     * @param googleMap
     */
    private void mapConfig(GoogleMap googleMap) {
        LatLng position=new LatLng(playerDetails.getLat(),playerDetails.getLongitude());

        googleMap.addMarker(new MarkerOptions().position(position).title(playerDetails.getWinnerName())).showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(position));
    }
}
