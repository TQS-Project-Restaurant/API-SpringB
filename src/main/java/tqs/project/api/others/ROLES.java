package tqs.project.api.others;

public enum ROLES {
    USER(0),
    WAITER(1),
    KITCHEN(2);

    private final int number;

    ROLES(final int number) {
        this.number = number;
    }

    public int getNumber() { return number; }
}
