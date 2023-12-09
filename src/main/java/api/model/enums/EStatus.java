package api.model.enums;

public enum EStatus {
    AWAITS("Awaits"),
    IN_PROGRESS("In progress"),
    DONE("Done");

    private final String value;

    EStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
