package models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class akita {
    @SerializedName("type")
    public String type;

    @SerializedName("headline")
    public String headline;

    @SerializedName("name")
    public String name;

    @SerializedName("description")
    public String description;

    @SerializedName("age")
    public Age age;

    @SerializedName("color")
    public Color color;

    @SerializedName("weight")
    public Weight weight;

    @SerializedName("character")
    public Character character;

    public static class Age {
        @SerializedName("years")
        public int years;
        @SerializedName("months")
        public int months;
    }

    public static class Color {
        @SerializedName("primary")
        public String primary;
        @SerializedName("secondary")
        public String secondary;
    }

    public static class Weight {
        @SerializedName("kg")
        public double kg;
        @SerializedName("lastMeasured")
        public String lastMeasured;
    }

    public static class Character {
        @SerializedName("temperament")
        public List<String> temperament;
        @SerializedName("energyLevel")
        public String energyLevel;
        @SerializedName("affectionLevel")
        public int affectionLevel;
        @SerializedName("childFriendly")
        public boolean childFriendly;
        @SerializedName("catFriendly")
        public boolean catFriendly;
        @SerializedName("strangerFriendly")
        public String strangerFriendly;
    }

}

