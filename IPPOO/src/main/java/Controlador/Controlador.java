package Controlador;

import Connect_BD.Consultas_BaseDatos;
import Modelo.logicaDeNegocio.Bloque;
import Modelo.logicaDeNegocio.Escuela;
import Modelo.logicaDeNegocio.Curso;
import Modelo.logicaDeNegocio.PlanDeEstudio;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

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
        salidaControlador.insertarPlanEstudio(plan,pNombreEscuela);
        
        salidaControlador.insertarCursoXPlan(pCodigoCurso, plan,pBloqueActivo);
        
        System.out.println("FUN4 COMPLETE");
    }
    
    public void poblarCboxCursosDeEscuela(JComboBox cbox_cursos, String codEscuela){
        ArrayList<String> listaCursosDeEscuela = consultaBase.seleccionarCursosDeEscuela(codEscuela);

        int contador = 0;
        while (listaCursosDeEscuela.size() > contador){
            cbox_cursos.addItem(listaCursosDeEscuela.get(contador));
            contador++;
            System.out.println("Contador " + contador);
        }

    //excepcion si lista vacia 
    }
    
    public void poblarCboxCursos(JComboBox cbox_cursos){
        ArrayList<String> listaCursos = consultaBase.seleccionarCursos();

        int contador = 0;
        while (listaCursos.size() > contador){
            cbox_cursos.addItem(listaCursos.get(contador));
            contador++;
        }

    //excepcion si lista vacia 
    }
    public void poblarCboxCodigosPlan(JComboBox cbox_codigosPlan,String codEscuela){
        
        ArrayList<String> listaCodPlanes = consultaBase.seleccionarCodPlanes(codEscuela);
        int contador = 0;
        while (listaCodPlanes.size() > contador){
            cbox_codigosPlan.addItem(listaCodPlanes.get(contador));
            contador++;
        }
        
    }
    
    public void agregarRequisitoACurso(String codCurso, String codReq){      
        //Arreglar el manejo de objetos en el codigo
        Curso cursoRequisito = new Curso(codReq);
        
        //Persistencia almacenado 
        salidaControlador.insertarRequisitoXCurso(codCurso, codReq);
       
    }
    
    public void agregarCorrequisitoACurso(String codCurso, String codCorreq){      
        //Arreglar el manejo de objetos en el codigo
        Curso cursoRequisito = new Curso(codCorreq);
        
        //Persistencia almacenado 
        salidaControlador.insertarRequisitoXCurso(codCurso, codCorreq);

    }
    
    public String obtenerNombreCurso(String codCurso){
        ArrayList<String> listaNombreCurso = salidaControlador.seleccionarNombreCurso(codCurso);
        int contador = 0;
        String nombreCurso = null;
        while (listaNombreCurso.size() > contador){
            System.out.println("\n Curso obtenido:" + nombreCurso);
            nombreCurso = listaNombreCurso.get(contador);
            contador++;
        }
        return nombreCurso;
    }
    
    public ArrayList<String> consultarRequisitos(String codCurso){
        ArrayList<String> listaRequisitosCurso = salidaControlador.seleccionarRequisitosCurso(codCurso);
        return listaRequisitosCurso;
    }
    
    public void poblarJTable(JTable table, String escuelaBuscar) throws SQLException{
        //envio la escuela
        //String codigoEscuela= this.obtenerCodEscuela(escuelaBuscar);
        //aqui falta un combo 
        
        System.out.println(escuelaBuscar);
        
        //recibe datos y construye la tabla 
        ResultSet rst = salidaControlador.verPlanDeEstudio(escuelaBuscar);
        ResultSetMetaData rsMd= rst.getMetaData();
        
        
        int numeroColumnas= rsMd.getColumnCount();
        
        DefaultTableModel modelo = new DefaultTableModel();
        table.setModel(modelo);
        
        for (int x=1; x<=numeroColumnas; x++){
            modelo.addColumn(rsMd.getColumnLabel(x));    
        }
        
        while(rst.next()){
            Object [] fila= new Object[numeroColumnas];
            
            for(int y=0; y<numeroColumnas;y++){
                fila[y]=rst.getObject(y+1);
                
            }
            modelo.addRow(fila);
        }
}
}
     
         