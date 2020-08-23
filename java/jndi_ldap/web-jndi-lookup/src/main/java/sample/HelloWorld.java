package sample;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.annotation.Resource;

import javax.naming.directory.InitialDirContext;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

@Path("/")
public class HelloWorld {

    @GET
    @Path("/search")
    @Produces({ "text/plain" })
    public String getSearchResults() throws NamingException{
        InitialDirContext context = (InitialDirContext) new InitialContext().lookup("java:global/ldapContext");
        
        SearchControls ctrls = new SearchControls();
        ctrls.setReturningAttributes(new String[] { "entrydn", "uid", "objectClass", "givenName", "sn","memberOf" });
        ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration<javax.naming.directory.SearchResult> searchResults = context.search("dc=redhat,dc=com", "(uid=*)", ctrls);

        String results = "";
        while (searchResults.hasMore()) {
            results += searchResults.next() + "\n";
        }

        return "result:\n" + results;
    }
}
