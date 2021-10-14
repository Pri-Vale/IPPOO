/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo.logicaDeNegocio;


import java.util.ArrayList;
import java.util.Date;

/**
 * Abstraccion de la clase PlanDeEstudio y su informacion referente
 * @author Valeria
 * @version 08 octubre, 2021
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
    public Date getFechaVigencia() {
        return fechaVigencia;
    }

    public void setFechaVigencia(Date fechaVigencia) {
        this.fechaVigencia = fechaVigencia;
    }
    
    public Bloque agregarBloque(String pIdBloque){
        //Aqui no se si meter una validación de si existe o no
        Bloque bloqueNuevo = new Bloque(pIdBloque);
        bloques.add(bloqueNuevo);
        return bloqueNuevo;
    }

    @Override
    public String toString() {
        return "PlanDeEstudio{" + "codPlanEstudio=" + codPlanEstudio + ", fechaVigencia=" + fechaVigencia + ", bloques=" + bloques + '}';
    }
}
