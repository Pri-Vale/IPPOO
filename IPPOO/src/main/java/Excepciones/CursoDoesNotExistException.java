/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo Curso no exista 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.1
 * @since 1.0
 */

public class CursoDoesNotExistException extends Exception{
    private String codCurso;
    
    public CursoDoesNotExistException(String pCodCurso){
        codCurso = pCodCurso;
    }
    
    public String getCodigoCurso(){
        return codCurso;
    }
    
    public String mensajeError(){
        String msg = "";
        
        msg += "El curso de código " + codCurso + " no existe";
        
        return msg;
    }
    
}
