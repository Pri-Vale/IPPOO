package Modelo.logicaDeNegocio;

import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Abstraccion de la clase Curso y su informacion referente
 * @author Valeria
 * @version 08 octubre, 2021
 */
public class Curso {
    private String nombreCurso;
    private String codCurso;
    private int cantCreditos;
    private int cantHorasLectivas;
    
    private ArrayList<Curso> requisitos;
    private ArrayList<Curso> correquisitos;
 
 
    /**
     * Constructor para objetos de la clase Curso
     */    
    public Curso(){
        
    }
    /**
     * Constructor para objetos de la clase Curso
     * @param pNombreCurso
     * @param pCodCurso
     * @param pCantCreditos
     * @param pCantHorasLectivas 
     */
    public Curso(String pNombreCurso, String pCodCurso, int pCantCreditos, int pCantHorasLectivas) {
        setNombreCurso(pNombreCurso);
        setCodCurso(pCodCurso);
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

    public void setCodCurso(String pCodCurso){
        this.codCurso = pCodCurso;
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

    @Override
    public String toString() {
        return "Curso{" + "nombreCurso=" + nombreCurso + ", codCurso=" + codCurso + ", cantCreditos=" + cantCreditos + ", cantHorasLectivas=" + cantHorasLectivas + ", requisitos=" + requisitos + ", correquisitos=" + correquisitos + '}';
    }
    
    
   
}