import client.CustomClientBuilder;
import conf.AppParams;
import geocoderapi.controller.ReverseGeocoderApi;
import geocoderapi.entity.ReverseGeocodeMode;
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
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/geocoderReverseGeocodeLocationSuccess.json"))));
        String apiResponse = reverseGeocoderApi.reverseGeocoderFromLocation("41.8839,-87.6389,250", ReverseGeocodeMode.retrieveAddresses,
                1, "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderReverseGeocodeLocationSuccess.json"), apiResponse);
    }
}
