/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.*;

/**
 *
 * @author UNKNOWN
 */
public class dbconnector {
    
    private Connection con;
    private Statement st;
    private ResultSet rs;
    
    public dbconnector() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinemsg", "root", "Allahis1");
            st = con.createStatement();
            
        } catch (Exception ex) {
            System.out.println("Error " + ex);
        }
    }
    public void getData() {
        
        try {
            
            String query = "Select * from login";
            rs = st.executeQuery(query);
            System.out.println("Records from Database");
            
            while(rs.next()) {
                String username = rs.getString("username");
                String password = rs.getString("password");
                //String message = rs.getString("message");
                System.out.println("username: " + username +" "+"password " + password +"");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
    
        public int countrecords (String columnName, String value){
                Connection conn = null;
                Statement stmt = null;
                int i = 0;
                try{ 
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinemsg", "root", "Allahis1");
                stmt = conn.createStatement();   
                 String sql = "select * from message where " + columnName + "='" + value + "'";
                 ResultSet rs = stmt.executeQuery(sql);
                  while(rs.next()){ 
                      i++;
               // String username = rs.getString("username");
               // String password = rs.getString("password");
                //String message = rs.getString("message");
                System.out.println("columnName: " + columnName +" "+"value " + value +"");
                  
                  } 
                 return i;  
                  }
        catch(Exception e){e.printStackTrace(); return 0;}
        
    }
        
        
        
  
}
