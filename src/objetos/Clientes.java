/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import actualizaciones.ChequearCantidadesPedidos;
import interfaces.Actualizable;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
        private Coneccion con;
        private Connection cc;
        private Integer idTango;

    public Integer getIdTango() {
        return idTango;
    }

    public void setIdTango(Integer idTango) {
        this.idTango = idTango;
    }
        

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

    public Clientes() {
        this.saldo=0.00;

    }
    private void RegistrarActualizacion(Clientes cli){
        Coneccion cn=new Coneccion();
                Connection cp=cn.getCn();
                String sql="select saldosclientes.monto,saldosclientes.id from saldosclientes where codigoCliente='"+cli.codigoCliente+"' and empresa='"+cli.empresa+"'";
                
            try {
                PreparedStatement st=cp.prepareStatement(sql);
                //st.execute(sql);
                ResultSet rs=st.executeQuery();
                Double saldo=0.00;
                Integer id=0;
                while(rs.next()){
                    saldo=rs.getDouble("monto");
                    id=rs.getInt("id");
                }
                rs.close();
                if(cli.getSaldo()==saldo){
                    sql="update saldosclientes set fechaRelevada=now() where id="+id;
                }else{
                    if(id==0){
                        sql="insert into saldosclientes (codigoCliente,monto,empresa) values ('"+cli.codigoCliente+"',"+cli.saldo+",'"+cli.empresa+"')";
                    }else{
                        sql="update saldosclientes set monto="+cli.saldo+",fechaRelevada=now() where id="+id;
                    }
                }
                st=cp.prepareStatement(sql);
                st.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    @Override
    public Double actualizarDatosSaldos(Connection ccn, String empresa, String codigo,Integer idTangoCliente) {
        Double cli=0.00;
        try {
            if(SiderconCapaatos.falloConecion==0){
                cli=0.00;
            String sql="select GVA14.SALDO_CC,GVA14.COD_CLIENT,GVA14.RAZON_SOCI from GVA14 where ID_GVA14 = "+idTangoCliente;
            Statement st=ccn.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                cli=rs.getDouble("SALDO_CC");
                System.out.println(" COD CLIENTE Y SALDO :"+rs.getString("COD_CLIENT")+" "+rs.getString("RAZON_SOCI")+"saldo "+cli);
            }
            st.close();
            }
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
                con=new Coneccion();
        cc=con.getCn(); 
        
        try {
            Statement st=cc.createStatement();
            String sql="update clientes set DOMICILIO='"+cll.getDomicilio()+"',LOCALIDAD='"+cll.getLocalidad()+"',TELEFONO_1='"+cll.getTelefono()+"' where COD_CLIENT ='"+cll.getCodigoCliente()+"' and RAZON_SOCI like '"+cll.getRazonSocial()+"'";
            //System.out.println("UPDATE EN MYSQL "+sql);
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        try {
            //Coneccion.CerrarConneccion(cnn);
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        * */
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
                        String sql="select AR_SALDOS.ID_GVA14,AR_SALDOS.FECHA_MODI,AR_SALDOS.SALDO_CC,GVA14.TELEFONO_1,GVA14.DOMICILIO,GVA14.LOCALIDAD from AR_SALDOS LEFT JOIN GVA14 ON GVA14.ID_GVA14=AR_SALDOS.ID_GVA14 where AR_SALDOS.COD_CLIENT ='"+cli.getCodigoCliente()+"'";
        try {
            Statement st=sqlC.createStatement();
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                cli.setFechaActualizacion(rs.getDate("FECHA_MODI"));
                cli.setEmpresa(empresa);
                
                cli.setDomicilio(rs.getString("DOMICILIO"));
                cli.setLocalidad(rs.getString("LOCALIDAD"));
                cli.setTelefono(rs.getString("TELEFONO_1"));
                
                cli.setIdTango(rs.getInt("ID_GVA14"));
                cli.setSaldo(rs.getDouble("SALDO_CC"));
            }
            
            rs.close();
            st.close();
            this.RegistrarActualizacion(cli);
        } catch (SQLException ex) {
            Logger.getLogger(Clientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cli;
    }
        
}
