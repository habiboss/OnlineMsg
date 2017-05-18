
import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import org.codehaus.jettison.json.JSONObject;
import service.service.LoginFacadeREST;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author UNKNOWN
 */
public class dbconnect {
        static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/onlinemsg";
    //  Database credentials
    static String USER = "root";
    static String PASS = "Allahis1";
public static void main(String[] args) {
    
  //  dbconnector connect = new dbconnector();
    //connect.getData();
 //  connect.countrecords("user", "habib");
   //LoginFacadeREST  hi = new LoginFacadeREST();
   
  // hi.getData();
   
         Connection conn;
        Statement stmt;
        String tablehead = " <table id='tbl' class='table table-striped'><thead><tr><th>ID</th><th>MSG</th><th>DATE</th><th>USER</th></tr></thead><tbody>";
        String result = "";
        int i = 0;
        int numberofrecords = 0;
        String [] data = new String[numberofrecords];
        int counter = 1;
        String tablebutton = "</tbody></table>";
        try{
        //JSONObject json = new  JSONObject(sh);
        //String users = json.getString("name");
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        String query = "Select id, msg, dates, user from message WHERE user = 'habib' ";
        ResultSet pr = stmt.executeQuery(query);
        while(pr.next())
        {
            //data[i] = pr.getString("msg");
            //result = result + data[i] ;
            //i++;
            //counter++;
            //out.println(pr.getString("msg"));
            //out.print(pr.getString("msg"));
            out.println("<h1>"+pr.getString("msg")+"</h1>");  
            //counter++;
        }
        //out.println(pr.getString("msg"));
         //pr.close();
         stmt.close();
         conn.close();
         
        }catch(Exception e){
     
        }
    
}



}
