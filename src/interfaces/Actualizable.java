/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.Connection;

/**
 *
 * @author Mauro Di
 */
public interface Actualizable {
    public Double actualizarDatosSaldos(Connection ccn,String empresa,String codigo,Integer idTangoCliente);
    public Double actualizarDatosArticulos(Connection ccn,String empresa,String numeroPedido,String CodigoArticulo);
    
}
