package com.sdware.javahereapitest;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Rule;


public class BaseApiTest {

    public BaseApiTest() {
    }

    public BaseApiTest(String appId, String appCode) {
        this.appId = appId;
        this.appCode = appCode;
    }

    CloseableHttpClient httpClient;
    String baseUrl = "http://localhost:9090";
    private String appId;
    private String appCode;

    @Rule
    public WireMockRule mockRule = new WireMockRule(9090);

    @Rule
    public WireMockRule mockRuleTwo = new WireMockRule(9091);

    @Before
    public void setUp() throws Exception {
        httpClient = HttpClients.createDefault();
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