package pl.javastart.restoffers.category;

import pl.javastart.restoffers.offer.NoCategoryException;

import java.util.Arrays;

public enum Category {

    ELECTRONICS("Elektronika"),
    AGD("AGD i RTV");

    private String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public static Category findByDisplayName(String displayName) {
        return Arrays.stream(values())
                .filter(category -> category.getDisplayName().equals(displayName))
                .findFirst()
                .orElseThrow(() -> new NoCategoryException("Brak kategorii o nazwie: " + displayName));
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
