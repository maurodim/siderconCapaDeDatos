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
}
