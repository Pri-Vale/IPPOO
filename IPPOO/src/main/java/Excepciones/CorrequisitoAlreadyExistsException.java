package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un correquisito ya esté asociado a un curso 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.1
 * @since 1.0
 */
public class CorrequisitoAlreadyExistsException extends Exception{
    private String codCurso;
    private String codCorreq;
    
    /**
     * Constructor para excepción de tipo CorrequisitoAlreadyExistsException
     * @param pCodCurso código del curso al que pertenece el correquisito
     * @param pCodCorreq código del curso que es correquisito
     */
    public CorrequisitoAlreadyExistsException(String pCodCurso, String pCodCorreq){
        codCurso = pCodCurso;
        codCorreq = pCodCorreq;
    }
    
    public String getCodigoCurso(){
        return codCurso;
    }
    
    public String getCodigoCorrequisito(){
        return codCorreq;
    }
    
    /**
     * Método para obtener el mensaje de error correspondiente a la excepción atrapada
     * @return el mensaje de error sobre que un correquisito ya pertenece a un curso en particular
     */
    public String mensajeError(){
        String msg = "";
        
        msg += "El curso de código " + codCorreq + " ya es correquisito del curso" + codCurso;
        
        return msg;
    }        
}
