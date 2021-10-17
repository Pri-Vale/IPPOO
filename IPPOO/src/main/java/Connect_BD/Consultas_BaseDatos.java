/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connect_BD;

import Modelo.logicaDeNegocio.Escuela;
import Modelo.logicaDeNegocio.Curso;
import Modelo.logicaDeNegocio.PlanDeEstudio;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

/**
 *
 * @author pri23
 */
public class Consultas_BaseDatos {
    
    Conexion con =  new Conexion();
    
    public void EjecutarQuery(String query) throws SQLException {
        
        System.out.print("Ejecuta");
        try (java.sql.Connection eConect = con.Conectar_a_base()) {
            Statement stmt= eConect.createStatement();
            stmt.execute(query); //execute se puede utilizar con cualquier tipo de sentencias SQL y devuelve un booleano.
            stmt.close();
        }
    }
    
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
    
    public void insertarEscuela(Escuela escuela) throws SQLException {
        String query="insert into Escuela(codEscuela,nombreEscuela) values('"+escuela.getCodEscuela()+"','"+escuela.getNombreEscuela()+"')";
        System.out.println(query);
        EjecutarQuery(query);
    }
    
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
    
    public void insertarCurso(String pCodEscuela, Curso pCurso){
        try{
            String query="INSERT INTO Curso VALUES('"+ pCurso.getCodCurso()+"','"
                    + pCurso.getNombreCurso() + "',"
                    + pCurso.getCantCreditos()+ ","
                    + pCurso.getCantHorasLectivas()+ ",'"
                    + pCodEscuela + "')";
            System.out.println(query);
            EjecutarQuery(query);             
        }
        catch(SQLException e){
            e.getErrorCode();
        }
    }
    
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
    
    public ArrayList<String> seleccionarCursosDeEscuela(String codEscuela){
        String sqlQuery = "SELECT codCurso FROM CURSO WHERE codEscuela = '" + codEscuela + "'";
        ArrayList<String> listaCursosDeEscuela = new ArrayList<>();
        try{
             listaCursosDeEscuela = EjecutarSelect(sqlQuery);        
             return listaCursosDeEscuela;
        }
        catch (SQLException e){
            e.getErrorCode();
        }
        return listaCursosDeEscuela;
    }
    
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
       
    public void insertarRequisitoXCurso(String codCurso, String codRequisito){
        try{
            System.out.println("CXP");
            
            String query ="INSERT INTO RequisitoXCurso VALUES('"+ codCurso +"','" + codRequisito + "')";
            System.out.println(query);
            EjecutarQuery(query);
            System.out.println("Insertado requisito de curso en la BD ");
        }
        catch(SQLException e){
            e.getErrorCode();
        }
    }   
    
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
    
    public ArrayList<String> seleccionarRequisitosCurso(String codCurso){
        String sqlQuery = "SELECT codRequisito, nombreCurso " +
                          "FROM (SELECT codRequisito FROM RequisitoXCurso WHERE codCurso = '" + codCurso +"') AS t1 " +
                          "INNER JOIN (SELECT codCurso, nombreCurso FROM Curso) AS t2 " +
                          "ON t1.codRequisito = t2.codCurso";
    }
}
    


