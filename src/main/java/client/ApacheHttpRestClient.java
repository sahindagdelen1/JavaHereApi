package client;

import com.google.gson.*;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ApacheHttpRestClient {

    CloseableHttpClient httpClient;
    private Gson json;
    private Gson jsonTicksDate;

    public ApacheHttpRestClient() {
        httpClient = HttpClients.createDefault();
        json = new GsonBuilder().create();
        GsonBuilder builderT = new GsonBuilder();
        builderT.registerTypeAdapter(Date.class, new JsonDeserializer() {

            public Date deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context)
                    throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        jsonTicksDate = builderT.create();
    }


    public HttpResponse getObjectHttpResponse(String url) {
        try {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
            return httpClient.execute(request);
        }catch (Exception ex){
            return null;
        }
    }

    public String getObject(String url) {
        try {
            HttpResponse httpResponse = getObjectHttpResponse(url);
            return convertHttpResponseToString(httpResponse);
        }catch (Exception ex){
            return null;
        }
    }

    public <T> T getObject(String basePath, String resource, Class<T> clazz, MultivaluedMap<String, String> params) {
        return null;
    }

    public <T> List<T> toList(T[] value) {
        return null;
    }

    public <T> T postObject(String basePath, String resource, Class<T> clazz, MultivaluedMap params) {
        return null;
    }

    public <T> T putObject(String basePath, String resource, Class<T> clazz, Object object) {
        return null;
    }

    public String convertInputStreamToString(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream,"UTF-8");
        String responseString = scanner.useDelimiter("\\Z").next();
        scanner.close();
        return responseString;
    }

    public String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }
}
