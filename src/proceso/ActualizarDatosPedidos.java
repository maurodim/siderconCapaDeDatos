/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.Articulos;
import objetos.Clientes;
import objetos.DetallePesosPedido;
import objetos.PedidosParaReparto;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author Administrador
 */
public class ActualizarDatosPedidos extends Thread {
    static Connection cped=Coneccion.ObtenerConeccion();
    private ArrayList pedidos;
    static Map saldoCliente=new HashMap();
    static String fecha=null;    
    //private String fecha;

    public static void setFecha(String fecha) {
        ActualizarDatosPedidos.fecha = fecha;
    }

    
    public void setPedidos(ArrayList pedidos) {
        this.pedidos = pedidos;
    }
    public synchronized void cargarPedidosParaActualizar() throws SQLException{
        DetallePesosPedido det=new DetallePesosPedido();
        String sql="select * from pedidos_carga1.COD_ARTIC,pedidos_carga1.CANT_PEDID,pedidos_carga1.numero,pedidos_carga1.peso from pedidos_carga1 where peso=0";
        Statement st=cped.createStatement();
        st.execute(sql);
        ResultSet rs=st.getResultSet();
        while(rs.next()){
            det.setCodigoArticulo(rs.getString(1));
            det.setCantidadArticulo(rs.getDouble(2));
            det.setNumero(rs.getInt(3));
            this.pedidos.add(det);
            
        }
        st.close();
        rs.close();
    }
    
    public synchronized void run(){
             String codigo;
            String clave;
            Double valor = null;
            String cli;
            Double Saldo=0.00;
            Integer codigoNum;
            Double pesoItems = 0.00;
            ArrayList retorno=new ArrayList();	
        Integer numerador = 0; 
        PedidosParaReparto pedidos=new PedidosParaReparto();
       
        
        Map<String,Double> ar=new HashMap<String,Double>();
        Map<String,Double> detalle=new HashMap<String,Double>();
 
        Coneccion cn=new Coneccion();      
        Articulos art=new Articulos(); 
        Clientes cl=new Clientes();
        try {
            try {
                art.cargarListado();
            } catch (InterruptedException ex) {
                Logger.getLogger(ActualizarDatosPedidos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        Procesos pro=new Procesos();
        /*
        try {
           saldoCliente=pro.cargarSaldosDeClientes();
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        * 
        */
        try {
            ar=pro.cargarPesosDeArticulos();
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        DecimalFormat fr=new DecimalFormat("00");
        Calendar c1=Calendar.getInstance();
	Calendar c2=new GregorianCalendar();
	String dia=Integer.toString(c2.get(Calendar.DAY_OF_MONTH));
	String mes=Integer.toString(c2.get(Calendar.MONTH));
	String ano=Integer.toString(c2.get(Calendar.YEAR));
	
        int da=Integer.parseInt(dia);
        int me=Integer.parseInt(mes);
        me++;
        dia=fr.format(da);
        mes=fr.format(me);
        this.fecha=dia+"/"+mes+"/"+ano;
	System.err.println(this.fecha);
        try {
            try {
                //fecha="23/12/2011";
                SiderconCapaatos.listaPedidos=pro.ListarPedidosPorFecha(ActualizarDatosPedidos.fecha);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ActualizarDatosPedidos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ActualizarDatosPedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator it=SiderconCapaatos.listaPedidos.listIterator();
                while(it.hasNext()){
                    Double totalPeso=0.00;
                    Double cantidadItems=0.00;
                    Saldo=0.00;
                    pedidos=(PedidosParaReparto) it.next();
                    numerador++;
            try {
                detalle=pro.DetallePedido(pedidos.getCodigoTangoDePedido());
            } catch (SQLException ex) {
                Logger.getLogger(ActualizarDatosPedidos.class.getName()).log(Level.SEVERE, null, ex);
            }
                    Iterator a=detalle.entrySet().iterator();
                    while(a.hasNext()){
                        Map.Entry e=(Map.Entry)a.next();
                        codigo=(String) e.getKey();
                        codigo.trim(); 
                        pesoItems=ar.get(codigo);
                        cantidadItems=(Double) e.getValue();
                           System.out.println("codigo "+codigo+" valor"+pesoItems+" "+totalPeso);
                           if(pesoItems==null){
                               pesoItems=0.00;
                           }
                        totalPeso=totalPeso + (cantidadItems * pesoItems);
                        
                       
                    }
                    
                    cli=pedidos.getCodigoCliente();
                    cli.trim();
                    
                    System.out.println(cli+" "+cli.length());
                     //Saldo=(Double)saldoCliente.get(cli);
                     //Saldo=cl.getSaldo();
                     
 
                    //pesoItems=ar.get(codigo);
                    

                    pedidos.setPesoItems(pesoItems);
                    pedidos.setPesoTotal(totalPeso);
                    //pedidos.setSaldoCliente(Saldo);
                    art.setPesoUnitario(pedidos.getPesoItems());
                    art.setPesoUnitario((Double)ar.get(pedidos.getCodigoArticulo()));
                    //System.out.println("El peso es :"+pesoItems+" "+codigo);
                    System.out.println(numerador+" pedido numero "+pedidos.getCodigoTangoDePedido()+" razon social "+ pedidos.getRazonSocial()+" saldo :"+pedidos.getSaldoCliente()+" peso del items "+pedidos.getPesoTotal());

                }
       
    }
}
