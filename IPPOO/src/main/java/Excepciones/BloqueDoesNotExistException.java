
package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo Bloque no exista 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.2
 * @since 1.0
 */

public class BloqueDoesNotExistException extends Exception{
    private int numPlanPerteneciente;
    
    public BloqueDoesNotExistException(int pNumPlanPerteneciente){
        numPlanPerteneciente = pNumPlanPerteneciente;
    }
    
    public int getSemestreBloque(){
        return numPlanPerteneciente;
    }
    
    public String mensajeError(){
        String msg = "";
        
        msg += "No existe un bloque asociado al plan de estudios numero" + numPlanPerteneciente;
        
        return msg;
    }
}
