package com.sdware.javahereapi.geocoderapi.entity;

public enum ReverseGeocodeMode {
    retrieveAddresses("retrieveAddresses"), retrieveAreas("retrieveAreas"),
    retrieveLandmarks("retrieveLandmarks"),
    retrieveAll("retrieveAll"), trackPosition("trackPosition");
    private String value;

    ReverseGeocodeMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
