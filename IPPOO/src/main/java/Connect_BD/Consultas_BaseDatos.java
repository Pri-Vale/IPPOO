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
            System.out.print(query);
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
    
    
}


