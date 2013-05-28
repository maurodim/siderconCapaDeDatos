/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import proceso.Coneccion;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;

/**
 *
 * @author hernan
 */
public class log {
    static Date fecha;
    static Integer cantidad;

    public log() {
        try {
            Coneccion con=new Coneccion();
    Connection cnn=con.ObtenerConeccion();
    String sql="select * from log";
    System.err.println(sql);
    Statement st=cnn.createStatement();
    st.execute(sql);
    ResultSet rs=st.getResultSet();
    while (rs.next()){
        this.fecha = rs.getDate("fecha");
        this.cantidad=rs.getInt("cantidad");
        System.out.println("fecha "+this.fecha);
    }  
    st.close();
    con.CerrarConneccion(cnn);
        } catch (SQLException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(log.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(1);
        }
    }
    
    public void validar(Date fechaAlmacenada) throws SQLException{
        //System.out.println(" fecha almacenada ++++"+fechaAlmacenada);
        if(log.cantidad == 0){
            //JOptionPane.showMessageDialog(null, "BD SATURADA DE TRANSACCIONES, REALICE EL MANTENIMIENTO DE BASES");
            System.err.println("BD SATURADA DE TRANSACCIONES, REALICE EL MANTENIMIENTO DE BASES");
            System.exit(1);
        }else{
            log.cantidad=log.cantidad - 1;
                        Coneccion con=new Coneccion();
    Connection cnn=con.ObtenerConeccion();
    String sql="update log set cantidad ="+log.cantidad+" where numero=2";
    System.err.println(sql);
    Statement st=cnn.createStatement();
    st.execute(sql);
    st.close();
    con.CerrarConneccion(cnn);
        }
    }

}
