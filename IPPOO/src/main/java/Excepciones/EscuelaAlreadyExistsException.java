
package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo Escuela ya exista 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.1
 * @since 1.0
 */

public class EscuelaAlreadyExistsException extends Exception{
    private String codEscuela;
    
    public EscuelaAlreadyExistsException(String pCodEscuela){
        codEscuela = pCodEscuela;
    }
    
    public String getSemestreBloque(){
        return codEscuela;
    }
    /**
     * Método para obtener el mensaje de error correspondiente a la excepción atrapada
     * @return el mensaje de error sobre una escuela que no existe
     */
    public String mensajeError(){
        String msg = "";
        
        msg += "Ya existe registrada una escuela de código " + codEscuela;
        
        return msg;
    }    
}
