package geocoderapi.controller;

import base.BaseApi;
import conf.AppParams;
import org.apache.http.HttpResponse;
import util.StringUtils;

public class GeocoderApi extends BaseApi {
    private StringUtils stringUtils;

    public GeocoderApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
        stringUtils = new StringUtils();
    }

    public String freeFormInput(String houseParameter, String street, String city, String country, String gen) {
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
}


