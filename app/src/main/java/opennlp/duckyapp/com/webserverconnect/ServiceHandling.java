package opennlp.duckyapp.com.webserverconnect;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ServiceHandling {
    public final static int GET = 1;
    public final static int POST = 2;

    public String call(String url, int method, List<NameValuePair> params) {
        InputStream is = null;
        String response = null;

        try {
            // http client
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpEntity httpEntity = null;
            HttpResponse httpResponse = null;

            // Kiem tra loai method la POST hay GET

            if (method == POST) {
                HttpPost httpPost = new HttpPost(url);
                // them tham so
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }

                httpResponse = httpClient.execute(httpPost);
            } else if (method == GET) {
                // them tham so vao url
                if (params != null) {
                    String paramString = URLEncodedUtils.format(params, "utf-8");
                    url += "?" + paramString;
                }

                HttpGet httpGet = new HttpGet(url);
                httpResponse = httpClient.execute(httpGet);
            }

            httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "/n");
            }
            is.close();
            response = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
