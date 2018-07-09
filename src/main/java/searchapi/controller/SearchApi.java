package searchapi.controller;

import client.ApacheHttpRestClient;
import conf.AppParams;
import org.apache.http.HttpResponse;

public class SearchApi {
    private String appId;
    private String appCode;

    public SearchApi(String appId, String appCode) {
        this.appId = appId;
        this.appCode = appCode;
    }

    public String autoSuggest(Double lat, Double lon, String q) {
        ApacheHttpRestClient apacheHttpRestClient = new ApacheHttpRestClient();
        String url = AppParams.PLACES_BASE_URL + AppParams.PLACES_PATH + AppParams.RESOURCE_AUTOSUGGEST + "?app_id=%s&app_code=%s&at=%s&q=%s";
        String formattedUrl = String.format(url, getAppId(), getAppCode(), (lat + lon), q);

        HttpResponse httpResponse = apacheHttpRestClient.getObjectHttpResponse(formattedUrl);
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            try {
                return apacheHttpRestClient.convertHttpResponseToString(httpResponse);
            } catch (Exception ex) {
                ex.printStackTrace();
                return "";
            }
        }
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
}
