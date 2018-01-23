package service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import service.Login;



/**
 *
 * @author UNKNOWN
 */
@javax.ejb.Stateless
@Path("service.login")
public class LoginFacadeREST extends AbstractFacade<Login> {
    @PersistenceContext(unitName = "messagePU")
    private EntityManager em;

  /////////////////////////
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/onlinemsg";
    //  Database credentials
    static String USER = "root";
    static String PASS = "";

    public LoginFacadeREST() {
        super(Login.class);
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
 /////////////////////////////////////////////SAME MESSAGE
 @POST
 @Consumes({"text/plain"})
    public String addmessage (String data) {
       Connection conn = null;


       java.util.Date date = new java.util.Date();
       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
       String formattedDate = sdf.format(date);
       try
        {
       Statement st = null;
       JSONObject json = new  JSONObject(data);

       String user = json.getString("name");
       String msg = json.getString("message");
       String type = json.getString("privacy");
       String toUser = json.getString("toUser");
       Class.forName(JDBC_DRIVER);
       conn = DriverManager.getConnection(DB_URL,USER,PASS);
       st = conn.createStatement();



       String query = "INSERT INTO message(msg, user, toUser, type, dates) VALUES('" + msg + "', '" + user + "', '" + toUser + "', '" + type + "', '" + formattedDate + "')";
       st.executeUpdate(query);

           st.close();
           conn.close();

            return "";

        } catch (Exception e)
        {

            e.printStackTrace();
            return "Sorry Message was not Saved"+e;

        }
  }
 /////////////////////////////////////////////////RETRIEVE MESSAGE
    @GET
    @Path("{id}/{rand}")
    @Produces({"text/html"})
    public String getmessages(@PathParam("id") String id, @PathParam("rand") String rand) {
                     String sql;
                     Connection conn = null;
                     Statement stmt = null;
                     int numberofrecords = 0;
                     String tablehead="";
                     String result ="";
                     String details = "";
                     System.out.println(id);
                     String tablebuttom = "</tbody></table>";

                      try{
                         Class.forName(JDBC_DRIVER);
                         conn = DriverManager.getConnection(DB_URL,USER, PASS);
                        stmt = conn.createStatement();

                        if("public".equals(id)){
                        sql = "select msg, user, type, dates from message where type ='"+id+"' order by dates DESC";
                        ResultSet rs = stmt.executeQuery(sql);
                        numberofrecords = this.countrecords("type",id);
                        String [] data = new String[numberofrecords];
                        int i = 0;
                        int counter=1;
                        while (rs.next())
                        {
                         data[i] =  "<tr><td>"+counter+"</td> <td>"+rs.getString("dates")+"</td><td>"+rs.getString("user")+"</td><td>"+rs.getString("msg")+"</td><td>"+rs.getString("type")+"</td></tr>";

                         result = result +data[i];     //adding up the string to a single string
                         i++;
                          counter++;
                         }
                          tablehead = " <table id = \"maintable\" class=\"table table-striped\">  <thead id = \"tablehead\" ><tr><th>S/N</th><th>Date/Time</th><th>User</th><th>Message</th><th>Type</th></tr></thead><tbody  id = \"tablebody\" >";
                         conn.close();
                         stmt.close();
                         rs.close();
                        }else{
                            ////Display private MSG
                           // sql = "select id, msg, type, dates from message where user ='"+id+"' or toUser ='"+id+"' order by dates DESC";
                            sql = "select id, msg, type, user, toUser, dates from message where toUser ='"+id+"' order by dates DESC";
                            ResultSet rs = stmt.executeQuery(sql);
                            numberofrecords = this.countrecords("user",id);
                            String [] data = new String[numberofrecords];
                            int i = 0;
                            int counter=1;
                            while (rs.next())
                            {
                           details +=  "<tr class =\"datarow\"> <td>"+counter+"</td> <td  id=\"id\">"+rs.getString("id")+"</td>"+"<td  id=\"id\">"+rs.getString("user")+"</td>"+""+"<td  id=\"id\">"+rs.getString("toUser")+"</td>"+"<td>"+rs.getString("dates")+"</td><td class = \"messagecel\">"+rs.getString("msg")+"</td><td class = \"privacycell\">"+rs.getString("type")+"</td><td>"
                             + "<div class=\"btn-group\" data-toggle=\"buttons\"><label class=\"btn-default\" ><input class = \"public\" type=\"radio\" name=\"options\" "
                             + "  id=\"option1\">Public</label><label class=\"btn-success\" ><input class = \"private\"type=\"radio\" name=\"options\" id=\"option2\">Private</label></div></td><td><label > <input id = \"editbutton\"  type=\"button\" class=\"edit\" value=\"Update\" name=\"Edit \" /></label></td><td><label > <input id = \"deletebutton\"  type=\"button\" class=\"btn-warning\" value=\"Remove\" name=\"Delete \" /></label></td></tr>";

                            result = result +details;     //adding up the string to a single string
                            i++;
                            counter++;
                             }
                             tablehead = " <table id = \"maintable\"  class=\"table table-striped\">  <thead id = \"tablehead\" ><tr><th>S/N</th><th>ID</th><th>From</th><th>TO</th><th>Date/Time</th><th>Message</th><th>Type</th><th> Change State </th><th> Edit </th><th>Remove</th></tr></thead> <tbody  id = \"tablebody\"  onmouseover = \"processtable()\">";
                             conn.close();
                         stmt.close();
                         rs.close();
                             }
                           }

                            catch (Exception e){e.printStackTrace(); }
                            return tablehead+result+tablebuttom;
    }
    //////////////////////////

    @GET
    @Path("/search/{users}")
    @Produces(MediaType.APPLICATION_JSON)
    public String searche(@PathParam("search") String users){
        Connection conn;
        JSONObject json_role;
        JSONObject json = new JSONObject();
        JSONArray role = new JSONArray();
        String result = "";
        String x = users;
        try{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM message WHERE user = 'habib' ");
        //stmt.setString(1, x);
        ResultSet rs = stmt.executeQuery();
        while(rs.next())
        {
           json_role = new JSONObject();
           json_role.put("Id", rs.getString("id"));
           json_role.put("Msg", rs.getString("msg"));
           json_role.put("Date", rs.getString("dates"));
           json_role.put("User", rs.getString("user"));
           role.put(json_role);
        }
         result += json.put("searchResult", role);
         conn.close();
         stmt.close();
         rs.close();
        }catch(Exception e){

        }
        return result;
    }


///////////////////////////////////////////////////
    @PUT
      public void edit(String data ) {
            Connection conn;
             try{
             Statement prpsmt = null;
             JSONObject json = new  JSONObject(data);
             String msg = json.getString("message");
             String type = json.getString("privacy");
             String currentid = json.getString("currentid");
             conn = DriverManager.getConnection(DB_URL,USER, PASS);
             prpsmt = conn.createStatement();
             String Query = "UPDATE message SET type = '"+type+"' where msg = '"+msg+"' and id = '"+currentid+"' ";
             prpsmt.executeUpdate(Query);
             conn.close();
             prpsmt.close();
               }catch (Exception e){e.printStackTrace();}

        }
//////////////////////////////////////////////////////////////
       @PUT
       @Consumes({"text/plain"})
       public String edit_msg(String data ) {
       Connection conn = null;
       java.util.Date date = new java.util.Date();
       SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy h:mm:ss a");
       String formattedDate = sdf.format(date);
       try
        {
       Statement st = null;
       JSONObject json = new  JSONObject(data);
       String currentid = json.getString("currentid");
       String msg = json.getString("message");
       String type = json.getString("privacy");
       Class.forName(JDBC_DRIVER);
       conn = DriverManager.getConnection(DB_URL,USER,PASS);
       st = conn.createStatement();
       String query = "UPDATE message SET msg = '" + msg + "', type = '" + type + "', dates = '" + formattedDate + "' WHERE id = '"+currentid+"' ";
       st.executeUpdate(query);
       st.close();
       conn.close();
       return "";

        } catch (Exception e)
        {

            e.printStackTrace();
            return "Sorry Message was not updated"+e;

        }
  }

////////////////////////////////////////////////////DELETE MESSAGE
        @DELETE
        public String remove( String data) {

                 Connection conn;
                 PreparedStatement prestmt;
                 try{
                    JSONObject json = new  JSONObject(data);
                    String msg = json.getString("message");
                    Class.forName(JDBC_DRIVER);
                    conn = DriverManager.getConnection(DB_URL,USER, PASS);
                    prestmt = conn.prepareStatement("delete from message where msg= ?");
                    prestmt.setString(1,msg);
                    prestmt.executeUpdate();
                    prestmt.close();
                    conn.close();
                     return "Message deleted";
                     } catch (Exception e){e.printStackTrace(); return "Message could not delete"+e;}

    }
    /////////////////////////
    @GET
    @Path("/getUsers/{id}/{rand}")
    @Produces({"text/html"})
    public String getUser(String users) {
        Connection conn;
        Statement stmt;
        String selectHead = " <select>";
        String result = "";
        String x = users;
        int counter = 1;
        String selectbutton = "</select>";
        String data = "";
        try{
        Class.forName(JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
        String query = "SELECT id, username FROM login";
        ResultSet pr = stmt.executeQuery(query);

        while(pr.next())
        {
            int id = pr.getInt("id");
            String user = pr.getString("username");
            data = user;
            result = result + data;
            counter++;

        }
         conn.close();
         stmt.close();
         pr.close();

        }catch(Exception e){

        }
        return selectHead+result+selectbutton;
    }
    //////////////////////////////////

         public int countrecords (String columnName, String value){
                Connection conn = null;
                Statement stmt = null;
                int i = 0;
                try{
                Class.forName(JDBC_DRIVER);
                conn = DriverManager.getConnection(DB_URL,USER, PASS);
                stmt = conn.createStatement();
                String sql = "select msg, user, toUser, type, dates from message where " + columnName + "='" + value + "'";

                 ResultSet rs = stmt.executeQuery(sql);
                  while(rs.next()){ i++;}
                  conn.close();
                  stmt.close();
                 return i;
                  }
        catch(Exception e){e.printStackTrace(); return 0;}

    }




}
