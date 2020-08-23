package sample;

import java.util.Enumeration;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class LdapClient {
    public static void main(String[] args) {

    	LdapClient client = new LdapClient();
    	
    	try {
            NamingEnumeration<SearchResult> result = client.search();
    		while (result.hasMore()) {
    			System.out.println(result.next());
    		}
    	}
    	catch(Exception e) {e.printStackTrace();}
    }
    
    public NamingEnumeration<SearchResult> search() throws NamingException{
    	InitialDirContext dirContext = getDirContext();

    	SearchControls ctrls = new SearchControls();
    	ctrls.setReturningAttributes(new String[] { "entrydn", "uid", "objectClass", "givenName", "sn","memberOf" });
    	ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

    	NamingEnumeration<javax.naming.directory.SearchResult> answers = dirContext.search("dc=redhat,dc=com", "(uid=*)", ctrls);
    	return answers;
    }

    public InitialDirContext getDirContext() throws NamingException{
    	Properties props = new Properties();
    	props.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
    	props.put(Context.PROVIDER_URL, "ldaps://localhost:10636");
    	props.put(Context.SECURITY_PRINCIPAL, "cn=Directory Manager");
    	props.put(Context.SECURITY_CREDENTIALS, "password");

    	InitialDirContext context = new InitialDirContext(props);
    	return context;
    }
}
