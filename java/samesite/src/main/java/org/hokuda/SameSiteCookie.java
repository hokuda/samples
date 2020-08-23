package org.hokuda;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet("/cookie")
public class SameSiteCookie extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // set cookie
        response.setHeader("Set-Cookie", "key=value; HttpOnly; path=/; SameSite=lax");
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head></head><body>");
        out.println("<p>");
        
        Enumeration headernames = request.getHeaderNames();
        while (headernames.hasMoreElements()){
            String name = (String)headernames.nextElement();
            Enumeration headervals = request.getHeaders(name);
            while (headervals.hasMoreElements()){
                String val = (String)headervals.nextElement();
                out.println(name  + ":" + val + "<br>");
            }
        }
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        doGet(request, response);
    }

}
