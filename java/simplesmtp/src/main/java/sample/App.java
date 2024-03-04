package sample;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.net.ssl.SSLContext;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String SUPPORTED_SSL_PROTOCOLS = getSupportedSslProtocols();

    public static void main(String[] args) {
        App mailSend = new App();
        System.out.println(SUPPORTED_SSL_PROTOCOLS);
        mailSend.send("JavaMail test", "testsubject");
    }
    
    public void send(String subject, String content) {
        
        final String to = "xxx@example.com";
        final String from = "xxx@redhat.com";
        
        final String username = "sorry.this.is.test@redhat.com";
        final String password = "****";
        
        final String charset = "UTF-8";
        
        final String encoding = "base64";
        
        String host = "smtp.office365.com";
        String port = "587";
        String starttls = "true";
        
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", starttls);
        
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.timeout", "10000");
        
        //props.put("mail.smtp.ssl.protocols", SUPPORTED_SSL_PROTOCOLS);
        //props.put("mail.smtp.ssl.protocols", "TLSv1.3 TLSv1.2 TLSv1.1 TLSv1 SSLv3 SSLv2Hello");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.1 TLSv1 SSLv3 SSLv2Hello");
        //props.put("mail.smtp.ssl.protocols", "TLSv1.2 TLSv1.1 TLSv1");
        //props.put("mail.smtp.ssl.protocols", "TLSv1.3 TLSv1.2 TLSv1.1 TLSv1");

        
        props.put("mail.debug", "true");
        
        Session session = Session.getInstance(props,
                                              new javax.mail.Authenticator() {
                                                  protected PasswordAuthentication getPasswordAuthentication() {
                                                      return new PasswordAuthentication(username, password);
                                                  }
                                              });
        
        try {
            MimeMessage message = new MimeMessage(session);
            
            message.setFrom(new InternetAddress(from, "xxxx"));
            message.setReplyTo(new Address[]{new InternetAddress(from)});
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            
            message.setSubject(subject, charset);
            message.setText(content, charset);
            
            message.setHeader("Content-Transfer-Encoding", encoding);
            
            Transport.send(message);
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getSupportedSslProtocols() {
        try {
            String[] protocols = SSLContext.getDefault().getSupportedSSLParameters().getProtocols();
            System.out.println(java.util.Arrays.toString(protocols));
            if (protocols != null) {
                return String.join(" ", protocols);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
