package com.sdware.javahereapi.geocoderapi.controller;

import com.sdware.javahereapi.base.BaseApi;
import com.sdware.javahereapi.conf.AppParams;
import com.sdware.javahereapi.geocoderapi.entity.ReverseGeocodeMode;
import com.sdware.javahereapi.util.StringUtils;
import org.apache.http.HttpResponse;

public class ReverseGeocoderApi extends BaseApi {
    private StringUtils stringUtils;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("ReverseGeocoderApi");

    public ReverseGeocoderApi(String appId, String appCode) {
        super(appId, appCode);
        baseUrl = AppParams.GEOCODER_REVERSE_BASE_URL;
        stringUtils = new StringUtils();
    }

    public ReverseGeocoderApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
        stringUtils = new StringUtils();
    }


    /**
     * Returns json response as String object which contains details about the district by latitute and longtitude.
     *
     * @param prox       lat,lon seperated by comma.
     * @param mode       type of reverse geocode  @see {@link ReverseGeocodeMode}
     * @param maxresults result size
     * @param gen        related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/reverse-geocode-district">HERE Developer Api</a>
     */
    public String reverseGeocoderFromLocation(String prox, ReverseGeocodeMode mode, int maxresults, String gen) {
        String url = baseUrl + AppParams.GEOCODER_REVERSE_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&prox=%s");
        stringBuilder.append("&mode=%s");
        stringBuilder.append("&maxresults=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = "";
        formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), prox, mode.getValue(), maxresults, gen);

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }


    /**
     * Returns json response as String object which contains details about the landmarks,streets,administrative areas
     * or all of this three (@see {@link ReverseGeocodeMode})by latitute and longtitude.
     *
     * @param prox lat,lon seperated by comma.
     * @param mode type of reverse geocode  @see {@link ReverseGeocodeMode}
     * @param gen  related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/reverse-geocode-landmarks">HERE Developer Api</a>
     */
    public String reverseGeocoderByMode(String prox, ReverseGeocodeMode mode, String gen) {
        String url = baseUrl + AppParams.GEOCODER_REVERSE_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&prox=%s");
        stringBuilder.append("&mode=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = "";
        formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), prox, mode.getValue(), gen);

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }


    /**
     * Returns json response as String object which contains shape of postal district by lat and lon.
     * @param prox  lat,lon seperated by comma.
     * @param mode        type of reverse geocode  @see {@link ReverseGeocodeMode}
     * @param maxresults    max result count.
     * @param additionaldata   key,value pairs for additional data seperated with comma.
     * @param gen            related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/reverse-geocode-postal-shape">HERE Developer Api</a>
     */
    public String reverseGeocoderShapeOfPostalCode(String prox, ReverseGeocodeMode mode, String maxresults, String additionaldata, String gen) {
        String url = baseUrl + AppParams.GEOCODER_REVERSE_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&prox=%s");
        stringBuilder.append("&mode=%s");
        stringBuilder.append("&maxresults=%s");
        stringBuilder.append("&additionaldata=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = "";
        formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), prox, mode.getValue(), maxresults, additionaldata, gen);

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }
}


