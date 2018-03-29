/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.ArrayList;

/**
 *
 * @author mauro di
 */
public interface Cargable {
    public Boolean agregarPedido(Object pedido);//AGREGA EL PEDIDO AL ARRAY DE PEDIDOS
    public ArrayList leerPedidosCargados(String fecha,int numeroUnidad);//DEVUELVE EL ARRAY CORRESPONDIENTE A LA CARGA DEL VEHICULO 
    public void listarPedidosCargados();//CARGA EL ARRAY CORRESPONDIENTE A LA CARGA DEL VEHICULO
    public Boolean quitarPedidoCargado();//ELIMINA EL PEDIDO DEL ARRAY
    public int pedidosParaNuevaRevision(Object pedido);//SERIA LO MISMO QUE AGREGAR PEDIDOS
    public Boolean actualizarEstadoDePedidos();//REVEE TODO EL ARRAY DE LA CARGA
    
}
