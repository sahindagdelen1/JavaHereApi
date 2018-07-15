package searchapi.controller;

import client.CustomClientBuilder;
import conf.AppParams;
import org.apache.http.HttpResponse;
import searchapi.entity.CategoryType;
import searchapi.entity.DiscoverParam;
import searchapi.entity.LocationType;

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

    public String categories(Double lat, Double lon, CategoryType categoryType) {
        String url;
        if (categoryType.equals(CategoryType.PLACES)) {
            url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_CATEGORIES_PLACES + "?app_id=%s&app_code=%s&at=%s";
        } else if (categoryType.equals(CategoryType.CUISINES)) {
            url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_CATEGORIES_CUISINES + "?app_id=%s&app_code=%s&at=%s";
        } else {
            return "Unexpected category type";
        }

        String latStr = lat.toString();
        String lonStr = lon.toString();
        String formattedUrl = String.format(url, getAppId(), getAppCode(), (latStr + "," + lonStr));

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }

    public String browse(Double lat, Double lon, int radius, LocationType locationType, String category) {
        String atOrInParam;
        String latStr = lat.toString();
        String lonStr = lon.toString();

        String url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_BROWSE_PLACES + "?app_id=%s&app_code=%s&in=%s&cat=%s";
        String urlWithAt = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_BROWSE_PLACES + "?app_id=%s&app_code=%s&at=%s&cat=%s";
        String formattedUrl = "";
        if (locationType.equals(LocationType.AT)) {
            atOrInParam = latStr + "," + lonStr;
            formattedUrl = String.format(urlWithAt, getAppId(), getAppCode(), atOrInParam, category);
        } else if (locationType.equals(LocationType.IN)) {
            atOrInParam = latStr + "," + lonStr + ";r=" + radius;
            formattedUrl = String.format(url, getAppId(), getAppCode(), atOrInParam, category);
        }

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";

    }

    public String discover(DiscoverParam discoverParam) {
        String latStr = discoverParam.getLat().toString();
        String lonStr = discoverParam.getLon().toString();
        String url = "";
        String atOrInParam = "";
        String formattedUrl;

        if (discoverParam.getLocationType().equals(LocationType.AT)) {
            atOrInParam = latStr + "," + lonStr;
            url = baseUrl + AppParams.PLACES_PATH + discoverParam.getResourceType() + "?app_id=%s&app_code=%s&at=%s";
        } else if (discoverParam.getLocationType().equals(LocationType.IN)) {
            atOrInParam = latStr + "," + lonStr + ";r=" + discoverParam.getRadius();
            url = baseUrl + AppParams.PLACES_PATH + discoverParam.getResourceType() + "?app_id=%s&app_code=%s&in=%s";
        }

        if (discoverParam.getResourceType().equals(AppParams.RESOURCE_DISCOVER_SEARCH)) {
            url = url + "&q=%s";
            formattedUrl = String.format(url, getAppId(), getAppCode(), atOrInParam, discoverParam.getQuery());
        } else {
            url = url + "&cat=%s";
            formattedUrl = String.format(url, getAppId(), getAppCode(), atOrInParam, discoverParam.getCategory());
        }
        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }


    public String healthCheck() {
        String url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_HEALTH + "?app_id=%s&app_code=%s";
        String formattedUrl = String.format(url, getAppId(), getAppCode());

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
