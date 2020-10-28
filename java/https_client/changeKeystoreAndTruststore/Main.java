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
    //static final String urlstring = "https://www.redhat.com/";
    //static final String urlstring = "https://fcm.googleapis.com/";
    //static final String urlstring = "https://fcm.googleapis.com/";
    
    public static void main(String[] args) {

        System.out.println("usage: java test.Main url path_to_truststore password");
        String urlstring = args[0];
        String trustStoreFile = args[1];
        char[] trustStorePassword = args[2].toCharArray();
        String keyStoreFile = args[3];
        char[] keyStorePassword = args[4].toCharArray();

        try {
            URL url = new URL(urlstring);
            
            // Create key manager
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(keyStoreFile), keyStorePassword);
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, keyStorePassword);
            KeyManager[] km = keyManagerFactory.getKeyManagers();
            
            // Create trust manager
            KeyStore trustStore = KeyStore.getInstance("JKS");
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(trustStore);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();
             
            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(km,  tm, null);

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
