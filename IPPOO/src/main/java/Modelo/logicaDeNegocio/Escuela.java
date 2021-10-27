package Modelo.logicaDeNegocio;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Abstracción de la clase Escuela y su información referente
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.3
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
        this.planesDeEstudio = new ArrayList<>();
        this.misCursos = new ArrayList<>();
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
     * Método que permite agregar planes de estudio a una escuela
     * @param pNumPlan número del plan de estudio 
     * @param pFechaVigencia fecha de vigencia del plan de estudio
     */
    public void agregarPlanesEstudio(int pNumPlan, Date pFechaVigencia){
        PlanDeEstudio nuevoPlanDeEstudio = new PlanDeEstudio(pNumPlan, pFechaVigencia);
        planesDeEstudio.add(nuevoPlanDeEstudio);
    }
    
    /**
     * Método que permite asociar cursos con una escuela 
     * @param pCurso el objeto de tipo Curso que se asociará con la escuela
     */
    public void asociarCurso(Curso pCurso){
        misCursos.add(pCurso);
    }
    
    /**
     * Método que retonar un curso perteneciente a una escuela según su código de curso
     * @param codCurso código del curso que se está buscando
     * @return el objeto de tipo Curso encontrado
     */
    public Curso buscarCursosEscuela(String codCurso){
        Curso cursoEncontrado = null;
        for (Curso curso : misCursos){
            if (codCurso.equals(curso.getCodCurso()) == true){
                cursoEncontrado = curso;
            }
        }
        return cursoEncontrado;
    }
    
    /**
     * Método que retonar un plan de estudios perteneciente a una escuela según su número de plan
     * @param numPlan número del plan que se está buscando
     * @return el objeto de tipo PlanDeEstudio encontrado
     */
    public PlanDeEstudio buscarPlanEscuela(int numPlan){
        PlanDeEstudio planEncontrado = null;
        for (PlanDeEstudio plan : planesDeEstudio){
            if (numPlan == plan.getCodPlanEstudio()){
                planEncontrado = plan;
            }
        }
        return planEncontrado;
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
