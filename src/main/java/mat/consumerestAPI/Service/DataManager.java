package mat.consumerestAPI.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import mat.consumerestAPI.Model.User;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Service
public class DataManager {

    private static final String url = "http://localhost:8080/home/";

    public User getUser(String url, long id) throws IOException {
        return new ObjectMapper().readValue(readJsonFromUrl(url+"user/"+id),User.class);
    }
    public void deleteUser(long id) throws IOException {
        URL endpoint = new URL(url+"delete/user/"+id);
        System.out.println(endpoint.toString());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            httpclient.execute(new HttpDelete(url+"delete/user/"+id));
        } catch (Exception e){
           e.printStackTrace();
        }
        finally {
            httpclient.close();
        }

    }
    public void postUser(User user) throws IOException {
        user = new User();
        user.setAge(21);
        user.setName("name");
        user.setSurname("surname");
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url+"add");
            StringEntity params = new StringEntity(JSONObject.valueToString(user));
            httpPost.addHeader("content-type", "application/json");
            httpPost.setEntity(params);
            httpclient.execute(httpPost);

        } catch (IOException e){
            e.printStackTrace();
        }
        finally {
            httpclient.close();
        }
    }

    public static String getUrl() {
        return url;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private static String readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String jsonText = readAll(rd);
            System.out.println(jsonText);
            return jsonText;
        } finally {
            is.close();
        }
    }
}
