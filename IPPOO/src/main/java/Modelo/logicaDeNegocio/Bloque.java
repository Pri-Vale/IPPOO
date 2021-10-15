/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.logicaDeNegocio;

import java.util.ArrayList;

/**
 *
 * @author pri23
 */
public class Bloque {
    private String idBloque;
    private ArrayList<Curso> cursos;

    public Bloque() {
    }
    

    public Bloque(String idBloque) {
        this.idBloque = idBloque;
    }

    public String getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(String idBloque) {
        this.idBloque = idBloque;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }
    
    public void agregarCurso(Curso curso){
        //aqui creo que tambien va otra validacion si existe o no
        ArrayList<Curso> cursos= new ArrayList<Curso>(); 
        cursos.add(curso);
    }

    @Override
    public String toString() {
        return "Bloque{" + "idBloque=" + idBloque + ", cursos=" + cursos + '}';
    }
  
}
