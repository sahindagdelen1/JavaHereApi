import client.CustomClientBuilder;
import conf.AppParams;
import geocoderapi.controller.GeocoderApi;
import geocoderapi.entity.LocationAttributes;
import geocoderapi.entity.ResponseAttributes;
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
    public void nullifyAfterTest() {
        geocoderApi = null;
    }


    @Test
    public void partialAddressInfoWhenFails() {
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
        String apiResponse = geocoderApi.partialAddresInfo("425", "randolph", "chicago", "usa", "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);

        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_BAD_REQUEST).withBody(FixtureHelpers.fixture("fixtures/geocoderBadRequest.json"))));
        apiResponse = geocoderApi.partialAddresInfo(null, null, null, null, null);
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderBadRequest.json"), apiResponse);
    }

    @Test
    public void partialAddressInfoSuccess() {
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

        String apiResponse = geocoderApi.partialAddresInfo("425", "randolph", "chicago", "usa", "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderSuccess.json"), apiResponse);
    }

    @Test
    public void freeFormFails() throws Exception {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("searchtext", equalTo("200 S Mathilda Sunnyvale CA"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));

        String apiResponse = geocoderApi.freeForm("200 S Mathilda Sunnyvale CA", "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }

    @Test
    public void freeFormSuccess() throws Exception {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("searchtext", equalTo("200 S Mathilda Sunnyvale CA"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/geocoderFreeFormSuccess.json"))));

        String apiResponse = geocoderApi.freeForm("200 S Mathilda Sunnyvale CA", "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderFreeFormSuccess.json"), apiResponse);
    }

    @Test
    public void boundingBoxFails() throws Exception {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("searchtext", equalTo("1 main"))
                .withQueryParam("mapview", equalTo("42.3902,-71.1293;42.3312,-71.0228"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));
        String apiResponse = geocoderApi.boundingBox("1 main", "42.3902,-71.1293;42.3312,-71.0228", "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }

    @Test
    public void boundingBoxSuccess() throws Exception {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("searchtext", equalTo("1 main"))
                .withQueryParam("mapview", equalTo("42.3902,-71.1293;42.3312,-71.0228"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/geocoderBoundingBoxSuccess.json"))));
        String apiResponse = geocoderApi.boundingBox("1 main", "42.3902,-71.1293;42.3312,-71.0228", "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderBoundingBoxSuccess.json"), apiResponse);
    }

    @Test
    public void streetIntersectionFails() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("city", equalTo("Chicago"))
                .withQueryParam("street", equalTo("State @ Madison"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));
        String apiResponse = geocoderApi.streetIntersection("Chicago", "State @ Madison", "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }

    @Test
    public void streetIntersectionSuccess() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("city", equalTo("Chicago"))
                .withQueryParam("street", equalTo("State @ Madison"))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/geocoderStreetIntersectionSuccess.json"))));
        String apiResponse = geocoderApi.streetIntersection("Chicago", "State @ Madison", "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderStreetIntersectionSuccess.json"), apiResponse);
    }

    @Test
    public void suppressingResponseFails() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("searchtext", equalTo("200 S Mathilda Sunnyvale CA"))
                .withQueryParam("responseattributes", equalTo(ResponseAttributes.none.toString()))
                .withQueryParam("locationattributes", equalTo(LocationAttributes.none.toString()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));
        String apiResponse = geocoderApi.suppressingResponse("200 S Mathilda Sunnyvale CA", "none", "none", "8");
        assertNotNull(apiResponse);
        assertEquals("", apiResponse);
    }

    @Test
    public void suppressingResponseSuccess() {
        stubFor(get(urlPathEqualTo(AppParams.GEOCODER_RESOURCE))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("searchtext", equalTo("200 S Mathilda Sunnyvale CA"))
                .withQueryParam("responseattributes", equalTo(ResponseAttributes.mc.toString() + ResponseAttributes.mt.toString()))
                .withQueryParam("locationattributes", equalTo(LocationAttributes.ai.toString() + LocationAttributes.ad.toString()))
                .withQueryParam("gen", equalTo("8"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK).withBody(FixtureHelpers.fixture("fixtures/geocoderStreetIntersectionSuccess.json"))));
        String apiResponse = geocoderApi.suppressingResponse("200 S Mathilda Sunnyvale CA", ResponseAttributes.mc.toString() + ResponseAttributes.mt.toString(), LocationAttributes.ai.toString() + LocationAttributes.ad.toString(), "8");
        assertNotNull(apiResponse);
        assertEquals(FixtureHelpers.fixture("fixtures/geocoderSupressingResponseSuccess.json"), apiResponse);
    }
}
