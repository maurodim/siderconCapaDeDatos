/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import config.Configu;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
 * @author MAURO DI
 */
public class EmisionDeListadosDeMaterialesDetallados extends Thread{
    static Connection cc;
    private Integer numeroListado;
    private Double totalKg;
    private Integer numeroRevision;

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
        String destino="////SERVER//ventas//LPM//"+this.numeroListado+"-Rev 0 - listado detallado de materiales.pdf";
        String destino2="C://listadosHdr//"+this.numeroListado+"-Rev 0 - listado detallado de materiales.pdf";
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
