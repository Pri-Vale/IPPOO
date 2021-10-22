package Modelo.logicaDeNegocio;

import java.util.ArrayList;

/**
 * Abstracción de la clase Curso y su informacion referente
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.4
 * @since 1.0
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
     * @param pNombreCurso nombre del curso
     * @param pCodCurso código del curso alfanumérico de 6 caracteres (2 caracteres del código de la escuela + 4 enteros positivos)
     * @param pCantCreditos valor entero que representa la cantidad de créditos (rango 0-4)
     * @param pCantHorasLectivas valor entero que representa la cantidad de horas lectivas (rango 1-5)
     */
    public Curso(String pNombreCurso, String pCodCurso, int pCantCreditos, int pCantHorasLectivas) {
        this.nombreCurso = pNombreCurso;
        this.codCurso = pCodCurso;
        this.cantCreditos = pCantCreditos;
        this.cantHorasLectivas = pCantHorasLectivas;
        
        this.requisitos = new ArrayList<Curso>();
        this.correquisitos = new ArrayList<Curso>();
    }
    
    /**
     * Constructor para objetos de la clase Curso
     * @param codCurso código del curso alfanumérico de 6 caracteres (2 caracteres del código de la escuela + 4 enteros positivos)
     */
    public Curso(String codCurso) {
        this.codCurso = codCurso;
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
    
    public ArrayList<Curso> getRequisitos(){
        return requisitos;
    }
    
    public ArrayList<Curso> getCorrequisitos(){
        return correquisitos;
    }

    /**
     * Método para registrar un requisito perteneciente a un curso
     * @param pCurso el curso que es requisito de un curso en particular
     */
    public void registrarRequisito(Curso pCurso){
        requisitos.add(pCurso);
        //existe o no el curso que se va a agregar como requisito
        //requisito ya está agregado al curso como requisito o correquisito
    }
    
    /**
     * Método para registrar un correquisito perteneciente a un curso
     * @param pCurso el curso que es correquisito de un curso en particular
     */
    public void registrarCorrequisito(Curso pCurso){
        correquisitos.add(pCurso);
        //existe o no el curso que se va a agregar como requisito
        //requisito ya está agregado al curso como requisito o correquisito
    }
   
    /**
     * Método para representar en caracteres el estado de un objeto de tipo Curso
     * @return a representación en caracteres de los atributos del objeto de tipo Curso
     */
    @Override
    public String toString() {
        String msg ="";
        
        msg += "Curso{\n";
        msg += "Nombre del curso: " + nombreCurso + "\n";
        msg += "Código del curso: " + codCurso + "\n";
        msg += "Cantidad de créditos: " + cantCreditos + "\n";
        msg += "Cantidad de horas lectivas " +cantHorasLectivas + "\n";
        msg += "Requisitos: \n";
        for (Curso requisito : requisitos){
            msg += requisito.getCodCurso() + "\n";
            msg += requisito.getNombreCurso() + "\n";
        } 
        msg += "Correquisitos: \n";
        for (Curso correquisito : correquisitos){
            msg += correquisito.getCodCurso() + "\n";
            msg += correquisito.getNombreCurso() + "\n";
        }
        msg += "}";
        
        return msg;
    }
 
}