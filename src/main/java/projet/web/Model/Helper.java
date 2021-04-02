/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.web.Model;

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
      		  
      		  String url = "jdbc:postgresql://localhost:5432/telephonie";
      		  String user = "etu";
      		  String passwd = "1234";
      		  
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
        public Connection getConnectionHeroku()
        {
            
            Connection connect=null;

            try 
            {
                 // This will load the MySQL driver, each DB has its own driver
                //Class.forName("com.mysql.jdbc.Driver");
              Class.forName("org.postgresql.Driver");
      		  System.out.println("Driver O.K.");
      		  String base="dbqiomboalisuu";
      		  String url = "jdbc:postgresql://ec2-54-73-147-133.eu-west-1.compute.amazonaws.com:5432/"+base;
    		  String user = "ppuswbnvodawvv";
    		  String passwd = "50f031c8009af390b938e54d7319bd2770440d7557386b99c30373069e57d8d7";
    		  
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