/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

/**
 *
 * @author Administrador
 */
public interface AbmHdr {
    public void agregarItem(Object item,Integer numero,String comprobante);
    public void anularHdr(Integer numeroHdr);
}
