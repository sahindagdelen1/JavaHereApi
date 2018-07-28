package geocoderapi.controller;

import base.BaseApi;
import util.StringUtils;

public class MultiReverseGeocoderApi extends BaseApi {
    private StringUtils stringUtils;
    java.util.logging.Logger logger = java.util.logging.Logger.getLogger("MultiReverseGeocoderApi");

    public MultiReverseGeocoderApi(String appId, String appCode, String baseUrl) {
        super(appId, appCode, baseUrl);
        stringUtils = new StringUtils();
    }
}


