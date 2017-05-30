package edu.phoenix.mbl402.LTA;

/**
 * Created by usyag on 5/29/2017.
 */
public class Trail {

    private String trailID;
    private String latitude;
    private String longitude;
    private String city;
    private String state;
    private String country;
    private String zip;

    public Trail(String latitude, String longitude, String city, String state, String country, String zip) {
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

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
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
}
