package com.avrahamzilberblat.battleshipfinal;

import android.location.Location;


public class PlayerDetails {

    private String diff;
    private String winnerName;
    private Double ratio;
    private Double lat;
    private Double longitude;
    private  Location location;


    public PlayerDetails(String winnerName, Double ratio,Location location) {
        this.winnerName = winnerName;
        this.ratio = ratio;
        this.location=location;
        this.lat=location.getLatitude();
        this.longitude=location.getLongitude();
    }

    public String getWinnerName() {
        return winnerName;
    }

    public void setWinnerName(String winnerName) {
        this.winnerName = winnerName;
    }

    public Double getRatio() {
        return ratio;
    }

    public void setRatio(Double ratio) {
        this.ratio = ratio;
    }

    /**
     * how the Player will be displayed
     * @return
     */
    @Override
    public String toString() {
        return   winnerName + ","  + ratio;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Location location) {
        this.longitude = location.getLongitude();
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Location location) {
        this.lat = location.getLatitude();
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }
}
