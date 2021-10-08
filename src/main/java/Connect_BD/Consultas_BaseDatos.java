/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Connect_BD;

import Modelo.logicaDeNegocio.Escuela;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author pri23
 */
public class Consultas_BaseDatos {
    
    Conexion con =  new Conexion();
    
    public void EjecutarQuery(String query) throws SQLException {
        try (java.sql.Connection eConect = con.Conectar_a_base()) {
            Statement stmt= eConect.createStatement();
            stmt.execute(query);
            stmt.close();
        }
        
}
    public void insertarEscuela(Escuela escuela) throws SQLException {
        String query="insert into Escuela(codEscuela,nombreEscuela) values('"+escuela.getCodEscuela()+"','"+escuela.getNombreEscuela()+"')";
        System.out.println(query);
        EjecutarQuery(query);
    }
    
    }


