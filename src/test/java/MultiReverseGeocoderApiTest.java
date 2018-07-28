import client.CustomClientBuilder;
import conf.AppParams;
import geocoderapi.controller.MultiReverseGeocoderApi;
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
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));
        String requestBody = FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody");
        String apiResponse = multiReverseGeocoderApi.multiReverseGeocoderAdresses(requestBody, ReverseGeocodeMode.retrieveAddresses, "8");
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
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/multiReverseGeocodeSuccess.json"))));
        String requestBody = FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody");
        String apiResponse = multiReverseGeocoderApi.multiReverseGeocoderAdresses(requestBody, ReverseGeocodeMode.retrieveAddresses, "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/multiReverseGeocodeSuccess.json"), apiResponse);
    }

}
