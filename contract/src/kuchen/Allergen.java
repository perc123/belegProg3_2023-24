package kuchen;

public enum Allergen {
    Gluten("Gluten"),
    Erdnuss("Erdnuss"),
    Haselnuss("Haselnuss"),
    Sesamsamen("Sesamsamen");


    private final String text;

    Allergen(String text) {
        this.text = text;
    }

    public static Allergen fromString(String text) {
        for (Allergen allergen : Allergen.values()) {
            if (allergen.text.equalsIgnoreCase(text)) {
                return allergen;
            }
        }
        return null;
    }

    public String getText() {
        return text;
    }

}