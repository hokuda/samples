package sample;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.PrintWriter;
import java.io.IOException;

public class DSServlet extends HttpServlet{
    private DataSource dataSource=null;
    String dataSourceName = "java:jboss/datasources/ExampleDS";
    
    public void init() throws ServletException{
        try{
            Context context=new InitialContext();
            dataSource=(DataSource)context.lookup(dataSourceName);
        }catch(NamingException e){
            throw new ServletException(e);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException{
        response.setContentType("text/plain; charset=UTF-8");
        PrintWriter writer=response.getWriter();
        
        Connection conn=null;
        try{
            conn=dataSource.getConnection();

            try {
                Statement dropStatement = conn.createStatement();
                dropStatement.executeUpdate("DROP TABLE TAB1");
                writer.println("dropped");
            }
            catch(SQLException e){
                //e.printStackTrace(writer);
                writer.println("table does not exist");
            }
            
            Statement createStatement = conn.createStatement();
            createStatement.executeUpdate("CREATE TABLE TAB1 (ID INTEGER, WORD VARCHAR(20))");
            writer.println("created");

            PreparedStatement insert = conn.prepareStatement ("INSERT INTO TAB1 (ID, WORD) VALUES (?, ?)");
            insert.setInt(1, 1);
            insert.setString(2, "www");
            insert.executeUpdate();
            writer.println("inserted");

            PreparedStatement select = conn.prepareStatement("SELECT * FROM TAB1");
            ResultSet results = select.executeQuery();
            while(results.next()){
                writer.println(results.getInt(1) + ":" + results.getString(2));
            }
        }catch(SQLException e){
            e.printStackTrace(writer);
        }finally{
            try{
                if(conn!=null) conn.close();
            }catch(SQLException e){}
        }
    }
}
