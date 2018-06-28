package com.monresto.acidlabs.monresto.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//TODO: test if getInt() automatically converts from string
public class Restaurant {
    private int id;
    private double latitude;
    private double longitude;
    private double distance;
    private Boolean isNew;

    private String name;
    private String description;
    private int estimatedTime;
    private String image;
    private String background;
    private double minimalPrice;
    private double deliveryCost;
    private String phone;

    private double rate;
    private String state;
    private int withPromotion;

    private String openTime;
    private String openDay;
    private String beginMorning;
    private String endMorning;
    private String beginNight;
    private String endNight;
    private int nbrAvis;

    private ArrayList<Speciality> specialities;
    private ArrayList<PaymentMode> paymentModes;


    public Restaurant(int id, double latitude, double longitude, double distance, Boolean isNew, String name, String description, int estimatedTime, String image, String background, double minimalPrice, double deliveryCost, String phone, double rate, String state, int withPromotion, String openTime, String openDay, String beginMorning, String endMorning, String beginNight, String endNight, int nbrAvis, ArrayList<Speciality> specialities, ArrayList<PaymentMode> paymentModes) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
        this.isNew = isNew;
        this.name = name;
        this.description = description;
        this.estimatedTime = estimatedTime;
        this.image = image;
        this.background = background;
        this.minimalPrice = minimalPrice;
        this.deliveryCost = deliveryCost;
        this.phone = phone;
        this.rate = rate;
        this.state = state;
        this.withPromotion = withPromotion;
        this.openTime = openTime;
        this.openDay = openDay;
        this.beginMorning = beginMorning;
        this.endMorning = endMorning;
        this.beginNight = beginNight;
        this.endNight = endNight;
        this.nbrAvis = nbrAvis;
        this.specialities = specialities;
        this.paymentModes = paymentModes;
    }

    public Restaurant(int id, String name, String background) {
        this.id = id;
        this.name = name;
        this.background = background;
    }

    public static Restaurant createFromJson(JSONObject obj) {
        ArrayList<Speciality> S = new ArrayList<>();
        ArrayList<PaymentMode> P = new ArrayList<>();
        try {
            JSONArray specs = obj.getJSONArray("Specialities");
            JSONArray payms = obj.getJSONArray("PaymentModes");
            for (int i = 0; i < specs.length(); i++) {
                JSONObject spec = specs.getJSONObject(i);
                S.add(new Speciality(Integer.valueOf(spec.optString("specialityID")), spec.optString("specialityTitle")));
            }
            for (int i = 0; i < payms.length(); i++) {
                JSONObject paym = payms.getJSONObject(i);
                P.add(new PaymentMode(Integer.valueOf(paym.optString("paymentModeID")), paym.optString("paymentModeTitle"), paym.optString("image")));
            }
            return new Restaurant(obj.optInt("restoID"), obj.optDouble("latitude"),
                    obj.optDouble("longitude"), obj.optDouble("distance"),
                    obj.optBoolean("isNewResto"), obj.optString("name"), obj.optString("description"),
                    obj.optInt("estimation"), obj.optString("imagePath"), obj.optString("imagePath2"),
                    obj.optDouble("minimalPrice"), obj.optDouble("deliveryPrice", obj.optDouble("deliveryCost")),
                    obj.optString("mobile"), obj.optDouble("rate"), obj.optString("state"),
                    obj.optInt("withPromotion"), obj.optString("openTime"), obj.optString("openDay"),
                    null, null, null, null,
                    obj.optInt("reviewsNumber"), S, P);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public int getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistance() {
        return distance;
    }

    public Boolean getNew() {
        return isNew;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getEstimatedTime() {
        return estimatedTime;
    }

    public String getImage() {
        return image;
    }

    public String getBackground() {
        return background;
    }

    public double getMinimalPrice() {
        return minimalPrice;
    }

    public double getDeliveryCost() {
        return deliveryCost;
    }

    public String getPhone() {
        return phone;
    }

    public double getRate() {
        return rate;
    }

    public String getState() {
        return state;
    }

    public int getWithPromotion() {
        return withPromotion;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getOpenDay() {
        return openDay;
    }

    public String getBeginMorning() {
        return beginMorning;
    }

    public String getEndMorning() {
        return endMorning;
    }

    public String getBeginNight() {
        return beginNight;
    }

    public String getEndNight() {
        return endNight;
    }

    public int getNbrAvis() {
        return nbrAvis;
    }

    public ArrayList<Speciality> getSpecialities() {
        return specialities;
    }

    public ArrayList<PaymentMode> getPaymentModes() {
        return paymentModes;
    }

    @Override
    public String toString() {
        return "ID: " + id + " - Name: " + name + " - Estimation: " + estimatedTime;
    }
}
