package com.monresto.acidlabs.monresto.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Address {
    private int id;
    private double lat;
    private double lon;
    private String emplacement;
    private String adresse;
    private String rue;
    private String rueTransversalle;
    private String appartement;
    private String postalCode;
    private int zoneID;
    private int cityID;
    private String municipality;
    private boolean isDefault;

    public Address() {

    }

    public Address(int id, double lat, double lon, String emplacement, String adresse, String rue, String rueTransversalle, String appartement, String postalCode, int zoneID, int cityID, String municipality, boolean isDefault) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.emplacement = emplacement;
        this.adresse = adresse;
        this.rue = rue;
        this.appartement = appartement;
        this.postalCode = postalCode;
        this.zoneID = zoneID;
        this.cityID = cityID;
        this.municipality = municipality;
        this.rueTransversalle = rueTransversalle;
        this.isDefault = isDefault;
    }

    public Address(double lat, double lon, String emplacement, String adresse, String rue, String rueTransversalle, String appartement, String postalCode, int zoneID, int cityID, String municipality) {
        this.lat = lat;
        this.lon = lon;
        this.emplacement = emplacement;
        this.adresse = adresse;
        this.rue = rue;
        this.rueTransversalle = rueTransversalle;
        this.appartement = appartement;
        this.postalCode = postalCode;
        this.zoneID = zoneID;
        this.cityID = cityID;
        this.municipality = municipality;
    }

    public static ArrayList<Address> makeListFromJson(JSONArray array) {
        System.out.println("array = [" + array.toString() + "]");
        ArrayList<Address> addresses = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                addresses.add(new Address(obj.optInt("adressID"), obj.optDouble("latitude"), obj.optDouble("latitude"),
                        obj.optString("emplacement"), obj.optString("adresse"), obj.optString("rue"),
                        obj.optString("rueTransversalle"), obj.optString("appartement"), obj.optString("codePostale"),
                        obj.optInt("zoneID"), obj.optInt("cityID"), obj.optString("municipalite"), obj.optInt("is_default") == 1));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("asize: "+addresses.size());
        return addresses;
    }

    public JSONObject toJson() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("latitude", String.valueOf(lat));
            obj.put("longitude", String.valueOf(lon));
            obj.put("emplacement", emplacement);
            obj.put("adresse", adresse);
            obj.put("rue", rue);
            obj.put("rueTransversalle", rueTransversalle);
            obj.put("appartement", appartement);
            obj.put("codePostale", String.valueOf(postalCode));
            obj.put("zoneID", String.valueOf(zoneID));
            obj.put("zoneID", String.valueOf(cityID));
            obj.put("municipalite", municipality);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }

    public int getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getRue() {
        return rue;
    }

    public String getRueTransversalle() {
        return rueTransversalle;
    }

    public String getAppartement() {
        return appartement;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public int getZoneID() {
        return zoneID;
    }

    public int getCityID() {
        return cityID;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public void setRueTransversalle(String rueTransversalle) {
        this.rueTransversalle = rueTransversalle;
    }

    public void setAppartement(String appartement) {
        this.appartement = appartement;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setZoneID(int zoneID) {
        this.zoneID = zoneID;
    }

    public void setCityID(int cityID) {
        this.cityID = cityID;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }
}
