/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.io.IOException;
import net.sf.jasperreports.engine.JRException;

/**
 *
 * @author Administrador
 */
public class EmisionDeListados {
    
    
    public void ImprimirListadoDetallado(Integer numeroListado,Double total,Integer numeroRevision) throws IOException, JRException{
       EmisionDeListadosDeMaterialesDetallados list=new EmisionDeListadosDeMaterialesDetallados();
       list.setNumeroListado(numeroListado);
       list.setNumeroRevision(numeroRevision);
       list.setTotalKg(total);
       list.start();
       
    }
    public void ImprimirListadoConsolidado(String fechaEnvio,Integer numVehiculo,String descVehiculo,Double total,Integer numeroListado,Integer revision) throws JRException, IOException{
        EmisionDeListadosDeMaterialesConsolidados emiList=new EmisionDeListadosDeMaterialesConsolidados();
        emiList.setFechaEnvio(fechaEnvio);
        emiList.setNumVehiculo(numVehiculo);
        emiList.setDescVehiculo(descVehiculo);
        emiList.setTotal(total);
        emiList.setNumeroListado(numeroListado);
        emiList.setRevision(revision);
        emiList.start();
    }
    }
