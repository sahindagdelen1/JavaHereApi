import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Before;
import org.junit.Rule;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;


public class BaseApiTest {

    public BaseApiTest() {
    }

    CloseableHttpClient httpClient;
    String baseUrl = "http://localhost:9090";

    @Rule
    public WireMockRule mockRule = new WireMockRule(9090);

    @Rule
    public WireMockRule mockRuleTwo = new WireMockRule(9091);

    @Before
    public void setUp() throws Exception {
         httpClient = HttpClients.createDefault();
    }

    private String convertInputStreamToString(InputStream inputStream)  throws IOException{
        Scanner scanner = new Scanner(inputStream,"UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }

    public String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }

    public HttpResponse getHttpResponseFromUrl(String url){
        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpResponse httpResponse;
        try {
            httpResponse = httpClient.execute(request);
        }catch (Exception ex){
            return null;
        }
        return httpResponse;
    }

}