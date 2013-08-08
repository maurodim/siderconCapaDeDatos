/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import objetos.Mail;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author Administrador
 */
public class EmisionDeListadosDeMaterialesConsolidados extends Thread{
    private static Connection cc1;
    private String fechaEnvio;
    private Integer numVehiculo;
    private String descVehiculo;
    private Double total;
    private Integer numeroListado;
    private Integer revision;

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    public void setDescVehiculo(String descVehiculo) {
        this.descVehiculo = descVehiculo;
    }

    public void setFechaEnvio(String fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public void setNumVehiculo(Integer numVehiculo) {
        this.numVehiculo = numVehiculo;
    }

    public void setNumeroListado(Integer numeroListado) {
        this.numeroListado = numeroListado;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
    
    
    public synchronized void run(){
        Map listConsolidado=new HashMap();
         
        cc1=Coneccion.ObtenerConeccion();
        listConsolidado.put("numListado",this.numeroListado);
        listConsolidado.put("totalKg",total);
        listConsolidado.put("revision",this.revision);
        System.out.println(fechaEnvio+"uni "+numVehiculo+" desc "+descVehiculo+" kg "+total+"LISTADO NUM"+this.numeroListado+" rev "+this.revision);
        String master=SiderconCapaatos.formularioConsolidado.trim();
        String destino="////Server//ventas//LPM//"+numeroListado+" R "+this.revision+" Listado consolidado de materiales.pdf";
        String destino2="C://listadosHdr//"+numeroListado+" R "+this.revision+" Listado consolidado de materiales.pdf";
        System.err.println(master);
        JasperReport reporte = null;
        try {
            reporte = (JasperReport)JRLoader.loadObject(master);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR EN EL MASTER");
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reporte, listConsolidado,cc1);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }
        JRExporter exporter=new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
        try {
            exporter.exportReport();
                     //cnn.close();
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }
                 
                 File f=new File(destino);
                 if(f.exists()){
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+destino2);
                Mail mail=new Mail();
                mail.setDireccionFile(destino);
                mail.setDetalleListado(this.numeroListado+" R "+this.revision+" Listado consolidado de materiales.pdf");
                mail.setAsunto("LPM CONSOLIDADA GENERADA N° "+this.numeroListado);
                         try {
                             mail.enviarMailRepartoCargaCompleta();
                         } catch (MessagingException ex) {
                             Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
                         }
            } catch (IOException ex) {
                Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
            }
}
                 
        /*         
        try {
            //Coneccion.CerrarConneccion(cc1);
        } catch (SQLException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }
        * */
    }
}
