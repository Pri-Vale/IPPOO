package Controlador;

import Connect_BD.Consultas_BaseDatos;
import Modelo.logicaDeNegocio.Escuela;
import java.sql.SQLException;

/**
 *
 * @author pri23
 */
public class Controlador {
    
    //Atributos 
    private final Escuela escuela= new Escuela();
    public static Consultas_BaseDatos salidaControlador =new Consultas_BaseDatos() ;
    
    public boolean crearEscuela(String pNombreEscuela, String pCodEscuela) throws SQLException{
        try{
        Escuela escuela= new Escuela(pNombreEscuela, pCodEscuela);
            System.out.println(pNombreEscuela);
            System.out.println(pCodEscuela);
        salidaControlador.insertarEscuela(escuela);
        System.out.println("A");
        return true;
        
        }catch(NullPointerException a){
          return false;  
        }                    
        
    }
}    