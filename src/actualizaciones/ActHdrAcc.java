/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import actualizaciones.objetosActualizables.EncabezadoHdr;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import proceso.ConeccionAccesRemota;

/**
 *
 * @author Administrador
 */
public class ActHdrAcc extends Thread{
    private ArrayList listadoEnc=new ArrayList();

    public void setListadoEnc(ArrayList listadoEnc) {
        this.listadoEnc = listadoEnc;
    }  
    public synchronized void run(){
                try {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(ActHdrAcc.class.getName()).log(Level.SEVERE, null, ex);
            }
            ConeccionAccesRemota caRem=new ConeccionAccesRemota();
            EncabezadoHdr en=new EncabezadoHdr();
            Connection cnn=caRem.ObtenerConexion();
            String sql=null;
            Iterator il=listadoEnc.listIterator();
            Statement st=null;
            while(il.hasNext()){
                en=(EncabezadoHdr)il.next();
                sql="insert into encabezado_hdr (numero,unidad,peso,fecha,turno,celular,operador,capacidad,total_cobros,total_vueltos,hdr,listado) values ("+en.getNumero()+","+en.getNumeroVehiculo()+","+en.getPesoCarga()+",'"+en.getFechaEntrega()+"',Mañana,'"+en.getCelular()+"','"+en.getNombreFletero()+"',"+en.getCapacidad()+","+en.getMontoTotal()+","+en.getTotalVuelto()+","+en.getNumero()+","+en.getListadoNumero()+")";
                st=cnn.createStatement();
                st.executeUpdate(sql);
                st.close();
                
            }
            caRem.CerrarConexion(cnn);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EncabezadoHdr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EncabezadoHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
