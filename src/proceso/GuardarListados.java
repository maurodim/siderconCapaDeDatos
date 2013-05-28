/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.PedidosParaReparto;

/**
 *
 * @author Administrador
 */
public class GuardarListados extends Thread{
    static Connection cg=Coneccion.ObtenerConeccion();
    private ArrayList list=new ArrayList();
    

    public void setList(ArrayList list) {
        this.list = list;
    }
    
    @Override
    public synchronized void run(){
        String sql=null;
        PedidosParaReparto pd=new PedidosParaReparto();
        Iterator ig=this.list.listIterator();
        //try {
        Statement st = null;
        try {
            st = cg.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(GuardarListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(ig.hasNext()){
            pd=(PedidosParaReparto)ig.next();
            sql="update pedidos_carga1 set hdr1="+pd.getNumeroDeHojaDeRuta()+", listado="+pd.getNumeroDeListadoDeMateriales()+",revision="+pd.getNumeroDeRevisionDeListado()+" where NRO_PEDIDO='"+pd.getCodigoTangoDePedido()+"' and entrega='"+pd.getFechaEnvio()+"'";
            try {
                st.executeUpdate(sql);
                System.out.println(sql);
            } catch (SQLException ex) {
                Logger.getLogger(GuardarListados.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
        try {
            /*
             * ACA DEBERÍA RECORRER LA TABLA PARA AGRUPAR LOS MATERIALES Y GENEERAR LA TABLA DE SOPORTE DE LA REVISION DE CONSOLIDADO,
             * UTILIZANDO EL MISMO STATEMENT
             * no, asi no sirve
             * 
             */
            //sql="";
            
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(GuardarListados.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
}
