/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.logicaDeNegocio;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Abstraccion de la clase PlanDeEstudio y su informacion referente
 * @author Valeria
 * @version 08 octubre, 2021
 */
public class PlanDeEstudio {
    private int codPlanEstudio;
    private LocalDate fechaVigencia; 
    private ArrayList<Bloque> bloques;

    /**
     * Constructor para objetos de la clase PlanDeEstudio
     */    
    public PlanDeEstudio(){
        
    }    
    public PlanDeEstudio(int codPlanEstudio, LocalDate fechaVigencia, ArrayList<Bloque> bloques) {
        this.codPlanEstudio = codPlanEstudio;
        this.fechaVigencia = fechaVigencia;
        this.bloques = bloques;
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
    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
    
    public void agregarBloque(String pIdBloque){
        //Aqui no se si meter una validación de si existe o no
        Bloque bloqueNuevo = new Bloque(pIdBloque);
        bloques.add(bloqueNuevo);
    }

    @Override
    public String toString() {
        return "PlanDeEstudio{" + "codPlanEstudio=" + codPlanEstudio + ", fechaVigencia=" + fechaVigencia + ", bloques=" + bloques + '}';
    }
}
