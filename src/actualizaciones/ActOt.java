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
public class ActOt extends Thread{
    @Override
    public void run(){
        
        ActualizarOrdenesDeTrabajo aOt=new GestionarOrdenesDeTrabajo();
        ActualizarOrdenesDeTrabajo otMy=new OrdenesTrabajoMy();
        ArrayList list=new ArrayList();
        
        list.clear();
        list=aOt.listarOT();
        otMy.actualzarOrdenes(list);
        aOt.actualzarOrdenes(list);
        
        
    }
}
