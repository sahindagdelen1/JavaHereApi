package geocoderapi.entity;

public enum LocationAttributes {
    none("none"), ar("address"), mr("mapReference"), mv("mapView"), dt("addressDetails"), sd("streetDetails"), ad("additionalData"), ai("adminIds"), li("linkInfo"), in("adminInfo");
    private String desc;

    LocationAttributes(String desc) {
        this.desc = desc;
    }

    String getDesc() {
        return desc;
    }
}
