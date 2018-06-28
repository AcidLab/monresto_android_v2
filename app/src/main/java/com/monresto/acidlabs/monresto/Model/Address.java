package com.monresto.acidlabs.monresto.Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Address {
    private int id;
    private double lat;
    private double lon;
    private String emplacement;
    private String adresse;
    private String rue;
    private String rueTransversalle;
    private String appartement;
    private int postalCode;
    private int zoneID;
    private int cityID;
    private String municipality;

    public Address(int id, double lat, double lon, String emplacement, String adresse, String rue, String rueTransversalle, String appartement, int postalCode, int zoneID, int cityID, String municipality) {
        this.id = id;
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

    public Address(double lat, double lon, String emplacement, String adresse, String rue, String rueTransversalle, String appartement, int postalCode, int zoneID, int cityID, String municipality) {
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

    public int getPostalCode() {
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
}
