/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones.objetosActualizables;

import actualizaciones.ActHdrAcc;
import actualizaciones.ActualizarHdr;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class EncabezadoHdr implements ActualizarHdr{
    private Integer numero;
    private Double pesoCarga;
    private Integer listadoNumero;
    private String fechaEntrega;
    private Integer numeroFletero;
    private Integer numeroVehiculo;
    private Integer knInicio;
    private Integer kmFinal;
    private String horaInicio;
    private String horaFinal;
    private Date fechaDeImpresion;
    private Double montoTotal;
    private Double totalVuelto;
    private String celular;
    private Integer capacidad;
    private String nombreFletero;

    public String getNombreFletero() {
        return nombreFletero;
    }

    public void setNombreFletero(String nombreFletero) {
        this.nombreFletero = nombreFletero;
    }
    
    

    public Date getFechaDeImpresion() {
        return fechaDeImpresion;
    }

    public void setFechaDeImpresion(Date fechaDeImpresion) {
        this.fechaDeImpresion = fechaDeImpresion;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Integer getKmFinal() {
        return kmFinal;
    }

    public void setKmFinal(Integer kmFinal) {
        this.kmFinal = kmFinal;
    }

    public Integer getKnInicio() {
        return knInicio;
    }

    public void setKnInicio(Integer knInicio) {
        this.knInicio = knInicio;
    }

    public Integer getListadoNumero() {
        return listadoNumero;
    }

    public void setListadoNumero(Integer listadoNumero) {
        this.listadoNumero = listadoNumero;
    }

    public Double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumeroFletero() {
        return numeroFletero;
    }

    public void setNumeroFletero(Integer numeroFletero) {
        this.numeroFletero = numeroFletero;
    }

    public Integer getNumeroVehiculo() {
        return numeroVehiculo;
    }

    public void setNumeroVehiculo(Integer numeroVehiculo) {
        this.numeroVehiculo = numeroVehiculo;
    }

    public Double getPesoCarga() {
        return pesoCarga;
    }

    public void setPesoCarga(Double pesoCarga) {
        this.pesoCarga = pesoCarga;
    }

    public Double getTotalVuelto() {
        return totalVuelto;
    }

    public void setTotalVuelto(Double totalVuelto) {
        this.totalVuelto = totalVuelto;
    }

    @Override
    public ArrayList leerEncabezados() {
        try {
            Coneccion caR=new Coneccion();
            Connection con=caR.ObtenerConeccion();
            ArrayList listado=new ArrayList();
            Statement st=con.createStatement();
            String sql="select *,(select fleteros.celular from fleteros where fleteros.numero=hdr.numeroFletero)as descFletero,(select unidades.carga_max from unidades where unidades.numero=hdr.numeroVehiculo)as cargaV,(select fleteros.nombre from fleteros where fleteros.numero=hdr.numeroFletero) as nombFletero from hdr where revizado=0";
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
               EncabezadoHdr enc=new EncabezadoHdr();
               enc.setNumero(rs.getInt("numero"));
               enc.setFechaDeImpresion(rs.getDate("fechaImpresion"));
               enc.setFechaEntrega(rs.getString("fechaEntrega"));
               enc.setHoraFinal(rs.getString("horaFinal"));
               enc.setHoraInicio(rs.getString("horaInicio"));
               enc.setKmFinal(rs.getInt("kmFinal"));
               enc.setKnInicio(rs.getInt("kmInicio"));
               enc.setListadoNumero(rs.getInt("listadoNumero"));
               enc.setMontoTotal(rs.getDouble("totalMonto"));
               enc.setNumeroFletero(rs.getInt("numeroFletero"));
               enc.setNumeroVehiculo(rs.getInt("numeroVehiculo"));
               enc.setPesoCarga(rs.getDouble("pesoCarga"));
               enc.setTotalVuelto(rs.getDouble("totalVuelto"));
               enc.setCelular(rs.getString("descFletero"));
               enc.setCapacidad(rs.getInt("cargaV"));
               enc.setNombreFletero(rs.getString("nombFletero"));
               System.err.println("HOJA DE RUTA Nº "+enc.getNumero());
               listado.add(enc);
               
            }
            rs.close();
            st.close();
            caR.CerrarConneccion(con);
            return listado;
        } catch (SQLException ex) {
            Logger.getLogger(EncabezadoHdr.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    @Override
    public ArrayList leerDetalles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void copiarEncabezados(ArrayList listadoEnc) {
        ActHdrAcc actu=new ActHdrAcc();
        actu.setListadoEnc(listadoEnc);
        actu.start();
        
    }

    @Override
    public void copiarDetalles(ArrayList listadoDet) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
