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
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import proceso.Coneccion;

/**
 *
 * @author Administrador
 */
public class EmisionDeInformesPorRepartidor extends Thread{
    private Integer numeroRepartidor;
    private Date fD;
    private Date fH;
    private Integer tipoDeInforme;

    public void setfD(Date fD) {
        this.fD = fD;
    }

    public void setTipoDeInforme(Integer tipoDeInforme) {
        this.tipoDeInforme = tipoDeInforme;
    }

    public void setfH(Date fH) {
        this.fH = fH;
    }

    public void setNumeroRepartidor(Integer numeroRepartidor) {
        this.numeroRepartidor = numeroRepartidor;
    }
    public synchronized void run(){
        Map listConsolidado=new HashMap();
        int num=0;
        Coneccion con=new Coneccion();
        Connection ch=con.getCn();
        listConsolidado.put("fechaDesde",fD);
        listConsolidado.put("fechaHasta",fH);
        //System.out.println(fechaEnvio+" "+numVehiculo+" "+descVehiculo+" "+total);
        String master=null;
        String destino=null;
        if(this.numeroRepartidor==0||this.numeroRepartidor==null){
            master="C://src//informes//InformePorRepartidos.jasper";
        if(this.tipoDeInforme==1){
            destino="C://Informes//"+"informes por repartidor.pdf";
        }else{
            destino="C://Informes//"+"informes por repartidor.xls";    
        }
        }else{
            listConsolidado.put("numeroFletero",this.numeroRepartidor);
            master="C://src//informes//InformePorRepartidorIndividual.jasper";
            if(this.tipoDeInforme==1){
            destino="C://Informes//"+"informes por repartidor "+this.numeroRepartidor+".pdf";
        }else{
            destino="C://Informes//"+"informes por repartidor "+this.numeroRepartidor+".xls";    
        }   
        }
        JasperReport reporte = null;
        try {
            reporte = (JasperReport)JRLoader.loadObject(master);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeInformesPorRepartidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reporte, listConsolidado,ch);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeInformesPorRepartidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.tipoDeInforme==1){
            JRExporter exporter=new JRPdfExporter();
           exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
        try {
            exporter.exportReport();
                     //cnn.close();
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeInformesPorRepartidor.class.getName()).log(Level.SEVERE, null, ex);
        }

        }else{
            OutputStream ouputStream = null;
        try {
                ouputStream = new FileOutputStream(new File(destino));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JRExporter exporter=new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
                exporter.setParameter (JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
                try {
                    exporter.exportReport();
                             //cnn.close();
                } catch (JRException ex) {
                    Logger.getLogger(EmisionDeInformesPorRepartidor.class.getName()).log(Level.SEVERE, null, ex);
                }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(InformesDePedidosDeTango.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
                try {
                    ouputStream.close();
                } catch (IOException ex) {
                    Logger.getLogger(InformesDePedidosDeTango.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        
                 
                 File f=new File(destino);
                 if(f.exists()){
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+destino);
            } catch (IOException ex) {
                Logger.getLogger(EmisionDeInformesPorRepartidor.class.getName()).log(Level.SEVERE, null, ex);
            }
}

    } 
}
