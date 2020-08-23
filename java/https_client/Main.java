package test;

import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyStore;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;

public class Main {
    
    public static void main(String[] args) {

        System.out.println("usage: java test.Main url path_to_truststore password");
        String urlstring = args[0];

        try {
            URL url = new URL(urlstring);
            
            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getDefault();

            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            conn.setSSLSocketFactory(sslContext.getSocketFactory());
            System.out.println(conn.getResponseCode());
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String body;
                while ((body = reader.readLine()) != null) {
                    System.out.println(body);
                }
            }
            catch (Exception e) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String body;
                while ((body = reader.readLine()) != null) {
                    System.out.println(body);
                }
            }                
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
