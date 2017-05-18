/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.persistence.EntityManager;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import service.Login;

/**
 *
 * @author Habib-AdvinTeck
 */
@javax.ejb.Stateless
@Path("service.message")
public class MessageRest {

    private EntityManager em;

    /////////////////////////
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/onlinemsg";
    //  Database credentials
    static String USER = "root";
    static String PASS = "Allahis1";

    @GET
    @Path("/getUsers")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUserNames() {
        JSONObject json_role;
        JSONObject json = new JSONObject();
        JSONArray role = new JSONArray();
        String details = "";
        Connection conn;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            PreparedStatement stmt = conn.prepareStatement("select * FROM login");
            ResultSet rs = stmt.executeQuery();
            JSONObject json_staff_id = new JSONObject();
            while (rs.next()) {
                json_role = new JSONObject();
                json_role.put("UserName", rs.getString("username"));
   
                role.put(json_role);
            }
            details += json.put("Login", role);
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.out.print(e);
            System.out.println(e.getMessage());
        }
        return details;
    }
}
