import client.ApacheHttpRestClient;
import com.github.tomakehurst.wiremock.http.Fault;
import conf.AppParams;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.junit.Test;
import searchapi.controller.SearchApi;

import java.io.IOException;
import java.util.Scanner;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;


public class SearchApiTest extends BaseApiTest {

    @Test
    public void emptyResponse() throws IOException {
        ApacheHttpRestClient  apacheHttpRestClient = new ApacheHttpRestClient();
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
    public void objectExist(){
        SearchApi searchApi = new SearchApi("app_id", "app_code");
        assertNotNull(searchApi);
    }

}
