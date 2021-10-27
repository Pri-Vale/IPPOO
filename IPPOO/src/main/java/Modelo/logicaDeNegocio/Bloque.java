package Modelo.logicaDeNegocio;

import java.util.ArrayList;

/**
 * Abstracción de la clase Bloque y su información referente
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.3
 * @since 1.0
 */
public class Bloque {
    private String idBloque;
    private ArrayList<Curso> cursos;

    public Bloque() {
    }
    
    /**
     * Constructor para objetos de la clase Bloque 
     * @param idBloque el identificador del bloque, correspondiente al número de semestre  
     */
    public Bloque(String idBloque) {
        this.idBloque = idBloque;
        this.cursos = new ArrayList<>();
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
    /**
     * Método que permite agregar un curso a un bloque de un plan de estudios 
     * @param curso el curso que se agrega al bloque del plan de estudios
     */
    public void agregarCurso(Curso curso){ 
        cursos.add(curso);
    }
    
    /**
     * Método que permite eliminar un curso de un bloque de un plan de estudios
     * @param curso el objeto de tipo Curso que se quiere eliminar del bloque
     */
    public void eliminarCurso(Curso curso){
        cursos.remove(curso);
    }
    
    /**
     * Método para buscar un curso perteneciente al bloque de estudio del plan de estudio
     * @param codCurso código del curso que se quiere encontrar
     * @return el objeto de tipo Curso que se encontró
     */
    public Curso buscarCursoBloque(String codCurso){
        Curso cursoEncontrado = null;
        for (Curso curso : cursos){
            if (codCurso.equals(curso.getCodCurso()) == true){
                cursoEncontrado = curso;
            }
        }
        return cursoEncontrado;
    }
    

    /**
     * Método para representar en caracteres el estado de un objeto de tipo Bloque
     * @return a representación en caracteres de los atributos del objeto de tipo Bloque
     */
    @Override
    public String toString() {
        String msg = "";
        
        msg += "Bloque{\n";
        msg += "Identificador del bloque: " + this.idBloque + "\n";
        msg += "Cursos pertenecientes:\n" + this.cursos + "\n";
        msg += "}";
        
        return msg;
    }
}
