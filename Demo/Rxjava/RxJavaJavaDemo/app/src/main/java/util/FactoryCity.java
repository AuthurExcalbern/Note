package util;

import model.Base;
import model.City;

public class FactoryCity implements Factory {
    @Override
    public Base create(String s) {
        return new City(s);
    }
}
