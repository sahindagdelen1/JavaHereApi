package geocoderapi.controller;

import base.BaseApi;
import conf.AppParams;
import geocoderapi.entity.ReverseGeocodeMode;
import org.apache.http.HttpResponse;
import util.StringUtils;

public class ReverseGeocoderApi extends BaseApi {
    private StringUtils stringUtils;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("ReverseGeocoderApi");

    public ReverseGeocoderApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
        stringUtils = new StringUtils();
    }

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
}


