
package Excepciones;

/**
 * Subclase de Exception, excepción en caso de que un objeto de tipo Plan ya exista en una escuela
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.1
 * @since 1.0
 */

public class PlanDeEstudioAlreadyExistsException extends Exception{
    private int numPlan;
    
    public PlanDeEstudioAlreadyExistsException(int pNumPlan){
        numPlan = pNumPlan;
    }
    
    public int getNumPlan(){
        return numPlan;
    }
    
    public String mensajeError(){
        String msg = "";
        
        msg += "Ya existe registrado un plan con número de plan  " + numPlan;
        
        return msg;
    }   
}
