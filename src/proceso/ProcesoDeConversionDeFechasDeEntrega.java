/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.ConversionesFecha;

/**
 *
 * @author Administrador
 */
public class ProcesoDeConversionDeFechasDeEntrega extends Thread{
    private Connection cp=null;
    private Coneccion con;

    public ProcesoDeConversionDeFechasDeEntrega() {
        con=new Coneccion();
        cp=con.getCn();
    }
    
    
    public synchronized void run(){
         
        try {
            
            ArrayList listadoConv=new ArrayList();        
            SimpleDateFormat fd=new SimpleDateFormat("yyyy-MM-dd");
            String fec=null;
            String dia=null;
            String mes=null;
            String ano=null;
            ConversionesFecha conv1=new ConversionesFecha();
            String sql="select pedidos_carga1.entrega,pedidos_carga1.numero,str_to_date(pedidos_carga1.entrega,'%d/%m/%Y') from pedidos_carga1 where entregaConv is null";
            Statement st=cp.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
            ConversionesFecha conv=new ConversionesFecha();
                conv.setNumeroDeCampo(rs.getInt(2));
                System.out.println("REGISTRO DE ENTREGA "+conv.getNumeroDeCampo());
                conv.setFechaOriginal(rs.getString(3).substring(0,10));
                
                /*
                dia=rs.getString(3).substring(0,2);
                mes=rs.getString(3).substring(3,5);
                ano=rs.getString(3).substring(6,10);
                fec=ano+"-"+mes+"-"+dia;
                */
                  //Date fec1=fd.format(fec);
                conv.setFechaConvertida(rs.getDate(3));
                
                System.err.println(" NUMEROOOOOOO "+conv.getFechaConvertida()+" fecha origianl "+rs.getString(1)+" foramto"+fec);
                listadoConv.add(conv);
            }
            
            rs.close();
            Iterator ipp=listadoConv.listIterator();
            while(ipp.hasNext()){
                conv1=(ConversionesFecha)ipp.next();
                sql="update pedidos_carga1 set entregaConv ='"+conv1.getFechaConvertida()+"' where numero="+conv1.getNumeroDeCampo();
                System.err.println("sql de fechas :"+sql);
                st.executeUpdate(sql);
            }
            st.close();
            //Coneccion.CerrarConneccion(cp);
        } catch (SQLException ex) {
            Logger.getLogger(ProcesoDeConversionDeFechasDeEntrega.class.getName()).log(Level.SEVERE, null, ex);
            
            /*
            try {
                //Coneccion.CerrarConneccion(cp);
            } catch (SQLException ex1) {
                Logger.getLogger(ProcesoDeConversionDeFechasDeEntrega.class.getName()).log(Level.SEVERE, null, ex1);
            }
            * */
        }
    }
        private Date deStringToDate(String fecha) {
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaEnviar = null;
        try {
            fechaEnviar = (Date) formatoDelTexto.parse(fecha);
            return fechaEnviar;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    
}
