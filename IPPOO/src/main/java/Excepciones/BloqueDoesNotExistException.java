
package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo Bloque no exista 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.2
 * @since 1.0
 */

public class BloqueDoesNotExistException extends Exception{
    private int numPlanPerteneciente;
    
    /**
     * Metodo que permite realizar la excepción
     * @param pNumPlanPerteneciente 
     */
    public BloqueDoesNotExistException(int pNumPlanPerteneciente){
        numPlanPerteneciente = pNumPlanPerteneciente;
    }
    
    public int getSemestreBloque(){
        return numPlanPerteneciente;
    }
    /**
     * Método para obtener el mensaje de error correspondiente a la excepción atrapada
     * @return el mensaje de error sobre que un bloque no existe 
     */
    public String mensajeError(){
        String msg = "";
        
        msg += "No existe un bloque asociado al plan de estudios numero" + numPlanPerteneciente;
        
        return msg;
    }
}
