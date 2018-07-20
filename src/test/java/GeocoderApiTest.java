import client.CustomClientBuilder;
import conf.AppParams;
import geocoderapi.controller.GeocoderApi;
import io.dropwizard.testing.FixtureHelpers;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GeocoderApiTest extends BaseApiTest {
    private GeocoderApi geocoderApi;
    CustomClientBuilder customClientBuilder;


    @Before
    public void setUp() {
        customClientBuilder = new CustomClientBuilder();
        geocoderApi = new GeocoderApi("appid", "appcode", baseUrl);
        geocoderApi.setCustomClientBuilder(customClientBuilder);
    }

    @After
    public void nullifySearchApi() {
        geocoderApi = null;
    }


    @Test
    public void freeFormInputWhenFails() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("housenumber", equalTo("425"))
                .withQueryParam("street", equalTo("randolph"))
                .withQueryParam("city", equalTo("chicago"))
                .withQueryParam("country", equalTo("usa"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody(""))
        );
        String apiResponse = geocoderApi.freeFormInput("425", "randolph", "chicago", "usa", "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);

        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_BAD_REQUEST).withBody(FixtureHelpers.fixture("fixtures/geocoderBadRequest.json"))));
        apiResponse = geocoderApi.freeFormInput(null, null, null, null, null);
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderBadRequest.json"), apiResponse);
    }

    @Test
    public void freeFormInputSuccess() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("housenumber", equalTo("425"))
                .withQueryParam("street", equalTo("randolph"))
                .withQueryParam("city", equalTo("chicago"))
                .withQueryParam("country", equalTo("usa"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/geocoderSuccess.json")))
        );

        String apiResponse = geocoderApi.freeFormInput("425", "randolph", "chicago", "usa", "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderSuccess.json"), apiResponse);
    }

}
