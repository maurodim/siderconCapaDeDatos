/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.ArrayList;

/**
 *
 * @author Mauro Di
 */
public interface Revisionar {
    public ArrayList listadoDeMovimientos(Integer numeroListado);
    public Boolean agregarRevision(ArrayList nuevos);
    public Object verificarCantidadAnterior(Object item);
    public int ultimaRevisionDeListado(Integer numeroDeListado);
    public ArrayList convertirARevision(ArrayList carga);
    public Boolean chequearCambioDeListado(ArrayList carga);//CHEQUEA EN TABLA HISTORICOPEDIDOSLISTADOS QUE EL MATERIAL Y PEDIDO NO CAMBIE DE LISTADO
    public Boolean guardarDatosRevision(ArrayList carga);//ENVIO EL ARRAY CON LOS OBJETOS PARA SER GUARDADOS Y RE GENERAR LOS LISTADOS
    public Integer leerNumeroDeListadoAnterior();
    
}
