/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import Objetos.PdfHdr;
import java.io.File;
import java.io.IOException;
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
import javax.swing.JOptionPane;
import objetos.Mail;
import objetos.PedidosParaReparto;
import objetos.Vehiculos;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author Administrador
 */
public class EmisionHojaDeRuta extends Thread{
    static Connection ch=null;
    private ArrayList listado=new ArrayList();
    private String fecha;
    private Integer numeroFletero;
    private Integer numeroVehiculo;
    private Double totalMonto;
    private Double totalVuelto;
    private Coneccion con=null;

    public void setTotalMonto(Double totalMonto) {
        this.totalMonto = totalMonto;
    }

    public void setTotalVuelto(Double totalVuelto) {
        this.totalVuelto = totalVuelto;
    }

    public void setNumeroFletero(Integer numeroFletero) {
        this.numeroFletero = numeroFletero;
    }

    public void setNumeroVehiculo(Integer numeroVehiculo) {
        this.numeroVehiculo = numeroVehiculo;
    }
    

    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
    public void setListado(ArrayList listado) throws SQLException {
       //Procesos pr=new Procesos();
        this.listado = listado;
    }

    public EmisionHojaDeRuta() {
        con=new Coneccion();
    }
    
    
    public void run(){
        PedidosParaReparto ped=new PedidosParaReparto();
        Procesos pro=new Procesos();
        Vehiculos uni=new Vehiculos();
        ArrayList unidades=new ArrayList();
         
        ch=con.getCn();
        try {
            unidades=pro.ListarVehiculos();
        } catch (SQLException ex) {
            Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql=null;
        Integer num=0;
        try {
            num = numeroHojaDeRuta();
        } catch (SQLException ex) {
            Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("corresponde hoja"+num);
        Double pesoTotal=0.00;
        Integer listadoNum=0;
        String fechaEntrega=null;
        Iterator il=this.listado.listIterator();
        while(il.hasNext()){
            ped=(PedidosParaReparto)il.next();
            ped.setNumeroDeHojaDeRuta(num);
            pesoTotal+=ped.getPesoTotal();
            
            System.out.println("listado numero "+listadoNum);
            if(ped.getNumeroDeListadoDeMateriales() > 0)listadoNum=ped.getNumeroDeListadoDeMateriales();
            System.out.println("listado numero2 "+listadoNum+" "+ped.getFechaEnvio()+" "+ped.getVehiculoAsignado());
            fechaEntrega=ped.getFechaEnvio();
            fechaEntrega=fechaEntrega.substring(0,10);
            this.numeroVehiculo=ped.getVehiculoAsignado();
            uni=(Vehiculos)unidades.get(ped.getVehiculoAsignado());
            System.out.println("pedidos "+ped.getCodigoTangoDePedido()+" listado "+ped.getNumeroDeListadoDeMateriales());
        }
        sql="insert into hdr (pesoCarga,listadoNumero,fechaEntrega,numeroFletero,numeroVehiculo,kmInicio,totalMonto,totalVuelto) values((select listadosdemateriales.pesoTotal from listadosdemateriales where listadosdemateriales.numero="+listadoNum+"),"+listadoNum+",'"+fechaEntrega+"',"+this.numeroFletero+","+this.numeroVehiculo+","+uni.getKilometrosActuales()+","+this.totalMonto+","+this.totalVuelto+")";
        System.out.println("sentencia hdr "+sql);
        Statement st = null;
        try {
            st = ch.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            st.executeUpdate(sql);
        } catch (SQLException ex) {
            System.err.println(ex);
            String sql1="select COD_ARTIC,CANT_PEDID,((select round(pesos.peso,2) from pesos where pesos.codigo = pedidos_carga1.COD_ARTIC limit 0,1) * CANT_PEDID) as pesoIndividual from pedidos_carga1 where listado="+listadoNum;
            Double estr=0.00;
            try {
            ResultSet rs=st.executeQuery(sql1);
           
            
                while(rs.next()){
                    estr=estr + rs.getDouble("pesoIndividual");
                }
            } catch (SQLException ex1) {
                Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex1);
            }
            estr=Math.round(estr * 100.0) / 100.0;
            sql="insert into hdr (pesoCarga,listadoNumero,fechaEntrega,numeroFletero,numeroVehiculo,kmInicio,totalMonto,totalVuelto) values("+estr+","+listadoNum+",'"+fechaEntrega+"',"+this.numeroFletero+","+this.numeroVehiculo+","+uni.getKilometrosActuales()+","+this.totalMonto+","+this.totalVuelto+")";
            System.out.println(sql);
            try {
                st.executeUpdate(sql);
            } catch (SQLException ex1) {
                Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        GuardarListados gl=new GuardarListados();
        gl.setList(this.listado);
        gl.start();
        ArrayList resultado=new ArrayList();
        resultado=this.listado;
        /*
        try {
            try {
                resultado=pro.actualizarComprobantesPedidos(this.listado);
            } catch (ParseException ex) {
                Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        
        Iterator ill=resultado.listIterator();
        
            //Statement st = null;
        try {
            st = ch.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex);
        }
        int orden=0;
        while(ill.hasNext()){
            ped=(PedidosParaReparto)ill.next();
            orden++;
            //String comp=ped.getNumeroComprobante().substring(0,15);
            //ped.setNumeroComprobante(comp);
            sql="insert into detalle_hdr (cliente,numero_cli,comprobante,importe,vuelto,hdr,empresa,orden,vendedor) values ('"+ped.getRazonSocial()+"','"+ped.getCodigoCliente()+"','"+ped.getNumeroComprobante()+"','"+ped.getSaldoACobrar()+"','"+ped.getVuelto()+"',"+ped.getNumeroDeHojaDeRuta()+",'"+ped.getEmpresa()+"',"+orden+","+ped.getNumeroVendedor()+")";
            System.out.println("codigo para hdr "+sql);
            //st.executeUpdate(sql);
           
            try {
                st.executeUpdate(sql);
                sql="update pedidos_carga1 set hdr1="+ped.getNumeroDeHojaDeRuta()+", fletero="+ped.getNumeroDeFletero()+",N_REMITO='"+ped.getNumeroComprobante()+"' where numero="+ped.getiDPedido();
                st.executeUpdate(sql);
            } catch (SQLException ex) {
                //JOptionPane.showMessageDialog(null, "EL CAMPO COMPROBANTE ES DEMASIADO LARGO - REDUZCALO. GRACIAS");
                System.out.println("EL CAMPO COMPROBANTE ES DEMASIADO LARGO - REDUZCALO. GRACIAS");
            }
        }
        
        Map listConsolidado=new HashMap();
        listConsolidado.put("numeroHdr",num);
        //System.out.println(fechaEnvio+" "+numVehiculo+" "+descVehiculo+" "+total);
        String master=SiderconCapaatos.formularioHdr;
        System.out.println(SiderconCapaatos.formularioHdr);
        String destino="////COLOSSUS//logistica//Sist HDR//HDR//"+num+"hdr.pdf";
        String destino2="C://Hdr//"+num+"hdr.pdf";
        
        Connection ch1=Coneccion.ObtenerConeccion();
        //System.out.println(ch1.toString());
        
        PdfHdr pdf=new PdfHdr(ch1,num,destino2,destino);
        pdf.start();
        
               
            File f=new File(destino2);
            System.out.println("destino2 hdr: "+destino2);
           // if(f.exists()){
           
                //Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+destino2);
                 Mail mail=new Mail();
                mail.setDireccionFile(destino2);
                mail.setDetalleListado(num+"hdr.pdf");
                mail.setAsunto("HDR GENERADA NÂ° "+num);
                         try {
                             mail.enviarMailRepartoCargaCompleta();
                         } catch (MessagingException ex) {
                             Logger.getLogger(EmisionHojaDeRuta.class.getName()).log(Level.SEVERE, null, ex);
                             //System.err.println(ex);
                             JOptionPane.showMessageDialog(null,"El mail no ha podido ser enviado, informe del error: "+ex);
                         }
            
           // }
        
 
    }
    private Integer numeroHojaDeRuta() throws SQLException{
        Integer numHdr=0;
        String sql="select hdr.numero from hdr order by numero";
        Statement st=ch.createStatement();
        st.execute(sql);
        ResultSet rs=st.getResultSet();
        while(rs.next()){
            numHdr=rs.getInt(1);
            //System.out.println("leyendo...........");
        }
        st.close();
        rs.close();
        numHdr++;
        return numHdr;
    }
}
