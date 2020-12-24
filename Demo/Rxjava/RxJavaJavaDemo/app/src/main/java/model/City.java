package model;

public class City implements Base {

    private String cityName;

    public City(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String getName() {
        return this.cityName;
    }
}
