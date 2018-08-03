package com.sdware.javahereapi.geocoderapi.controller;

import com.sdware.javahereapi.base.BaseApi;
import com.sdware.javahereapi.conf.AppParams;
import com.sdware.javahereapi.geocoderapi.entity.ReverseGeocodeMode;
import com.sdware.javahereapi.util.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;

public class MultiReverseGeocoderApi extends BaseApi {
    private StringUtils stringUtils;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MultiReverseGeocoderApi");

    public MultiReverseGeocoderApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
        stringUtils = new StringUtils();
    }


    /**
     * Returns json response as String object which contains address details of multiple (up to one hundred) lat,lon
     *
     * @param requestBody list of lat,lon seperated by comma.
     * @param mode        type of reverse geocode  @see {@link ReverseGeocodeMode}
     * @param maxResults  result size
     * @param gen         related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/multi-reverse-geocode">HERE Developer Api</a>
     */
    public String multiReverseGeocoderByMode(String requestBody, ReverseGeocodeMode mode, String gen, int maxResults) {
        String url = baseUrl + AppParams.MULTI_GEOCODER_REVERSE_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&mode=%s");
        stringBuilder.append("&maxresults=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = "";
        formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), mode.getValue(), maxResults, gen);

        List<BasicHeader> basicHeaders = new ArrayList<BasicHeader>();
        BasicHeader basicHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, "*");
        basicHeaders.add(basicHeader);
        basicHeader = new BasicHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
        basicHeaders.add(basicHeader);
        HttpResponse httpResponse = customClientBuilder.postObjectHttpResponse(formattedUrl, basicHeaders, requestBody);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }
}


