/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import objetos.PedidosParaReparto;

/**
 *
 * @author Administrador
 */
public class EmisionDeListados {
    
    
    public void ImprimirListadoDetallado(Integer numeroListado,Double total,Integer numeroRevision,String unidad,ArrayList lista) throws IOException{
       EmisionDeListadosDeMaterialesDetallados list=new EmisionDeListadosDeMaterialesDetallados();
       list.setVehiculo(unidad);
       list.setListado(lista);
       list.setNumeroListado(numeroListado);
       list.setNumeroRevision(numeroRevision);
       list.setTotalKg(total);
       list.start();
       
    }
    public void ImprimirListadoConsolidado(String fechaEnvio,Integer numVehiculo,String descVehiculo,Double total,Integer numeroListado,Integer revision) throws IOException{
        EmisionDeListadosDeMaterialesConsolidados emiList=new EmisionDeListadosDeMaterialesConsolidados();
        emiList.setFechaEnvio(fechaEnvio);
        emiList.setNumVehiculo(numVehiculo);
        emiList.setDescVehiculo(descVehiculo);
        emiList.setTotal(total);
        emiList.setNumeroListado(numeroListado);
        emiList.setRevision(revision);
        emiList.start();
    }
    public void ImprimirListadoDeDescargaDeMateriales(Integer numeroListado,Integer numeroDeRevision,ArrayList detallePedido,String vehiculo,String codigoCliente,String nombreCliente) throws IOException{
        EmisionDeListadosDeDescargaDeMateriales descM=new EmisionDeListadosDeDescargaDeMateriales();
        descM.setNumeroListado(numeroListado);
        descM.setNumeroDeRevision(numeroDeRevision);
        descM.setCodigoCliente(codigoCliente);
        descM.setNombreCliente(nombreCliente);
        descM.setDescripcionVehiculo(vehiculo);
        PedidosParaReparto ped=new PedidosParaReparto();
        Iterator il=detallePedido.listIterator();
        while(il.hasNext()){
            ped=(PedidosParaReparto)il.next();
            ped.setCantidadArticulo(ped.getCantidadArticulo()*(-1));
            descM.addPedido(ped);
        }
        descM.start();
    }
    public void ImprimirListadoConsolidadoR(String fechaEnvio,Integer numVehiculo,String descVehiculo,Double total,Integer numeroListado,Integer revision) throws IOException{
        EmisionDeListadosDeMaterialesConsolidadosR emiList=new EmisionDeListadosDeMaterialesConsolidadosR();
        emiList.setFechaEnvio(fechaEnvio);
        emiList.setNumVehiculo(numVehiculo);
        emiList.setDescVehiculo(descVehiculo);
        emiList.setTotal(total);
        emiList.setNumeroListado(numeroListado);
        emiList.setRevision(revision);
        emiList.start();
    }
}
