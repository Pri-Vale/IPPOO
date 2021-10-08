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
    private int numPlan;
    private LocalDate fechaVigencia; 
    private ArrayList<Curso> cursos;

    /**
     * Constructor para objetos de la clase PlanDeEstudio
     */    
    public PlanDeEstudio(){
        
    }    
    
    /**
     * Constructor para objetos de la clase PlanDeEstudio
     * @param pNumPlan el numero de plan de estudio
     * @param pFechaVigencia a partir de cuando comienza a regir el plan de estudio
     * 
     */      
    public PlanDeEstudio(int pNumPlan, LocalDate pFechaVigencia) {
        this.numPlan = numPlan;
        this.fechaVigencia = fechaVigencia;
    }

    public int getNumPlan() {
        return numPlan;
    }

    public void setNumPlan(int numPlan) {
        this.numPlan = numPlan;
    }
    
    //hay que arreglar los de fecha
    public LocalDate getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(LocalDate fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }

    @Override
    public String toString() {
        return "PlanDeEstudio{" + "numPlan=" + numPlan + ", fechaVigencia=" + fechaVigencia + ", cursos=" + cursos + '}';
    }
    
    
    
    
    
    
}
