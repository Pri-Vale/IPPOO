package Modelo.logicaDeNegocio;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Abstraccion de la clase Curso y su información referente
 * @author Valeria
 * @version 04 octubre, 2021
 */
public class Curso {
    private String nombreCurso;
    private String codCurso;
    private int cantCreditos;
    private int cantHorasLectivas;
    
    public Curso(){
        
    }
    /**
     * Constructor para objetos de la clase Curso
     * @param pNombreCurso
     * @param pCodCurso
     * @param pCantCreditos
     * @param pCantHorasLectivas 
     */
    public Curso(String pNombreCurso, int pNumCurso, int pCantCreditos, int pCantHorasLectivas) {
        setNombreCurso(pNombreCurso);
        setCodCurso(pNumCurso);
        setCantCreditos(pCantCreditos);
        setCantHorasLectivas(pCantHorasLectivas);
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public void setNombreCurso(String pNombreCurso) {
        this.nombreCurso = pNombreCurso;
    }

    public String getCodCurso() {
        return codCurso;
    }

    public void setCodCurso(int pNumCurso){
        String numCurso = String.valueOf(pNumCurso); 
        //String prefijoEscuela = generarPrefijoEscuela(pNombreEscuela);
        //codCurso += prefijoEscuela;
        codCurso += numCurso;
    }

    public int getCantCreditos() {
        return cantCreditos;
    }

    public void setCantCreditos(int pCantCreditos) {
        this.cantCreditos = pCantCreditos;
    }

    public int getCantHorasLectivas() {
        return cantHorasLectivas;
    }

    public void setCantHorasLectivas(int pCantHorasLectivas) {
        this.cantHorasLectivas = pCantHorasLectivas;
    }
   
}