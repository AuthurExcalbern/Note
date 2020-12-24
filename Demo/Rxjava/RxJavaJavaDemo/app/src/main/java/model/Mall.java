package model;

public class Mall implements Base {
    private String mallName;

    public Mall(String mallName) {
        this.mallName = mallName;
    }

    @Override
    public String getName() {
        return this.mallName;
    }
}
