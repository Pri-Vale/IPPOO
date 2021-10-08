/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo.logicaDeNegocio;

/**
 *
 * @author pri23
 */
public class Escuela {
    
    private String nombreEscuela;
    private String codEscuela; 

    public Escuela() {
    }
    
    public Escuela(String nombreEscuela, String codEscuela) {
        this.nombreEscuela = nombreEscuela;
        this.codEscuela = codEscuela;
    }

    public String getNombreEscuela() {
        return nombreEscuela;
    }

    public void setNombreEscuela(String nombreEscuela) {
        this.nombreEscuela = nombreEscuela;
    }

    public String getCodEscuela() {
        return codEscuela;
    }

    public void setCodEscuela(String codEscuela) {
        this.codEscuela = codEscuela;
    }

    @Override
    public String toString() {
        return "Escuela{" + "nombreEscuela=" + nombreEscuela + ", codEscuela=" + codEscuela + '}';
    }
    
}
