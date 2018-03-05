/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import Objetos.PdfListado;
import actualizaciones.ChequearCantidadesPedidos;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import objetos.Clientes;
import objetos.Mail;
import objetos.PedidosParaReparto;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author MAURO DI
 */
public class EmisionDeListadosDeMaterialesDetallados extends Thread{
    static Connection cc;
    private Integer numeroListado;
    private Double totalKg;
    private Integer numeroRevision;
    private String vehiculo;
    private String fechaEntrega;
    private ArrayList listado;

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setListado(ArrayList listado) {
        this.listado = listado;
    }
    
    

    public void setNumeroRevision(Integer numeroRevision) {
        this.numeroRevision = numeroRevision;
    }
    

    public void setNumeroListado(Integer numeroListado) {
        this.numeroListado = numeroListado;
    }

    public void setTotalKg(Double totalKg) {
        this.totalKg = totalKg;
    }

    

    
    @Override
    public synchronized void run(){
        chequearListado(this.numeroListado);
        //Configu configuracion=Configu.DETALLADO;
         
        cc=Coneccion.ObtenerConeccion();
        Map listDetallado=new HashMap();
        listDetallado.put("numeroListado",this.numeroListado);
        listDetallado.put("numeroRevision", this.numeroRevision);
        System.err.println("Listado "+this.numeroListado+" kg "+this.totalKg);
        listDetallado.put("kG",totalKg);
        String master = SiderconCapaatos.formularioDetallado;//"C://src//listadosDePreparacion//revisionDeListados.jasper";
        //configuracion.valueOf(master);
        System.out.println("DIRECCION DE DESTINO //////////////////////////////////// "+master);
        String destino="////COLOSSUS//logistica//Sist HDR//LPM//"+this.numeroListado+"-Rev 0 - listado detallado de materiales.pdf";
        String destino2="C://listadosHdr//"+this.numeroListado+"-Rev 0 - listado detallado de materiales.pdf";
        String kg=String.valueOf(totalKg);
        Iterator it=this.listado.listIterator();
        ArrayList definitivo=new ArrayList();
        PdfListado pdfD=new PdfListado();
        PedidosParaReparto pedi;
        Clientes cliente;
        ChequearCantidadesPedidos cheq=new Clientes();
        
        while(it.hasNext()){
            pedi= (PedidosParaReparto) it.next();
            pdfD=new PdfListado();
            cliente=new Clientes();
            cliente.setCodigoCliente(pedi.getCodigoCliente());
            cliente.setEmpresa(pedi.getEmpresa());
            cliente=(Clientes) cheq.actualizar(cliente);
            pdfD.setIdListado(pedi.getNumeroDeListadoDeMateriales());
            pdfD.setIdRevision(pedi.getNumeroDeRevisionDeListado());
            pdfD.setVehiculo(String.valueOf(pedi.getVehiculoAsignado()));
            pdfD.setKilos(String.valueOf(pedi.getPesoTotal()));
            pdfD.setFechaEntrega(pedi.getFechaEnvio());
            pdfD.setNumeroPedido(pedi.getCodigoTangoDePedido());
            pdfD.setNombreCliente(pedi.getRazonSocial());
            pdfD.setLeyenda1(pedi.getObservaciones());
            pdfD.setLeyenda2(pedi.getObservaciones1());
            pdfD.setLeyenda3(pedi.getObservaciones2());
            pdfD.setPeso(String.valueOf(pedi.getPesoItems()));
            pdfD.setSaldoCliente(String.valueOf(cliente.getSaldo()));
            pdfD.setRevision(String.valueOf(pedi.getNumeroDeRevisionDeListado()));
            pdfD.setFechaPedido(pedi.getFechaPedidosTango());
            pdfD.setLocalidad(pedi.getZonaDescripcion());
            pdfD.setCodigoArticulo(pedi.getCodigoArticulo());
            pdfD.setDescripcion(pedi.getDescripcionArticulo());
            pdfD.setCantidad(String.valueOf(pedi.getCantidadArticulo()));
            pdfD.setOrdenDetrabajo(String.valueOf(pedi.getNumeroDeProceso()));
            //pdfD.setRepetido(String.valueOf(pedi.get));
            pdfD.setEmpresa(pedi.getEmpresa());
            pdfD.setNombreVendedor(pedi.getNombreVendedor());
            pdfD.setDomicilioCliente(cliente.getDomicilio());
            pdfD.setLocalidadCliente(cliente.getLocalidad());
            
            definitivo.add(pdfD);
        }
        
        
        
        PdfListado pdf=new PdfListado(cc,this.numeroListado,this.numeroRevision,this.vehiculo,kg,this.fechaEntrega,destino2,destino,definitivo);
        pdf.start();
        
        
        /*
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
                 JRExporter exporter1=new JRPdfExporter();
                 exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                 exporter1.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
                 exporter1.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino));
                 exporter.setParameter(JRExporterParameter.OUTPUT_FILE,new java.io.File(destino2));
        try {
            
            exporter.exportReport();
            exporter1.exportReport();
            
            //cnn.close();
        } catch (JRException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("ERROR EN LA CREACION DEL ARCHIVO PDF :"+ex+" "+destino);
        }
        */
                PrintWriter print = new PrintWriter(System.out, true);
                 File f=new File(destino2);
                 if(f.exists()){
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+destino2);
                
                System.out.println("DESTINO :::"+destino2+"---");
                Mail mail=new Mail();
                mail.setDireccionFile(destino2);
                mail.setDetalleListado(this.numeroListado+"-Rev 0 - listado detallado de materiales.pdf");
                mail.setAsunto("LPM DETALLADA GENERADA N° "+this.numeroListado);
                         try {
                             mail.enviarMailRepartoCargaCompleta();
                         } catch (MessagingException ex) {
                             Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
                         }
            } catch (IOException ex) {
                Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
            }
}
                 
                 /*
        try {
            //Coneccion.CerrarConneccion(cc);
        } catch (SQLException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
        }
        * */
    }
    private synchronized void chequearListado(int listadox){
        try {
            Coneccion con=new Coneccion();
            Connection cnn1=con.ObtenerConeccion();
            blanquearListado(cnn1,listadox);
            ArrayList listado=new ArrayList();
            ArrayList numeros=new ArrayList();
            String sql="select pedidos_carga1.DESC_ARTIC,pedidos_carga1.numero from pedidos_carga1 where listado="+listadox;
            Statement st=cnn1.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                listado.add(rs.getString(1));
                numeros.add(rs.getInt(2));
            }
            rs.close();
            st.close();
            String actual="";
            for (int i=0;i<listado.size();i++){
             String descripcion=(String)listado.get(i);   
             Integer numeroSel=(Integer)numeros.get(i);
             Integer numeroDestino=0;
             Statement st1=cnn1.createStatement();
             int c=i+1;
             for(int h=c;h<listado.size();h++){
                actual=(String)listado.get(h);
                numeroDestino=(Integer)numeros.get(h);
                //actual=(String)il.next();
                if(actual.equals(descripcion)){
                    System.err.println(actual+" descr "+descripcion);
                    sql="update pedidos_carga1 set repetidoEnListado=1 where numero="+numeroSel;
                    st1.executeUpdate(sql);
                    sql="update pedidos_carga1 set repetidoEnListado=1 where numero="+numeroDestino;
                    st1.executeUpdate(sql);
                }
            }
            st1.close();
            ////Coneccion.CerrarConneccion(cnn);
            //con.CerrarConneccion(cc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmisionDeListadosDeMaterialesDetallados.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    private void blanquearListado(Connection cc,int lis) throws SQLException{
        String sql="update pedidos_carga1 set repetidoEnListado=0 where listado="+lis;
        Statement sta=cc.createStatement();
        sta.executeUpdate(sql);
        System.out.println(sql);
        sta.close();
    }
    
}
