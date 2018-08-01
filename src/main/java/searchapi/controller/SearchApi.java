package searchapi.controller;

import client.CustomClientBuilder;
import conf.AppParams;
import org.apache.http.HttpResponse;
import searchapi.entity.CategoryType;
import searchapi.entity.DiscoverParam;
import searchapi.entity.LocationType;
import searchapi.entity.LookupSource;

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
     * @param  discoverParam  parameters required are wrapped in DiscoverParam class.
     * @param  {Double] discoverParam.lat   - Latitute of location
     * @param  {Double] discoverParam.lon   - Longtitude of location
     * @param  {String] discoverParam.category   - Category of location which can be retrieved from {@link #categories(Double, Double, CategoryType)}  method}
     * @param  {String] discoverParam.query   - Query string which is retrieved from user as input.
     * @param  {Integer] discoverParam.radius   - Query string which is retrieved from user as input.
     * @param  {{@link LocationType}] discoverParam.locationType   -  Location type which user wants to discover.
     * @param  {String] discoverParam.resourceType   -  There two resources linked with this ws, discover/here and discover/explore.
     * @see   DiscoverParam
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
     * @param  id
     * @param  lookupSource
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
