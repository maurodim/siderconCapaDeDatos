/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Administrador
 */
public class Mail {
    private final Properties propiedades=new Properties();
    private String password="Sider002";
    private Session sesion;
    private String direccionFile;
    private String detalleListado;
    private String asunto;

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    
    public void setDetalleListado(String detalleListado) {
        this.detalleListado = detalleListado;
    }
    
    
    public void setDireccionFile(String direccionFile) {
        this.direccionFile = direccionFile;
    }
    
    private void init(){
        propiedades.put("mail.smtp.host","mail.sidercon.com.ar");
        propiedades.put("mail.smtp.starttls.enable","true");
        propiedades.put("mail.smtp.port",587);
        propiedades.put("mail.smtp.mail.sender","no_responder@sidercon.com.ar");
        propiedades.put("mail.smtp.user","no_responder@sidercon.com.ar");
        propiedades.put("mail.smtp.auth","true");
        sesion=Session.getDefaultInstance(propiedades);
        
    }
    public void enviarMailRepartoCargaCompleta() throws MessagingException{
        init();
        try{
            MimeMessage mensaje=new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress((String)propiedades.get("mail.smtp.mail.sender")));
            mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress("hernangonzalez@sidercon.com"));
            mensaje.addRecipient(Message.RecipientType.CC,new InternetAddress("comercial@sidercon.com"));
            mensaje.setSubject(asunto);
            BodyPart texto=new MimeBodyPart();
            texto.setText("LPM GENERADA, LA MISMA SE ENCUENTRA GUARDADA EN LA CARPETA Server/Ventas/LPM   \n Saludos");
            BodyPart adjunto=new MimeBodyPart();
            adjunto.setDataHandler(new DataHandler(new FileDataSource(direccionFile)));
            adjunto.setFileName(detalleListado);
            MimeMultipart multiParte=new MimeMultipart();
            multiParte.addBodyPart(texto);
            multiParte.addBodyPart(adjunto);
            //mensaje.setText("El reparto del vehiculo esta cerrado para el reparto. Motivo :CAPACIDAD DE CARGA COMPLETADA");
            mensaje.setContent(multiParte);
            Transport t=sesion.getTransport("smtp");
            t.connect((String)propiedades.get("mail.smtp.user"), password);
            t.sendMessage(mensaje,mensaje.getAllRecipients());
            t.close();
        }catch(MessagingException me){
            System.err.println("EL MENSAJE NO SE PUDO ENVIAR "+me);
        }
    }
   public void enviarMailRepartoCerrado(String descripcionVehiculo,String fecha) throws MessagingException{
        init();
        try{
            MimeMessage mensaje=new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress((String)propiedades.get("mail.smtp.mail.sender")));
            mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress("hernangonzalez@sidercon.com"));
            mensaje.addRecipient(Message.RecipientType.CC,new InternetAddress("rgonzalez@sidercon.com"));
            mensaje.setSubject("REPARTO CERRADO");
            mensaje.setText("El reparto del vehiculo "+descripcionVehiculo+" esta cerrado para el reparto del dia "+fecha+". Motivo :CAPACIDAD DE REPARTO COMPLETA");
            Transport t=sesion.getTransport("smtp");
            t.connect((String)propiedades.get("mail.smtp.user"), password);
            t.sendMessage(mensaje,mensaje.getAllRecipients());
            t.close();
        }catch(MessagingException me){
            System.err.println("EL MENSAJE NO SE PUDO ENVIAR "+me);
        }
    }
   public void enviarMailDeEliminacionDePedido(String encabezado,ArrayList listadoPedido,String causa){
       init();
        try{
            MimeMessage mensaje=new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress((String)propiedades.get("mail.smtp.mail.sender")));
            //mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress("mauro@bambusoft.com.ar"));
            mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress("no_responder@sidercon.com.ar"));
            //mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress("hernangonzalez@sidercon.com"));
            //mensaje.addRecipient(Message.RecipientType.CC,new InternetAddress("rgonzalez@sidercon.com"));
            // aca envalúo el texto y el asunto recorriendo el pedido o los items eliminados
            
            
            mensaje.setSubject(encabezado);
            String detalle="";
            Iterator it=listadoPedido.listIterator();
            PedidosParaReparto pedido;
            while(it.hasNext()){
                pedido=(PedidosParaReparto) it.next();
                detalle+="                <tr style=\"width: 100%;\">\n" +
"                    <td>"+pedido.getRazonSocial()+"</td>\n" +
"                    <td>"+pedido.getEmpresa()+"</td>\n" +
"                    <td>"+pedido.getCodigoArticulo()+"</td>\n" +
"                    <td>"+pedido.getDescripcionArticulo()+"</td>\n" +
"                    <td>"+pedido.getCantidadArticulo()+"</td>\n" +
"                    <td>"+pedido.getFechaEnvio()+"</td>\n" +
"                    \n" +
"                </tr>\n" ;

            }
            String cuerpo="<html>\n" +
"    <head>\n" +
"        <title>TODO supply a title</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    </head>\n" +
"    <body>\n" +
"        <div>\n" +
"            <div style=\"width: 100%;\">\n" +
"               <strong> "+encabezado+"</strong>\n" +
"            <br></div>\n" +
"            <table border=\"1\">\n" +
"                \n" +
"                <tr style=\"width: 100%;\">\n" +
"                    <td><strong>Cliente</strong></td>\n" +
"                    <td><strong>Empresa</strong></td>\n" +
"                    <td><strong>Codigo</strong></td>\n" +
"                    <td><strong>Articulo</strong></td>\n" +
"                    <td><strong>Cantidad</strong></td>\n" +
"                    <td><strong>Fecha de Entrega</strong></td>\n" +
"                    \n" +
"                </tr>\n" +
"                \n" +detalle+

                    "            </table>\n" +
                    " <br><p><strong>Motivo Anulacion:</strong> "+causa+"</p>\n" +
"        </div>\n" +
"    </body>\n" +
"</html>";
            //mensaje.setText(cuerpo);
            mensaje.setContent(cuerpo,"text/html; charset=utf-8");
            Transport t=sesion.getTransport("smtp");
            t.connect((String)propiedades.get("mail.smtp.user"), password);
            t.sendMessage(mensaje,mensaje.getAllRecipients());
            t.close();
        }catch(MessagingException me){
            System.err.println("EL MENSAJE NO SE PUDO ENVIAR "+me);
        }
   }
   public void enviarMailDeReinsercionDePedido(String encabezado,ArrayList listadoPedido,String causa){
       init();
        try{
            MimeMessage mensaje=new MimeMessage(sesion);
            mensaje.setFrom(new InternetAddress((String)propiedades.get("mail.smtp.mail.sender")));
            //mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress("mauro@bambusoft.com.ar"));
            mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress("no_responder@sidercon.com.ar"));
            //mensaje.addRecipient(Message.RecipientType.TO,new InternetAddress("hernangonzalez@sidercon.com"));
            //mensaje.addRecipient(Message.RecipientType.CC,new InternetAddress("rgonzalez@sidercon.com"));
            // aca envalúo el texto y el asunto recorriendo el pedido o los items eliminados
            
            
            mensaje.setSubject(encabezado);
            String detalle="";
            Iterator it=listadoPedido.listIterator();
            PedidosParaReparto pedido;
            while(it.hasNext()){
                pedido=(PedidosParaReparto) it.next();
                detalle+="                <tr style=\"width: 100%;\">\n" +
"                    <td>"+pedido.getRazonSocial()+"</td>\n" +
"                    <td>"+pedido.getEmpresa()+"</td>\n" +
"                    <td>"+pedido.getCodigoArticulo()+"</td>\n" +
"                    <td>"+pedido.getDescripcionArticulo()+"</td>\n" +
"                    <td>"+pedido.getCantidadArticulo()+"</td>\n" +
"                    <td>"+pedido.getFechaEnvio()+"</td>\n" +
"                    \n" +
"                </tr>\n" ;

            }
            String cuerpo="<html>\n" +
"    <head>\n" +
"        <title>TODO supply a title</title>\n" +
"        <meta charset=\"UTF-8\">\n" +
"        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
"    </head>\n" +
"    <body>\n" +
"        <div>\n" +
"            <div style=\"width: 100%;\">\n" +
"               <strong> "+encabezado+"</strong>\n" +
"            <br></div>\n" +
"            <table border=\"1\">\n" +
"                \n" +
"                <tr style=\"width: 100%;\">\n" +
"                    <td><strong>Cliente</strong></td>\n" +
"                    <td><strong>Empresa</strong></td>\n" +
"                    <td><strong>Codigo</strong></td>\n" +
"                    <td><strong>Articulo</strong></td>\n" +
"                    <td><strong>Cantidad</strong></td>\n" +
"                    <td><strong>Fecha de Entrega</strong></td>\n" +
"                    \n" +
"                </tr>\n" +
"                \n" +detalle+

                    "            </table>\n" +
                    " <br><p><strong>Motivo Anulacion:</strong> "+causa+"</p>\n" +
"        </div>\n" +
"    </body>\n" +
"</html>";
            //mensaje.setText(cuerpo);
            mensaje.setContent(cuerpo,"text/html; charset=utf-8");
            Transport t=sesion.getTransport("smtp");
            t.connect((String)propiedades.get("mail.smtp.user"), password);
            t.sendMessage(mensaje,mensaje.getAllRecipients());
            t.close();
        }catch(MessagingException me){
            System.err.println("EL MENSAJE NO SE PUDO ENVIAR "+me);
        }
   }
}
