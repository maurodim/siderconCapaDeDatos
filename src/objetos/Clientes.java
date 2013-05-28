/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import actualizaciones.ChequearCantidadesPedidos;
import interfaces.Actualizable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import proceso.Coneccion;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author Administrador
 */
public class Clientes implements Actualizable,ChequearCantidadesPedidos{
        private String codigoCliente;
        private String razonSocial;
        private Double saldo;
        private Integer condicionDePago;
        private Date fechaActualizacion;
        private String empresa;
        private String domicilio;
        private String localidad;
        private String telefono;

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
        
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
        


    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public Integer getCondicionDePago() {
        return condicionDePago;
    }

    public void setCondicionDePago(Integer condicionDePago) {
        this.condicionDePago = condicionDePago;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    @Override
    public Double actualizarDatosSaldos(Connection ccn, String empresa, String codigo) {
        Double cli=0.00;
        try {
            
            String sql="select GVA14.SALDO_CC from GVA14 where COD_CLIENT LIKE '"+codigo+"'";
            Statement st=ccn.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                cli=rs.getDouble("SALDO_CC");
            }
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("!!!! ERROR EN LA ACTUALIZACION DE SALDO "+ex);
            cli=0.00;
        }
        return cli;
    }

    @Override
    public Double actualizarDatosArticulos(Connection ccn, String empresa, String numeroPedido, String CodigoArticulo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object check(Object pedido) {
        Clientes cll=(Clientes)pedido;
        Connection cnn=Coneccion.ObtenerConeccion();
        try {
            Statement st=cnn.createStatement();
            String sql="update clientes set DOMICILIO='"+cll.getDomicilio()+"',LOCALIDAD='"+cll.getLocalidad()+"',TELEFONO_1='"+cll.getTelefono()+"' where COD_CLIENT ='"+cll.getCodigoCliente()+"' and RAZON_SOCI like '"+cll.getRazonSocial()+"'";
            System.out.println("UPDATE EN MYSQL "+sql);
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Coneccion.CerrarConneccion(cnn);
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cll;
    }

    @Override
    public void verificar() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object actualizar(Object pedido) {
        Clientes cli=(Clientes)pedido;
        String empresa=cli.getEmpresa();
                        int numeroConeccion=0;
                        if(empresa.equals("BU")){
                            numeroConeccion=1;
                        }else{
                            if(empresa.equals("SD")){
                                numeroConeccion=2;
                            }else{
                                numeroConeccion=3;
                            }
                        }
                        Connection sqlC=null;
                        switch (numeroConeccion){
                            case 1:
                                sqlC=(Connection)SiderconCapaatos.sqlBu;
                                break;
                            case 2:
                                sqlC=(Connection)SiderconCapaatos.sqlSd;
                                break;
                            case 3:
                                sqlC=(Connection)SiderconCapaatos.sqlSdSrl;
                                break;
                        }
                        String sql="select GVA14.DOMICILIO,GVA14.LOCALIDAD,GVA14.TELEFONO_1 from GVA14 where COD_CLIENT ='"+cli.getCodigoCliente()+"'";
        try {
            Statement st=sqlC.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                cli.setDomicilio(rs.getString("DOMICILIO"));
                cli.setLocalidad(rs.getString("LOCALIDAD"));
                cli.setTelefono(rs.getString("TELEFONO_1"));
            }
            rs.close();
            st.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cli;
    }
        
}
