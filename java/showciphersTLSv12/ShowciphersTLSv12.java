package showciphers;

import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;

public class ShowciphersTLSv12 {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        //SSLSocketFactory fac = (SSLSocketFactory)SSLSocketFactory.getDefault();
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        try {
            ctx.init(null, null, null);
        }
        catch(Exception e){}
        //SSLSocketFactory fac = ctx.getSocketFactory();
        SSLServerSocketFactory fac = ctx.getServerSocketFactory();
        String[] list = fac.getDefaultCipherSuites();
        System.out.println("\nDefaultCipherSuites:");
        for (String s : list)
            System.out.println(s); 
        list = fac.getSupportedCipherSuites();
        System.out.println("\nSupportedCipherSuites:");
        for (String s : list)
            System.out.println(s); 
        System.out.println("\nAES max key length: " + Cipher.getMaxAllowedKeyLength("AES"));
    }
}
