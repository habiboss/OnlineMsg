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
import javax.servlet.http.Cookie;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author UNKNOWN
 */
public class select extends HttpServlet {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    
       public select() {
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            
        } catch (Exception ex) {
            System.out.println("Error " + ex);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        
//////////////////////////////////////////////////////////////////////////////////
   
         String username = request.getParameter("username");
        String password = request.getParameter("password");
 
        try {

            String salt = "Random$SaltValue#WithSpecialCharacters12@$@4&#%^$*";
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/onlinemsg", "root", "Allahis1");
            st = con.createStatement();
            
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Statement statement;
        ResultSet resultSet;
 
           statement = con.createStatement();
           resultSet = statement
              .executeQuery("SELECT username, password FROM login WHERE username ='" + username + "' AND password = '" + MD5(password + salt) + "'");
          if (resultSet.next()){
           HttpSession session = request.getSession();
           session.setAttribute("user", username);
           out.print("<input type=\"hidden\" id=\"welcome\" value="+ session.getAttribute("user") + ">");
           RequestDispatcher rd = request.getRequestDispatcher("Home_Page.html");
           rd.include(request, response);
          } else  {
           out.println("<script type=\"text/javascript\">");  
           out.println("alert('Sorry: Wrong username or password');");  
           out.println("</script>");
           RequestDispatcher rd = request.getRequestDispatcher("index.html");
           rd.include(request, response);
          }
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
 
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////    
    }
 private String MD5(String password) {
     // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

     String md5 = null;
         
        if(null == password) return null;
         
        try {
             
        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
         
        //Update input string in message digest
        digest.update(password.getBytes(), 0, password.length());
 
        //Converts message digest value in base 16 (hex) 
        md5 = new BigInteger(1, digest.digest()).toString(16);
 
        } catch (NoSuchAlgorithmException e) {
 
            e.printStackTrace();
        }
        return md5;
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
