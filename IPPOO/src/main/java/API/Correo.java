/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package API;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author pri23
 */
public class Correo {
    
    /**
     * Metodo que permite generar un correo 
     * @param propiedades Propiedades que se le pueden asiganar a un correo
     * @param correoDestinatario Correo destinatario del reporte
     * @throws AddressException
     * @throws MessagingException 
     */
    public void generarCorreo(Properties propiedades, String correoDestinatario) throws AddressException, MessagingException{
        propiedades.setProperty("mail.smtp.host", "smtp.googlemail.com");
        propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.setProperty("mail.smtp.port", "587");
        propiedades.setProperty("mail.smtp.auth", "true");
        
        
        Session sesion = Session.getDefaultInstance(propiedades);
        //tenemos que valorar crearnos un correo XD
        String correo_emisor = "ati.sgpe@gmail.com";
        String contraseña_emisor = "privalePOO123";
        
        //Me falta pegar la ventana de correo
        String correo_receptor = correoDestinatario;
        String asunto = "Reporte de plan ATI-SGPE!";
        String mensaje = "El sistema gestor de planes de estudio ATI-SGPE! adjunta su reporte ";
        
        //PDF
        BodyPart texto = new MimeBodyPart();
        texto.setContent(mensaje,"text/html");
        
        
        
        BodyPart pdf = new MimeBodyPart();
        String ruta = System.getProperty("user.home");
        pdf.setDataHandler(new DataHandler(new FileDataSource(ruta+"\\Documents\\GitHub\\IPPOO\\Reportes\\ReportesBD.pdf")));
        //pdf.setDataHandler(new DataHandler(new FileDataSource(ruta+"\\OneDrive\\Documents\\GitHub\\IPPOO\\Reportes\\ReportesBD.pdf")));
        
        //DataHandler dh = new DataHandler(new FileDataSource("C:\\Users\\pri23\\Documents\\GitHub\\IPPOO\\Reportes"));
        
        MimeMultipart partes= new MimeMultipart();
        partes.addBodyPart(texto);
        partes.addBodyPart(pdf);

        //
       
        //Ahora esto es la construccion 
        MimeMessage message = new MimeMessage(sesion);
        message.setFrom(new InternetAddress(correo_emisor));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo_receptor));
        message.setSubject(asunto);
        //message.setText(mensaje);
        message.setContent(partes);
         
        
        //Esto es lo que hace el transporte
        Transport transporte = sesion.getTransport("smtp");
        transporte.connect(correo_emisor,contraseña_emisor);
        transporte.sendMessage(message , message.getRecipients(Message.RecipientType.TO));
        transporte.close();
        
        
    }
    
}
