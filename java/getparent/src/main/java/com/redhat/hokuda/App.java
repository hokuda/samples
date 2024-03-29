package com.redhat.hokuda;

//import com.redhat.hokuda.internal.App;

import org.drools.core.common.ProjectClassLoader;

import java.net.URI;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ApplicationPath("/")
@Path("/")
public class App extends javax.ws.rs.core.Application {
    @GET
    @Path("/lax")
    @Produces({ "text/html" })
    public Response lax() {
        return Response.ok()
            .entity("hoge\n")
            .header("Set-Cookie", "laxcookie=value; HttpOnly; path=/; SameSite=Lax")
            .build();
    }

    @GET
    @Path("/none")
    @Produces({ "text/html" })
    public Response none() {
        return Response.ok()
            .entity("hoge\n")
            .header("Set-Cookie", "nonecookie=value; HttpOnly; path=/; SameSite=None")
            .build();
    }

    @GET
    @Path("/strict")
    @Produces({ "text/html" })
    public Response strict() {
        return Response.ok()
            .entity("hoge\n")
            .header("Set-Cookie", "strictcookie=value; HttpOnly; path=/; SameSite=strict")
            .build();
    }

    @GET
    @Path("/toplevel")
    @Produces({ "text/html" })
    public Response toplevel(@Context UriInfo uriInfo) {
        String target = "test.example.com";
        URI uri = uriInfo.getBaseUri();
        System.out.println(uri);
        String hostname = uri.getHost();
        if (!hostname.equals(target)) {
            try {
                return Response.temporaryRedirect(new URI("http://" + target + ":8080/samesite/toplevel")).build();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        String body = "<html><body>hi<br>"
            + "<a href=http://localhost:8080/samesite/none>none</a><br>"
            + "<a href=http://localhost:8080/samesite/lax>lax</a><br>"
            + "<a href=http://localhost:8080/samesite/strict>strict</a><br>"
            + "<a href=http://localhost:8080/samesite/toplevel>toplevle</a><br>"
            + "</body></html>";
        return Response.ok().entity(body).build();
    }

    @GET
    @Path("/getparent")
    @Produces({ "text/html" })
    public Response getparent() {
        //ClassLoader loader = this.getClass().getClassLoader();
        ClassLoader loader = ClassLoader.getSystemClassLoader();
        //ClassLoader loader = ClassLoader.getPlatformClassLoader();
        //ClassLoader loader = ProjectClassLoader.createProjectClassLoader();
        System.out.println(loader);
        try {
            loader.loadClass("com.sun.crypto.provider.DESKey");
            //loader.loadClass("javax.security.auth.PrivateCredentialPermission");
            //loader.loadClass("org.slf4j.helpers.Util");
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return Response.ok().entity(loader.getParent().toString()).build();
    }
    
    /*    @GET
    @Path("/internal")
    @Produces({ "text/html" })
    public Response internal() {
        return Response.ok().entity(
                                    (new com.redhat.hokuda.internal.App()).doGetParent()
                                    ).build();
                                    }*/
    
}
