/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo Curso, requisito de otro objeto tipo Curso, no exista 
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.2
 * @since 1.0
 */

public class RequisitoDoesNotExistException extends Exception{
    private String codCurso;
    
    public RequisitoDoesNotExistException(String pCodCurso){
        codCurso = pCodCurso;
    }
    
    public String getNumeroDePlan(){
        return codCurso;
    }
    
    public String mensajeError(){
        String msg="";
        
        msg += "No existen requisitos asociados al curso de código " + codCurso;
        
        return msg;
    }      
}
