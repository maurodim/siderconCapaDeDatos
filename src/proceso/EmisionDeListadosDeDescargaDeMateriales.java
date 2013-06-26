/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.io.File;
import java.io.IOException;
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
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author hernan
 */
public class EmisionDeListadosDeDescargaDeMateriales extends Thread{
    private Integer numeroListado;
    private Integer numeroDeRevision;
    

    public synchronized void run(){
        //chequearListado(this.numeroListado);
        //cc=Coneccion.ObtenerConeccion();
        
        Map listDetallado=new HashMap();
        listDetallado.put("numeroListado",this.numeroListado);
        listDetallado.put("numeroRevision", this.numeroRevision);
        System.err.println("Listado "+this.numeroListado+" kg "+this.totalKg);
        listDetallado.put("kG",totalKg);
        String master="C://src//listadosDePreparacion//revisionDeListados.jasper";
        System.out.println("DIRECCION DE DESTINO "+master);
        String destino="C://ListadosHdr//"+this.numeroListado+"-Rev 0 - listado detallado de materiales.pdf";
        JasperReport reporte = null;
        try {
            reporte = (JasperReport)JRLoader.loadObject(master);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
        }
        JasperPrint jasperPrint = null;
        try {
            jasperPrint = JasperFillManager.fillReport(reporte, listDetallado,cc);
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR EN LA IMPRSION :"+ex);
        }
                 JRExporter exporter=new JRPdfExporter();
                 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                 exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
        try {
            exporter.exportReport();
            //cnn.close();
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR EN LA CREACION DEL ARCHIVO PDF :"+ex);
        }
                 
                 File f=new File(destino);
                 if(f.exists()){
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+destino);
            } catch (IOException ex) {
                Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
            }
}

}

}
