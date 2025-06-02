/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jdbc_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

/**
 *
 * @author ACTIVE
 */
public class ConnectWithDB {
    private static String URL = "jdbc:sqlserver://localhost:1433;databaseName=test;encrypt=true;trustServerCertificate=true";
    private static String USERNAME="user";
    private static String PASSWORD = "123";

    private static Connection con =null;
    private static Statement stat =null;
    private static ResultSet rs =null;
    private static PreparedStatement prepstat=null;
    
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }
    
    
    public static Connection connectDB() throws SQLException
    {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
         
    }
    
    public static void colseconnect() throws SQLException
    {
        if(rs != null)
        { rs.close(); } 

        if(stat != null)
        { stat.close(); } 

        if(prepstat != null)
        {  prepstat.close();} 

        if(con != null)
        { con.close(); }
         
    }
    
}
