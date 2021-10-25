package Modelo.logicaDeNegocio;

import java.util.ArrayList;
import java.sql.Date;
import Excepciones.BloqueDoesNotExistException;

/**
 * Abstracción de la clase PlanDeEstudio y su información referente
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.2
 * @since 1.0
 */

public class PlanDeEstudio {
    private int codPlanEstudio;
    private Date fechaVigencia; 
    private ArrayList<Bloque> bloques;

    /**
     * Constructor para objetos de la clase PlanDeEstudio
     */    
    public PlanDeEstudio(){
        
    }    
    
    /**
     * Constructor para objetos de la clase PlanDeEstudio
     * @param codPlanEstudio número del plan de estudios (consecutivo numérico de 4 dígitos)
     * @param fechaVigencia 
     */
    public PlanDeEstudio(int codPlanEstudio, Date fechaVigencia) {
        this.codPlanEstudio = codPlanEstudio;
        this.fechaVigencia = fechaVigencia;
        this.bloques = new ArrayList<Bloque>();
    }

    public int getCodPlanEstudio() {
        return codPlanEstudio;
    }

    public void setCodPlanEstudio(int codPlanEstudio) {
        this.codPlanEstudio = codPlanEstudio;
    }

    //hay que arreglar los de fecha
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
    
    public ArrayList<Bloque> getBloques() {
        return bloques;
    }
    
    public Bloque buscarBloquePlan(String semestreActivo) throws BloqueDoesNotExistException{
        Bloque bloqueEncontrado = null;
        for (Bloque bloque : bloques){
            if (semestreActivo.equals(bloque.getIdBloque()) == true){
                bloqueEncontrado = bloque;
                return bloqueEncontrado;
            }
        }
        if (bloqueEncontrado == null){
            throw new BloqueDoesNotExistException(this.getCodPlanEstudio());
        }
        
        return bloqueEncontrado;
    }
    
    /**
     * Método que agregar un bloque con cursos al plan de estudio
     * @param pIdBloque identificador del bloque 
     * @return el nuevo bloque de estudios creado
     */
    public Bloque agregarBloque(String pIdBloque){
        //validación de si existe o no
        Bloque bloqueNuevo = new Bloque(pIdBloque);
        //me parece que acá no hace falta declarar un nuevo arraylist de bloques pq ya está el creado en los atributos de la clase
        //entonces más bien lo inicializas en el constructor y acá solo agregas los bloques ahí :D
        bloques.add(bloqueNuevo);
        return bloqueNuevo;
    }

    /**
     * Método para representar en caracteres el estado de un objeto de tipo PlanDeEstudio
     * @return a representación en caracteres de los atributos del objeto de tipo PlanDeEstudio
     */
    public String toString() {
        String msg = "";
        msg += "Plan de Estudio{\n";
        msg += "Código del plan de estudio: " + codPlanEstudio + "\n";
        msg += "Fecha de vigencia del plan: " + fechaVigencia + "\n";
        msg += "Bloques que conforman el plan: ";
        msg += bloques;
        return msg;
    }
}
