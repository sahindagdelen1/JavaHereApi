package searchapi.controller;

public class SearchApi {
    private String appId;
    private String appCode;


    public SearchApi(String appId, String appCode) {
        this.appId = appId;
        this.appCode = appCode;
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
