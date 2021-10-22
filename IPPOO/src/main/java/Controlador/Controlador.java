package Controlador;

import Connect_BD.Consultas_BaseDatos;
import Correo.Correo;
import Modelo.logicaDeNegocio.Bloque;
import Modelo.logicaDeNegocio.Escuela;
import Modelo.logicaDeNegocio.Curso;
import Modelo.logicaDeNegocio.PlanDeEstudio;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Abstracción de la clase Controlador
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 2.0
 * @since 1.0
 */
public class Controlador {
    
    //Atributos 
    //private final Escuela escuela= new Escuela();
    
    private ArrayList<Escuela> escuelas = new ArrayList<Escuela>();
    private ArrayList<Curso> cursos = new ArrayList<Curso>();
    
    
    public static Consultas_BaseDatos salidaControlador = new Consultas_BaseDatos() ;
    
    private Consultas_BaseDatos consultaBase = new Consultas_BaseDatos();
    private Correo salidaCorreo=new Correo();
    
    
    /**
     * 
     * @param pNombreEscuela
     * @param pCodEscuela
     * @return
     * @throws SQLException 
     */
    public boolean crearEscuela(String pNombreEscuela, String pCodEscuela) throws SQLException{
        try{
            Escuela escuela= new Escuela(pNombreEscuela, pCodEscuela);
            System.out.println(pNombreEscuela);
            System.out.println(pCodEscuela);
            escuelas.add(escuela);
            salidaControlador.insertarEscuela(escuela);
            System.out.println(escuelas.toString());
            return true;
        
        }catch(NullPointerException a){
          return false;  
        }                            
    }
    
    /**
     * 
     * @param cbox_escuelas 
     */
    public void poblarCboxEscuelas(JComboBox cbox_escuelas){
        ArrayList<String> listaEscuelas = consultaBase.seleccionarEscuelas();
        
        int contador = 0;
        while (listaEscuelas.size() > contador){
            cbox_escuelas.addItem(listaEscuelas.get(contador));
            contador++;
        }
        
        //excepcion si lista vacia 
    }
    
    /**
     * 
     * @param cboxPlanesEst 
     */
    public void poblarCboxEscuelas2(JComboBox cboxPlanesEst){
        ArrayList<String> listaEscuelas = consultaBase.seleccionarEscuelas();
        
        int contador = 0;
        while (listaEscuelas.size() > contador){
            cboxPlanesEst.addItem(listaEscuelas.get(contador));
            contador++;
        }
        
        //excepcion si lista vacia //tratar de hacer un if 
    }
    
    /**
     * 
     * @param nombreEscuela
     * @return 
     */
    public String obtenerCodEscuela(String nombreEscuela){
        String codEscuela = consultaBase.seleccionarCodEscuela(nombreEscuela);
        return codEscuela;
    }
   
    /**
     * 
     * @param pCodEscuela
     * @param pNombreCurso
     * @param pCodCurso
     * @param pCantCreditos
     * @param pCantHorasLectivas 
     */
    public void crearCurso(String pCodEscuela, String pNombreCurso, String pCodCurso, int pCantCreditos, int pCantHorasLectivas){        
        Curso nuevoCurso = new Curso(pNombreCurso, pCodCurso, pCantCreditos, pCantHorasLectivas);
        
        //buscar la Escuela a la que pertenece el curso para hacer la asociacion correspondiente
        for (Escuela escuelaEncontrada : escuelas){ 
            System.out.print(escuelaEncontrada.getCodEscuela());
            if (pCodEscuela.equals(escuelaEncontrada.getCodEscuela()) == true){
                escuelaEncontrada.asociarCurso(nuevoCurso);
                cursos.add(nuevoCurso);
                //insertar cod escuela en la tabla intermedia
                salidaControlador.insertarCurso(pCodEscuela, nuevoCurso);
                System.out.println(escuelas.toString());
                System.out.println(cursos.toString());
                break;
            }
        }  
        //falta manejo de excepciones
        //existe curso en escuela
        //existe escuela
    }
    
    /**
     * 
     * @param pNombreEscuela
     * @param pCodigoPlan
     * @param pVigenciaPlan
     * @param pCodigoCurso
     * @param pBloqueActivo 
     */
    public void crearPlanEstudios(String pNombreEscuela,int pCodigoPlan,Date pVigenciaPlan,String pCodigoCurso,String pBloqueActivo){
        PlanDeEstudio plan = new PlanDeEstudio(pCodigoPlan, pVigenciaPlan);
        Bloque bloque = plan.agregarBloque(pBloqueActivo);
        //Este curso no se si va aca
        Curso cursoBloque = new Curso(pCodigoCurso);
        bloque.agregarCurso(cursoBloque);
        //Aqui tengo la duda si es el objeto curso o el int
        
        //Persistencia almacenado de plan de estudios
        salidaControlador.insertarPlanEstudio(plan,pNombreEscuela);
        
        salidaControlador.insertarCursoXPlan(pCodigoCurso, plan, pBloqueActivo);
        
        System.out.println("FUN4 COMPLETE");
    }
    
    /**
     * 
     * @param cbox_cursos
     * @param codEscuela 
     */
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
    
    /**
     * 
     * @param cbox_cursos 
     */
    public void poblarCboxCursos(JComboBox cbox_cursos){
        ArrayList<String> listaCursos = consultaBase.seleccionarCursos();

        int contador = 0;
        while (listaCursos.size() > contador){
            cbox_cursos.addItem(listaCursos.get(contador));
            contador++;
        }

    //excepcion si lista vacia 
    }
    
    /**
     * 
     * @param cbox_codigosPlan
     * @param codEscuela 
     */
    public void poblarCboxCodigosPlan(JComboBox cbox_codigosPlan,String codEscuela){
        
        ArrayList<String> listaCodPlanes = consultaBase.seleccionarCodPlanes(codEscuela);
        int contador = 0;
        while (listaCodPlanes.size() > contador){
            cbox_codigosPlan.addItem(listaCodPlanes.get(contador));
            contador++;
        }
        
    }
    
    /**
     * 
     * @param codCurso
     * @param codReq 
     */
    public void agregarRequisitoACurso(String codCurso, String codReq){      
        for (Curso curso: cursos){
            System.out.println("Codigo del curso: " + curso.getCodCurso());
            if(codCurso.equals(curso.getCodCurso()) == true){
                for (Curso cursoRequisito : cursos){
                    System.out.println("Codigo del requisito: " + cursoRequisito.getCodCurso());
                    if(codReq.equals(cursoRequisito.getCodCurso()) == true){
                        curso.registrarRequisito(cursoRequisito);
                        salidaControlador.insertarRequisitoXCurso(codCurso, codReq); //Persistencia almacenado
                    }
                }    
            }
        }   
    }
    
    /**
     * 
     * @param codCurso
     * @param codCorreq 
     */
    public void agregarCorrequisitoACurso(String codCurso, String codCorreq){      
        for (Curso curso: cursos){
            if(codCurso.equals(curso.getCodCurso()) == true){
                for (Curso cursoCorrequisito : cursos){
                    if(codCorreq.equals(cursoCorrequisito.getCodCurso()) == true){
                        curso.registrarRequisito(cursoCorrequisito);
                        salidaControlador.insertarCorrequisitoXCurso(codCurso, codCorreq); //Persistencia almacenado
                    }
                }    
            }
        }
    }
    
    /**
     * 
     * @param codCurso
     * @return 
     */
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
    
    /**
     * 
     * @param codCurso
     * @return 
     */
    public ArrayList<String> consultarRequisitos(String codCurso){
        return null;
    }
    
    /**
     * 
     * @param table
     * @param escuelaBuscar
     * @throws SQLException 
     */
    public void poblarJTable(JTable table, String escuelaBuscar) throws SQLException{
        //envio la escuela
        
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
    
    //RECORDAR PASAR A CLASE PDF :D
    /**
     * 
     * @param documento
     * @param escuelaBuscar
     * @throws SQLException 
     */
    public void poblarPDF(Document documento,String escuelaBuscar) throws SQLException{
        ResultSet rst = salidaControlador.verPlanDeEstudio(escuelaBuscar);
        
        try{
            String ruta = System.getProperty("user.home");
            //PdfWriter.getInstance(documento, new FileOutputStream(ruta+"\\Documents\\GitHub\\IPPOO\\Reportes\\ReportesBD.pdf"));
            PdfWriter.getInstance(documento, new FileOutputStream(ruta+"\\OneDrive\\Documents\\GitHub\\IPPOO\\Reportes\\ReportesBD.pdf"));
            documento.open();

            PdfPTable tabla = new PdfPTable(7);
            tabla.addCell("Codigo de curso");
            tabla.addCell("Nombre del curso");
            tabla.addCell("Cantidad de Creditos");
            tabla.addCell("Cantidad de horas lectivas");
            tabla.addCell("Escuela asociada");
            tabla.addCell("Curso");
            tabla.addCell("Codigo de plan");

            if(rst.next()){
                do{
                    tabla.addCell(rst.getString(1));
                    tabla.addCell(rst.getString(2));
                    tabla.addCell(rst.getString(3));
                    tabla.addCell(rst.getString(4));
                    tabla.addCell(rst.getString(5));
                    tabla.addCell(rst.getString(6));
                    tabla.addCell(rst.getString(7));

                }while(rst.next());
                documento.add(tabla);


            }else{
                System.out.println("No hay datos");
            }
            documento.close();
        }
        catch(DocumentException | FileNotFoundException e){
            System.out.println(e);
        }
    }
    
    /**
     * 
     * @param propiedades
     * @param correoDestinatario 
     */
    public void poblarCorreo(Properties propiedades,String correoDestinatario){
        try {
            salidaCorreo.generarCorreo(propiedades, correoDestinatario);
        } catch (MessagingException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /**
     * Método que toma los datos en la base y crea los objetos de tipo Escuela
     * @throws SQLException 
     */
    private void crearObjetosEscuela() throws SQLException{
        ResultSet escuelasObtenidas; 
        escuelasObtenidas = consultaBase.CargarDatosEscuelas();
        
        String nombreEscuela;
        String codEscuela;
  
        Escuela nuevaEscuela;
        
        while (escuelasObtenidas.next()){
            nombreEscuela = escuelasObtenidas.getString("nombreEscuela");
            codEscuela = escuelasObtenidas.getString("codEscuela");
            System.out.println("Escuelas creadas a partir de la info de la base");
            System.out.println("nombreEscuela: " + nombreEscuela + " codEscuela: " + codEscuela);
            nuevaEscuela = new Escuela(nombreEscuela, codEscuela);
            escuelas.add(nuevaEscuela);
        }
    }

    /**
     * Método que toma los datos en la base y crea los objetos de tipo Curso y su relación con los objetos de tipo Escuela
     * @throws SQLException
     */
    private void crearObjetosCurso() throws SQLException{
        ResultSet cursosObtenidos; 
        cursosObtenidos = consultaBase.CargarDatosCursos();
        
        
        String nombreCurso;
        String codCurso;
        int cantCreditos;
        int cantHorasLectivas;
        //cambiar porque existe la tabla intermedia
        String codEscuela;
        
        Curso nuevoCurso;
        
        while (cursosObtenidos.next()){
            codCurso = cursosObtenidos.getString(1);
            nombreCurso = cursosObtenidos.getString(2);
            cantCreditos = cursosObtenidos.getInt(3);
            cantHorasLectivas = cursosObtenidos.getInt(4);
            codEscuela = cursosObtenidos.getString(5);
            System.out.println("Cursos creados a partir de la info desde la base: \n");
            System.out.println("codCurso: " + codCurso + " nombreCurso: " + nombreCurso + " cantCreditos: " + cantCreditos + " cantHorasLectivas: " + cantHorasLectivas +"\n");
            
            nuevoCurso = new Curso(nombreCurso, codCurso, cantCreditos, cantHorasLectivas);
            cursos.add(nuevoCurso);
            
            for (Escuela escuelaEncontrada : escuelas){
                if(codEscuela.equals(escuelaEncontrada.getCodEscuela()) == true){
                    escuelaEncontrada.asociarCurso(nuevoCurso);
                }
            }
        }
    }
    
     /**
     * Método que relaciona los objetos de tipo Curso que son correquisitos de un curso con su curso respectivo
     * @throws SQLException 
     */
    private void crearRelacionRequisitosCursos() throws SQLException{
        ResultSet requisitosObtenidos; 
        requisitosObtenidos = consultaBase.CargarDatosRequisitos();
        
        String codCurso;
        String codRequisito;
        
        while (requisitosObtenidos.next()){
            codCurso = requisitosObtenidos.getString(1);
            codRequisito = requisitosObtenidos.getString(2);
            
            for (Curso cursoEncontrado : cursos){
                if (codCurso.equals(cursoEncontrado.getCodCurso()) == true){
                    for (Curso cursoReq : cursos){
                        if (codRequisito.equals(cursoReq.getCodCurso()) == true){
                            cursoEncontrado.registrarRequisito(cursoReq);
                        }
                    }
                }
            }   
        } 
    }
    
    /**
     * Método que relaciona los objetos de tipo Curso que son requisitos de un curso con su curso respectivo
     * @throws SQLException 
     */
    private void crearRelacionCorrequisitosCursos() throws SQLException{
        ResultSet correquisitosObtenidos; 
        correquisitosObtenidos = consultaBase.CargarDatosCorrequisitos();
        
        String codCurso;
        String codCorrequisito;
        
        while (correquisitosObtenidos.next()){
            codCurso = correquisitosObtenidos.getString(1);
            codCorrequisito = correquisitosObtenidos.getString(2);
            
            for (Curso cursoEncontrado : cursos){
                if (codCurso.equals(cursoEncontrado.getCodCurso()) == true){
                    for (Curso cursoCorreq : cursos){
                        if (codCorrequisito.equals(cursoCorreq.getCodCurso()) == true){
                            cursoEncontrado.registrarCorrequisito(cursoCorreq);
                        }
                    }
                }
            }   
        } 
    }
    
    /**
     * Método que toma los datos en la base y crea los objetos de tipo PlanDeEstudio y su relación con los objetos de tipo Escuela
     * @throws SQLException 
     */
    private void crearObjetosPlanDeEstudio() throws SQLException{
        ResultSet planesObtenidos;
        planesObtenidos = consultaBase.CargarDatosPlanesDeEstudio();
        
        int numPlan;
        //hacer conversion de sql a sql convencional
        Date fechaVigencia; 
        String codEscuela;
        
        while(planesObtenidos.next()){
            numPlan = planesObtenidos.getInt(1);
            fechaVigencia = planesObtenidos.getDate(2);
            codEscuela = planesObtenidos.getString(3);
            for (Escuela escuelaEncontrada : escuelas){
                if (codEscuela.equals(escuelaEncontrada.getCodEscuela()) == true){
                    escuelaEncontrada.agregarPlanesEstudio(numPlan, fechaVigencia);
                }
            }
        }
    }
    
    private void relacionarCursoPlan() throws SQLException{
        ResultSet cursosObtenidosPlan;
        cursosObtenidosPlan = consultaBase.CargarDatosCursosPertenecientesPlan();
        
        String codCurso;
        int numPlan; 
        String semestreActivo;
        
        ArrayList<PlanDeEstudio> planes;
        
        while (cursosObtenidosPlan.next()){
            codCurso = cursosObtenidosPlan.getString(1);
            numPlan = cursosObtenidosPlan.getInt(2);
            semestreActivo = cursosObtenidosPlan.getString(3);
            for (Escuela escuelaEncontrada : escuelas){
                planes = escuelaEncontrada.getPlanesDeEstudio();
                for (PlanDeEstudio plan : planes){
                    if (plan.getCodPlanEstudio() == numPlan){
                        //
                    }
                }
            }
        }
    }
    
    public void generarObjetos(){
        try{
            crearObjetosEscuela(); 
            crearObjetosCurso(); 
            crearRelacionRequisitosCursos();
            crearRelacionCorrequisitosCursos();
            crearObjetosPlanDeEstudio();
            relacionarCursoPlan();
            crearObjetosPlanDeEstudio();
            relacionarCursoPlan();
            System.out.println(escuelas.toString());
            //System.out.println();
        }
        catch(SQLException e){
            e.getErrorCode();
        }
    }
     
}
     
         