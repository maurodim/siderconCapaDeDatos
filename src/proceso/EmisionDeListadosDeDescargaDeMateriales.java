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
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author mauro di
 */
public class EmisionDeListadosDeDescargaDeMateriales extends Thread{
    private Integer numeroListado;
    private Integer numeroDeRevision;
    private ArrayList detallePedidos1=new ArrayList();
    private Double pesoTotal;
    private Connection cc;
    private String codigoCliente;
    private String nombreCliente;
    private int numeroVehiculo;
    private String descripcionVehiculo;
    private String fechaEntrega;

    public void setDetallePedidos1(ArrayList detallePedidos1) {
        this.detallePedidos1 = detallePedidos1;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setNumeroVehiculo(int numeroVehiculo) {
        this.numeroVehiculo = numeroVehiculo;
    }

    public void setDescripcionVehiculo(String descripcionVehiculo) {
        this.descripcionVehiculo = descripcionVehiculo;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setNumeroListado(Integer numeroListado) {
        this.numeroListado = numeroListado;
    }

    public void setNumeroDeRevision(Integer numeroDeRevision) {
        this.numeroDeRevision = numeroDeRevision++;
    }

    public void setPesoTotal(Double pesoTotal) {
        this.pesoTotal = pesoTotal;
    }

public void addPedido(PedidosParaReparto ped){
    this.detallePedidos1.add(ped);
}
    
    
    

    @Override
    public synchronized void run(){
        //chequearListado(this.numeroListado);
         
        cc=Coneccion.ObtenerConeccion();
        String fecha="";
        PedidosParaReparto ped=new PedidosParaReparto();
        Double cantidad=0.00;
        Double peso=0.00;
        Map listDetallado=new HashMap();
        listDetallado.put("numeroListado",this.numeroListado);
        listDetallado.put("numeroRevision", this.numeroDeRevision);
        //System.err.println("Listado "+this.numeroListado+" kg "+this.totalKg);
        ArrayList listadoP=this.detallePedidos1;
        Iterator il=listadoP.listIterator();
        ListaDataSource datasource=new ListaDataSource();  
        int cantidadItems=0;
        cantidadItems=listadoP.size();
        Double pesoDescarga=0.00;
        while(il.hasNext()){
            ped=(PedidosParaReparto)il.next();
            fecha=ped.getFechaEnvio();
            pesoDescarga=ped.getPesoTotal() * (-1);
            DetalleListado det=new DetalleListado(ped.getCodigoArticulo(),ped.getDescripcionArticulo(),ped.getCantidadArticulo(),(ped.getPesoTotal()*(-1)));
            
            datasource.addListaDataSource(det);
            System.out.println("cant IT :"+cantidadItems+" / "+ped.getCodigoArticulo()+" / "+ped.getDescripcionArticulo()+" / "+ped.getPesoItems()+" "+ped.getPesoTotal());
            
        }
        listDetallado.put("nombreCliente",this.nombreCliente);
        listDetallado.put("codigoCliente",this.codigoCliente);
        listDetallado.put("fechaDeEntrega", fecha);
        listDetallado.put("descripcionVehiculo",this.descripcionVehiculo);
        //listDetallado.put("numeroPedido",ped.getCodigoTangoDePedido());
        //listDetallado.put("kG",totalKg);
        String master=SiderconCapaatos.formularioDescarga;
        System.out.println("DIRECCION DE DESTINO "+master);
        String destino="C://ListadosHdr//"+numeroListado+"-Rev "+numeroDeRevision+" - listado consolidado de descarga de materiales.pdf";
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
            System.err.println("ERROR EN LA IMPRESION :"+ex);
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
