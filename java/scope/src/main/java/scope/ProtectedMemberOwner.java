package scope;

/**
 * Hello world!
 *
 */
public class ProtectedMemberOwner extends PrivateMemberOwner
{
    protected String privateMember="protected";
    
    public static void main( String[] args )
    {
        ProtectedMemberOwner priv = new ProtectedMemberOwner();

        System.out.println(priv.getMember());
    }
}
