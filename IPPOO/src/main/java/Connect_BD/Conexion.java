package Connect_BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase conexión para realizar la conexión a la base de datos
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.1
 * @since 1.0
 */
public class Conexion {
    public static Connection conect;

    /**
     * Constructor de la clase Conexion
     */
    public Conexion() {
          
    }

    public static void setConect(Connection conect) {
        Conexion.conect = conect;
    }

    public static Connection getConect() {
        return conect;
    }
   
    
    /**
     * Metodo que permite realizar la conexión a la base
     * @return Connection, objeto que contiene la conexión activa
     */
    public Connection Conectar_a_base(){
        try{

        String conectionURL= "jdbc:sqlserver://;database=GestorPlanesDeEstudio;integratedSecurity=true;";
        conect = DriverManager.getConnection(conectionURL);
        System.out.println("Conexion con base exitosa");
        }
        catch (SQLException ex){
            System.out.println("Error.");
        } 
        return conect;
    }

    /**
     * Método para cerrar la conexión con la base de datos
     * 
     */
    public void Cerrar_la_conexion(){
        try{
            conect.close();

        }
        catch(SQLException ex){
            System.out.println("No se ha podido cerrar la conexion");
        }
    }  
}
