package model;

import androidx.annotation.NonNull;

public class Card {
    private String cityName;
    private String mallName;

    public Card(String cityName, String mallName) {
        this.cityName = cityName;
        this.mallName = mallName;
    }

    @NonNull
    @Override
    public String toString() {
        return "你到了 " + this.cityName + " 的 " + this.mallName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }
}
