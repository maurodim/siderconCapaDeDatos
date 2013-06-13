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
import proceso.Coneccion;

/**
 *
 * @author Administrador
 */
public class Listados {
        //private String titulo;
        private Date fechaDeImpresion;
        private Integer numeroPedidoParaReparto;
        //private String codigoArticulo;
        //private String descripcionArticulo;
        //private Double cantidadArticulo;
        //private String detalleFraccion;
        //private Double pesoArticulo;
        private String observacionesPedido;
        private static Integer numeroListado;
        private static Integer numeroRevision;
        private Date fechaDeEntrega;

    public Date getFechaDeEntrega() {
        return fechaDeEntrega;
    }

    public void setFechaDeEntrega(Date fechaDeEntrega) {
        this.fechaDeEntrega = fechaDeEntrega;
    }

    public Date getFechaDeImpresion() {
        return fechaDeImpresion;
    }

    public void setFechaDeImpresion(Date fechaDeImpresion) {
        this.fechaDeImpresion = fechaDeImpresion;
    }

    public Integer getNumeroListado() {
        return numeroListado;
    }

    public void setNumeroListado(Integer numeroListado) {
        Listados.numeroListado = numeroListado;
    }

    public Integer getNumeroPedidoParaReparto() {
        return numeroPedidoParaReparto;
    }

    public void setNumeroPedidoParaReparto(Integer numeroPedidoParaReparto) {
        this.numeroPedidoParaReparto = numeroPedidoParaReparto;
    }

    public Integer getNumeroRevision() {
        return numeroRevision;
    }

    public void setNumeroRevision(Integer numeroRevision) {
        Listados.numeroRevision = numeroRevision;
    }

    public String getObservacionesPedido() {
        return observacionesPedido;
    }

    public void setObservacionesPedido(String observacionesPedido) {
        this.observacionesPedido = observacionesPedido;
    }

    public Listados() {
        Listados.numeroRevision=0;
    }
       
public static void nuevoListado() throws SQLException{
    Coneccion cnn=new Coneccion();
    Connection con=Coneccion.ObtenerConeccion();
    String sql="select * from listadosDeMateriales order by numero";
    Statement st=con.createStatement();
    st.execute(sql);
    ResultSet rs=st.getResultSet();
    int numeroL=0;
    while(rs.next()){
        numeroL=rs.getInt("numero");
    }
    Listados.numeroListado=numeroL++;
    Listados.numeroRevision=0;
    sql="insert into historicolistadorevision (numeroListado,numeroRevision) values ("+Listados.numeroListado+","+Listados.numeroRevision+")";
    st.executeUpdate(sql);
    st.close();
    rs.close();
    Coneccion.CerrarConneccion(con);
}    
public static Integer nuevaRevision() throws SQLException{
    Integer rev=0;
    Coneccion cnn=new Coneccion();
    Connection con=Coneccion.ObtenerConeccion();
    String sql="select * from historicolistadorevision where numeroListado ="+Listados.numeroListado+" order by numeroRevision";
    Statement st=con.createStatement();
    st.execute(sql);
    int numeroR=0;
    ResultSet rs=st.getResultSet();
    while(rs.next()){
        numeroR=rs.getInt("numeroRevision");
    }
    Listados.numeroRevision=numeroR++;
    //Listados.numeroRevision=0;
    sql="insert into historicolistadorevision (numeroListado,numeroRevision) values ("+Listados.numeroListado+","+Listados.numeroRevision+")";
    st.execute(sql);
    sql="update listadosDeMateriales set revision="+Listados.numeroRevision+" where numero="+Listados.numeroListado;
    st.executeUpdate(sql);
    st.close();
    rs.close();
    Coneccion.CerrarConneccion(con);
    rev=Listados.numeroRevision;
    return rev;
}
}