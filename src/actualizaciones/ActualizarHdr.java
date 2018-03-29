/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import java.util.ArrayList;

/**
 *
 * @author Administrador
 */
public interface ActualizarHdr {
    public ArrayList leerEncabezados();
    public ArrayList leerDetalles();
    public void copiarEncabezados(ArrayList listadoEnc);
    public void copiarDetalles(ArrayList listadoDet);
    
}
