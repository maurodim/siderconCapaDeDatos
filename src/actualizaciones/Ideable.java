/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import java.util.ArrayList;

/**
 *
 * @author mauro
 */
public interface Ideable {
    public Integer leerId(ArrayList lst,String empresa);
    public Boolean guardarIdEnMysql(Integer idTango,Integer idMy);
    public Boolean marcarComoLeido(Integer idMy);
    
}
