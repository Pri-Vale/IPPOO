/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connect_BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author pri23
 */
public class Conexion {
    public static Connection conect;

    public Conexion() {
          
    }

    public static void setConect(Connection conect) {
        Conexion.conect = conect;
    }

    public static Connection getConect() {
        return conect;
    }
   /*String IP, String Puerto, String BDname, String USER, String password*/
public Connection Conectar_a_base(){
    try{
        
    String conectionURL= "jdbc:sqlserver://;database=GestorPlanesDeEstudio;integratedSecurity=true;";
    conect = DriverManager.getConnection(conectionURL);
        System.out.println("Hola");
        System.out.println("Hola Mundo Hola Mundo");
    }
    catch (SQLException ex){
        System.out.println("Error.");
        System.out.println("Error.");
    } 
    return conect;
}

public void Cerrar_la_conexion(){
    try{
        conect.close();
        
    }
    catch(SQLException ex){
        System.out.println("No se ha podido cerrar la conexion");
    }
}  
}
