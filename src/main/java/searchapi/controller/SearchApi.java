package searchapi.controller;

import client.CustomClientBuilder;
import conf.AppParams;
import org.apache.http.HttpResponse;

public class SearchApi {
    private String appId;
    private String appCode;
    private CustomClientBuilder customClientBuilder;
    String baseUrl;

    public SearchApi(String appId, String appCode, String baseUrl) {
        this.appId = appId;
        this.appCode = appCode;
        customClientBuilder = new CustomClientBuilder();
        this.baseUrl = baseUrl;
    }

    public String autoSuggest(Double lat, Double lon, String q) {
        String url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_AUTOSUGGEST + "?app_id=%s&app_code=%s&at=%s&q=%s";
        String latStr = lat.toString();
        String lonStr = lon.toString();
        String formattedUrl = String.format(url, getAppId(), getAppCode(), (latStr + "," + lonStr), q);

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }

    public String getCategories(Double lat, Double lon) {
        String url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_CATEGORIES_PLACES + "?app_id=%s&app_code=%s&at=%s";
        String latStr = lat.toString();
        String lonStr = lon.toString();
        String formattedUrl = String.format(url, getAppId(), getAppCode(), (latStr + "," + lonStr));

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public CustomClientBuilder getCustomClientBuilder() {
        return customClientBuilder;
    }

    public void setCustomClientBuilder(CustomClientBuilder customClientBuilder) {
        this.customClientBuilder = customClientBuilder;
    }
}
