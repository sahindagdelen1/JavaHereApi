package com.sdware.javahereapitest;

import com.sdware.javahereapi.client.CustomClientBuilder;
import com.sdware.javahereapi.conf.AppParams;
import com.sdware.javahereapi.geocoderapi.controller.MultiReverseGeocoderApi;
import com.sdware.javahereapi.geocoderapi.entity.ReverseGeocodeMode;
import io.dropwizard.testing.FixtureHelpers;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MultiReverseGeocoderApiTest extends BaseApiTest {
    private MultiReverseGeocoderApi multiReverseGeocoderApi;
    CustomClientBuilder customClientBuilder;


    @Before
    public void setUp() {
        customClientBuilder = new CustomClientBuilder();
        multiReverseGeocoderApi = new MultiReverseGeocoderApi("appid", "appcode", baseUrl);
        multiReverseGeocoderApi.setCustomClientBuilder(customClientBuilder);
    }

    @After
    public void nullifyAfterTest() {
        multiReverseGeocoderApi = null;
    }

    @Test
    public void multiReverseGeocoderAdressesFails() {
        stubFor(post(urlPathEqualTo(AppParams.MULTI_GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("*"))
                .withHeader(HttpHeaders.CACHE_CONTROL, containing("no-cache"))
                .withRequestBody(equalTo(FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody")))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAddresses.getValue()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("maxresults", equalTo("1"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));
        String requestBody = FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody");
        String apiResponse = multiReverseGeocoderApi.multiReverseGeocoderByMode(requestBody, ReverseGeocodeMode.retrieveAddresses, "8", 1);
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }


    @Test
    public void multiReverseGeocoderAdressesSuccess() {
        stubFor(post(urlPathEqualTo(AppParams.MULTI_GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("*"))
                .withHeader(HttpHeaders.CACHE_CONTROL, containing("no-cache"))
                .withRequestBody(equalTo(FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody")))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAddresses.getValue()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("maxresults", equalTo("1"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/multiReverseGeocodeSuccess.json"))));
        String requestBody = FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody");
        String apiResponse = multiReverseGeocoderApi.multiReverseGeocoderByMode(requestBody, ReverseGeocodeMode.retrieveAddresses, "8", 1);
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/multiReverseGeocodeSuccess.json"), apiResponse);
    }


    @Test
    public void multiReverseGeocoderLandmarksFails() {
        stubFor(post(urlPathEqualTo(AppParams.MULTI_GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("*"))
                .withHeader(HttpHeaders.CACHE_CONTROL, containing("no-cache"))
                .withRequestBody(equalTo(FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody")))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveLandmarks.getValue()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("maxresults", equalTo("1"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));
        String requestBody = FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody");
        String apiResponse = multiReverseGeocoderApi.multiReverseGeocoderByMode(requestBody, ReverseGeocodeMode.retrieveLandmarks, "8", 1);
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }


    @Test
    public void multiReverseGeocoderLandmarksSuccess() {
        stubFor(post(urlPathEqualTo(AppParams.MULTI_GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("*"))
                .withHeader(HttpHeaders.CACHE_CONTROL, containing("no-cache"))
                .withRequestBody(equalTo(FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody")))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveLandmarks.getValue()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("maxresults", equalTo("1"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/multiReverseGeocodeLandmarksSuccess.json"))));
        String requestBody = FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody");
        String apiResponse = multiReverseGeocoderApi.multiReverseGeocoderByMode(requestBody, ReverseGeocodeMode.retrieveLandmarks, "8", 1);
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/multiReverseGeocodeLandmarksSuccess.json"), apiResponse);
    }

}
