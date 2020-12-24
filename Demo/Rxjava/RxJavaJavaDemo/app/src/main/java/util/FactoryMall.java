package util;

import model.Base;
import model.Mall;

public class FactoryMall implements Factory {
    @Override
    public Base create(String s) {
        return new Mall(s);
    }
}
