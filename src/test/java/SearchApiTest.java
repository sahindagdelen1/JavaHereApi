import com.github.tomakehurst.wiremock.http.Fault;
import conf.AppParams;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;


public class SearchApiTest extends BaseApiTest {


    public SearchApiTest() {
    }

    @Test
    public void correctUrl() throws IOException {
        stubFor(get(urlPathEqualTo("/places/v1/discover/search"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam(AppParams.APPID_KEYNAME, equalTo(AppParams.APPID_KEYVALUE))
                .withQueryParam(AppParams.APPCODE_KEYNAME, equalTo(AppParams.APPCODE_KEYVALUE))
                .withQueryParam("q", equalTo("restaurant"))
                .withQueryParam("at", equalTo("40.9892,28.7792"))
                .willReturn(aResponse().withStatus(200).withHeader(HttpHeaders.CONTENT_TYPE, "application/json[; charset=UTF-8")
                        .withBodyFile("placesdiscover.json")));

        String testUrl = "http://localhost:9090/places/v1/discover/search?%s=%s&%s=%s&q=restaurant&at=40.9892,28.7792";
        testUrl = String.format(testUrl, AppParams.APPID_KEYNAME, AppParams.APPID_KEYVALUE, AppParams.APPCODE_KEYNAME, AppParams.APPCODE_KEYVALUE);

        HttpResponse httpResponse = getHttpResponseFromUrl(testUrl);

        assertThat(httpResponse.getStatusLine().getStatusCode(), is(200));
        assertThat(httpResponse.getStatusLine().getStatusCode(), not(404));
        assertEquals("application/json[; charset=UTF-8", httpResponse.getFirstHeader("Content-Type").getValue());
    }

    @Test
    public void exactPathMatch() throws IOException {
        stubFor(get(urlPathEqualTo("/placesMore/v1/discover/search"))
                .willReturn(aResponse().withStatus(401)
                        .withHeader(HttpHeaders.CONTENT_TYPE, "text/plain")
                        .withBody("Not allowed!")));

        String testUrl = "http://localhost:9090/placesMore/v1/discover/search";
        HttpResponse httpResponse = getHttpResponseFromUrl(testUrl);

        assertThat(httpResponse.getStatusLine().getStatusCode(), is(401));
        assertThat(httpResponse.getStatusLine().getStatusCode(), not(200));
    }

    @Test
    public void emptyResponse() throws IOException {
        mockRule.stubFor(get(urlPathEqualTo("/places/v1/discover/search"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .willReturn(aResponse().withFault(Fault.EMPTY_RESPONSE)));

        HttpResponse httpResponse = getHttpResponseFromUrl(baseUrl + "/places/v1/discover/search");
        assertNull(httpResponse);

        mockRuleTwo.stubFor(get(urlPathEqualTo("/places/v1/discover/search"))
                .withHeader(HttpHeaders.CONTENT_TYPE, containing("json"))
                .willReturn(aResponse().withFault(Fault.CONNECTION_RESET_BY_PEER)));

        httpResponse = getHttpResponseFromUrl(baseUrl + "/places/v1/discover/search");
        assertNull(httpResponse);
    }

}
