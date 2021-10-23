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
 * @author Valeria Fernández y Priscilla Ramírez
 * @version 1.8
 * @since 1.0
 */
public class Consultas_BaseDatos {
    
    Conexion con =  new Conexion();
    
    /**
     * 
     * @param query
     * @throws SQLException 
     */
    public void EjecutarQuery(String query) throws SQLException {
        
        System.out.print("Ejecuta");
        try (java.sql.Connection eConect = con.Conectar_a_base()) {
            Statement stmt= eConect.createStatement();
            stmt.execute(query); //execute se puede utilizar con cualquier tipo de sentencias SQL y devuelve un booleano.
            stmt.close();
        }
    }
    
    /**
     * 
     * @param query
     * @return
     * @throws SQLException 
     */
    public ArrayList<String> EjecutarSelect(String query) throws SQLException {
        try (java.sql.Connection eConect = con.Conectar_a_base()) {
            Statement stmt= eConect.createStatement();
            ResultSet rs;
	    rs = stmt.executeQuery(query); //executeQuery ejecuta declaraciones que retornan un conjunto de resultados al obtener  datos de la BD. Solo selects.
            ArrayList<String>listaResultSet = new ArrayList<>();
            while(rs.next()){
                listaResultSet.add(rs.getString(1));
            }            
            
            return listaResultSet;
        }
    }
    
    /**
     * 
     * @param escuela
     * @throws SQLException 
     */
    public void insertarEscuela(Escuela escuela) throws SQLException {
        String query="insert into Escuela(codEscuela,nombreEscuela) values('"+escuela.getCodEscuela()+"','"+escuela.getNombreEscuela()+"')";
        System.out.println(query);
        EjecutarQuery(query);
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<String> seleccionarEscuelas(){
        String sqlQuery = "SELECT nombreEscuela FROM Escuela ORDER BY nombreEscuela ASC;";
        ArrayList<String> listaEscuelas = new ArrayList<>();
        try{
             listaEscuelas = EjecutarSelect(sqlQuery);        
             return listaEscuelas;
        }
        catch (SQLException e){
            e.getErrorCode();
        }
        return listaEscuelas;
    }
    
    /**
     * 
     * @param nombreEscuela
     * @return 
     */
    public String seleccionarCodEscuela(String nombreEscuela){
        String sqlQuery = "SELECT codEscuela FROM Escuela WHERE nombreEscuela = " + "'" + nombreEscuela + "'";
        System.out.println(nombreEscuela);
        String codEscuela= null;
        ArrayList<String> codEscuelaLista = new ArrayList<>();
        try{
            codEscuelaLista = EjecutarSelect(sqlQuery);
            codEscuela = codEscuelaLista.get(0);
            System.out.println(codEscuelaLista);
            return codEscuela;
        }
        catch(SQLException e){
            e.getErrorCode();
        }
        return codEscuela;
    }
    
    /**
     * 
     * @param pCodEscuela
     * @param pCurso 
     */
    public void insertarCurso(Curso pCurso){
        try{
            String query="INSERT INTO Curso VALUES('"+ pCurso.getCodCurso()+"','"
                    + pCurso.getNombreCurso() + "',"
                    + pCurso.getCantCreditos()+ ","
                    + pCurso.getCantHorasLectivas()+ ")";
            System.out.println(query);
            EjecutarQuery(query);             
        }
        catch(SQLException e){
            e.getErrorCode();
        }
    }
    
    public void insertarCursoXEscuela(String pCodEscuela, Curso pCurso){
        try{
            String query="INSERT INTO CursoXEscuela VALUES('"+ pCurso.getCodCurso()+"','" + pCodEscuela + "')";
            System.out.println(query);
            EjecutarQuery(query);
        }catch(SQLException e){
            e.getErrorCode();
        }
    }
    
    /**
     * 
     * @param plan
     * @param pNombreEscuela 
     */
    public void insertarPlanEstudio(PlanDeEstudio plan, String pNombreEscuela){
        String pCodEscuela = seleccionarCodEscuela(pNombreEscuela);
        try{
            Date fecha;
            
            String query="INSERT INTO PlanDeEstudio VALUES('"+ plan.getCodPlanEstudio()+"','"
                    + plan.getFechaVigencia() + "','"
                    + pCodEscuela+ "')";
            
            EjecutarQuery(query);             
        }
        catch(SQLException e){
            e.getErrorCode();
        }
    }
    
    /**
     * 
     * @param codCurso
     * @param plan
     * @param pBloqueActivo 
     */
    public void insertarCursoXPlan(String codCurso,PlanDeEstudio plan,String pBloqueActivo){
        try{
            System.out.println("CXP");
            
            String query1="INSERT INTO CursoXPlanDeEstudio VALUES('"+ codCurso +"','"
                    + plan.getCodPlanEstudio() + "','"
                    + pBloqueActivo+ "')";
            System.out.println(query1);
            EjecutarQuery(query1);
            System.out.println("Iniciado insertado de Plan");
        }
        catch(SQLException e){
            e.getErrorCode();
        }
    }
    
    /**
     * 
     * @param codEscuela
     * @return 
     */
    public ArrayList<String> seleccionarCursosDeEscuela(String codEscuela){
        String sqlQuery = "SELECT codCurso FROM CURSO WHERE codEscuela = '" + codEscuela + "'";
        ArrayList<String> listaCursosDeEscuela = new ArrayList<>();
        try{
             listaCursosDeEscuela = EjecutarSelect(sqlQuery);        
             System.out.println("lo intenta");
             return listaCursosDeEscuela;
        }
        catch (SQLException e){
            e.getErrorCode();
        }
        return listaCursosDeEscuela;
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList<String> seleccionarCursos(){
        String sqlQuery = "SELECT codCurso FROM CURSO";
        ArrayList<String> listaCursos = new ArrayList<>();
        try{
             listaCursos = EjecutarSelect(sqlQuery);        
             return listaCursos;
        }
        catch (SQLException e){
            e.getErrorCode();
        }
        return listaCursos;
    }
    
    /**
     * 
     * @param codEscuela
     * @return 
     */
    public ArrayList<String> seleccionarCodPlanes(String codEscuela){
        String sqlQuery = "Select numPlan From PlanDeEstudio Where escuelaAsociada = '"+codEscuela+"'";
        ArrayList<String> listaCodPlanes = new ArrayList<>();
        try{
             listaCodPlanes = EjecutarSelect(sqlQuery);
             System.out.println(listaCodPlanes);
             return listaCodPlanes;
        }
        catch (SQLException e){
            e.getErrorCode();
        }
        return listaCodPlanes;
    }
       
    /**
     * 
     * @param codCurso
     * @param codRequisito 
     */
    public void insertarRequisitoXCurso(String codCurso, String codRequisito){
        try{
            
            String query ="INSERT INTO RequisitoXCurso VALUES('"+ codCurso +"','" + codRequisito + "')";
            System.out.println(query);
            EjecutarQuery(query);
            System.out.println("Insertado requisito de curso en la BD ");
        }
        catch(SQLException e){
            e.getErrorCode();
        }
    }   
    
    /**
     * 
     * @param codCurso
     * @param codCorrequisito 
     */
    public void insertarCorrequisitoXCurso(String codCurso, String codCorrequisito){
        try{
            System.out.println("CXP");
            
            String query ="INSERT INTO CorrequisitoXCurso VALUES('"+ codCurso +"','" + codCorrequisito + "')";
            System.out.println(query);
            EjecutarQuery(query);
            System.out.println("Insertado correquisito de curso en la BD ");
        }
        catch(SQLException e){
            e.getErrorCode();
        }
    }
    
    /**
     * 
     * @param codCurso
     * @return 
     */
    public ArrayList<String> seleccionarNombreCurso(String codCurso){
        ArrayList<String> nombreCurso = new ArrayList<>();
        try{
            String sqlQuery = "SELECT nombreCurso FROM Curso WHERE codCurso = '" + codCurso + "'";
            nombreCurso = EjecutarSelect(sqlQuery);
        }
        catch(SQLException e){
            e.getErrorCode();
        }
        return nombreCurso;
    }
    
    /**
     * 
     * @param codCurso
     * @return
     * @throws SQLException 
     */
    public ResultSet seleccionarRequisitosCurso(String codCurso)throws SQLException{
        String sqlQuery = "SELECT codRequisito, nombreCurso " +
                          "FROM (SELECT codRequisito FROM RequisitoXCurso WHERE codCurso = '" + codCurso +"') AS t1 " +
                          "INNER JOIN (SELECT codCurso, nombreCurso FROM Curso) AS t2 " +
                          "ON t1.codRequisito = t2.codCurso";
        ResultSet requisitos = EjecutarSelectRS(sqlQuery);
        return requisitos;
    }
    
    /**
     * 
     * @param codCurso
     * @return
     * @throws SQLException 
     */
    public ResultSet seleccionarCorrequisitosCurso(String codCurso) throws SQLException{
        String sqlQuery = "SELECT codCorrequisito, nombreCurso " +
                          "FROM (SELECT codCorrequisito FROM CorrequisitoXCurso WHERE codCurso = '" + codCurso +"') AS t1 " +
                          "INNER JOIN (SELECT codCurso, nombreCurso FROM Curso) AS t2 " +
                          "ON t1.codCorrequisito = t2.codCurso";
        ResultSet correquisitos = EjecutarSelectRS(sqlQuery);
        return correquisitos;
    }
    
    /**
     * 
     * @param codigoEscuela
     * @return
     * @throws SQLException 
     */
    public ResultSet verPlanDeEstudio(String codigoEscuela) throws SQLException  {
        System.out.println("Llego aca");
        String query="Select * From Curso Inner join CursoXPlanDeEstudio ON Curso.codCurso =CursoXPlanDeEstudio.codCurso Where CursoXPlanDeEstudio.planEstudio = "+ codigoEscuela +"";
        System.out.println(query);
        ResultSet consulta = EjecutarSelectRS(query);
        return consulta;
    }
    
    /**
     * 
     * @param query
     * @return
     * @throws SQLException 
     */
    public ResultSet EjecutarSelectRS(String query) throws SQLException {
        java.sql.Connection eConect = con.Conectar_a_base();
	Statement stmt= eConect.createStatement();
	ResultSet rs= stmt.executeQuery(query);
	return rs;
        
    }
    
    /**
     * 
     * @param escuelas
     * @return
     * @throws SQLException 
     */
    public ResultSet CargarDatosEscuelas() throws SQLException{
        ResultSet escuelasRS = null;
        try{
            String sqlQuery = "SELECT * FROM Escuela";
            escuelasRS = EjecutarSelectRS(sqlQuery);
            return escuelasRS;
        }catch(SQLException e){
            e.getErrorCode();
        }
        return escuelasRS;
    }
    
    /**
     * 
     * @param cursos
     * @return
     * @throws SQLException 
     */
    public ResultSet CargarDatosCursos() throws SQLException{
        ResultSet cursosRS = null;
        try{
            String sqlQuery = "SELECT * FROM Curso";
            cursosRS = EjecutarSelectRS(sqlQuery);
            return cursosRS;
        }catch(SQLException e){
            e.getErrorCode();
        }
        return cursosRS;
    }
    
    public ResultSet CargarDatosCursosDeEscuela() throws SQLException{
        ResultSet cursosEscuelaRS = null;
        try{
            String sqlQuery = "SELECT * FROM CursoXEscuela";
            cursosEscuelaRS = EjecutarSelectRS(sqlQuery);
            return cursosEscuelaRS;
        }catch(SQLException e){
            e.getErrorCode();
        }
        return cursosEscuelaRS;
    }
    
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public ResultSet CargarDatosRequisitos() throws SQLException{
        ResultSet requisitosRS = null;
        try{
            String sqlQuery = "SELECT * FROM RequisitoXCurso";
            requisitosRS = EjecutarSelectRS(sqlQuery);
            return requisitosRS;
        }catch(SQLException e){
            e.getErrorCode();
        }
        return requisitosRS;
    }
    
    public ResultSet CargarDatosCorrequisitos() throws SQLException{
        ResultSet correquisitosRS = null;
        try{
            String sqlQuery = "SELECT * FROM CorrequisitoXCurso";
            correquisitosRS = EjecutarSelectRS(sqlQuery);
            return correquisitosRS;
        }catch(SQLException e){
            e.getErrorCode();
        }
        return correquisitosRS;
    }
    
    public ResultSet CargarDatosPlanesDeEstudio() throws SQLException{
        ResultSet planesRS = null;
        try{
            String sqlQuery = "SELECT * FROM PlanDeEstudio";
            planesRS = EjecutarSelectRS(sqlQuery);
            return planesRS;
        }catch(SQLException e){
            e.getErrorCode();
        }
        return planesRS;
    }
    
    public ResultSet CargarDatosCursosPertenecientesPlan() throws SQLException{
        ResultSet cursosPlanesRS = null;
        try{
            String sqlQuery = "SELECT * FROM CursoXPlanDeEstudio";
            cursosPlanesRS = EjecutarSelectRS(sqlQuery);
            return cursosPlanesRS;
        }catch(SQLException e){
            e.getErrorCode();
        }
        return cursosPlanesRS;
    }
    
    public void eliminarRequisitos(String codCurso, String reqCurso){
        try{
            String query= "DELETE FROM RequisitoXCurso WHERE codCurso = '" + codCurso +"' AND codRequisito = '" + reqCurso +"'";
            EjecutarSelectRS(query);
            System.out.println("Eliminado");
        }catch(SQLException e){
            e.getErrorCode();
        }
    }
       
}

