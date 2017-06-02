package edu.phoenix.mbl402.LTA.Models;

/**
 * Created by usyag on 5/29/2017.
 */
public class Trail {

    private String trailID;
    private String name;
    private double latitude;
    private double longitude;
    private String city;
    private String state;
    private String country;
    private String zip;

    public Trail(){

    }

    public Trail(String name, double latitude, double longitude, String city, String state, String country, String zip) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zip = zip;
    }

    public String getTrailID() {
        return trailID;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getCountry() {
        return country;
    }

    public String getZip() {
        return zip;
    }

    public void setTrailID(String trailID) {
        this.trailID = trailID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
