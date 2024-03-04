package sample;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.HttpClient;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import org.apache.http.util.EntityUtils;

import org.apache.http.entity.StringEntity;

public class App {
    public static void main( String[] args ) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // HTTPリクエストの設定を行います。
            // ここでは例としてタイムアウトの時間を設定します。
            RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000)
                .build();
            
            // HTTPのGETリクエストを構築します。
            // ここでは例としてHTTPヘッダ(User-Agent)と設定をセットします。
            //HttpGet httpGet = new HttpGet("http://localhost:8080/auth/admin/realms/testrealm/users");
            HttpPost httpPost = new HttpPost("http://localhost:8080/auth/admin/realms/testrealm/users");
            //StringEntity entity = new StringEntity("{\"username\": \"user3\", \"lastName\": \"ヒタチ タロウ\"}");
            StringEntity entity = new StringEntity("{\"username\": \"user3\", \"lastName\": \"ヒタチ タロウ\"}", "UTF-8");
            //StringEntity entity = new StringEntity("{\"username\": \"user3\", \"lastName\": \"%E3%83%92%E3%82%BF%E3%83%81+%E3%82%BF%E3%83%AD%E3%82%A6\"}");
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            httpPost.addHeader("Authorization", "bearer " + args[0]);
            //httpPost.addHeader("Content-Type", "application/json");
            httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");
            httpPost.setConfig(config);
            httpPost.setEntity(entity);
            
            // HTTPリクエストを実行します。 HTTPステータスが200の場合は取得したHTMLを表示します。
            try {
                CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
                if( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK ) {
                    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
                }
                else {
                    System.out.println("200以外のステータスコードが返却されました。");
                    System.out.println("body=" + EntityUtils.toString(httpResponse.getEntity()));
                }
            }
            catch(Exception exception) {
                throw exception;
            }
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            // HTTPリクエストの設定を行います。
            // ここでは例としてタイムアウトの時間を設定します。
            RequestConfig config = RequestConfig.custom()
                .setSocketTimeout(3000)
                .setConnectTimeout(3000)
                .build();
            
            // HTTPのGETリクエストを構築します。
            // ここでは例としてHTTPヘッダ(User-Agent)と設定をセットします。
            //HttpGet httpGet = new HttpGet("http://localhost:8080/auth/admin/realms/testrealm/users");
            HttpGet httpGet = new HttpGet("http://localhost:8080/auth/admin/realms/testrealm/users?lastName=%E3%83%92%E3%82%BF%E3%83%81+%E3%82%BF%E3%83%AD%E3%82%A6");
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
            httpGet.addHeader("Authorization", "bearer " + args[0]);
            httpGet.setConfig(config);
            
            // HTTPリクエストを実行します。 HTTPステータスが200の場合は取得したHTMLを表示します。
            try {
                CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
                if( httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK ) {
                    System.out.println(EntityUtils.toString(httpResponse.getEntity()));
                }
                else {
                    System.out.println("200以外のステータスコードが返却されました。");
                }
            }
            catch(Exception exception) {
                throw exception;
            }
        }
        catch(Exception exception) {
            exception.printStackTrace();
        }
    }
}
