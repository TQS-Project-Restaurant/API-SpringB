package tqs.project.api.others;

public enum STATUS {
    PENDING(0),
    PREPARING(1),
    COMPLETED(2),
    CANCELED(3);

    private final int number;

    STATUS(final int number) {
        this.number = number;
    }

    public int getNumber() { return number; }
}
