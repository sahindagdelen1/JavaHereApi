package searchapi.entity;

public class DiscoverParam {
    private Double lat;
    private Double lon;
    private String category;
    private Integer radius;
    private LocationType locationType;
    private String resourceType;

    public DiscoverParam(Double lat, Double lon, String category, Integer radius, LocationType locationType, String resourceType) {
        this.lat = lat;
        this.lon = lon;
        this.category = category;
        this.radius = radius;
        this.locationType = locationType;
        this.resourceType = resourceType;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }

    public LocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(LocationType locationType) {
        this.locationType = locationType;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
