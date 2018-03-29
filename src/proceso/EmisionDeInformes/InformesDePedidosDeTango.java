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
import proceso.EmisionDeListadosDeMaterialesConsolidados;

/**
 *
 * @author Administrador
 */
public class InformesDePedidosDeTango extends Thread{
    private Integer tipoDeInforme;
    private Date fD;
    private Date fH;

    public void setTipoDeInforme(Integer tipoDeInforme) {
        this.tipoDeInforme = tipoDeInforme;
    }

    public void setfD(Date fD) {
        this.fD = fD;
    }

    public void setfH(Date fH) {
        this.fH = fH;
    }
    
    public synchronized void run(){
        Map listConsolidado=new HashMap();
        int num=0;
         
        Connection ch=Coneccion.ObtenerConeccion();
        listConsolidado.put("fechaDesde",fD);
        listConsolidado.put("fechaHasta",fH);
        //System.out.println(fechaEnvio+" "+numVehiculo+" "+descVehiculo+" "+total);
        String master="C://src//informes//InformesDePedidosDeTango.jasper";
        String destino=null;
        if(this.tipoDeInforme==1){
            destino="C://Informes//"+"informes de pedidos de tango.pdf";
        }else{
            destino="C://Informes//"+"informes de pedidos de tango.xls";  
            
        }
        JasperReport reporte = null;
        try {
            reporte = (JasperReport)JRLoader.loadObject(master);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reporte, listConsolidado,ch);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.tipoDeInforme==1){
            JRExporter exporter=new JRPdfExporter();
           exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
        try {
            exporter.exportReport();
                     //cnn.close();
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
        }

        }else{
            OutputStream ouputStream = null;
        try {
                ouputStream = new FileOutputStream(new File(destino));
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                JRExporter exporter=new JRXlsExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
                //exporter.setParameter (JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.TRUE);
                try {
                    exporter.exportReport();
                             //cnn.close();
                } catch (JRException ex) {
                    Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(EmisionDeListadosDeMaterialesConsolidados.class.getName()).log(Level.SEVERE, null, ex);
            }
}
    
 
    }
    private void EmitirXls(){
        String sql="select pedidos_carga1.NRO_PEDIDO,pedidos_carga1.RAZON_SOC,pedidos_carga1.CANT_PEDID,if(pedidos_carga1.CANT_FACT >0,"+"Entrega Parcial"+","+"Entrega Total"+"),pedidos_carga1.TALON_PEDI,pedidos_carga1.FEC_PEDIDO,pedidos_carga1.hdr1,pedidos_carga1.entrega,STR_TO_DATE(entrega,'%d.%m.%Y') as fech,pedidos_carga1.N_REMITO as comprobante from pedidos_carga1 where entregaConv between '"+fD+"' and '"+fH+"' and reparto=1 group by NRO_PEDIDO";
        
    }
    
}
