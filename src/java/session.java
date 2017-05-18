/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author UNKNOWN
 */
public class session extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String data)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
       
        HttpSession sessions = request.getSession(false);
    
        try{
        JSONObject json = new  JSONObject(data);
        String session = json.getString("session");
        
        if (null == session){
           out.println("<script type=\"text/javascript\">");  
           out.println("alert('Your Session is invalid: \" + \" Please Log in First');");  
           out.println("</script>");
           RequestDispatcher rd = request.getRequestDispatcher("index.html");
           rd.include(request, response);
        }
        

        }catch(Exception e){
     
        }
    }
}