package geocoderapi.entity;

public enum ResponseAttributes {

    none("none"), ps("performedSearch"), mq("matchQuality"), mt("matchType"), mc("matchCode"), pr("parsedRequest");
    private String desc;

    ResponseAttributes(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
