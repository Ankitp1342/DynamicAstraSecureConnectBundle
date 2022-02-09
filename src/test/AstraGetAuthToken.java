package test;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

/*
 * 
 * 
	curl --request POST \
  --url https://api.astra.datastax.com/v2/databases/ca91bcb3-1fe4-4fd4-abd1-33deca9a88ab/secureConnectBundle \
  
  --header 'content-type: application/json' \
  --header 'x-cassandra-token: AstraCS:awSImgpvKitycEFFzRfhWDvC:a3c0c936d2c3436a343eb9174049726079bec4828f13a7c6599983487993c926' \
  --data {}'
 */
public class AstraGetAuthToken {


    public static String ASTRA_CLUSTER_ID = "<DATABASE_ID>";
    public static String AUTHORIZATION_TOKEN = "<AUTH_TOKEN_VALUE>";

    public static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
    }


    public static void main(String args[]) {
        secureBundleURL();
        ;
    }

    // http://localhost:8080/RESTfulExample/json/product/get
    public static String secureBundleURL() {
        HttpURLConnection conn = null;
        try {

//            Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.1", 8080));

            URL url = new URL("https://api.astra.datastax.com/v2/databases/" + ASTRA_CLUSTER_ID + "/datacenters");
//            conn = (HttpURLConnection) url.openConnection(proxy);
			conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Bearer " + AUTHORIZATION_TOKEN);
            String input = "{}";

            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String output;
                while ((output = br.readLine()) != null) {
                    System.out.println(output);

                }
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {

                System.out.println(output);
                JsonNode data = objectMapper.readTree(output);
                for (JsonNode node : data) {

                    System.out.println(node.get("secureBundleUrl").textValue());
                    return node.get("secureBundleUrl").textValue();

                }

            }


        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return "";

    }

}
