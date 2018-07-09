import client.ApacheHttpRestClient;
import com.github.tomakehurst.wiremock.http.Fault;
import com.google.gson.Gson;
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
    Gson gson;

    @Before
    public void setUp() {
        searchApi = new SearchApi("appid", "appcode");
        gson = new Gson();
    }

    @After
    public void nullifySearchApi() {
        searchApi = null;
    }

    @Test
    public void autoSuggestTestWhenFails() {
        mockRule.stubFor(get(urlPathEqualTo("/places/v1/autosuggest"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .willReturn(aResponse().withStatus(401)));
        String responseJson = searchApi.autoSuggest(40.9892, 28.7792, "istanbul");
        assertNotNull(responseJson);
        assertEquals(responseJson, containing("{\"status\":401,\"message\":\"Invalid app_id app_code combination\"}"));
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
        SearchApi searchApi = new SearchApi("app_id", "app_code");
        assertNotNull(searchApi);
    }

}
