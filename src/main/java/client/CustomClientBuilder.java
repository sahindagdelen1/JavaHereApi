package client;

import com.google.gson.*;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Scanner;

public class CustomClientBuilder {

    CloseableHttpClient httpClient;
    private Gson json;
    private Gson jsonTicksDate;

    public CustomClientBuilder() {
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
            request.addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            return httpClient.execute(request);
        }catch (Exception ex){
            return null;
        }
    }

    public String getJsonResponse(HttpResponse httpResponse) {
        if (httpResponse != null && httpResponse.getStatusLine() != null) {
            try {
                return convertHttpResponseToString(httpResponse);
            } catch (Exception ex) {
                ex.printStackTrace();
                return "";
            }
        }
        return null;
    }
    public String getObject(String url) {
        try {
            HttpResponse httpResponse = getObjectHttpResponse(url);
            return convertHttpResponseToString(httpResponse);
        }catch (Exception ex){
            return null;
        }
    }

    public String convertInputStreamToString(InputStream inputStream) throws IOException {
        Scanner scanner = new Scanner(inputStream,"UTF-8");
        scanner = scanner.useDelimiter("\\Z");
        if (scanner.hasNext()) {
            String responseString = scanner.next();
            scanner.close();
            return responseString;
        }
        return "";
    }

    public String convertHttpResponseToString(HttpResponse httpResponse) throws IOException {
        InputStream inputStream = httpResponse.getEntity().getContent();
        return convertInputStreamToString(inputStream);
    }
}
