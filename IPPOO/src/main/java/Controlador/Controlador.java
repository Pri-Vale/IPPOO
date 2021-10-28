package Controlador;

import Connect_BD.Consultas_BaseDatos;
import API.Correo;
import API.PDF;
import Excepciones.CorrequisitoAlreadyExistsException;
import Excepciones.CursoAlreadyExistsException;
import Excepciones.CursoDoesNotExistException;
import Excepciones.EscuelaAlreadyExistsException;
import Excepciones.PlanDeEstudioAlreadyExistsException;
import Excepciones.RequisitoAlreadyExistsException;
import Modelo.logicaDeNegocio.Bloque;
import Modelo.logicaDeNegocio.Escuela;
import Modelo.logicaDeNegocio.Curso;
import Modelo.logicaDeNegocio.PlanDeEstudio;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Label;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Abstracción de la clase Controlador
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 2.3
 * @since 1.0
 */
public class Controlador {
    
    //Atributos de instancia
    private ArrayList<Escuela> escuelas = new ArrayList<Escuela>();
    private ArrayList<Curso> cursos = new ArrayList<Curso>();
    private Correo salidaCorreo=new Correo();
    private PDF salidaPDF =new PDF();
    
    //Atributos de clase
    public static Consultas_BaseDatos salidaControlador = new Consultas_BaseDatos() ;
    
    /**
     * Método que retorna el objeto de tipo Escuela encontrado en el registro de escuelas del programa a partir de su código
     * @param codEscuela el código de la escuela que se quiere encontrar
     * @return objeto de tipo Escuela
     */
    public Escuela buscarEscuela(String codEscuela){
        Escuela escuela = null;
        for (Escuela escuelaEncontrada : escuelas){
            if (codEscuela.equals(escuelaEncontrada.getCodEscuela()) == true){
                escuela = escuelaEncontrada;
            }
        }
        return escuela;
    }
    
    /**
     * Método que retorna el objeto de tipo Curso encontrado en el registro de cursos del programa a partir de su código
     * @param pCodCurso el código del curso que se quiere encontrar
     * @return objeto de tipo Escuela
     */
    public Curso buscarCurso(String pCodCurso){
        Curso curso = null;
        for (Curso cursoEncontrado : cursos){
            if(pCodCurso.equals(cursoEncontrado.getCodCurso()) == true){
                curso = cursoEncontrado;
            }
        }
        return curso;
    }
    
    
    /**
     * Método que permite crear un objeto de tipo Escuela
     * @param pNombreEscuela el nombre de la escuela que se quiere crear
     * @param pCodEscuela el código de la escuela que se quiere crear
     * @return verdadero si la escuela se creó exitosamente, falso en caso contrario
     * @throws SQLException si la inserción de la escuela en la base de datos falla
     * @throws Excepciones.EscuelaAlreadyExistsException  si la escuela ya existe 
     */
    public boolean crearEscuela(String pNombreEscuela, String pCodEscuela) throws SQLException, EscuelaAlreadyExistsException{
        try{
            if (buscarEscuela(pCodEscuela) == null){
                Escuela escuela= new Escuela(pNombreEscuela, pCodEscuela);
                escuelas.add(escuela);
                salidaControlador.insertarEscuela(escuela);
                System.out.println(escuelas.toString());
                return true;
            }else{
                throw new EscuelaAlreadyExistsException(pCodEscuela);
            }
        }catch(NullPointerException a){
          return false;  
        }                            
    }
    

    /**
     * Método para poblar un JComboBox en la interfaz gráfica con las escuelas registradas en el sistema
     * @param cbox_escuelas el JComboBox que se poblará a partir de los nombres de las escuelas registradas
     */
    public void poblarCboxEscuelas(JComboBox cbox_escuelas){
        int contador = 0;
        while (escuelas.size() > contador){
            cbox_escuelas.addItem(escuelas.get(contador).getNombreEscuela());
            System.out.println("sirve cbox");
            contador++;
        } 
    }
    
  /**
     * Método para obtener el código de una escuela a partir de su nombre
     * @param nombreEscuela el nombre de la escuela de la cual se desea obtener su código
     * @return el código de la escuela 
     */
    public String obtenerCodEscuela(String nombreEscuela){
        String codEscuela = salidaControlador.seleccionarCodEscuela(nombreEscuela);
        return codEscuela;
    }
   
    /**
     * Método para crear un objeto de tipo Curso
     * @param pCodEscuela el código de la escuela a la que pertenecerá el curso por crear
     * @param pNombreCurso el nombre del curso por crear
     * @param pCodCurso el código del curso por crear
     * @param pCantCreditos la cantidad de créditos del curso por crear
     * @param pCantHorasLectivas la cantidad de horas lectivas del curso por crear
     * @throws Excepciones.CursoAlreadyExistsException si el curso ya se encuentra registrado en el sistema 
     */
    public void crearCurso(String pCodEscuela, String pNombreCurso, String pCodCurso, int pCantCreditos, int pCantHorasLectivas) throws CursoAlreadyExistsException{        
        for (Escuela escuelaEncontrada : escuelas){ 
            if (escuelaEncontrada != null){
                if (pCodEscuela.equals(escuelaEncontrada.getCodEscuela()) == true){
                    if (escuelaEncontrada.buscarCursosEscuela(pCodCurso) == null){
                        Curso nuevoCurso = new Curso(pNombreCurso, pCodCurso, pCantCreditos, pCantHorasLectivas);
                        escuelaEncontrada.asociarCurso(nuevoCurso);
                        cursos.add(nuevoCurso);
                        salidaControlador.insertarCurso(nuevoCurso);
                        salidaControlador.insertarCursoXEscuela(pCodEscuela, nuevoCurso);
                        break;
                    }else{
                        throw new CursoAlreadyExistsException(pCodCurso);
                    }
                }
            }
        }  
    }
    
    /**
     * Método para crear un objeto de tipo PlanDeEstudio
     * @param pNombreEscuela el nombre de la escuela a la que pertenecerá el plan de estudios por crear
     * @param pCódigoPlan el número de 4 dígitos del plan por crear
     * @param pVigenciaPlan la fecha de vigencia del plan por crear
     * @param pCódigoCurso el código del curso que se le agregará al plan de estudio 
     * @param pBloqueActivo el número de semestre activo al que se le asignará el curso del plan de estudios
     * @throws Excepciones.CursoAlreadyExistsException si el curso por registrar ya pertenece al plan
     * @throws Excepciones.CursoDoesNotExistException si el curso por registrar no existe en el sistema
     * @throws Excepciones.PlanDeEstudioAlreadyExistsException  si el plan de estudio con ese número de plan ya existe en esa escuela u otra
     */
    public void crearPlanEstudios(String pNombreEscuela,int pCódigoPlan,Date pVigenciaPlan,String pCódigoCurso,String pBloqueActivo) 
            throws CursoAlreadyExistsException, CursoDoesNotExistException, PlanDeEstudioAlreadyExistsException{
        
        String codEscuela = obtenerCodEscuela(pNombreEscuela); //buscar el codigo de una escuela a partir de su nombre
        Escuela escuelaEncontrada = buscarEscuela(codEscuela); //buscar el objeto Escuela en la que vamos a crear el plan de estudios
        
        PlanDeEstudio planExistente = escuelaEncontrada.buscarPlanEscuela(pCódigoPlan);
        boolean planYaExiste = false;
        //buscar que el plan no exista ya en otras escuelas
        for (Escuela escuela : escuelas){
            for (PlanDeEstudio planExiste : escuela.getPlanesDeEstudio()){
                if (pCódigoPlan == planExiste.getCodPlanEstudio()){
                    if (escuela.getCodEscuela().equals(codEscuela) == false){
                        planYaExiste = true;
                    }else{
                        planYaExiste = false;
                    }   
                }
            }
        }
        if (planYaExiste == false){
            escuelaEncontrada.agregarPlanesEstudio(pCódigoPlan, pVigenciaPlan); //relación de composición: la creación del plan se hace dentro de Escuela

            PlanDeEstudio nuevoPlan = escuelaEncontrada.buscarPlanEscuela(pCódigoPlan); //buscar el objeto PlanDeEstudio que acabamos de crear en escuelaEncontrada

            nuevoPlan.agregarBloque(pBloqueActivo); //relación de composición: la creación del bloque se hace dentro del plan

            Bloque bloque = nuevoPlan.buscarBloquePlan(pBloqueActivo); //buscar el objeto Bloque que acabamos de crear en nuevoPlan

            Curso cursoBloque = bloque.buscarCursoBloque(pCódigoCurso); //buscar el objeto Curso 

            ArrayList<PlanDeEstudio> planesEscuela = escuelaEncontrada.getPlanesDeEstudio();

            boolean cursoExisteEnPlan = false; 
            //buscar que el curso no exista en ese plan de estudios:
            for (PlanDeEstudio plan : planesEscuela){
                for (Bloque bloqueEncontrado : plan.getBloques()){
                    if (bloqueEncontrado.buscarCursoBloque(pCódigoCurso) != null){
                        cursoExisteEnPlan = true;
                    }
                }
            }

            if (cursoExisteEnPlan == false){
                Curso nuevoCurso = buscarCurso(pCódigoCurso);
                if (nuevoCurso != null){
                    bloque.agregarCurso(nuevoCurso); //relación de agregación: la creación del curso en el bloque se hace pasando el objeto
                    //Persistencia almacenado de plan de estudios
                    salidaControlador.insertarPlanEstudio(nuevoPlan,pNombreEscuela);
                    salidaControlador.insertarCursoXPlan(pCódigoCurso, nuevoPlan, pBloqueActivo);
                }else{
                    throw new CursoDoesNotExistException(pCódigoCurso);
                }  
            }else{
                throw new CursoAlreadyExistsException(pCódigoCurso);
            }
        }else{
            throw new PlanDeEstudioAlreadyExistsException(pCódigoPlan);
        }
    }
    
    /**
     * Método para poblar un JComboBox con los códigos de los cursos pertenecientes a una escuela en particular
     * @param cbox_cursos el JComboBox por poblar
     * @param codEscuela el código de la escuela a la que pertenecen los cursos 
     */
    public void poblarCboxCursosDeEscuela(JComboBox cbox_cursos, String codEscuela){
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
    }
    
    /**
     * Método para poblar un JComboBox con los códigos de todos registrados en el sistema
     * @param cbox_cursos el JComboBox por poblar
     */
    public void poblarCboxCursos(JComboBox cbox_cursos){
        int contador = 0;
        while (cursos.size() > contador){
            cbox_cursos.addItem(cursos.get(contador).getCodCurso());
            contador++;
        }
    }
    
    /**
     * Método para poblar un JComboBox con los números de plan de estudio de una escuela en particular
     * @param cbox_codigosPlan el JComboBox por poblar
     * @param codEscuela el código de la escuela a la que pertenecen los planes de estudio
     */
    public void poblarCboxCodigosPlan(JComboBox cbox_codigosPlan,String codEscuela){
        ArrayList<String> listaCodPlanes = salidaControlador.seleccionarCodPlanes(codEscuela);
        int contador = 0;
        while (listaCodPlanes.size() > contador){
            cbox_codigosPlan.addItem(listaCodPlanes.get(contador));
            contador++;
        }
    }
    
    /**
     * Método para agregar un requisito a un curso en particular
     * @param codCurso el código del curso al que se le agregará el requisito
     * @param codReq el código del curso que se agregará como requisito de un curso
     * @throws Excepciones.RequisitoAlreadyExistsException si el requisito ya esté relacionado con el curso
     */
    public void agregarRequisitoACurso(String codCurso, String codReq) throws RequisitoAlreadyExistsException{      
        for (Curso curso: cursos){
            if(codCurso.equals(curso.getCodCurso()) == true){
                for (Curso cursoRequisito : cursos){
                    if(codReq.equals(cursoRequisito.getCodCurso()) == true){
                        if (curso.buscarRequisito(codReq) == null){
                            curso.registrarRequisito(cursoRequisito);
                            salidaControlador.insertarRequisitoXCurso(codCurso, codReq); //Persistencia almacenado
                        }else{
                            throw new RequisitoAlreadyExistsException(codCurso, codReq);
                        }
                    }
                }    
            }
        }   
    }
    
    /**
     * Método para agregar un correquisito a un curso en particular
     * @param codCurso el código del curso al que se le agregará el correquisito
     * @param codCorreq el código del curso que se agregará como correquisito de un curso
     * @throws Excepciones.CorrequisitoAlreadyExistsException si el correquisito ya esté relacionado con el curso
     */
    public void agregarCorrequisitoACurso(String codCurso, String codCorreq) throws CorrequisitoAlreadyExistsException{      
        for (Curso curso: cursos){
            if(codCurso.equals(curso.getCodCurso()) == true){
                for (Curso cursoCorrequisito : cursos){
                    if(codCorreq.equals(cursoCorrequisito.getCodCurso()) == true){
                        if (curso.buscarCorrequisito(codCorreq) == null){
                            curso.registrarCorrequisito(cursoCorrequisito);
                            salidaControlador.insertarCorrequisitoXCurso(codCurso, codCorreq); //Persistencia almacenado
                        }else{
                            throw new CorrequisitoAlreadyExistsException(codCurso, codCorreq);
                        }
                    }
                }    
            }
        }
    }
    
    /**
     * Método para obtener el nombre de un curso a partir de su código de curso
     * @param codCurso el código del curso del que se quiere obtener el nombre
     * @return el nombre del curso obtenido a partir de su código 
     */
    public String obtenerNombreCurso(String codCurso){
        ArrayList<String> listaNombreCurso = salidaControlador.seleccionarNombreCurso(codCurso);
        int contador = 0;
        String nombreCurso = null;
        while (listaNombreCurso.size() > contador){
            nombreCurso = listaNombreCurso.get(contador);
            contador++;
        }
        return nombreCurso;
    }
    
    /**
     * Método que permite consultar los requisitos de un curso en particular
     * @param codCurso código del curso del que se quieren consultar los requisitos
     * @return los requisitos del curso
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
    
    /**
     * Método para poblar una JTable con los requisitos consultados de un curso en particular
     * @param codCursoReqs curso del cual se van a consultar los requisitos
     * @param tablaRequisitos JTable que se va a poblar con los requisitos del curso consultado
     */
    public void poblarTablaRequisitos(String codCursoReqs, JTable tablaRequisitos){
        DefaultTableModel modelo = new DefaultTableModel();
        ArrayList<Curso> requisitos = consultarRequisitos(codCursoReqs);
        ArrayList<Object> columna = new ArrayList<>();
        
        columna.add("Código Curso");
        columna.add("Nombre Curso");
        columna.add("Cantidad de creditos");
        columna.add("Cantidad de horas lectivas");
        
        for(Object col: columna){
            modelo.addColumn(col);
        }
        
        Object datosFila[] = new Object[4];
        
        for (int i = 0; i < requisitos.size(); i++){
            datosFila[0] = requisitos.get(i).getCodCurso();
            datosFila[1] = requisitos.get(i).getNombreCurso();
            datosFila[2] = requisitos.get(i).getCantCreditos();
            datosFila[3] = requisitos.get(i).getCantHorasLectivas();
             
             modelo.addRow(datosFila);
        } 
        tablaRequisitos.setModel(modelo);
    }
    
    /**
     * Método para poblar un JComboBox con los requisitos de un curso en particular
     * @param jCBRequisitosEliminar JComboBox que se poblará con los códigos de los requisitos del curso consultado
     * @param codCursoReqs código del curso del que se quieren consultar los requisitos
     */
    public void poblarCBoxRequisitos(JComboBox jCBRequisitosEliminar, String codCursoReqs){
        ArrayList<Curso> requisitos = consultarRequisitos(codCursoReqs);
        int contador = 0;
        while (requisitos.size() > contador){
            jCBRequisitosEliminar.addItem(requisitos.get(contador).getCodCurso());
            contador++;
        }   
    }
    
    /**
     * Método que permite consultar los correquisitos de un curso en particular
     * @param codCurso el código del curso del que se quieren consultar los correquisitos
     * @return los correquisitos del curso
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
    
    /**
     * Método para poblar una JTable con los correquisitos consultados de un curso en particular
     * @param codCursoCorreqs curso del cual se van a consultar los correquisitos
     * @param tablaCorrequisitos JTable que se va a poblar con los correquisitos del curso consultado
     */
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
    
    //REVISAR
    /**
     * Método que permite consultar los planes de estudio que contienen un curso en particular en sus bloques de estudio
     * @param codCurso código del curso que se va a consultar
     * @return la lista de planes de estudio que contienen el curso consultado
     */
    private ArrayList <PlanDeEstudio> consultarPlanesConCiertoCurso(String codCurso){
        ArrayList <PlanDeEstudio> planesEscuela;
        ArrayList <Bloque> bloquesPlan;
        ArrayList <Curso> cursosBloque;
        ArrayList <PlanDeEstudio> planesConCurso = new ArrayList<>();
        
        for (Escuela escuelaEncontrada : escuelas){
            planesEscuela = escuelaEncontrada.getPlanesDeEstudio();
            for (PlanDeEstudio planEncontrado : planesEscuela){
                bloquesPlan = planEncontrado.getBloques();
                for (Bloque bloqueEncontrado : bloquesPlan){
                    bloqueEncontrado.buscarCursoBloque(codCurso);
                    planesConCurso.add(planEncontrado);
                    cursosBloque = bloqueEncontrado.getCursos();
                    for (Curso cursoEncontrado : cursosBloque){
                        cursoEncontrado.buscarRequisito(codCurso);
                    }
                }    
            }
        }
        return planesConCurso;
    }
    
    /**
     * Método para poblar una JTable con la información de los planes que contienen un curso en particular
     * @param codCurso código del curso que se desea consultar
     * @param tablaPlanes JTabla que se poblará con la información obtenida de los planes de estudio
     */
    public void poblarTablaPlanesCiertoCurso(String codCurso, JTable tablaPlanes){
        DefaultTableModel modelo = new DefaultTableModel();
        
        ArrayList<PlanDeEstudio> planesConCurso = consultarPlanesConCiertoCurso(codCurso);
        ArrayList<Object> columna = new ArrayList<>();
        
        columna.add("Código Plan Estudios");
        columna.add("Fecha de Vigencia");
        
        for(Object col: columna){
            modelo.addColumn(col);
        }

        Object datos[] = new Object[2]; 
        for (int i = 0; i < planesConCurso.size(); i++){
                datos[0] = planesConCurso.get(i).getCodPlanEstudio();
                datos[1] = planesConCurso.get(i).getFechaVigencia();

                modelo.addRow(datos);
        } 
        tablaPlanes.setModel(modelo);           
    }
    
    
    /**
     * Metodo para poblar un Jtable con los cursos que se encuentran en un plan
     * @param table Jtable que se poblara con la información obtenida de los cursos
     * @param escuelaBuscar codigo de la escuela que se desean consultar los planes
     * @param Label label donde se mostraran los resultados
     * @throws SQLException 
     */
    public void poblarCursoEnPlan(JTable table, String escuelaBuscar,Label txtnumeroCursos, Label txtCantCreditos) throws SQLException{
        System.out.println(escuelaBuscar);
        
        ResultSet rst = salidaControlador.verPlanDeEstudio(escuelaBuscar);
        ResultSetMetaData rsMd= rst.getMetaData();
        
        
        int numeroColumnas= rsMd.getColumnCount();
        int numeroCursos = 0;
        int cantCreditos = 0;
        
        DefaultTableModel modelo = new DefaultTableModel();
        table.setModel(modelo);
        
        for (int x=1; x<=numeroColumnas; x++){
            modelo.addColumn(rsMd.getColumnLabel(x));    
        }
        
        while(rst.next()){
            Object [] fila= new Object[numeroColumnas];
            numeroCursos++;
            cantCreditos +=Integer.parseInt(rst.getObject(3).toString());
            for(int y=0; y<numeroColumnas;y++){
                fila[y]=rst.getObject(y+1);
                
            }
            modelo.addRow(fila);
            txtnumeroCursos.setText(String.valueOf(numeroCursos));
            txtCantCreditos.setText(String.valueOf(cantCreditos));
            
        }    
    }
    
    
    /**
     * Metodo que llama a la función de generación de PDF
     * @param documento Objeto de la libreria que permite la creación del documento
     * @param escuelaBuscar Código de la escuela sobre la que se desea realizar el reporte 
     * @throws SQLException 
     */
    public void poblarPDF(Document documento,String escuelaBuscar) throws SQLException{
        ResultSet rst = salidaControlador.verPlanDeEstudio(escuelaBuscar);
        this.salidaPDF.generarPDF(documento,rst);
    }
    
    
    /**
     * Metodo que llama a la función de generación de correo.
     * @param propiedades Objeto de la libreria que permite establecer los componentes del correo
     * @param correoDestinatario Correo de la persona destinataria del correo
     */
    public void poblarCorreo(Properties propiedades,String correoDestinatario){
        try {
            salidaCorreo.generarCorreo(propiedades, correoDestinatario);
        } catch (MessagingException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
  
    /**
     * Metodo que permite poblar un ComboBox con los cursos en un plan
     * @param jCBCursosAsEliminar ComboBox que se llenara con la información
     * @param escuelaBuscar Código de la escuela a buscar
     * @param planBuscar Plan sobre el que se desea buscar
     * @throws SQLException si la consulta a la base de datos no se realiza con éxito 
     */
    public void poblarCBCursosXPlan(JComboBox jCBCursosAsEliminar, String escuelaBuscar, int planBuscar) throws SQLException {
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
                                String a = bloqueEncontrados.get(y).getCursos().get(j).getCodCurso();
                                jCBCursosAsEliminar.addItem(a);
                                System.out.println(a);
                            }
                        }
                    }

                }
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
        escuelasObtenidas = salidaControlador.CargarDatosEscuelas();
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
        cursosObtenidos = salidaControlador.CargarDatosCursos();
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
    
    /**
     * Método que relaciona los objetos de tipo Curso con la escuela a la que pertenecen
     * @throws SQLException si la consulta a la base de datos no se realiza con éxito 
     */
    private void crearRelacionCursosEscuela() throws SQLException{
        ResultSet cursosObtenidosEscuela; 
        cursosObtenidosEscuela = salidaControlador.CargarDatosCursosDeEscuela();
        
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
     * @throws SQLException si la consulta a la base de datos no se realiza con éxito 
     */
    private void crearRelacionRequisitosCursos() throws SQLException{
        ResultSet requisitosObtenidos; 
        requisitosObtenidos = salidaControlador.CargarDatosRequisitos();
        
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
     * @throws SQLException si la consulta a la base de datos no se realiza con éxito 
     */
    private void crearRelacionCorrequisitosCursos() throws SQLException{
        ResultSet correquisitosObtenidos; 
        correquisitosObtenidos = salidaControlador.CargarDatosCorrequisitos();
        
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
     * @throws SQLException si la consulta a la base de datos no se realiza con éxito 
     */
    private void crearObjetosPlanDeEstudio() throws SQLException{
        ResultSet planesObtenidos;
        planesObtenidos = salidaControlador.CargarDatosPlanesDeEstudio();
        
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
    
    /**
     * Método que relaciona los objetos de tipo Curso registrados, cargados desde la base de datos, a sus planes de estudio respectivos
     * @throws SQLException si la consulta a la base de datos no se realiza con éxito 
     */
    private void relacionarCursoPlan() throws SQLException{
        ResultSet cursosObtenidosPlan;
        cursosObtenidosPlan = salidaControlador.CargarDatosCursosPertenecientesPlan();
        
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
    
    /**
     * Método para generar los objetos del programa a partir de la persistencia de datos almacenados en la base de datos relacional
     */
    public void generarObjetos(){
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
            e.getMessage();
        }
    }
    
    /**
     * Método que permite eliminar un requisito de un curso 
     * @param cursoEliminar el código del curso del que se desea eliminar el requisito 
     * @param requisitoEliminar el código del requisito que se quiere eliminar del curso
     */
    public void eliminarRequisitoCurso(String cursoEliminar,String requisitoEliminar){
        for (Curso curso: cursos){
            //System.out.println("Código del curso: " + curso.getCodCurso());
            if(cursoEliminar.equals(curso.getCodCurso()) == true){
                for (Curso cursoRequisito : cursos){
                    //System.out.println("Código del requisito: " + cursoRequisito.getCodCurso());
                    if(requisitoEliminar.equals(cursoRequisito.getCodCurso()) == true){
                        cursoRequisito.eliminarRequisito(curso);//Elimino la relaci[on
                        this.salidaControlador.eliminarRequisitos(cursoEliminar, requisitoEliminar);    
                    }
                }    
            }
        }    
    }
    
    /**
     * Método para eliminar un curso perteneciente a un plan de estudios
     * @param codEscuela el código de la escuela a la que pertenece el plan de estudios
     * @param numPlan el número del plan de estudios del que ser quiere eliminar el curso
     * @param cursoEliminar el código del curso que se quiere eliminar del plan de estudios
     */
    public void eliminarCursoPlanEstudio(String codEscuela, int numPlan, String cursoEliminar) {
        for (Escuela escuelaSeleccionada : escuelas) {
            if (codEscuela.equals(escuelaSeleccionada.getCodEscuela()) == true) {
                //declarar el objeto de tipo plan que vamos a buscar:
                PlanDeEstudio planEncontrado;
                planEncontrado = escuelaSeleccionada.buscarPlanEscuela(numPlan);
                ArrayList<Bloque> bloquesDePlan;
                bloquesDePlan = planEncontrado.getBloques();
                for (Bloque bloqueSeleccionado : bloquesDePlan) {
                    Curso cursoEncontrado;
                    cursoEncontrado = bloqueSeleccionado.buscarCursoBloque(cursoEliminar);
                    if (codEscuela.equals(escuelaSeleccionada.getCodEscuela()) == true) {
                        bloqueSeleccionado.eliminarCurso(cursoEncontrado);
                        String numConvString=Integer.toString(numPlan);  
                        this.salidaControlador.eliminarCursoXPlanEstudio(cursoEliminar,numConvString);
                        System.out.println("Eliminado");
                    }
                }
            }
        }
    }
    
    /**
     * Método que permite eliminar un curso de una escuela, mientras que no exista en ningún plan de estudios
     * @param codEscuela el código de la escuela a la que pertenece el curso
     * @param cursoEliminar el código del curso que se desea eliminar
     */
    public void eliminarCurso(String codEscuela, String cursoEliminar) {
        //cualquier cosa que necesites con este me avisas
        ArrayList<PlanDeEstudio> planEscuela;
        for (Escuela escuelaSeleccionada : escuelas) {
            if (codEscuela.equals(escuelaSeleccionada.getCodEscuela()) == true) {
                planEscuela = escuelaSeleccionada.getPlanesDeEstudio();
                for (PlanDeEstudio planesEncontrado : planEscuela) {

                    ArrayList<Bloque> bloquesDePlan;
                    bloquesDePlan = planesEncontrado.getBloques();

                    for (Bloque bloqueSeleccionado : bloquesDePlan) {

                        Curso cursoEncontrado;

                        cursoEncontrado = bloqueSeleccionado.buscarCursoBloque(cursoEliminar);
                        if (codEscuela.equals(escuelaSeleccionada.getCodEscuela()) == false) {

                        } else if (codEscuela.equals(escuelaSeleccionada.getCodEscuela()) == true) {
                            Curso cursoEncontrado2;
                            cursoEncontrado2 = escuelaSeleccionada.buscarCursosEscuela(cursoEliminar);
                            cursos.remove(cursoEncontrado2);

                            this.salidaControlador.eliminarCurso(cursoEliminar);
                            System.out.println("Eliminado curso de Escuela");

                            break;
                        }
                    }
                }
            }
        }
    }  
}