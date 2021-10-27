package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un requisito ya esté asociado a un curso 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.1
 * @since 1.0
 */

public class RequisitoAlreadyExistsException extends Exception{
    private String codCurso;
    private String codReq;
    
    /**
     * Constructor para excepción de tipo RequisitoAlreadyExistsException
     * @param pCodCurso código del curso al que pertenece el requisito
     * @param pCodReq código del curso que es requisito
     */
    public RequisitoAlreadyExistsException(String pCodCurso, String pCodReq){
        codCurso = pCodCurso;
        codReq = pCodReq;
    }
    
    public String getCodigoCurso(){
        return codCurso;
    }
    
    public String getCodigoRequisito(){
        return codReq;
    }
    
    /**
     * Método para obtener el mensaje de error correspondiente a la excepción atrapada
     * @return el mensaje de error sobre que un requisito ya pertenece a un curso en particular
     */
    public String mensajeError(){
        String msg = "";
        
        msg += "El curso de código " + codReq + " ya es requisito del curso" + codCurso;
        
        return msg;
    }    
}
