package Controlador;

import Connect_BD.Consultas_BaseDatos;
import API.Correo;
import API.PDF;
import Excepciones.CursoDoesNotExistException;
import Excepciones.PlanDeEstudioDoesNotExistException;
import Excepciones.RequisitoDoesNotExistException;
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
 * @version 2.3
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
    private PDF salidaPDF =new PDF();
    
    
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
        //ArrayList<String> listaEscuelas = consultaBase.seleccionarEscuelas();
        
        int contador = 0;
        while (escuelas.size() > contador){
            cbox_escuelas.addItem(escuelas.get(contador).getNombreEscuela());
            System.out.println("sirve cbox");
            contador++;
        }
        
        //excepcion si lista vacia 
    }
    
    /**
     * Vale porque tenemos 2 de escuelas? 
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
                salidaControlador.insertarCurso(nuevoCurso);
                salidaControlador.insertarCursoXEscuela(pCodEscuela, nuevoCurso);
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
        //ArrayList<String> listaCursosDeEscuela = consultaBase.seleccionarCursosDeEscuela(codEscuela);
        ArrayList<Curso> cursosEscuela = new ArrayList<>();
        
        int contador = 0;
        
        for (Escuela escuelaEncontrada : escuelas){
            System.out.println(codEscuela + "abeja");
            System.out.println(escuelaEncontrada.getCodEscuela() + "abeja2");
            if(codEscuela.equals(escuelaEncontrada.getCodEscuela()) == true){
                cursosEscuela = escuelaEncontrada.getCursos();
                System.out.println(cursosEscuela);
                while (cursosEscuela.size() > contador){
                    cbox_cursos.addItem(cursosEscuela.get(contador).getCodCurso());
                    contador++;
                    System.out.println("Contador " + contador);
                }
            }
        }
       
    //excepcion si lista vacia 
    }
    
    /**
     * 
     * @param cbox_cursos 
     */
    public void poblarCboxCursos(JComboBox cbox_cursos){
        //ArrayList<String> listaCursos = consultaBase.seleccionarCursos();

        int contador = 0;
        while (cursos.size() > contador){
            cbox_cursos.addItem(cursos.get(contador).getCodCurso());
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
    private ArrayList<Curso> consultarRequisitos(String codCurso){
        ArrayList<Curso> requisitos = new ArrayList<>();
        for (Curso curso : cursos){
            if(codCurso.equals(curso.getCodCurso()) == true){
                requisitos = curso.getRequisitos();
                break;
            }
        }
        return requisitos;
    }
    
    public void poblarTablaRequisitos(String codCursoReqs, JTable tablaRequisitos){
        ArrayList<Curso> requisitos = consultarRequisitos(codCursoReqs);
        
        DefaultTableModel modelo = (DefaultTableModel)tablaRequisitos.getModel();
        
        Object datosFila[] = new Object[4];
        
        for (int i = 0; i < requisitos.size(); i++){
            datosFila[0] = requisitos.get(i).getCodCurso();
            datosFila[1] = requisitos.get(i).getNombreCurso();
            datosFila[2] = requisitos.get(i).getCantCreditos();
            datosFila[3] = requisitos.get(i).getCantHorasLectivas();
             
             modelo.addRow(datosFila);
        }   
    }
    
    public void poblarCBoxRequisitos(JComboBox jCBRequisitosEliminar, String codCursoReqs){
        ArrayList<Curso> requisitos = consultarRequisitos(codCursoReqs);
        
        
        int contador = 0;
        while (requisitos.size() > contador){
            jCBRequisitosEliminar.addItem(requisitos.get(contador).getCodCurso());
            contador++;
        }
        
    }
    
    /**
     * 
     * @param codCurso
     * @return 
     */
    private ArrayList<Curso> consultarCorrequisitos(String codCurso){
        ArrayList<Curso> correquisitos = new ArrayList<>();
        for (Curso curso : cursos){
            if(codCurso.equals(curso.getCodCurso()) == true){
                correquisitos = curso.getCorrequisitos();
                break;
            }
        }
        return correquisitos;
    }
    
    public void poblarTablaCorrequisitos(String codCursoCorreqs, JTable tablaCorrequisitos){
        ArrayList<Curso> correquisitos = consultarCorrequisitos(codCursoCorreqs);
        
        DefaultTableModel modeloCorreq = (DefaultTableModel)tablaCorrequisitos.getModel();
        
        Object datos[] = new Object[4];
        
        for (int i = 0; i < correquisitos.size(); i++){
            datos[0] = correquisitos.get(i).getCodCurso();
            datos[1] = correquisitos.get(i).getNombreCurso();
            datos[2] = correquisitos.get(i).getCantCreditos();
            datos[3] = correquisitos.get(i).getCantHorasLectivas();
             
             modeloCorreq.addRow(datos);
        }   
    }
    
    public ArrayList <PlanDeEstudio> consultarPlanesConCiertoCurso(String codCurso) throws CursoDoesNotExistException{
        ArrayList <PlanDeEstudio> planesEscuela;
        ArrayList <Bloque> bloquesPlan;
        ArrayList <Curso> cursosBloque;
        ArrayList <PlanDeEstudio> planesConCurso = new ArrayList<>();
        
        for (Escuela escuelaEncontrada : escuelas){
            planesEscuela = escuelaEncontrada.getPlanesDeEstudio();
            for (PlanDeEstudio planEncontrado : planesEscuela){
                bloquesPlan = planEncontrado.getBloques();
                for (Bloque bloqueEncontrado : bloquesPlan){
                    try{
                        bloqueEncontrado.buscarCursoBloque(codCurso);
                        planesConCurso.add(planEncontrado);
                    }
                    catch(CursoDoesNotExistException e){
                        e.mensajeError();
                    }finally{
                        cursosBloque = bloqueEncontrado.getCursos();
                            for (Curso cursoEncontrado : cursosBloque){
                                try{
                                    cursoEncontrado.buscarRequisito(codCurso);
                                }
                                catch(RequisitoDoesNotExistException eReq){
                                    eReq.mensajeError();
                                }    
                        }
                    }

                }
            }
        }
        return planesConCurso;
    }
    
    
    public void poblarTablaPlanesCiertoCurso(String codCurso, JTable tablaPlanes){
        try{
            ArrayList<PlanDeEstudio> planesConCurso = consultarPlanesConCiertoCurso(codCurso);
            DefaultTableModel modelo = (DefaultTableModel)tablaPlanes.getModel();
        
            Object datos[] = new Object[2];
        
            for (int i = 0; i < planesConCurso.size(); i++){
                datos[0] = planesConCurso.get(i).getCodPlanEstudio();
                datos[1] = planesConCurso.get(i).getFechaVigencia();

                modelo.addRow(datos);
            } 
        }catch(CursoDoesNotExistException e){
            e.mensajeError();
        }
        
        
  
    }
    
    
    /**
     * 
     * @param table
     * @param escuelaBuscar
     * @throws SQLException 
     */
    public void poblarCursoEnPlan(JTable table, String escuelaBuscar) throws SQLException{
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
    
    
    /**
     * 
     * @param documento
     * @param escuelaBuscar
     * @throws SQLException 
     */
    public void poblarPDF(Document documento,String escuelaBuscar) throws SQLException{
        ResultSet rst = salidaControlador.verPlanDeEstudio(escuelaBuscar);
        
        this.salidaPDF.generarPDF(documento,rst);
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
    
    public void poblarCBCursosXPlan(JComboBox jCBCursosAsEliminar, String escuelaBuscar, int planBuscar) throws SQLException {
        //primero debo de  buscar en la escuela 
        try{
           for (Escuela escuelaEncontrada : escuelas) {
            if (escuelaBuscar.equals(escuelaEncontrada.getCodEscuela()) == true) {
                
                ArrayList<PlanDeEstudio> planesTotales = escuelaEncontrada.getPlanesDeEstudio();
                for (int i = 0; i <= planesTotales.size(); i++) {
                    if (planesTotales.get(i).getCodPlanEstudio() == planBuscar) {
                        
                        ArrayList<Bloque> bloqueEncontrados = planesTotales.get(i).getBloques();
                        for (int y = 0; y <= bloqueEncontrados.size(); y++) {
                            
                            for (int j = 0; j <= bloqueEncontrados.size(); j++) {
                                System.out.println(bloqueEncontrados.size());
                                jCBCursosAsEliminar.addItem(bloqueEncontrados.get(y).getCursos().get(j));
                                System.out.println(bloqueEncontrados.get(y).getCursos().get(j));
                            }

                        }

                    }

                }

                //segundo debo de buscar el plan en la escuela 
                //debo buscar los cursos asociados
            }
        }
        }catch(Exception e){
            System.out.println(e);
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
        
        Curso nuevoCurso;
        
        while (cursosObtenidos.next()){
            codCurso = cursosObtenidos.getString(1);
            nombreCurso = cursosObtenidos.getString(2);
            cantCreditos = cursosObtenidos.getInt(3);
            cantHorasLectivas = cursosObtenidos.getInt(4);
            System.out.println("Cursos creados a partir de la info desde la base: \n");
            System.out.println("codCurso: " + codCurso + " nombreCurso: " + nombreCurso + " cantCreditos: " + cantCreditos + " cantHorasLectivas: " + cantHorasLectivas +"\n");
            
            nuevoCurso = new Curso(nombreCurso, codCurso, cantCreditos, cantHorasLectivas);
            cursos.add(nuevoCurso);
        }
    }
    
    private void crearRelacionCursosEscuela() throws SQLException{
        ResultSet cursosObtenidosEscuela; 
        cursosObtenidosEscuela = consultaBase.CargarDatosCursosDeEscuela();
        
        String codCurso;
        String codEscuela;
        
        try{
            while (cursosObtenidosEscuela.next()){
                codCurso = cursosObtenidosEscuela.getString(1);
                codEscuela = cursosObtenidosEscuela.getString(2);
                for (Escuela escuelaEncontrada : escuelas){
                    if(codEscuela.equals(escuelaEncontrada.getCodEscuela()) == true){
                        for (Curso cursoEncontrado : cursos ){
                            if(codCurso.equals(cursoEncontrado.getCodCurso()) == true){
                                escuelaEncontrada.asociarCurso(cursoEncontrado);
                            }
                        }
                    }
                }
            }
        }catch(SQLException e){
            e.getErrorCode();
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
        ArrayList<Bloque> bloques;
        
        while (cursosObtenidosPlan.next()){
            codCurso = cursosObtenidosPlan.getString(1);
            numPlan = cursosObtenidosPlan.getInt(2);
            semestreActivo = cursosObtenidosPlan.getString(3);
            for (Escuela escuelaEncontrada : escuelas){
                planes = escuelaEncontrada.getPlanesDeEstudio();
                for (PlanDeEstudio plan : planes){
                    if (plan.getCodPlanEstudio() == numPlan){
                        plan.agregarBloque(semestreActivo);
                        bloques = plan.getBloques();
                        for (Bloque bloqueEncontrado : bloques){
                            if (semestreActivo.equals(bloqueEncontrado.getIdBloque())){
                                for (Curso cursoEncontrado : cursos){
                                    if(codCurso.equals(cursoEncontrado.getCodCurso()) == true){
                                        bloqueEncontrado.agregarCurso(cursoEncontrado);
                                    }
                                }                                
                            }
                        }
                    }
                }
            }
        }
    }
    
    public String generarObjetos(){
        String msg = "";
        try{
            crearObjetosEscuela(); 
            crearObjetosCurso(); 
            crearRelacionCursosEscuela();
            crearRelacionRequisitosCursos();
            crearRelacionCorrequisitosCursos();
            crearObjetosPlanDeEstudio();
            relacionarCursoPlan();
            System.out.println(escuelas.toString());
            System.out.println(cursos.toString());
        }
        catch(SQLException e){
            msg += "¡Excepción! ";
            msg += e.getMessage();
            return msg;
        }
        return msg;
    }
    
    
    public void eliminarRequisitoCurso(String cursoEliminar,String requisitoEliminar){
        for (Curso curso: cursos){
            //System.out.println("Codigo del curso: " + curso.getCodCurso());
            if(cursoEliminar.equals(curso.getCodCurso()) == true){
                for (Curso cursoRequisito : cursos){
                    //System.out.println("Codigo del requisito: " + cursoRequisito.getCodCurso());
                    if(requisitoEliminar.equals(cursoRequisito.getCodCurso()) == true){
                        cursoRequisito.eliminarRequisito(curso);//Elimino la relaci[on
                        this.consultaBase.eliminarRequisitos(cursoEliminar, requisitoEliminar);    
                    }
                }    
            }
        }    
    }
    
    public void eliminarCursoPlanEstudio(String codEscuela, int numPlan, String cursoEliminar) throws CursoDoesNotExistException, 
            PlanDeEstudioDoesNotExistException{
        
        /*holis :D
        sugerencia para hacer el método:
        primero agarras la escuela seleccionada del combobox verdad
        entonces puedes recorrer la lista general de escuelas para encontrarla con un ciclo
        for (Escuela escuelaSeleccionada : escuelas){
            if (codEscuela.equals(escuelaSeleccionada.getCodEscuela()) == true){
                ya aquí estarías dentro del objeto de la escuela, lo atrapaste con el if
                    declarar el objeto de tipo plan que vamos a buscar:
        
                    PlanDeEstudio planEncontrado;
        
                    ahora puedes buscar buscar el plan con el siguiente metodo y asignarle el valor a la variable plan
        
                    planEncontrado = escuelaSeleccionada.buscarPlanEscuela(numPlan)
        
                    ya una vez dentro del plan
                        ahora hay que buscar el curso, que solo va a estar una vez en el plan entonces 
                        hay que ir bloque por bloque
                        con algo asi:
                        ArrayList<Bloque> bloquesDePlan;
                        bloquesDePlan = planSeleccionado.getBloques();
                        y luego el ciclo for
                        for (Bloque bloqueSeleccionado : bloquesDePlan)
                            y ya aca dentro declaramos un objeto de tipo Curso
        
                            Curso cursoEncontrado;
        
                            y le asignamos su valor con el metodo siguiente
        
                            cursoEncontrado = buscarCursoBloque(cursoEliminar);
        
                            yyy ahora si aqui ya tienes el objeto curso que hay que eliminar asi que aca 
                            nada mas agregas el metodo para eliminar el curso de un plan, pasandole el objeto cursoEncontrado
        
                            bloqueSeleccionado.eliminarCurso(cursoEncontrado);
        
                            y listoooo :D
            }
        }
        recorda poner todo lo anterior dentro de un try y atrapar las dos excepciones 
        
        catch (CursoDoesNotExistException eCurso){
            eCurso.mensajeError();
        } catch(PlanDeEstudioDoesNotExistException ePlan){
            ePlan.mensajeError();
        }
        
        */    
    }    
    
    public void eliminarCurso(String cursoEliminar){
        //cualquier cosa que necesites con este me avisas
    }
     
}
     
         