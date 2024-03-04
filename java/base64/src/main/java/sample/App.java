package sample;

/**
 * Hello world!
 *
 */

import java.util.Base64;

public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println( "base6 encoded: " + new String(Base64.getEncoder().encode("user1:password".getBytes())));
    }
}
