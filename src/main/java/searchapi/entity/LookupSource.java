package searchapi.entity;

public enum LookupSource {
    PVID("pvid"), VENUES_CONTENT("venues.content"), VENUES_DESTINATION("venues.destination"), VENUES_VENUE("venues.venue"), SHARING("sharing");
    private String value;

    private LookupSource(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
