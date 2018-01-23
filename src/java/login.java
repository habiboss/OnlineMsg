/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.sql.*;

/**
 *
 * @author UNKNOWN
 */
public class login extends HttpServlet {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    
       public login() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
          //  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinemsg", "root", "");
           // st = con.createStatement();
            
        } catch (Exception ex) {
            System.out.println("Error " + ex);
        }
    }
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
     
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////    
         String username = request.getParameter("username");
        String password = request.getParameter("password");
       // String id = null;
      //  String userID = null;
       // String db_username = null;
       // String db_password = null;
        
        try {
            
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinemsg", "root", "");
            st = con.createStatement();
            
             String query = "SELECT id, username, password FROM login WHERE username ='" + username + "' AND password = '" + password + "'";
         //   ResultSet sql =  st.executeQuery(query);
           
          //  System.out.println("Records from Database");
         
            
        
            
        Statement statement = null;
        ResultSet resultSet = null;
        String userName = null;
 
          statement = con.createStatement();
          resultSet = statement
              .executeQuery("SELECT username, password FROM login WHERE username ='" + username + "' AND password = '" + password + "'");
          if (resultSet.next())
            userName = resultSet.getString(1); 
          out.println("Hello " + userName + "!<p>");
    
        } catch (SQLException sqle) {
            throw new ServletException("Database Error", sqle);
        } 
        finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException sqle) {
                }
            }
        } 
        
  /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////      
         HttpSession session = request.getSession(true);
      // Get session creation time.
      Date createTime = new Date(session.getCreationTime());
      // Get last access time of this web page.
      Date lastAccessTime = 
                        new Date(session.getLastAccessedTime());

      String title = "Welcome Back to my website";
      Integer visitCount = new Integer(0);
      String visitCountKey = new String("visitCount");
      String userIDKey = new String("userID");
      String userId = new String(username);

      if (session.isNew()){
         title = "Welcome to my website";
         session.setAttribute(userIDKey, userId);
      } else {
         visitCount = (Integer)session.getAttribute(visitCountKey);
         visitCount = visitCount + 1;
         userId = (String)session.getAttribute(userIDKey);
      }
      session.setAttribute(visitCountKey,  visitCount);

      // Set response content type
      response.setContentType("text/html");
     // PrintWriter outs = response.getWriter();

      String docType =
      "<!doctype html public \"-//w3c//dtd html 4.0 " +
      "transitional//en\">\n";
      out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                 "<h2 align=\"center\">Session Infomation</h2>\n" +
                "<table border=\"1\" align=\"center\">\n" +
                "<tr bgcolor=\"#949494\">\n" +
                "  <th>Session info</th><th>value</th></tr>\n" +
                "<tr>\n" +
                "  <td>id</td>\n" +
                "  <td>" + session.getId() + "</td></tr>\n" +
                "<tr>\n" +
                "  <td>Creation Time</td>\n" +
                "  <td>" + createTime + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>Time of Last Access</td>\n" +
                "  <td>" + lastAccessTime + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>User ID</td>\n" +
                "  <td>" + userId + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>Number of visits</td>\n" +
                "  <td>" + visitCount + "</td></tr>\n" +
                "</table>\n" +
                "</body></html>");
        
        
        
        
        
        
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
