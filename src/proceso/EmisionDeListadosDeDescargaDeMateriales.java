/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
import objetos.PedidosParaReparto;

/**
 *
 * @author hernan
 */
public class EmisionDeListadosDeDescargaDeMateriales extends Thread{
    private Integer numeroListado;
    private Integer numeroDeRevision;
    private ArrayList detallePedidos=new ArrayList();
    private Double pesoTotal;
    private Connection cc;

    public Integer getNumeroListado() {
        return numeroListado;
    }

    public void setNumeroListado(Integer numeroListado) {
        this.numeroListado = numeroListado;
    }

    public Integer getNumeroDeRevision() {
        return numeroDeRevision;
    }

    public void setNumeroDeRevision(Integer numeroDeRevision) {
        this.numeroDeRevision = numeroDeRevision;
    }

    public ArrayList getDetallePedidos() {
        return detallePedidos;
    }

    public void setDetallePedidos(ArrayList detallePedidos) {
        this.detallePedidos = detallePedidos;
    }

    public Double getPesoTotal() {
        return pesoTotal;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }
    
    
    

    public synchronized void run(){
        //chequearListado(this.numeroListado);
        cc=Coneccion.ObtenerConeccion();
        PedidosParaReparto ped=new PedidosParaReparto();
        Double cantidad=0.00;
        Double peso=0.00;
        Map listDetallado=new HashMap();
        listDetallado.put("numeroListado",numeroListado);
        listDetallado.put("numeroRevision", numeroDeRevision);
        //System.err.println("Listado "+this.numeroListado+" kg "+this.totalKg);
        Iterator il=detallePedidos.listIterator();
        while(il.hasNext()){
            ped=(PedidosParaReparto)il.next();
            listDetallado.put("codigoArticulo", ped.getCodigoArticulo());
            listDetallado.put("descripcionArticulo",ped.getDescripcionArticulo());
            cantidad=(Double)ped.getCantidadArticulo();
            cantidad=cantidad * (-1);
            listDetallado.put("cantidad",cantidad);
            peso=ped.getPesoTotal();
            peso=peso *(-1);
            listDetallado.put("peso",peso);
            peso=0.00;
            cantidad=0.00;
            
        }
        listDetallado.put("nombreCliente",ped.getRazonSocial());
        listDetallado.put("codigoCliente",ped.getCodigoCliente());
        listDetallado.put("numeroPedido",ped.getCodigoTangoDePedido());
        //listDetallado.put("kG",totalKg);
        String master="C://src//listadosDePreparacion//descargaDeMateriales.jasper";
        System.out.println("DIRECCION DE DESTINO "+master);
        String destino="C://ListadosHdr//"+numeroListado+"-Rev "+numeroDeRevision+" - listado consolidado de materiales.pdf";
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
