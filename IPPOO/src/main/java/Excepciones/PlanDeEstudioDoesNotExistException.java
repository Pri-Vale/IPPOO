
package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo PlanDeEstudio no exista 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.2
 * @since 1.0
 */

public class PlanDeEstudioDoesNotExistException extends Exception{
    private String nombreEscuela;
    
    public PlanDeEstudioDoesNotExistException(String pNombreEscuela){
        nombreEscuela = pNombreEscuela;
    }
    
    public String getNumeroDePlan(){
        return nombreEscuela;
    }
    /**
     * Método para obtener el mensaje de error correspondiente a la excepción atrapada
     * @return el mensaje de error sobre un plan de estudio que no existe
     */
    public String mensajeError(){
        String msg="";
        
        msg += "No existe un plan de estudio asociado a la escuela" + nombreEscuela;
        
        return msg;
    }  
}
