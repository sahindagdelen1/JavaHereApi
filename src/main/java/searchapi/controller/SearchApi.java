package searchapi.controller;

import client.ApacheHttpRestClient;
import conf.AppParams;
import org.apache.http.HttpResponse;

public class SearchApi {
    private String appId;
    private String appCode;
    private ApacheHttpRestClient apacheHttpRestClient;
    String baseUrl;

    public SearchApi(String appId, String appCode, String baseUrl) {
        this.appId = appId;
        this.appCode = appCode;
        apacheHttpRestClient = new ApacheHttpRestClient();
        this.baseUrl = baseUrl;
    }

    public String autoSuggest(Double lat, Double lon, String q) {
        String url = baseUrl + AppParams.PLACES_PATH + AppParams.RESOURCE_AUTOSUGGEST + "?app_id=%s&app_code=%s&at=%s&q=%s";
        String latStr = lat.toString();
        String lonStr = lon.toString();
        String formattedUrl = String.format(url, getAppId(), getAppCode(), (latStr + "," + lonStr), q);

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

    public ApacheHttpRestClient getApacheHttpRestClient() {
        return apacheHttpRestClient;
    }

    public void setApacheHttpRestClient(ApacheHttpRestClient apacheHttpRestClient) {
        this.apacheHttpRestClient = apacheHttpRestClient;
    }
}
