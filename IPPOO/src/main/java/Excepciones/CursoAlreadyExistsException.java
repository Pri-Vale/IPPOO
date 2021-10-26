
package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo Bloque no exista 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.1
 * @since 1.0
 */

public class CursoAlreadyExistsException extends Exception{
    private String codCurso;
    
    public CursoAlreadyExistsException(String pCodCurso){
        codCurso = pCodCurso;
    }
    
    public String getCodigoDeCurso(){
        return codCurso;
    }
    
    public String mensajeError(){
        String msg = "";
        
        msg += "Ya existe registrado un curso de código " + codCurso;
        
        return msg;
    }       
}
