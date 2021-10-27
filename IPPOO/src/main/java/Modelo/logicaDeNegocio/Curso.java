package Modelo.logicaDeNegocio;

import java.util.ArrayList;

/**
 * Abstracción de la clase Curso y su informacion referente
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.5
 * @since 1.0
 */
public class Curso {
    //Atributos de instancia
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
        this.requisitos = new ArrayList<>();
        this.correquisitos = new ArrayList<>();
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
    }
    
    /**
     * Método para registrar un correquisito perteneciente a un curso
     * @param pCurso el curso que es correquisito de un curso en particular
     */
    public void registrarCorrequisito(Curso pCurso){
        correquisitos.add(pCurso);
    }
    
    /**
     * Método para encontrar un curso que es requisito de un curso en particular
     * @param codRequisito código del requisito que se quiere encontrar
     * @return el requisito encontrado
     */
    public Curso buscarRequisito(String codRequisito){
        Curso requisitoEncontrado = null;
        for (Curso requisito : requisitos){
            if (codRequisito.equals(requisito.getCodCurso()) == true){
                requisitoEncontrado = requisito;
            }
        }
        return requisitoEncontrado;
    }
    
    /**
     * Método para encontrar un curso que es correquisito de un curso en particular
     * @param codCorrequisito código del correquisito que se quiere encontrar
     * @return el correquisito encontrado
     */
    public Curso buscarCorrequisito(String codCorrequisito){
        Curso correquisitoEncontrado = null;
        for (Curso correquisito : correquisitos){
            if (codCorrequisito.equals(correquisito.getCodCurso()) == true){
                correquisitoEncontrado = correquisito;
            }
        }
        return correquisitoEncontrado;
    }
    
    /**
     * Método que permite eliminar un requisito de un curso en particular
     * @param pCurso el requisito que se quiere eliminar
     */
    public void eliminarRequisito(Curso pCurso){
        requisitos.remove(pCurso);
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