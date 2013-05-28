/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seguimientos;

/**
 *
 * @author hernan
 */
public interface GuardarMovimientos {
    public void registrarMovimiento(String texto,String nombre,String fecha);
    public void registrarSentencias(String texto,String nombre,String fecha);
    public void registrarErrores(String texto,String nombre,String fecha);
}
