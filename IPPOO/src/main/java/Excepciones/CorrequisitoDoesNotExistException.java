package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo Curso, correquisito de otro objeto tipo Curso, no exista 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.0
 * @since 1.0
 */

public class CorrequisitoDoesNotExistException extends Exception{
    private String codCurso;
    
    public CorrequisitoDoesNotExistException(String pCodCurso){
        codCurso = pCodCurso;
    }
    
    public String getNumeroDePlan(){
        return codCurso;
    }
    
    public String mensajeError(){
        String msg="";
        
        msg += "No existen correquisitos asociados al curso de código " + codCurso;
        
        return msg;
    }     
}
