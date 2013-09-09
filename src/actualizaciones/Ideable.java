/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

/**
 *
 * @author mauro
 */
public interface Ideable {
    public Integer leerId(String numeroDePedido,String codigoDeArticulo,Double cantidad,String descripcionArticulo,String empresa);
    public Boolean guardarIdEnMysql(Integer idTango,Integer idMy);
    public Boolean marcarComoLeido(Integer idMy);
    
}
