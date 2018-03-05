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
import java.util.logging.Level;
import java.util.logging.Logger;
import proceso.Coneccion;

/**
 *
 * @author mauro
 */
public class Vendedores {
    private Integer numero;
    private String nombre;
    private static ArrayList listado=new ArrayList();

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static ArrayList getListado() {
        return listado;
    }
    
    
    public static void listar(){
        Coneccion con=new Coneccion();
        Connection cnn=con.getCn();
        listado.clear();
        try {
            Statement st=cnn.createStatement();
            String sql="select * from vendedores";
            st.execute(sql);
            ResultSet rs=st.getResultSet();
            Vendedores ven=null;
            while(rs.next()){
                ven=new Vendedores();
                ven.setNumero(rs.getInt("numero"));
                ven.setNombre(rs.getString("nombre"));
                listado.add(ven);
            }
            rs.close();
            st.close();
            con.CerrarCn(cnn);
        } catch (SQLException ex) {
            Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void guardarVendedor(Vendedores vend){
        Coneccion con=new Coneccion();
        Connection cnn=con.getCn();
        String sql="insert into vendedores (numero,nombre) values ("+vend.getNumero()+",'"+vend.getNombre()+"')";
        try {
            Statement st=cnn.createStatement();
            st.executeUpdate(sql);
            st.close();
            con.CerrarCn(cnn);
        } catch (SQLException ex) {
            Logger.getLogger(Vendedores.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
