/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import interfaces.Ideable;
import java.sql.Connection;
import java.util.ArrayList;
import proceso.Coneccion;

/**
 *
 * @author mauro di
 */
public class ActualizarATango implements Ideable{
    private Connection con;
    private Coneccion cone;
    private Integer numeroMy;
    private Integer Id_gva03;
    private ArrayList items=new ArrayList();
    
    @Override
    public void ActualizarIds() {
        String sql="select pedidos_carga1.numero from pedidos_carga1 where ID_GVA03=0";
        
    }
    
}
