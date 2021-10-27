/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package API;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author pri23
 */
public class PDF {

    public PDF() {
    }
    
    /**
     * Metodo que permite generar el reporte en PDF
     * @param documento Documento que contendra el reporte de PDF
     * @param rst Resulset que contiene la información extraida sobre el plan
     * @throws SQLException 
     */
    
   public void generarPDF(Document documento,ResultSet rst) throws SQLException{
    try{
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(documento, new FileOutputStream(ruta+"\\Documents\\GitHub\\IPPOO\\Reportes\\ReportesBD.pdf"));
            System.out.println(ruta+"\\Documents\\GitHub\\IPPOO\\Reportes\\ReportesBD.pdf");
            //PdfWriter.getInstance(documento, new FileOutputStream(ruta+"\\OneDrive\\Documents\\GitHub\\IPPOO\\Reportes\\ReportesBD.pdf"));
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
    
}
