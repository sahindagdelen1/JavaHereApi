package geocoderapi.controller;

import base.BaseApi;
import conf.AppParams;
import geocoderapi.entity.ReverseGeocodeMode;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.message.BasicHeader;
import util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MultiReverseGeocoderApi extends BaseApi {
    private StringUtils stringUtils;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MultiReverseGeocoderApi");

    public MultiReverseGeocoderApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
        stringUtils = new StringUtils();
    }

    public String multiReverseGeocoderAdresses(String requestBody, ReverseGeocodeMode mode, String gen) {
        String url = baseUrl + AppParams.MULTI_GEOCODER_REVERSE_RESOURCE;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(url);
        stringBuilder.append("?app_id=%s");
        stringBuilder.append("&app_code=%s");
        stringBuilder.append("&mode=%s");
        stringBuilder.append("&gen=%s");
        String formattedUrl = "";
        formattedUrl = String.format(stringBuilder.toString(), getAppId(), getAppCode(), mode.getValue(), gen);

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


