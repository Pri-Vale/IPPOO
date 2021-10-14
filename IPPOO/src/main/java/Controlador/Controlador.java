package Controlador;

import Connect_BD.Consultas_BaseDatos;
import Modelo.logicaDeNegocio.Bloque;
import Modelo.logicaDeNegocio.Escuela;
import Modelo.logicaDeNegocio.Curso;
import Modelo.logicaDeNegocio.PlanDeEstudio;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JComboBox;

/**
 *
 * @author pri23
 */
public class Controlador {
    
    //Atributos 
    private final Escuela escuela= new Escuela();
    public static Consultas_BaseDatos salidaControlador = new Consultas_BaseDatos() ;
    private Consultas_BaseDatos consultaBase = new Consultas_BaseDatos();
    
    public boolean crearEscuela(String pNombreEscuela, String pCodEscuela) throws SQLException{
        try{
        Escuela escuela= new Escuela(pNombreEscuela, pCodEscuela);
            System.out.println(pNombreEscuela);
            System.out.println(pCodEscuela);
        salidaControlador.insertarEscuela(escuela);
        return true;
        
        }catch(NullPointerException a){
          return false;  
        }                            
    }
    
    public void poblarCboxEscuelas(JComboBox cbox_escuelas){
        ArrayList<String> listaEscuelas = consultaBase.seleccionarEscuelas();
        
        int contador = 0;
        while (listaEscuelas.size() > contador){
            cbox_escuelas.addItem(listaEscuelas.get(contador));
            contador++;
        }
        
        //excepcion si lista vacia 
    }
    
    public void poblarCboxEscuelas2(JComboBox cboxPlanesEst){
        ArrayList<String> listaEscuelas = consultaBase.seleccionarEscuelas();
        
        int contador = 0;
        while (listaEscuelas.size() > contador){
            cboxPlanesEst.addItem(listaEscuelas.get(contador));
            contador++;
        }
        
        //excepcion si lista vacia //tratar de hacer un if 
    }
    
    public String obtenerCodEscuela(String nombreEscuela){
        String codEscuela = consultaBase.seleccionarCodEscuela(nombreEscuela);
        return codEscuela;
    }
   
    
    
    public void crearCurso(String pCodEscuela, String pNombreCurso, String pCodCurso, int pCantCreditos, int pCantHorasLectivas){
        //ArrayList<String> listaEscuelas = consultaBase.seleccionarEscuelas();
        
        Curso nuevoCurso = new Curso(pNombreCurso, pCodCurso, pCantCreditos, pCantHorasLectivas);
        salidaControlador.insertarCurso(pCodEscuela, nuevoCurso);
        //buscar la Escuela a la que pertenece el curso para hacer la asociacion correspondiente
        /*
        for (Escuela escuela : escuelas){ 
            if (codEscuela == escuela.getCodEscuela()){
                Curso nuevoCurso = new Curso(pNombreCurso, pCodCurso, pCantCreditos, pCantHorasLectivas);
                curso.asociarCurso(nuevoCurso);
                salidaControlador.insertarCurso(nuevoCurso);
            }
        }  
        */  
        
        //falta manejo de excepciones       
    }
    
    public void crearPlanEstudios(String pNombreEscuela,int pCodigoPlan,Date pVigenciaPlan,String pCodigoCurso,String pBloqueActivo){
        PlanDeEstudio plan = new PlanDeEstudio(pCodigoPlan, pVigenciaPlan);
        Bloque bloque = plan.agregarBloque(pBloqueActivo);
        //Este curso no se si va aca
        Curso cursoBloque=new Curso(pCodigoCurso);
        bloque.agregarCurso(cursoBloque);
        //Aqui tengo la duda si es el objeto curso o el int
        
        //Persistencia almacenado de plan de estudios
        salidaControlador.insertarPlanEstudio
        
        
    }
    
}    