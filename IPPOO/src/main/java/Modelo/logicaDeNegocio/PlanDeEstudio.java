/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.logicaDeNegocio;


import java.util.ArrayList;
import java.sql.Date;

/**
 * Abstracción de la clase PlanDeEstudio y su información referente
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.1
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
    public PlanDeEstudio(int codPlanEstudio, Date fechaVigencia) {
        this.codPlanEstudio = codPlanEstudio;
        this.fechaVigencia = fechaVigencia;
        //ArrayList<Bloque> bloques = new ArrayList<Bloque>();
    }

    public int getCodPlanEstudio() {
        return codPlanEstudio;
    }

    public void setCodPlanEstudio(int codPlanEstudio) {
        this.codPlanEstudio = codPlanEstudio;
    }

    public ArrayList<Bloque> getBloques() {
        return bloques;
    }

    public void setBloques(ArrayList<Bloque> bloques) {
        this.bloques = bloques;
    }

    //hay que arreglar los de fecha
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
    
    /**
     * Método que agregar un bloque con cursos al plan de estudio
     * @param pIdBloque identificador del bloque 
     * @return el nuevo bloque de estudios creado
     */
    public Bloque agregarBloque(String pIdBloque){
        //validación de si existe o no
        Bloque bloqueNuevo = new Bloque(pIdBloque);
        ArrayList<Bloque> bloques = new ArrayList<Bloque>();
        //me parece que acá no hace falta declarar un nuevo arraylist de bloques pq ya está el creado en los atributos de la clase
        //entonces más bien lo inicializas en el constructor (te lo dejé comentado) y acá solo agregas los bloques ahí :D
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
        /*
        for (Bloque bloque : bloques){
            msg += bloque.toString();
        }
        */
        //no sé si ese msg += bloques los imprime bien entonces por si las moscas dejo este ciclo for
        msg += bloques;
        return msg;
    }
}
