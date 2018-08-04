package com.sdware.javahereapi.geocoderapi.controller;

import com.sdware.javahereapi.base.BaseApi;
import com.sdware.javahereapi.conf.AppParams;
import com.sdware.javahereapi.util.StringUtils;
import org.apache.http.HttpResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GeocoderApi extends BaseApi {
    private StringUtils stringUtils;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("GeocoderApi");


    public GeocoderApi(String appId, String appCode) {
        super(appId, appCode);
        baseUrl = AppParams.GEOCODER_BASE_URL;
    }


    public GeocoderApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
        stringUtils = new StringUtils();
    }

    /**
     * Returns json response as String object which contains latitude,longtitude,details of an address parameters.
     *
     * @param houseParameter houseNumber of given address.
     * @param street         street name of given address.
     * @param city           city name  of given address.
     * @param country        country of given address.
     * @param gen            related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/latitude-longitude-by-partial-address">HERE Developer Api</a>
     */
    public String partialAddresInfo(String houseParameter, String street, String city, String country, String gen) {
        String url = baseUrl + AppParams.GEOCODER_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        if (!stringUtils.isEmpty(houseParameter)) {
            stringBuilder.append("&housenumber=" + houseParameter);
        }
        if (!stringUtils.isEmpty(street)) {
            stringBuilder.append("&street=" + street);
        }
        if (!stringUtils.isEmpty(city)) {
            stringBuilder.append("&city=" + city);
        }
        if (!stringUtils.isEmpty(country)) {
            stringBuilder.append("&country=" + country);
        }
        if (!stringUtils.isEmpty(gen)) {
            stringBuilder.append("&gen=" + gen);
        }
        String formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode());
        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }


    /**
     * Returns json response as String object which contains latitude,longtitude,details of an address parameters based on free-form input text.
     *
     * @param searchText free-form input text.
     * @param gen        related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @throws UnsupportedEncodingException  throws exception if encoding fails because of unsupported encoding.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/latitude-longitude-by-free-form-address">HERE Developer Api</a>
     */
    public String freeForm(String searchText, String gen) throws UnsupportedEncodingException {
        String url = baseUrl + AppParams.GEOCODER_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&searchtext=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), URLEncoder.encode(searchText, "UTF-8"), gen);
        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }

    /**
     * Returns json response as String object which contains latitude,longtitude of an address parameters based on restricted area input.
     *
     * @param searchText free-form input text.
     * @param mapview lat-lon pair seperated by comma.
     * @param gen   related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @throws UnsupportedEncodingException   throws exception if encoding fails because of unsupported encoding.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/latitude-longitude-by-mapview-parameter">HERE Developer Api</a>
     */
    public String boundingBox(String searchText, String mapview, String gen) throws UnsupportedEncodingException {
        String url = baseUrl + AppParams.GEOCODER_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&searchtext=%s");
        stringBuilder.append("&mapview=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), URLEncoder.encode(searchText, "UTF-8"), mapview, gen);
        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }

    /**
     * Returns json response as String object which contains latitude,longtitude of an address parameters based on street intersection.
     *
     * @param city  city name.
     * @param street street name
     * @param gen   related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/latitude-longitude-intersection">HERE Developer Api</a>
     */
    public String streetIntersection(String city, String street, String gen) {
        String url = baseUrl + AppParams.GEOCODER_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&city=%s");
        stringBuilder.append("&street=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = "";
        try {
            formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), city, URLEncoder.encode(street, "UTF-8"), gen);
        } catch (UnsupportedEncodingException ex) {
            logger.warning(ex.getMessage());
            return "";
        }

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }


    /**
     * Returns json response as String object which contains only latitude,longtitude for a given address.
     *
     * @param searchText  free-form text.
     * @param responseAtt response attributes (which elements are present in response) seperated by comma  @see {@link com.sdware.javahereapi.geocoderapi.entity.ResponseAttributes}
     * @param locationAtt response attributes (which elements are present in response) seperated by comma  @see {@link com.sdware.javahereapi.geocoderapi.entity.LocationAttributes}
     * @param gen   related to backward compatibility of Here api. Currently it is 8.
     * @return json representation of response as String, empty String if error occurs.
     * @see <a href="https://developer.here.com/api-explorer/rest/geocoder/latitude-longitude-no-attributes">HERE Developer Api</a>
     */
    public String suppressingResponse(String searchText, String responseAtt, String locationAtt, String gen) {
        String url = baseUrl + AppParams.GEOCODER_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&searchtext=%s");
        stringBuilder.append("&responseattributes=%s");
        stringBuilder.append("&locationattributes=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = "";
        try {
            formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), URLEncoder.encode(searchText, "UTF-8"), responseAtt, locationAtt, gen);
        } catch (UnsupportedEncodingException ex) {
            logger.warning(ex.getMessage());
            return "";
        }

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(formattedUrl);
        String jsonResponseStr = customClientBuilder.getJsonResponse(httpResponse);
        if (jsonResponseStr != null) return jsonResponseStr;
        return "";
    }
}


