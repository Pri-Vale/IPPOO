/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Correo;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author pri23
 */
public class Correo {
    
    public void generarCorreo(Properties propiedades) throws AddressException, MessagingException{
        propiedades.setProperty("mail.smtp.host", "smtp.gmail.com");
        propiedades.setProperty("mail.smtp.starttls.enable", "true");
        propiedades.setProperty("mail.smtp.port", "587");
        propiedades.setProperty("mail.smtp.auth", "true");
        
        
        Session sesion = Session.getDefaultInstance(propiedades);
        //tenemos que valorar crearnos un correo XD
        String correo_emisor = "pri231296@gmail.com";
        String contraseña_emisor = "ilove6-2";
        
        //Me falta pegar la ventana de correo
        String correo_receptor = "valeria700602@gmail.com";
        String asunto = "Estoy haciendo pruebas";
        String mensaje = "Hola Vale, soy Pri";
        
        //Ahora esto es la construccion 
        
         MimeMessage message = new MimeMessage(sesion);
        message.setFrom(new InternetAddress(correo_emisor));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(correo_receptor));
        message.setSubject(asunto);
        message.setText(mensaje);
        
        //Esto es lo que hace el transporte
        Transport transporte = sesion.getTransport("smtp");
        transporte.connect(correo_emisor,contraseña_emisor);
        transporte.sendMessage(message , message.getRecipients(Message.RecipientType.TO));
        transporte.close();
        
        
    }
    
}
