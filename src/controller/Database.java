package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database{
        public static Connection connectDB(){
            try{
                Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/employes", "root", "");           
                return connection;
            } 
            catch(SQLException e){
                System.out.println("il ya un probleme au niveau de la connection au base de donnees"+e.getMessage());
            return null;
            }            
          }
}
