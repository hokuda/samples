package sample;

import java.util.Properties;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class HelloWorld {

    @GET
    @Path("/search")
    @Produces({"text/plain"})
    public String getSearchResults() throws NamingException {
        InitialDirContext context = getDirContext();

        SearchControls ctrls = new SearchControls();
        String[] attrs = new String[] {"entrydn", "uid", "objectClass", "givenName", "sn", "memberOf"};
        ctrls.setReturningAttributes(attrs);
                          
        ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration<javax.naming.directory.SearchResult> searchResults =
            context.search("dc=redhat,dc=com", "(uid=*)", ctrls);

        String results = "";
        while (searchResults.hasMore()) {
            results += searchResults.next() + "\n";
        }

        return "result:\n" + results;
    }

    public InitialDirContext getDirContext() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        props.put(Context.PROVIDER_URL, "ldaps://localhost:10636");
        props.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
        props.put(Context.SECURITY_CREDENTIALS, "password");
        props.put("java.naming.ldap.factory.socket", "sample.MySSLSocketFactory");

        return new InitialDirContext(props);
    }

}
