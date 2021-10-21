package Modelo.logicaDeNegocio;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Abstracción de la clase Escuela y su información referente
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.2
 * @since 1.0
 */
public class Escuela{
    
    private String nombreEscuela;
    private String codEscuela; 
    private ArrayList<PlanDeEstudio> planesDeEstudio;
    private ArrayList<Curso> misCursos;
    
    /**
     * Constructor para objetos de la clase Escuela  
     */
    public Escuela(){
        
    }
    
    /**
     * Constructor para objetos de la clase Escuela  
     * @param nombreEscuela el nombre de la escuela o área académica
     * @param codEscuela el código de la escuela (de 2 caracteres)
     */
    public Escuela(String nombreEscuela, String codEscuela){
        this.nombreEscuela = nombreEscuela;
        this.codEscuela = codEscuela;
        this.planesDeEstudio = new ArrayList<PlanDeEstudio>();
        this.misCursos = new ArrayList<Curso>();
    }

    public String getNombreEscuela(){
        return nombreEscuela;
    }

    public void setNombreEscuela(String nombreEscuela){
        this.nombreEscuela = nombreEscuela;
    }

    public String getCodEscuela(){
        return codEscuela;
    }

    public void setCodEscuela(String codEscuela){
        this.codEscuela = codEscuela;
    }
    
    public ArrayList<PlanDeEstudio> getPlanesDeEstudio(){
        return planesDeEstudio;
    }
    
    public ArrayList<Curso> getCursos(){
        return misCursos;
    }
            
    /**
     * 
     * @param pNumPlan
     * @param pFechaVigencia 
     */
    public void agregarPlanesEstudio(int pNumPlan, Date pFechaVigencia){
        PlanDeEstudio nuevoPlanDeEstudio = new PlanDeEstudio(pNumPlan, pFechaVigencia);
        planesDeEstudio.add(nuevoPlanDeEstudio);
        //excepcion si el plan ya existe
    }
    
    /**
     * 
     * @param pCurso 
     */
    public void asociarCurso(Curso pCurso){
        misCursos.add(pCurso);
        //excepcion si el curso existe o no
        //excepcion si el curso ya estaba asociado
    }

    /**
     * Método para representar en caracteres el estado de un objeto de tipo Escuela
     * @return a representación en caracteres de los atributos del objeto de tipo Escuela
     */
    @Override
    public String toString() {
        String msg = "";
        
        msg += "Escuela{\n";
        msg += "Nombre de la escuela: " + nombreEscuela + "\n";
        msg += "Código de la escuela: " + codEscuela + " }\n";
        msg += planesDeEstudio;
        msg += misCursos;
        
        return msg;
    }
}
