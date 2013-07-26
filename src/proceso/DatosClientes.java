/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.Clientes;
import objetos.PedidosParaReparto;
import siderconcapadatos.ListadoDeCargaPorVehiculo;

/**
 *
 * @author Administrador
 */
public class DatosClientes extends Thread{
    static Connection cd=null;
    private ArrayList cargados=new ArrayList();

    public void setCargados(ArrayList cargados) {
        this.cargados = cargados;
    }

    public ArrayList getCargados() {
        return cargados;
    }
    
    public synchronized void run(){
        Map saldoCliente=new HashMap();
        PedidosParaReparto pd=new PedidosParaReparto();
        Clientes cl=new Clientes();
        Coneccion cone=new Coneccion();
        cd=cone.ObtenerConeccion();
        String sql=null;
        String cli=null;
        Double saldo=0.00;
        Procesos pro=new Procesos();
       /*
        try {
            saldoCliente=pro.cargarSaldosDeClientes();
        } catch (SQLException ex) {
            Logger.getLogger(DatosClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        * 
        */
        Iterator ic=ListadoDeCargaPorVehiculo.carga.listIterator();
        Statement st = null;
        try {
            st = cd.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DatosClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(ic.hasNext()){
            pd=(PedidosParaReparto)ic.next();
            cli=pd.getCodigoCliente();
            cli.trim();
            saldo=(Double)pd.getSaldoCliente();
            //pd.setSaldoCliente(saldo);
            String numPed=pd.getCodigoTangoDePedido();
            int cantidad=numPed.length();
            numPed=numPed.substring(1, cantidad);
            System.out.println(numPed+" saldo "+saldo);
            /*
            if (saldo==null){
                System.out.println("es nulo");
            }else{
                sql="update pedidos_carga1 set saldoCliente="+pd.getSaldoCliente()+" where NRO_PEDIDO='%"+numPed+"' and entrega='"+pd.getFechaEnvio()+"'";
                System.out.println(sql);
                try {
                    st.executeUpdate(cli);
                } catch (SQLException ex) {
                    Logger.getLogger(DatosClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(DatosClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
             * 
          */   
        }
    }
}
