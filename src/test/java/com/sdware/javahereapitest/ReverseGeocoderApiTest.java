package com.sdware.javahereapitest;

import com.sdware.javahereapi.client.CustomClientBuilder;
import com.sdware.javahereapi.conf.AppParams;
import com.sdware.javahereapi.geocoderapi.controller.ReverseGeocoderApi;
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

public class ReverseGeocoderApiTest extends BaseApiTest {
    private ReverseGeocoderApi reverseGeocoderApi;
    CustomClientBuilder customClientBuilder;


    @Before
    public void setUp() {
        customClientBuilder = new CustomClientBuilder();
        reverseGeocoderApi = new ReverseGeocoderApi("appid", "appcode", baseUrl);
        reverseGeocoderApi.setCustomClientBuilder(customClientBuilder);
    }

    @After
    public void nullifyAfterTest() {
        reverseGeocoderApi = null;
    }

    @Test
    public void reverseGeocodeFromLocationFails() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("prox", equalTo("41.8839,-87.6389,250"))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAddresses.getValue()))
                .withQueryParam("maxresults", equalTo("1"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));
        String apiResponse = reverseGeocoderApi.reverseGeocoderFromLocation("41.8839,-87.6389,250", ReverseGeocodeMode.retrieveAddresses,
                1, "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }

    @Test
    public void reverseGeocodeFromLocationSuccess() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("prox", equalTo("41.8839,-87.6389,250"))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAddresses.getValue()))
                .withQueryParam("maxresults", equalTo("1"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/reverseGeocodeLocationSuccess.json"))));
        String apiResponse = reverseGeocoderApi.reverseGeocoderFromLocation("41.8839,-87.6389,250", ReverseGeocodeMode.retrieveAddresses,
                1, "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/reverseGeocodeLocationSuccess.json"), apiResponse);
    }

    @Test
    public void reverseGeocodeLandmarksFails() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("prox", equalTo("37.7442,-119.5931,1000"))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveLandmarks.getValue()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));

        String apiResponse = reverseGeocoderApi.reverseGeocoderByMode("37.7442,-119.5931,1000", ReverseGeocodeMode.retrieveLandmarks, "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }

    @Test
    public void reverseGeocodeLandmarksSuccess() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("prox", equalTo("37.7442,-119.5931,1000"))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveLandmarks.getValue()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/reverseGeocodeLandmarksSuccess.json"))));

        String apiResponse = reverseGeocoderApi.reverseGeocoderByMode("37.7442,-119.5931,1000", ReverseGeocodeMode.retrieveLandmarks, "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/reverseGeocodeLandmarksSuccess.json"), apiResponse);
    }

    @Test
    public void reverseGeocodeRetrieveAreasFails() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("prox", equalTo("37.7442,-119.5931,1000"))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAreas.getValue()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));

        String apiResponse = reverseGeocoderApi.reverseGeocoderByMode("37.7442,-119.5931,1000", ReverseGeocodeMode.retrieveAreas, "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }


    @Test
    public void reverseGeocodeRetrieveAreasSuccess() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("prox", equalTo("37.7442,-119.5931,1000"))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAreas.getValue()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/reverseGeocodeRetrieveAreasSuccess.json"))));

        String apiResponse = reverseGeocoderApi.reverseGeocoderByMode("37.7442,-119.5931,1000", ReverseGeocodeMode.retrieveAreas, "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/reverseGeocodeRetrieveAreasSuccess.json"), apiResponse);
    }

    @Test
    public void reverseGeocodeShapeOfPostalCodeFails() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("prox", equalTo("41.8839,-87.6389,150"))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAddresses.getValue()))
                .withQueryParam("maxresults", equalTo("1"))
                .withQueryParam("additionaldata", equalTo("IncludeShapeLevel,postalCode"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));

        String apiResponse = reverseGeocoderApi.reverseGeocoderShapeOfPostalCode("41.8839,-87.6389,150", ReverseGeocodeMode.retrieveAddresses
                , "1", "IncludeShapeLevel,postalCode", "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }

    @Test
    public void reverseGeocodeShapeOfPostalCodeSuccess() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_REVERSE_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("prox", equalTo("41.8839,-87.6389,150"))
                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAddresses.getValue()))
                .withQueryParam("maxresults", equalTo("1"))
                .withQueryParam("additionaldata", equalTo("IncludeShapeLevel,postalCode"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/reverseGeocodeShapeOfPcSuccess.json"))));

        String apiResponse = reverseGeocoderApi.reverseGeocoderShapeOfPostalCode("41.8839,-87.6389,150", ReverseGeocodeMode.retrieveAddresses
                , "1", "IncludeShapeLevel,postalCode", "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/reverseGeocodeShapeOfPcSuccess.json"), apiResponse);
    }
}
