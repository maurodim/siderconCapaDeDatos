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
import java.util.List;
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
import objetos.DetalleListado;
import objetos.PedidosParaReparto;

/**
 *
 * @author hernan
 */
public class EmisionDeListadosDeDescargaDeMateriales extends Thread{
    private static Integer numeroListado;
    private static Integer numeroDeRevision;
    private static List<PedidosParaReparto> detallePedidos=new ArrayList<PedidosParaReparto>();
    private static Double pesoTotal;
    private static Connection cc;

    public static Integer getNumeroListado() {
        return numeroListado;
    }

    public static void setNumeroListado(Integer numeroListado) {
        EmisionDeListadosDeDescargaDeMateriales.numeroListado = numeroListado;
    }

    public static Integer getNumeroDeRevision() {
        return numeroDeRevision;
    }

    public static void setNumeroDeRevision(Integer numeroDeRevision) {
        EmisionDeListadosDeDescargaDeMateriales.numeroDeRevision = numeroDeRevision;
    }

    public static Double getPesoTotal() {
        return pesoTotal;
    }

    public static void setPesoTotal(Double pesoTotal) {
        EmisionDeListadosDeDescargaDeMateriales.pesoTotal = pesoTotal;
    }

    public static void setDetallePedidos(List<PedidosParaReparto> detallePedidos) {
        EmisionDeListadosDeDescargaDeMateriales.detallePedidos = detallePedidos;
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
        ListaDataSource datasource=new ListaDataSource();
        int cantidadItems=0;
        
        while(il.hasNext()){
            ped=(PedidosParaReparto)il.next();
            DetalleListado det=new DetalleListado(ped.getCodigoArticulo(),ped.getDescripcionArticulo(),ped.getCantidadArticulo(),ped.getPesoItems());
            datasource.addListaDataSource(det);
            System.out.println("cant IT :"+cantidadItems+" / "+ped.getCodigoArticulo()+" / "+ped.getDescripcionArticulo());
            
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
            jasperPrint = JasperFillManager.fillReport(reporte, listDetallado,datasource);
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
