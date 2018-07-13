import client.CustomClientBuilder;
import com.github.tomakehurst.wiremock.http.Fault;
import conf.AppParams;
import io.dropwizard.testing.FixtureHelpers;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchapi.controller.SearchApi;
import searchapi.entity.BrowseLocationType;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;


public class SearchApiTest extends BaseApiTest {

    private SearchApi searchApi;
    CustomClientBuilder customClientBuilder;

    @Before
    public void setUp() {
        customClientBuilder = new CustomClientBuilder();
        searchApi = new SearchApi("appid", "appcode", baseUrl);
        searchApi.setCustomClientBuilder(customClientBuilder);
    }

    @After
    public void nullifySearchApi() {
        searchApi = null;
        mockRule.stop();
        mockRuleTwo.stop();
    }

    @Test
    public void autoSuggestTestWhenFails() {
        stubFor(get(urlPathEqualTo("/places/v1/autosuggest"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .withQueryParam("at", equalTo("40.9892,28.7792"))
                .withQueryParam("q", equalTo("istanbul"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(401).withBody("{\"status\":401,\"message\":\"Invalid app_id app_code combination\"}")));
        String responseJson = searchApi.autoSuggest(40.9892, 28.7792, "istanbul");
        assertNotNull(responseJson);
        assertEquals(responseJson, ("{\"status\":401,\"message\":\"Invalid app_id app_code combination\"}"));
    }

    @Test
    public void autoSuggestTestWhenSucceeds() throws IOException {
        stubFor(get(urlPathEqualTo("/places/v1/autosuggest"))
                .withQueryParam("at", equalTo("21.9892,28.7792"))
                .withQueryParam("q", equalTo("istanbul"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .willReturn(aResponse().withStatus(200).withBody("{\n" +
                        "    \"results\": [ \n" +
                        "        {\n" +
                        "            \"title\": \"Istanbulstraße\",\n" +
                        "            \"highlightedTitle\": \"<b>Istanbul</b>straße\",\n" +
                        "            \"completion\": \"istanbulstraße\",\n" +
                        "            \"href\": \"https://places.cit.api.here.com/places/v1/autosuggest;context=cmVzdWx0X3R5cGVzPXF1ZXJ5JTJDYWRkcmVzcyUyQ2NhdGVnb3J5JTJDcGxhY2UlMkNjaGFpbiZmbG93LWlkPTYwNzNkMjZhLWQ5MDItNTZlZS1iMGQyLTE1MmU4NzBkMzEyNl8xNTMxMzM0NzI1OTMzXzk5MTRfNjIxNiZyYW5rPTE4?q=Istanbulstra%C3%9Fe&at=21.9892%2C28.7792&app_id=yOecByTUi1010XgBqKne&app_code=Djh77liYdfjuFZHFNsIq0A\",\n" +
                        "            \"type\": \"urn:nlp-types:autosuggest\",\n" +
                        "            \"resultType\": \"query\"\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}")));

        String jsonResponse = searchApi.autoSuggest(21.9892, 28.7792, "istanbul");
        assertNotNull(jsonResponse);
        assertEquals("{\n" +
                "    \"results\": [ \n" +
                "        {\n" +
                "            \"title\": \"Istanbulstraße\",\n" +
                "            \"highlightedTitle\": \"<b>Istanbul</b>straße\",\n" +
                "            \"completion\": \"istanbulstraße\",\n" +
                "            \"href\": \"https://places.cit.api.here.com/places/v1/autosuggest;context=cmVzdWx0X3R5cGVzPXF1ZXJ5JTJDYWRkcmVzcyUyQ2NhdGVnb3J5JTJDcGxhY2UlMkNjaGFpbiZmbG93LWlkPTYwNzNkMjZhLWQ5MDItNTZlZS1iMGQyLTE1MmU4NzBkMzEyNl8xNTMxMzM0NzI1OTMzXzk5MTRfNjIxNiZyYW5rPTE4?q=Istanbulstra%C3%9Fe&at=21.9892%2C28.7792&app_id=yOecByTUi1010XgBqKne&app_code=Djh77liYdfjuFZHFNsIq0A\",\n" +
                "            \"type\": \"urn:nlp-types:autosuggest\",\n" +
                "            \"resultType\": \"query\"\n" +
                "        }\n" +
                "    ]\n" +
                "}", jsonResponse);
    }


    @Test
    public void discoverSearchEmptyResponse() throws IOException {
        CustomClientBuilder customClientBuilder = new CustomClientBuilder();
        mockRule.stubFor(get(urlPathEqualTo("/places/v1/discover/search"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(baseUrl + "/places/v1/discover/search");
        assertNull(httpResponse);

        mockRuleTwo.stubFor(get(urlPathEqualTo("/places/v1/discover/search"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));

        httpResponse = customClientBuilder.getObjectHttpResponse(baseUrl + "/places/v1/discover/search");
        assertNull(httpResponse);
    }

    @Test
    public void categoriesWhenFails() throws IOException {
        stubFor(get(urlPathEqualTo(AppParams.PLACES_PATH + AppParams.RESOURCE_CATEGORIES_PLACES))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON))
                .withQueryParam("at", equalTo("40.9892,28.7792"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode")).willReturn(aResponse().withStatus(401).withBody("{ \"status\" : 401, \"message\" : \"Invalid app_id app_code combination\" }")));

        HttpResponse httpResponse = customClientBuilder.getObjectHttpResponse(baseUrl + "/places/v1/categories/places?at=40.9892,28.7792&app_id=appid&app_code=appcode");
        assertThat(httpResponse.getStatusLine().getStatusCode(), is(HttpStatus.SC_UNAUTHORIZED));
        assertThat(httpResponse.getStatusLine().getStatusCode(), not(HttpStatus.SC_OK));

        String jsonResponse = searchApi.getCategories(40.9892, 28.7792);
        assertNotNull(jsonResponse);
        assertThat(jsonResponse, containsString("401"));
    }

    @Test
    public void categoriesWhenSucceeds() {
        stubFor(get(urlPathEqualTo(AppParams.PLACES_PATH + AppParams.RESOURCE_CATEGORIES_PLACES))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON))
                .withQueryParam("at", equalTo("40.9892,28.7792"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .willReturn(aResponse().withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withBody(FixtureHelpers.fixture("fixtures/searchApiGetCategories.json"))));

        String jsonResponse = searchApi.getCategories(40.9892, 28.7792);
        assertEquals(jsonResponse, FixtureHelpers.fixture("fixtures/searchApiGetCategories.json"));
        assertNotNull(jsonResponse);
        assertThat(jsonResponse, containsString("items"));
        verify(1, getRequestedFor(urlPathEqualTo(AppParams.PLACES_PATH + AppParams.RESOURCE_CATEGORIES_PLACES)));
    }

    @Test
    public void objectExists() {
        SearchApi searchApi = new SearchApi("app_id", "app_code", baseUrl);
        assertNotNull(searchApi);
    }

    @Test
    public void browseWhenFails() {
        stubFor(get(urlPathEqualTo(AppParams.PLACES_PATH + AppParams.RESOURCE_BROWSE_PLACES))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON))
                .withQueryParam("in", equalTo("52.521,13.3807;r=2000"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .withQueryParam("cat", equalTo("petrol-station"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withBody(FixtureHelpers.fixture("fixtures/unauthorized.json"))));

        String jsonResponse = searchApi.browse(52.521, 13.3807, 2000, BrowseLocationType.IN, "petrol-station");
        assertNotNull(jsonResponse);
        assertEquals(jsonResponse, FixtureHelpers.fixture("fixtures/unauthorized.json"));
        verify(1, getRequestedFor(urlPathEqualTo(AppParams.PLACES_PATH + AppParams.RESOURCE_BROWSE_PLACES)));
    }

    @Test
    public void browseWhenSucceds() {
        stubFor(get(urlPathEqualTo(AppParams.PLACES_PATH + AppParams.RESOURCE_BROWSE_PLACES))
                .withHeader(HttpHeaders.CONTENT_TYPE, equalTo(MediaType.APPLICATION_JSON))
                .withQueryParam("in", equalTo("52.521,13.3807;r=2000"))
                .withQueryParam("app_id", equalTo("appid"))
                .withQueryParam("app_code", equalTo("appcode"))
                .withQueryParam("cat", equalTo("petrol-station"))
                .willReturn(aResponse().withStatus(HttpStatus.SC_OK)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                        .withBody(FixtureHelpers.fixture("fixtures/browseSuccess.json"))));

        String jsonResponse = searchApi.browse(52.521, 13.3807, 2000, BrowseLocationType.IN, "petrol-station");
        assertNotNull(jsonResponse);
        assertEquals(jsonResponse, FixtureHelpers.fixture("fixtures/browseSuccess.json"));
        verify(1, getRequestedFor(urlPathEqualTo(AppParams.PLACES_PATH + AppParams.RESOURCE_BROWSE_PLACES)));
    }

}
