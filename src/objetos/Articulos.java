/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;
import proceso.Coneccion;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;

/**
 *
 * @author Administrador
 */
public class Articulos {
    private String codigo;
    private Double pesoUnitario;
    private static ArrayList codigoL=new ArrayList();
    private static ArrayList pesoUnitarioL=new ArrayList();
    static Connection cart=null;
    private String descripcionArticulo;
    private String sinonimoArticulo;
    private int estado;
    private String unidadDeMedida;

    public String getUnidadDeMedida() {
        return unidadDeMedida;
    }

    public void setUnidadDeMedida(String unidadDeMedida) {
        this.unidadDeMedida = unidadDeMedida;
    }
    

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo;
    }

    public void setDescripcionArticulo(String descripcionArticulo) {
        this.descripcionArticulo = descripcionArticulo;
    }

    public String getSinonimoArticulo() {
        return sinonimoArticulo;
    }

    public void setSinonimoArticulo(String sinonimoArticulo) {
        this.sinonimoArticulo = sinonimoArticulo;
    }
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo=codigo+"      ";
    }

    public Double getPesoUnitario() {
        return pesoUnitario;
    }

    public void setPesoUnitario(Double pesoUnitario) {
        this.pesoUnitario = pesoUnitario;
    }
    public synchronized void cargarListado() throws SQLException, InterruptedException{
         
        cart=Coneccion.ObtenerConeccion();
        try{
        String codig;
        String sql="select * from PESOS";
        Statement st=cart.createStatement();
        st.execute(sql);
        ResultSet rs=st.getResultSet();
        while(rs.next()){
            codig=rs.getString("codigo");
            codig.trim();
            codigoL.add(codig);
            pesoUnitarioL.add(rs.getDouble("peso"));
        }
        rs.close();
    }catch(Exception ex){
        //Thread.sleep(2000);
        GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
        String codig;
        String sql="select * from PESOS";
        Statement st=cart.createStatement();
        st.execute(sql);
        ResultSet rs=st.getResultSet();
        while(rs.next()){
            codig=rs.getString("codigo");
            codig.trim();
            codigoL.add(codig);
            pesoUnitarioL.add(rs.getDouble("peso"));
        }
        rs.close();
        
    }
    }

    public static ArrayList getCodigoL() {
        return codigoL;
    }

    public static ArrayList getPesoUnitarioL() {
        return pesoUnitarioL;
    }
    public Integer ubicarPosicion(String cod){
        Integer cantidad;
        Integer posicion = 1;
        String compar;
        cantidad=codigoL.size();
        System.out.println("ubicarPosicion.. cantidad:"+cantidad+" string:"+cod);
        for(Integer i=0;i<cantidad;i++){
            compar=(String) codigoL.get(i);
            compar.trim();
            compar+="      ";
            cod.trim();
            System.out.println(compar.length()+" "+compar+" "+cod.length()+" "+cod);
            if(compar.equals(cod)){
                posicion=i;

        }
       
    }
         return posicion;
    }
    public void cargarObjetoSeleccionado(Integer posicion){
        System.out.println("cargarObjetoSeleccionado recibe :"+posicion);
        this.codigo=(String) codigoL.get(posicion);
        this.pesoUnitario=(Double) pesoUnitarioL.get(posicion);
        
    }
}
