package com.sdware.javahereapi.searchapi.controller;

import com.sdware.javahereapi.base.BaseApi;
import com.sdware.javahereapi.client.CustomClientBuilder;
import com.sdware.javahereapi.conf.AppParams;
import com.sdware.javahereapi.searchapi.entity.CategoryType;
import com.sdware.javahereapi.searchapi.entity.DiscoverParam;
import com.sdware.javahereapi.searchapi.entity.LocationType;
import com.sdware.javahereapi.searchapi.entity.LookupSource;
import org.apache.http.HttpResponse;

public class SearchApi extends BaseApi {
    private CustomClientBuilder customClientBuilder;

    public SearchApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
    }

    public SearchApi(String appId, String appCode) {
        super(appId, appCode);
        baseUrl = AppParams.PLACES_BASE_URL;
    }
    /**
     * Returns json response as String object which contains list of suggested
     * places related to given input.
     *
     * @param lat Latitude
     * @param lon Longtitude
     * @param q   query input
     * @return json representation of response as String, empty String if error occurs.
     * @see <a href="https://developer.here.com/documentation/places/topics_api/resource-autosuggest.html">HERE Developer Api</a>
     */
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


    /**
     * Returns json response as String object which contains list of categories
     * of places.
     *
     * @param  lat  Latitude
     * @param  lon  Longtitude
     * @param  categoryType  type of category
     * @see   CategoryType
     * @return json representation of response as String, empty String if error occurs.
     */
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

    /**
     * Returns json response as String object which contains places around a given point
     * @param  lat  Latitude
     * @param  lon  Longtitude
     * @param  radius  radius
     * @param  locationType  type of Location
     * @see   LocationType
     *  @param  category category
     * @return json representation of response as String, empty String if error occurs.
     *  @see <a href="https://developer.here.com/documentation/places/topics_api/resource-browse.html">HERE Developer Api</a>
     */
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


    /**
     * Returns json response as String object which contains interesting places in the viewport of the map (discover/explore)
     * Returns json response as String object which helps user identify his/her location (discover/here)
     * Response changes for resourceType parameter.
     * @param  discoverParam  parameters required are wrapped in DiscoverParam class. @see {@link DiscoverParam}
     * @return json representation of response as String, empty String if error occurs.
     *  @see <a href="https://developer.here.com/documentation/places/topics_api/resource-explore.html">HERE Developer Api Discover Explore</a>
     *  @see <a href="https://developer.here.com/documentation/places/topics_api/resource-here.html">HERE Developer Api Discover Here</a>
     */
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

    /**
     * Returns json response of data which contains information if places api is up and running.
     * @return json representation of response as String, empty String if error occurs.
     *  @see <a href="https://developer.here.com/documentation/places/topics_api/resource-health.html">HERE Developer Places Api Health Check</a>
     */
    public String healthCheck() {
        String url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_HEALTH + "?app_id=%s&app_code=%s";
        String formattedUrl = String.format(url, getAppId(), getAppCode());

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }


    /**
     * Returns json response as String object which contains place by its foreign ID.
     * @param  id  foreign ID of place.
     * @param  lookupSource   lookup source
     * @see   LookupSource
     * @return json representation of response as String, empty String if error occurs.
     *  @see <a href="https://developer.here.com/documentation/places/topics_api/resource-lookup.html">HERE Developer Api</a>
     */
    public String lookup(String id, LookupSource lookupSource) {
        String url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_LOOKUP + "?app_id=%s&app_code=%s&source=%s&id=%s";
        String formattedUrl = String.format(url, getAppId(), getAppCode(), lookupSource.getValue(), id);

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }

    public CustomClientBuilder getCustomClientBuilder() {
        return customClientBuilder;
    }

    public void setCustomClientBuilder(CustomClientBuilder customClientBuilder) {
        this.customClientBuilder = customClientBuilder;
    }


}
