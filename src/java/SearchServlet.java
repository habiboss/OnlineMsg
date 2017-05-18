import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import org.codehaus.jettison.json.JSONObject;



public class SearchServlet extends HttpServlet {
public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{


String search = request.getParameter("search");
response.setContentType("text/html");

if(!((search.trim()).equals(""))){
String searchString = getSearchResult(search);
response.getWriter().write(searchString);
}
else
response.getWriter().write("");
}
public String getSearchResult(String search) {
Connection conn = null;
Statement st=null;
ResultSet res=null;
String finalSearch = "";

try{
    JSONObject json = new  JSONObject(search);
        String users = json.getString("name");
Class.forName("com.mysql.jdbc.Driver");
conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinemsg","root","Allahis1");
st=conn.createStatement();
String s = "SELECT user from message WHERE user = '"+search+"%'ORDER BY user";
res = st.executeQuery(s) ;
while (res.next())
{
String un = res.getString(1);
finalSearch+= un+"\n";

}
st.close();
conn.close();
}catch(Exception e){}
return finalSearch;
}
}