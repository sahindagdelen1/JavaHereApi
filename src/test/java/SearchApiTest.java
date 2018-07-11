import client.ApacheHttpRestClient;
import com.github.tomakehurst.wiremock.http.Fault;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import searchapi.controller.SearchApi;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;


public class SearchApiTest extends BaseApiTest {


    private SearchApi searchApi;
    ApacheHttpRestClient apacheHttpRestClient;

    @Before
    public void setUp() {
        apacheHttpRestClient = new ApacheHttpRestClient();
        searchApi = new SearchApi("appid", "appcode", baseUrl);
        searchApi.setApacheHttpRestClient(apacheHttpRestClient);
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
    public void emptyResponse() throws IOException {
        ApacheHttpRestClient apacheHttpRestClient = new ApacheHttpRestClient();
        mockRule.stubFor(get(urlPathEqualTo("/places/v1/discover/search"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));

        HttpResponse httpResponse = apacheHttpRestClient.getObjectHttpResponse(baseUrl + "/places/v1/discover/search");
        assertNull(httpResponse);

        mockRuleTwo.stubFor(get(urlPathEqualTo("/places/v1/discover/search"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));

        httpResponse = apacheHttpRestClient.getObjectHttpResponse(baseUrl + "/places/v1/discover/search");
        assertNull(httpResponse);
    }

    @Test
    public void objectExist() {
        SearchApi searchApi = new SearchApi("app_id", "app_code", baseUrl);
        assertNotNull(searchApi);
    }

}
