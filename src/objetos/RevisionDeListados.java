/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import interfaces.Revisionar;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import proceso.Coneccion;

/**
 *
 * @author Mauro Di
 */
public class RevisionDeListados implements Revisionar{
    private Integer numeroListado;
    private int numeroDeRevision;
    private String codigoDeArticulo;
    private Double cantidad;
    private Double cantidadNueva;
    private String codigoPedido;
    //private String campoNumero="numero";
            

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }
    
    public RevisionDeListados() {
     this.codigoDeArticulo = "";
        this.cantidad = 0.00;
        this.cantidadNueva =0.00;   
    }

    public Integer getNumeroListado() {
        return numeroListado;
    }

    public void setNumeroListado(Integer numeroListado) {
        this.numeroListado = numeroListado;
    }

    public int getNumeroDeRevision() {
        return numeroDeRevision;
    }

    public void setNumeroDeRevision(int numeroDeRevision) {
        this.numeroDeRevision = numeroDeRevision;
    }

    public String getCodigoDeArticulo() {
        return codigoDeArticulo;
    }

    public void setCodigoDeArticulo(String codigoDeArticulo) {
        this.codigoDeArticulo = codigoDeArticulo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getCantidadNueva() {
        return cantidadNueva;
    }

    public void setCantidadNueva(Double cantidadNueva) {
        this.cantidadNueva = cantidadNueva;
    }

    public RevisionDeListados(Integer numeroListado, int numeroDeRevision) {
        this.numeroListado = numeroListado;
        this.numeroDeRevision = numeroDeRevision+1;
        this.codigoDeArticulo = "";
        this.cantidad = 0.00;
        this.cantidadNueva =0.00;
    }

    @Override
    public ArrayList listadoDeMovimientos(Integer numeroListado) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean agregarRevision(ArrayList nuevos) {
        Connection con=Coneccion.ObtenerConeccion();
        Boolean check=false;
       
        try {
            Statement st=con.createStatement();
            String sql="";
            Iterator in=nuevos.listIterator();
            while(in.hasNext()){
                RevisionDeListados rev=(RevisionDeListados)in.next();
                sql="insert into movimientosLpm (numeroListado,revision,codigoArticulo,cantidad,cantidadNueva) values ("+rev.getNumeroListado()+","+rev.getNumeroDeRevision()+",'"+rev.getCodigoDeArticulo()+"',"+rev.getCantidad()+","+rev.getCantidadNueva()+")";
                st.executeUpdate(sql);
            }
            st.close();
            Coneccion.CerrarConneccion(con);
            check=true;
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }

    @Override
    public Object verificarCantidadAnterior(Object item) {
        RevisionDeListados rev=(RevisionDeListados)item;
        Connection con=Coneccion.ObtenerConeccion();
        try {
            Statement st=con.createStatement();
            String sql="select * from movimientosLpm where numeroListado ="+rev.getNumeroListado()+" order by revision";
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                String codigo=rs.getString("codigoArticulo");
                if(rev.getCodigoDeArticulo().equals(codigo)){
                    rev.setCantidad(rs.getDouble("cantidadNueva"));
                }
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rev;
    }

    @Override
    public int ultimaRevisionDeListado(Integer numeroDeListado) {
        Connection con=Coneccion.ObtenerConeccion();
        int ultimoListado=0;
        String sql="select movimientosLpm.revision from movimientosLpm where numeroListado ="+numeroDeListado+" group by revision";
        try {
            Statement st=con.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                ultimoListado=rs.getInt("revision");
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ultimoListado;
    }

    @Override
    public ArrayList convertirARevision(ArrayList carga) {
        PedidosParaReparto ped=new PedidosParaReparto();
        ArrayList listadoDeRevisiones=new ArrayList();
        Iterator ic=carga.listIterator();
        String sql="";
        Connection con=Coneccion.ObtenerConeccion();
        Integer numeroListado=0;
        int revisionNumero=0;
        try {
            Statement st=con.createStatement();
            while(ic.hasNext()){
                ped=(PedidosParaReparto)ic.next();
                sql="insert into movimientosLpm (numeroListado,revision,codigoArticulo,cantidadNueva) values ("+ped.getNumeroDeListadoDeMateriales()+","+ped.getNumeroDeRevisionDeListado()+",'"+ped.getCodigoArticulo()+"',"+ped.getCantidadArticulo()+")";
                st.executeUpdate(sql);
                sql="update pedidos_carga1 set revisionado=1 where numero ="+ped.getiDPedido();
                st.executeUpdate(sql);
                numeroListado=ped.getNumeroDeListadoDeMateriales();
                revisionNumero=0;
                
            }
            //st.close();
            //Coneccion.CerrarConneccion(con);
            listadoDeRevisiones=convercion(numeroListado,revisionNumero,st);
            st.close();
            Coneccion.CerrarConneccion(con);
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listadoDeRevisiones;
    }
    private ArrayList convercion(Integer listado,int revision,Statement sst) throws SQLException{
        ArrayList listadoRev=new ArrayList();
        String sql="select * from movimientosLpmV where numeroListado ="+listado+" and revision="+revision;
        //sql="select pedidos_carga1.NRO_PEDIDO,pedidos_carga1.COD_CLIENT,pedidos_carga1.listado,pedidos_carga1.RAZON_SOC,pedidos_carga1.COD_ARTIC,pedidos_carga1.DESC_ARTIC,sum(pedidos_carga1.CANT_PEDID * pedidos_carga1.peso)as total,sum(pedidos_carga1.CANT_PEDID),left(pedidos_carga1.entrega,10)as entrega,pedidos_carga1.vehiculo,(select unidades.descripcion from unidades where unidades.numero=pedidos_carga1.vehiculo)as descripcion,sum(pedidos_carga1.CANT_PEDID * pedidos_carga1.peso)as totalList from pedidos_carga1 where listado=$P{numListado} and COD_ARTIC not like '999%' and COD_ARTIC not like '30030011%' and COD_ARTIC not like '90020010%' and orden_num=0 group by COD_ARTIC";
        sst.execute(sql);
        ResultSet rs=sst.getResultSet();
        while(rs.next()){
            RevisionDeListados rev=new RevisionDeListados();
            rev.setNumeroListado(listado);
            rev.setNumeroDeRevision(revision);
            rev.setCantidad(rs.getDouble("totalCantidad"));
            rev.setCodigoDeArticulo(rs.getString("codigoArticulo"));
            System.err.println("REVISION CONVERTIDA "+rev.getCodigoDeArticulo()+" "+rev.getCantidad());
            listadoRev.add(rev);
        }
        
        return listadoRev;
    }

    @Override
    public Boolean chequearCambioDeListado(ArrayList carga) {
        Connection con=Coneccion.ObtenerConeccion();
        Boolean chq=false;
        String sql="";
        Statement st=null;
        ResultSet rs=null;
        PedidosParaReparto ped=new PedidosParaReparto();
        try {
            st=con.createStatement();
            
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator ic=carga.listIterator();
        while(ic.hasNext()){
            ped=(PedidosParaReparto)ic.next();
            /*
             * VER SI NO ES CONVENIENTE LEER DIRECTAMENTE DE LA TABLA PEDIDOS_CARGA1
             */
            sql="select movimientospedidoslistados.numero from movimientospedidoslistados where pedidoNumero like '%"+ped.getCodigoTangoDePedido()+"' and fechaEntrega like '"+ped.getFechaEnvio()+"' and listadoNumero="+ped.getNumeroDeListadoDeMateriales();
            try {
                st.execute(sql);
                rs=st.getResultSet();
                while(rs.next()){
                    chq=true;
                }
                rs.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        try {
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Coneccion.CerrarConneccion(con);
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return chq;
    }

    @Override
    public Boolean guardarDatosRevision(ArrayList carga) {
        Boolean resp=false;
        Connection con=Coneccion.ObtenerConeccion();
        Statement st=null;
        String sql="";
        try {
            st=con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        PedidosParaReparto ped=new PedidosParaReparto();
        Iterator icc=carga.listIterator();
        while(icc.hasNext()){
            ped=(PedidosParaReparto)icc.next();
            sql="insert into movimientospedidoslistados (pedidoNumero,fechaEntrega,listadoNumero,revisionNumero) values ('"+ped.getCodigoTangoDePedido()+"','"+ped.getFechaEnvio()+"',"+ped.getNumeroDeListadoDeMateriales()+","+ped.getNumeroDeRevisionDeListado()+")";
            System.out.println(" STRING SQL GUARDAR REVISION "+sql);
            try {
                st.executeUpdate(sql);
                resp=true;
            } catch (SQLException ex) {
                Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Coneccion.CerrarConneccion(con);
        } catch (SQLException ex) {
            Logger.getLogger(RevisionDeListados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resp;
    }
    
}
