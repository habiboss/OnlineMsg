import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static javax.ws.rs.client.Entity.json;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author UNKNOWN
 */
public class search extends HttpServlet {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/onlinemsg";
    //  Database credentials
    static String USER = "root";
    static String PASS = "Allahis1";
    private Statement st; 
    
    
    public String searches (String sh){
         
        
        return sh;
       }
    
    
    
    
    protected String processRequest(HttpServletRequest request, HttpServletResponse response, String users)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
      
        
         Connection conn;
        Statement stmt;
        String tablehead = " <table id='tbl' class='table table-striped'><thead><tr><th>ID</th><th>MSG</th><th>DATE</th><th>USER</th></tr></thead><tbody>";
        String result = "";
        String x = users;
        int counter = 1;
        String tablebutton = "</tbody></table>";
        String data = "";
        try{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        String query = "SELECT id, msg, user, dates from message WHERE user = ' "+x+"' ";
        ResultSet pr = stmt.executeQuery(query);
        while(pr.next())
        {
           int id = pr.getInt("id");
            String msg = pr.getString("msg");
            String dates = pr.getString("dates");
            String user = pr.getString("user");      
            data = "<tr class =\"datarow\"> <td>"+id+"</td>"+"<td>"+msg+"</td>"+"<td>"+dates+"</td>"+"<td>"+user+"</td></tr>"; 
            result = result + data;
            counter++;
        }
         conn.close();
         stmt.close();
         pr.close();
          
        }catch(Exception e){
     
        }
        return tablehead+result+tablebutton;
    }
}