package com.monresto.acidlabs.monresto.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Dish implements Parcelable {
    private int id;
    private String title;
    private String description;
    private double price;
    private String promotion;
    private String tva;
    private boolean isOrdered;
    private boolean isFavorite;
    private boolean isComposed;
    private String imagePath;

    private int restoID;

    private boolean isBay;
    private String ingredient;
    private String restoname;
    private String restoimage;
    private JSONArray paymentmethode;
    private int quantity;

    public static class Option {
        private int id;
        private String title;
        private double price;

        public Option(int id, String title, double price) {
            this.id = id;
            this.title = title;
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }
    }

    public static class Component {
        private int id;
        private String name;
        private int numberChoice;
        private int numberChoiceMax;

        private ArrayList<Option> options;

        public Component(int id, String name, int numberChoice, int numberChoiceMax, ArrayList<Option> options) {
            this.id = id;
            this.name = name;
            this.numberChoice = numberChoice;
            this.numberChoiceMax = numberChoiceMax;
            this.options = options;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getNumberChoice() {
            return numberChoice;
        }

        public int getNumberChoiceMax() {
            return numberChoiceMax;
        }

        public ArrayList<Option> getOptions() {
            return options;
        }
    }

    ArrayList<Option> dimensions;
    ArrayList<Component> components;

    private Dish(int id, int restoID, String title, String description, double price, String promotion, String tva, boolean isOrdered, boolean isFavorite, boolean isComposed, String imagePath) {
        this.id = id;
        this.restoID = restoID;
        this.title = title;
        this.description = description;
        this.price = price;
        this.promotion = promotion;
        this.tva = tva;
        this.isOrdered = isOrdered;
        this.isFavorite = isFavorite;
        this.isComposed = isComposed;
        this.imagePath = imagePath;

        if (isComposed) {
            dimensions = new ArrayList<>();
            components = new ArrayList<>();
        }
    }

    public static Dish createFromJson(JSONObject obj) {
        if (obj.optInt("isComposed") != 0) {
            Log.d("DISH", "createFromJson: " + obj.optInt("dishID"));
        }
        return new Dish(obj.optInt("dishID"), obj.optInt("restoID"), obj.optString("title"), obj.optString("description"),
                obj.optDouble("price"), obj.optString("promotion"), obj.optString("tva"),
                obj.optInt("isOrdered") != 0, obj.optInt("isFavorite") != 0, obj.optInt("isComposed") != 0,
                obj.optString("imagePath"));
    }

    public int getId() {
        return id;
    }

    public int getRestoID() {
        return restoID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getPromotion() {
        return promotion;
    }

    public String getTva() {
        return tva;
    }

    public boolean isOrdered() {
        return isOrdered;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public boolean isComposed() {
        return isComposed;
    }

    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public String getImagePath() {
        return imagePath;
    }

    public void addDimension(int id, String title, double price) {
        dimensions.add(new Option(id, title, price));
    }

    public void addComponent(int componentID, String name, int numberChoice, int numberChoiceMax, ArrayList<Option> options) {
        components.add(new Component(componentID, name, numberChoice, numberChoiceMax, options));
    }

    public ArrayList<Option> getDimensions() {
        return dimensions;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Creates a parcel to be passed through Intents
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(restoID);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeDouble(price);
        parcel.writeString(promotion);
        parcel.writeString(tva);
        parcel.writeByte((byte) (isOrdered ? 1 : 0));
        parcel.writeByte((byte) (isFavorite ? 1 : 0));
        parcel.writeByte((byte) (isComposed ? 1 : 0));
        parcel.writeString(imagePath);
    }

    // This is used to regenerate the object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<Dish> CREATOR = new Parcelable.Creator<Dish>() {
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

    // Constructor that takes a Parcel and gives you an object populated with it's values
    private Dish(Parcel in) {
        id = in.readInt();
        restoID = in.readInt();
        title = in.readString();
        description = in.readString();
        price = in.readDouble();
        promotion = in.readString();
        tva = in.readString();
        isOrdered = in.readByte() != 0;
        isFavorite = in.readByte() != 0;
        isComposed = in.readByte() != 0;
        imagePath = in.readString();
    }

}
