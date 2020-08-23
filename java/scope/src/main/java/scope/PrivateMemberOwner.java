package scope;

/**
 * Hello world!
 *
 */
public class PrivateMemberOwner
{
    private String privateMember="private";
    
    public static void main( String[] args )
    {
        PrivateMemberOwner priv = new PrivateMemberOwner();

        System.out.println(priv.getMember());
    }

    public String getMember()
    {
        return privateMember;
    }
}
