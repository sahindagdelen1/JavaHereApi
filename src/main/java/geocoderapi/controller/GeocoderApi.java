package geocoderapi.controller;

import base.BaseApi;
import conf.AppParams;
import org.apache.http.HttpResponse;
import util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GeocoderApi extends BaseApi {
    private StringUtils stringUtils;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("GeocoderApi");

    public GeocoderApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
        stringUtils = new StringUtils();
    }

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


    public String freeForm(String searchText, String gen) throws Exception {
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


    public String boundingBox(String searchText, String mapview, String gen) throws Exception {
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


