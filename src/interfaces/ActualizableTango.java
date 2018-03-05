/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.ArrayList;

/**
 *
 * @author mauro
 */
public interface ActualizableTango {
    public String verificarRemitosTotales(ArrayList listadoHdr);
    public Boolean regenerarCantidadesTango(ArrayList pedidoDetalladoTango);
    
}
