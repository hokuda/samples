public class Finally{
    
    public static void main(String[] args) {
        Finally _finally = new Finally();
        System.out.println("Hello World!!");

        _finally.doFinally();
    }
    
    public void doFinally() {

        try {
            System.out.println("let's do something!!");
            if (System.getProperty("throw.exception") != null) {
                throw new Exception("thrown!!!!");
            }
            return;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("finally!");
        }
    }
}

