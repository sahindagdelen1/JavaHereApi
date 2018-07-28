import client.CustomClientBuilder;
import geocoderapi.controller.MultiReverseGeocoderApi;
import org.junit.After;
import org.junit.Before;

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

//    @Test
//    public void multiReverseGeocoderAdressesFails() {
//        stubFor(post(urlPathEqualTo(AppParams.MULTI_GEOCODER_REVERSE_RESOURCE))
//                .withHeader(HttpHeaders.CONTENT_TYPE, containing("*"))
//                .withHeader(HttpHeaders.CACHE_CONTROL, containing("no-cache"))
//                .withRequestBody(equalTo(FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody")))
//                .withQueryParam("mode", equalTo(ReverseGeocodeMode.retrieveAddresses.getValue()))
//                .withQueryParam("gen", equalTo("8"))
//                .withQueryParam("app_id", equalTo("appid"))
//                .withQueryParam("app_code", equalTo("appcode"))
//                .willReturn(aResponse().withStatus(HttpStatus.SC_UNAUTHORIZED).withBody("")));
//        String requestBody = FixtureHelpers.fixture("fixtures/multiReverseGeocoderAddReqBody");
//        String apiResponse = multiReverseGeocoderApi.multiReverseGeocoderAdresses(requestBody, ReverseGeocodeMode.retrieveAddresses,"8");
//        assertNotNull(apiResponse);
//        assertEquals("", apiResponse);
//    }

}
