/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author 3MI
 */
public class Helper {
       
       // String user="root";
        //String passwd="";
        //String base="BOURSE";
        public Connection getConnection()
        {
            
            Connection connect=null;

            try 
            {
                 // This will load the MySQL driver, each DB has its own driver
                //Class.forName("com.mysql.jdbc.Driver");
                Class.forName("org.postgresql.Driver");
      		  System.out.println("Driver O.K.");
      		  
      		 // String url = "jdbc:postgresql://localhost:5432/baseetudiant";
      		  //String user = "useretu";
      		  //String passwd = "1234";
      		  String base="d95tfsaeju6b18";
      		String url = "ec2-54-73-147-133.eu-west-1.compute.amazonaws.com/"+base;
    		  String user = "rkieoczhzltzfc";
    		  String passwd = "828cda5b3c4d8bdd2c17a304956887e280ccab9b7da665af6fd3f780701c1d3d";
    		  
                // Setup the connection with the DB
                connect = DriverManager.getConnection(url, user, passwd);
                System.out.println("mandeha");
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
            catch (ClassNotFoundException ex) 
            {
                ex.printStackTrace();
            }
            return connect;
            }
}