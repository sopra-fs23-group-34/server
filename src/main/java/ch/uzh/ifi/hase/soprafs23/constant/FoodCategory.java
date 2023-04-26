package ch.uzh.ifi.hase.soprafs23.constant;

public enum FoodCategory {
    ALL("0"), FRUITS("1"), VEGETABLES("2"), MEAT("3"), SNACKS("4");

    private final String value;

    FoodCategory(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}


