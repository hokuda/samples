package com.redhat.hokuda.sample;

import java.io.IOException;
import javax.servlet.ServletException;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.valves.ValveBase;

public class Valve extends ValveBase{
    
    @Override
    public void invoke(Request request, Response response) throws IOException, ServletException {
        before(request, response);
        getNext().invoke(request, response); // The filter and servlet will be executed within this context.
        after(request, response);
    }
    
    private void before(Request request, Response response) throws IOException, ServletException {
        response.addHeader("CustomValveHeader", "=== custom value added by custom valve ===");
        System.err.println("Do something before filters");
    }
    
    private void after(Request request, Response response) throws IOException, ServletException {
        System.err.println("Do something after filters");
    }
}
