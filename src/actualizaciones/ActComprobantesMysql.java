/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import actualizaciones.objetosActualizables.Comprobantes;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import proceso.Coneccion;

/**
 *
 * @author Administrador
 */
public class ActComprobantesMysql extends Thread{
    private ArrayList listadoComprobantes;

    public void setListadoComprobantes(ArrayList listadoComprobantes) {
        this.listadoComprobantes = listadoComprobantes;
    }
    public synchronized void run(){
        try {
            Comprobantes comp=new Comprobantes();
            Coneccion con=new Coneccion();
            String sql=null;
            Connection cp=null;
            cp=con.ObtenerConeccion();
            Statement st=cp.createStatement();
            /*
            if (this.listadoComprobantes.size()>100){
                sql="truncate table TABLA2";
                st.execute(sql);
            }
             * 
             */
            Iterator ilC=this.listadoComprobantes.listIterator();
            String fecha=null;
            Date conv=null;
            while(ilC.hasNext()){
                comp=(Comprobantes)ilC.next();
            SimpleDateFormat fd=new SimpleDateFormat("yyyy-MM-dd");
            //DecimalFormat df=new DecimalFormat("00");
            //DecimalFormat da=new DecimalFormat("0000");
            //String fec=null;
            //String dia=null;
            //String mes=null;
            //String ano=null;
            //Integer das=0;
            //Integer ms=0;
            //Integer ao=0;
            //ConversionesFecha conv1=new ConversionesFecha();
            fecha=String.valueOf(comp.getFechaRemito());
           
            if(comp.getFechaRemito()==null){
                Date fechN=null;
                    try {
                        fecha="2010-01-01";
                        fechN = fd.parse(fecha);
                        comp.setFechaRemito(fechN);
                    } catch (ParseException ex) {
                        Logger.getLogger(ActComprobantesMysql.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
            }
            System.err.println("fecha leida "+fecha);
            
                try {
                    conv=fd.parse(fecha);
                } catch (ParseException ex) {
                    Logger.getLogger(ActComprobantesMysql.class.getName()).log(Level.SEVERE, null, ex);
                }
                sql="insert into TABLA2 (COD_CLI,RAZON_SOCI,N_COMP,FECHA_EMI,COND_VTA,COD_ARTICU,DESCRIPCIO,COD_DEP,CANT_REM,N_COMP_REM,FECHA_REM) values('"+comp.getCodigoCliente()+"','"+comp.getRazonSocial()+"','"+comp.getNumeroComprobante()+"','"+comp.getFechaEmisionComprobante()+"',"+comp.getCondicionVenta()+",'"+comp.getCodigoArticulo()+"','"+comp.getDescripcion()+"',"+comp.getCodigoDeposito()+","+comp.getCantidad()+",'"+comp.getNumeroRemito()+"','"+comp.getFechaRemito()+"')";
               st.executeUpdate(sql); 
               
            }
            st.close();
            con.CerrarConneccion(cp);
            ActComprobantesAcc acmD=new ActComprobantesAcc();
            acmD.setListadoComprobantes(listadoComprobantes);
            acmD.start();
        } catch (SQLException ex) {
            Logger.getLogger(ActComprobantesMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
