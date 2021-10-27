package Connect_BD;

import Modelo.logicaDeNegocio.Escuela;
import Modelo.logicaDeNegocio.Curso;
import Modelo.logicaDeNegocio.PlanDeEstudio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;
import java.sql.ResultSetMetaData;

/**
 * Clase Consultas_BaseDatos para realizar consultas a la base de datos
 *
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.8
 * @since 1.0
 */
public class Consultas_BaseDatos {

    Conexion con = new Conexion();

    /**
     *Método que permite realizar la ejecución de un query en la base 
     * @param query Contiene la consulta sql que se desea realizar
     * @throws SQLException
     */
    public void EjecutarQuery(String query) throws SQLException {
        try (java.sql.Connection eConect = con.Conectar_a_base()) {
            Statement stmt = eConect.createStatement();
            stmt.execute(query); //execute se puede utilizar con cualquier tipo de sentencias SQL y devuelve un booleano.
            stmt.close();
        }catch(SQLException e){
            System.out.println(e.getErrorCode() + "" + e.getMessage());
        }
    }

    /**
     * Método que permite realizar la ejecución especifica de un select en la base de datos
     * @param query ontiene la consulta sql que se desea realizar
     * @return
     * @throws SQLException
     */
    public ArrayList<String> EjecutarSelect(String query) throws SQLException {
        try (java.sql.Connection eConect = con.Conectar_a_base()) {
            Statement stmt = eConect.createStatement();
            ResultSet rs;
            rs = stmt.executeQuery(query); //executeQuery ejecuta declaraciones que retornan un conjunto de resultados al obtener  datos de la BD. Solo selects.
            ArrayList<String> listaResultSet = new ArrayList<>();
            while (rs.next()) {
                listaResultSet.add(rs.getString(1));
            }

            return listaResultSet;
        }
    }

    /**
     * Método que permite la preparación para insertar una escuela en la base de datos
     * @param escuela Objeto escuela a almacenar
     * @throws SQLException
     */
    public void insertarEscuela(Escuela escuela) throws SQLException {
        String query = "insert into Escuela(codEscuela,nombreEscuela) values('" + escuela.getCodEscuela() + "','" + escuela.getNombreEscuela() + "')";
        System.out.println(query);
        EjecutarQuery(query);
    }

    /**
     * Método que realiza la preparación del query para selección de los nombres de escuela en la base de datos 
     * @return
     */
    public ArrayList<String> seleccionarEscuelas() {
        String sqlQuery = "SELECT nombreEscuela FROM Escuela ORDER BY nombreEscuela ASC;";
        ArrayList<String> listaEscuelas = new ArrayList<>();
        try {
            listaEscuelas = EjecutarSelect(sqlQuery);
            return listaEscuelas;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return listaEscuelas;
    }

    /**
     * Método que realiza la preparación del query para seleccionar los codigos de escuela en la base de datos
     * @param nombreEscuela
     * @return
     */
    public String seleccionarCodEscuela(String nombreEscuela) {
        String sqlQuery = "SELECT codEscuela FROM Escuela WHERE nombreEscuela = " + "'" + nombreEscuela + "'";
        System.out.println(nombreEscuela);
        String codEscuela = null;
        ArrayList<String> codEscuelaLista = new ArrayList<>();
        try {
            codEscuelaLista = EjecutarSelect(sqlQuery);
            codEscuela = codEscuelaLista.get(0);
            System.out.println(codEscuelaLista);
            return codEscuela;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return codEscuela;
    }

    /**
     * Método que permite la preparación para insertar uun curso en la base de datos 
     * @param pCurso codigo del curso 
     */
    public void insertarCurso(Curso pCurso) {
        try {
            String query = "INSERT INTO Curso VALUES('" + pCurso.getCodCurso() + "','"
                    + pCurso.getNombreCurso() + "',"
                    + pCurso.getCantCreditos() + ","
                    + pCurso.getCantHorasLectivas() + ")";
            System.out.println(query);
            EjecutarQuery(query);
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    /**
     * Método que permite la preparación para insertar un curso en una escuela en la base de datos
     * @param pCodEscuela codigo de la escuela
     * @param pCurso codigo del curso
     */
    
    public void insertarCursoXEscuela(String pCodEscuela, Curso pCurso) {
        try {
            String query = "INSERT INTO CursoXEscuela VALUES('" + pCurso.getCodCurso() + "','" + pCodEscuela + "')";
            System.out.println(query);
            EjecutarQuery(query);
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    /**
     * Método que permite la preparación para insertar un plan de estudio en la base de datos
     * @param plan Objeto plan a insertar
     * @param pNombreEscuela Código de escuela a la que se le afia el plan
     */
    public void insertarPlanEstudio(PlanDeEstudio plan, String pNombreEscuela) {
        String pCodEscuela = seleccionarCodEscuela(pNombreEscuela);
        try {
            Date fecha;

            String query = "INSERT INTO PlanDeEstudio VALUES('" + plan.getCodPlanEstudio() + "','"
                    + plan.getFechaVigencia() + "','"
                    + pCodEscuela + "')";

            EjecutarQuery(query);
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    /**
     * Método que permite la preparación para insertar un curso en un plan en la base de datos
     * @param codCurso Código a afiliar a un plan
     * @param plan plan al que se afiliara el curso
     * @param pBloqueActivo bloque en el que se encuentra activo el curso
     */
    public void insertarCursoXPlan(String codCurso, PlanDeEstudio plan, String pBloqueActivo) {
        try {
            System.out.println("CXP");

            String query1 = "INSERT INTO CursoXPlanDeEstudio VALUES('" + codCurso + "','"
                    + plan.getCodPlanEstudio() + "','"
                    + pBloqueActivo + "')";
            System.out.println(query1);
            EjecutarQuery(query1);
            System.out.println("Iniciado insertado de Plan");
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    /**
     *  Método que realiza la preparación del query para seleccionar los codigos de curso de escuela en la base de datos
     * @param codEscuela Código de escuela sobre la que se van a buscar los cursos
     * @return
     */
    public ArrayList<String> seleccionarCursosDeEscuela(String codEscuela) {
        String sqlQuery = "SELECT codCurso FROM CURSO WHERE codEscuela = '" + codEscuela + "'";
        ArrayList<String> listaCursosDeEscuela = new ArrayList<>();
        try {
            listaCursosDeEscuela = EjecutarSelect(sqlQuery);
            System.out.println("lo intenta");
            return listaCursosDeEscuela;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return listaCursosDeEscuela;
    }

    /**
     *  Método que realiza la preparación del query para seleccionar todos los codigos de curso
     * @return
     */
    public ArrayList<String> seleccionarCursos() {
        String sqlQuery = "SELECT codCurso FROM CURSO";
        ArrayList<String> listaCursos = new ArrayList<>();
        try {
            listaCursos = EjecutarSelect(sqlQuery);
            return listaCursos;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return listaCursos;
    }

    /**
     * Método que realiza la preparación del query para seleccionar los planes 
     * existentes en una escuela en la base de datos
     * @param codEscuela Código de la escuela sobre la que se buscaran los planes
     * @return
     */
    public ArrayList<String> seleccionarCodPlanes(String codEscuela) {
        String sqlQuery = "Select numPlan From PlanDeEstudio Where escuelaAsociada = '" + codEscuela + "'";
        ArrayList<String> listaCodPlanes = new ArrayList<>();
        try {
            listaCodPlanes = EjecutarSelect(sqlQuery);
            System.out.println(listaCodPlanes);
            return listaCodPlanes;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return listaCodPlanes;
    }

    /**
     * Método que permite la preparación para insertar un requisito a un curso en la base de datos
     * @param codCurso Código del curso al que se le afiliara un requisito
     * @param codRequisito Código del curso que se volvera requisito
     */
    public void insertarRequisitoXCurso(String codCurso, String codRequisito) {
        try {

            String query = "INSERT INTO RequisitoXCurso VALUES('" + codCurso + "','" + codRequisito + "')";
            System.out.println(query);
            EjecutarQuery(query);
            System.out.println("Insertado requisito de curso en la BD ");
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    /**
     * Método que permite la preparación para insertar un corequisito a un curso en la base de datos
     * @param codCurso Código del curso al que se le afiliara un requisito 
     * @param codCorrequisito Código del curso que se volvera correquisito
     */
    public void insertarCorrequisitoXCurso(String codCurso, String codCorrequisito) {
        try {
            System.out.println("CXP");

            String query = "INSERT INTO CorrequisitoXCurso VALUES('" + codCurso + "','" + codCorrequisito + "')";
            System.out.println(query);
            EjecutarQuery(query);
            System.out.println("Insertado correquisito de curso en la BD ");
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }

    /**
     *Método que realiza la preparación del query para seleccionar un curso especifico
     * @param codCurso Código del curso a seleccionar
     * @return
     */
    public ArrayList<String> seleccionarNombreCurso(String codCurso) {
        ArrayList<String> nombreCurso = new ArrayList<>();
        try {
            String sqlQuery = "SELECT nombreCurso FROM Curso WHERE codCurso = '" + codCurso + "'";
            nombreCurso = EjecutarSelect(sqlQuery);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return nombreCurso;
    }

    /**
     * Método que permite buscar los requisitos asosciados a un curso
     * @param codCurso Código del curso al que se le desean buscar los requiitos
     * @return
     * @throws SQLException
     */
    public ResultSet seleccionarRequisitosCurso(String codCurso) throws SQLException {
        String sqlQuery = "SELECT codRequisito, nombreCurso "
                + "FROM (SELECT codRequisito FROM RequisitoXCurso WHERE codCurso = '" + codCurso + "') AS t1 "
                + "INNER JOIN (SELECT codCurso, nombreCurso FROM Curso) AS t2 "
                + "ON t1.codRequisito = t2.codCurso";
        ResultSet requisitos = EjecutarSelectRS(sqlQuery);
        return requisitos;
    }

    /**
     * Método que buscar los correquisitos de un curso en particular 
     * @param codCurso Código del curso al que se le desean buscar los requisitos
     * @return
     * @throws SQLException
     */
    public ResultSet seleccionarCorrequisitosCurso(String codCurso) throws SQLException {
        String sqlQuery = "SELECT codCorrequisito, nombreCurso "
                + "FROM (SELECT codCorrequisito FROM CorrequisitoXCurso WHERE codCurso = '" + codCurso + "') AS t1 "
                + "INNER JOIN (SELECT codCurso, nombreCurso FROM Curso) AS t2 "
                + "ON t1.codCorrequisito = t2.codCurso";
        ResultSet correquisitos = EjecutarSelectRS(sqlQuery);
        return correquisitos;
    }

    /**
     * Método que permite seleccionar toda la información relacionada a un plan de estudio 
     * @param codigoEscuela Código de la escuela a buscar
     * @return
     * @throws SQLException
     */
    public ResultSet verPlanDeEstudio(String codigoEscuela) throws SQLException {
        System.out.println("Llego aca");
        String query = "Select * From Curso Inner join CursoXPlanDeEstudio ON Curso.codCurso =CursoXPlanDeEstudio.codCurso Where CursoXPlanDeEstudio.planEstudio = " + codigoEscuela + "";
        System.out.println(query);
        ResultSet consulta = EjecutarSelectRS(query);
        return consulta;
    }

    /**
     * Mtodo que ejecuta un select con salida de ResultSet
     * @param query Contiene la consulta sql que se desea realizar
     * @return
     * @throws SQLException
     */
    public ResultSet EjecutarSelectRS(String query) throws SQLException {
        java.sql.Connection eConect = con.Conectar_a_base();
        Statement stmt = eConect.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        return rs;

    }

    /**
     * Método que permite realizar la carga de las escuelas a los arrayList iniciales
     * @return
     * @throws SQLException
     */
    public ResultSet CargarDatosEscuelas() throws SQLException {
        ResultSet escuelasRS = null;
        try {
            String sqlQuery = "SELECT * FROM Escuela";
            escuelasRS = EjecutarSelectRS(sqlQuery);
            return escuelasRS;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return escuelasRS;
    }

    /**
     * Método que permite realizar la carga de los cursos a los arrayList iniciales
     * @return
     * @throws SQLException 
     */
    public ResultSet CargarDatosCursos() throws SQLException {
        ResultSet cursosRS = null;
        try {
            String sqlQuery = "SELECT * FROM Curso";
            cursosRS = EjecutarSelectRS(sqlQuery);
            return cursosRS;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return cursosRS;
    }
    /**
     * Método que permite realizar la carga de los cursos de escuelas a los arrayList iniciales
     * @return
     * @throws SQLException 
     */
    public ResultSet CargarDatosCursosDeEscuela() throws SQLException {
        ResultSet cursosEscuelaRS = null;
        try {
            String sqlQuery = "SELECT * FROM CursoXEscuela";
            cursosEscuelaRS = EjecutarSelectRS(sqlQuery);
            return cursosEscuelaRS;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return cursosEscuelaRS;
    }

    /**
     * Método que permite realizar la carga de los requistos a los arraylist iniciales
     * @return @throws SQLException
     */
    public ResultSet CargarDatosRequisitos() throws SQLException {
        ResultSet requisitosRS = null;
        try {
            String sqlQuery = "SELECT * FROM RequisitoXCurso";
            requisitosRS = EjecutarSelectRS(sqlQuery);
            return requisitosRS;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return requisitosRS;
    }
    
    /**
     * Método que permite realizar la carga de los correquistos a los arraylist iniciales
     * @return
     * @throws SQLException 
     */

    public ResultSet CargarDatosCorrequisitos() throws SQLException {
        ResultSet correquisitosRS = null;
        try {
            String sqlQuery = "SELECT * FROM CorrequisitoXCurso";
            correquisitosRS = EjecutarSelectRS(sqlQuery);
            return correquisitosRS;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return correquisitosRS;
    }
    /**
     * Método que permite realizar la carga de los planes de estudio a los arraylist iniciales
     * @return
     * @throws SQLException 
     */

    public ResultSet CargarDatosPlanesDeEstudio() throws SQLException {
        ResultSet planesRS = null;
        try {
            String sqlQuery = "SELECT * FROM PlanDeEstudio";
            planesRS = EjecutarSelectRS(sqlQuery);
            return planesRS;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return planesRS;
    }
    /**
     * Método que permite realizar la carga de los cursos pertenecientes a un plan a los arraylist iniciales
     * @return
     * @throws SQLException 
     */

    public ResultSet CargarDatosCursosPertenecientesPlan() throws SQLException {
        ResultSet cursosPlanesRS = null;
        try {
            String sqlQuery = "SELECT * FROM CursoXPlanDeEstudio";
            cursosPlanesRS = EjecutarSelectRS(sqlQuery);
            return cursosPlanesRS;
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return cursosPlanesRS;
    }
    /**
     * Método que permite realizar la eliminación de requisitos
     * @param codCurso codigo del curso a eliminar el requisito
     * @param reqCurso codigo del requisito a eliminar 
     */

    public void eliminarRequisitos(String codCurso, String reqCurso) {
        try {
            String query = "DELETE FROM RequisitoXCurso WHERE codCurso = '" + codCurso + "' AND codRequisito = '" + reqCurso + "'";
            EjecutarSelectRS(query);
            System.out.println("Eliminado");
        } catch (SQLException e) {
            e.getErrorCode();
        }
    }
    /**
     * Método que permite realizar la eliminación de cursos en plan de estudio
     * @param codCurso codigo del curso que se desea eliminar del plan 
     * @param codplanEstudio plan del que se va a eliminar el curso
     */
    public void eliminarCursoXPlanEstudio(String codCurso, String codplanEstudio) {
        try {
            String query = "DELETE FROM CursoXPlanDeEstudio WHERE codCurso = '" + codCurso + "' AND planEstudio = '" + codplanEstudio + "'";
            EjecutarSelectRS(query);
            System.out.println("Eliminado");
        } catch (SQLException e) {
            e.getErrorCode();
        }
    };
    
    /**
     * Método que permite eliminar un curso
     * @param codCurso Código del curso a eliminar
     */
    public void eliminarCurso(String codCurso) {
        try {
            String query = "DELETE FROM Curso WHERE codCurso = '" + codCurso + "'";
            EjecutarSelectRS(query);
            System.out.println("Eliminado");
        } catch (SQLException e) {
            e.getErrorCode();
        }
    };

}
