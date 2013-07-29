/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso.EmisionDeInformes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import proceso.Coneccion;

/**
 *
 * @author Administrador
 */
public class EmisionDeInformesPorHdr extends Thread{
    private Date fD;
    private Date fH;
    private Integer tipoDeInforme;

    public void setfD(Date fD) {
        this.fD = fD;
    }

    public void setfH(Date fH) {
        this.fH = fH;
    }

    public void setTipoDeInforme(Integer tipoDeInforme) {
        this.tipoDeInforme = tipoDeInforme;
    }
    public synchronized void run(){
        Map listConsolidado=new HashMap();
        int num=0;
         
        Connection ch=Coneccion.ObtenerConeccion();
        listConsolidado.put("fechaDesde",fD);
        listConsolidado.put("fechaHasta",fH);
        System.out.println(fD+" "+fH);
        String master=System.getProperty("user.dir")+"//src//informes//InformePorHdr.jasper";
        String destino=null;
        if(this.tipoDeInforme==1){
            destino="C://Informes//"+"informes por Hoja de Ruta.pdf";
        }else{
            destino="C://Informes//"+"informes por Hoja de Ruta.xls";    
        }
        JasperReport reporte = null;
        try {
            reporte = (JasperReport)JRLoader.loadObject(master);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeInformesPorHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reporte, listConsolidado,ch);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeInformesPorHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.tipoDeInforme==1){
            JRExporter exporter=new JRPdfExporter();
           exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
        try {
            exporter.exportReport();
                     //cnn.close();
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeInformesPorHdr.class.getName()).log(Level.SEVERE, null, ex);
        }

        }else{
           // OutputStream ouputStream = null;
       
             //   ouputStream = new FileOutputStream(new File(destino));
               // ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JRExporter exporter=new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
               // exporter.setParameter (JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.TRUE);
                try {
                    exporter.exportReport();
                             //cnn.close();
                } catch (JRException ex) {
                    Logger.getLogger(EmisionDeInformesPorHdr.class.getName()).log(Level.SEVERE, null, ex);
                }
      

        }
        
                 
                 File f=new File(destino);
                 if(f.exists()){
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+destino);
            } catch (IOException ex) {
                Logger.getLogger(EmisionDeInformesPorHdr.class.getName()).log(Level.SEVERE, null, ex);
            }
}

    }
}
