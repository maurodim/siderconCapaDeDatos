/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import interfaces.ActualizableTango;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.PedidosParaReparto;
import proceso.Coneccion;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author mauro di
 */
public class Checking implements ChequearCantidadesPedidos,Ideable,ActualizableTango{
    private Coneccion con;
    private static Connection cnMy;
    private Integer numeroIdMysql;
    private Double cantidadMysql;
    private String codigoMy;
    private Integer idNumeroTango;
    private Double cantidadTango;
    private String codigoTango;
    private Statement sta;
    

    public Checking() {
        con=new Coneccion();
        cnMy=con.getCn();
        try {
            sta=cnMy.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
        cantidadMysql=0.00;
        numeroIdMysql=0;
    }
    
    @Override
    public Object check(Object pedido) {
        PedidosParaReparto ped=new PedidosParaReparto();
        ped=(PedidosParaReparto)pedido;
        String empresa=ped.getEmpresa();
        String codigoPedido=ped.getCodigoTangoDePedido().substring(2);
        Double cantidad=(Double)ped.getCantidadArticulo();
        Double cantidadPendiente=(Double)ped.getCantidadArticuloPendiente();
        Double cantidadTotal=(Double)ped.getCantidadArticulosTotales();
        String codigoArticulo=ped.getCodigoArticulo();
        ArrayList idTango=new ArrayList();
        ArrayList cantidadesTango=new ArrayList();
        Integer idTT=0;
        String sql="select GVA03.CAN_EQUI_V,GVA03.CANT_A_DES,GVA03.CANT_PEDID,GVA03.COD_ARTICU from GVA03 where NRO_PEDIDO like '%"+codigoPedido+"' order by GVA03.ID_GVA03";
        
        Statement xt=null;
        if(SiderconCapaatos.falloConecion==0){
        int numeroConeccion=0;
                        if(empresa.equals("BU")){
                            numeroConeccion=1;
                        }else{
                            if(empresa.equals("SD")){
                                numeroConeccion=2;
                            }else{
                                numeroConeccion=3;
                            }
                        }
                        Connection sqlC=null;
                        switch (numeroConeccion){
                            case 1:
                                sqlC=(Connection)SiderconCapaatos.sqlBu;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case 2:
                                sqlC=(Connection)SiderconCapaatos.sqlSd;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case 3:
                                sqlC=(Connection)SiderconCapaatos.sqlSdSrl;
                                
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                        }
                        try {
                            //xt=null;
                            xt.execute(sql);
                            ResultSet rs=xt.getResultSet();
                            Double cantidadT=0.00;
                            Double cantidadTPendiente=0.00;
                            Double cantidadEq=0.00;
                            int cantidadesItemsTango=0;
                            while(rs.next()){
                                Checking tgCh=new Checking();
                                cantidadT=rs.getDouble("CANT_A_DES");
                                cantidadTPendiente=rs.getDouble("CANT_PEDID");
                                cantidadEq=rs.getDouble("CAN_EQUI_V");
                                //idTT=rs.getInt("ID_GVA03");
                                System.out.println("CANT A DES "+cantidadT+" articulo "+rs.getString("COD_ARTICU"));
                                cantidadesItemsTango++;
                               cantidadT=cantidadT/cantidadEq;
                                cantidadTPendiente=cantidadTPendiente / cantidadEq;
                                tgCh.cantidadTango=cantidadT;
                                tgCh.idNumeroTango=idTT;
                                tgCh.codigoTango=rs.getString("COD_ARTICU");
                                cantidadesTango.add(tgCh);
                                //idTango.add(idTT);
                            }
                            
                            rs.close();
                            //xt.close();
                            
                            
                            /*
                             * TENGO QUE BUSCAR DE DONDE VIENE EL LLAMADO AL CHECKING - PARA PODER RENOVAR LOS DATOS DEL PEDIDO/PEDIDOS PARA MODIFICARLOS
                             */
                            ArrayList pdMy=revisarMyPedidos(ped);
                            System.out.println("RESULTADO DEL CHEQUEO :"+verificarMatrices(pdMy,cantidadesTango,ped));
                                
                            
                           // if(verif(pedido,xt,cantidadesItemsTango,cantidadT,idTango)){
                            System.err.println(" CANTIDADES PEDIDOS pedido "+ped.getRazonSocial()+" cant pend "+cantidadT+" articulo "+ped.getDescripcionArticulo()+" cod pedido "+ped.getCodigoTangoDePedido());
                            /*
                            if(cantidad > cantidadT){
                                cantidad=cantidadT;
                            }else{
                                if(cantidad == 0.00){
                                    cantidad=cantidadT;
                                }
                            }
                            if(cantidadPendiente > cantidadT){
                                cantidadPendiente=cantidadT;
                            }
                            if(cantidadTotal == cantidadTPendiente){
                            }else{
                                cantidadTotal=cantidadTPendiente;
                            }
                            ped.setCantidadArticulo(cantidad);
                            ped.setCantidadArticuloPendiente(cantidadPendiente);
                            ped.setCantidadArticulosTotales(cantidadTotal);
                            //}
                            */ 
                        } catch (SQLException ex) {
                           
                            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                            System.err.println(" OJO QUE SE CORTO LA CONEXION A TANGO ");
                            
                        }
        }
        return ped;
    }
    public void emitirMensaje(){
        
    }
 
    @Override
    public void verificar() {
        ArrayList pedidosPendientesTango=new ArrayList();
        String sql="select GVA03.CAN_EQUI_V,GVA03.CANT_A_DES,GVA03.CANT_PEDID,GVA03.COD_ARTICU,GVA03.NRO_PEDIDO from GVA03 where CANT_A_DES > 0";
        Connection sqlC=null;
        Statement xt=null;
        for(int a=1;a <4;a++){
                        int numeroConeccion=a;
                        
                        
                        switch (numeroConeccion){
                            case 1:
                                sqlC=(Connection)SiderconCapaatos.sqlBu;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case 2:
                                sqlC=(Connection)SiderconCapaatos.sqlSd;
                                
                                try {
                                    
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case 3:
                                sqlC=(Connection)SiderconCapaatos.sqlSdSrl;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                        }
            try {
                xt.execute(sql);
                ResultSet rs=xt.getResultSet();
                while(rs.next()){
                    PedidosParaReparto ped=new PedidosParaReparto();
                    ped.setCodigoTangoDePedido(rs.getString("NRO_PEDIDO"));
                    ped.setCodigoArticulo(rs.getString("COD_ARTICU"));
                    Double equiv=rs.getDouble("CAN_EQUI_V");
                    Double cant=rs.getDouble("CANT_A_DES");
                    Double cantidad=cant/equiv;
                    ped.setCantidadArticuloPendiente(cantidad);
                    cant=rs.getDouble("CANT_PEDID");
                    cantidad=cant/equiv;
                    ped.setCantidadArticulosTotales(cantidad);
                    System.err.println("PEDIDO ENCONTRADO "+ped.getCodigoTangoDePedido());
                    pedidosPendientesTango.add(ped);
                }
                rs.close();
                xt.close();
            } catch (SQLException ex) {
                
                Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        //aqui continua el codigo
        /* PRIMERO BLANQUEAR LAS CANTIDADES DE LOS PEDIDOS PENDIENTES Y LUEGO MODIFICAR LAS CANTIDADES CHECKEADAS
         */
         
        sqlC=cnMy;
        sql="select pedidos_carga1.CANT_FACT,pedidos_carga1.COD_ARTIC,pedidos_carga1.NRO_PEDIDO,pedidos_carga1.CANT_FACT,pedidos_carga1.CANT_DESC,pedidos_carga1.CANT_PEDID from pedidos_carga1 where CANT_FACT > 0";
        String sql1="";
        try {
            xt=sqlC.createStatement();
            Statement xt1=sqlC.createStatement();
            xt.execute(sql);
            ResultSet rs=xt.getResultSet();
            while(rs.next()){
                String codigoPedido=rs.getString("NRO_PEDIDO");
                String codigoArticulo=rs.getString("COD_ARTIC");
                sql1="update pedidos_carga1 set CANT_FACT=0.00 where NRO_PEDID0 like '%"+codigoPedido+"' and COD_ARTIC = '"+codigoArticulo+"'";
                xt1.executeUpdate(sql1);
            }
            xt1.close();
            rs.close();
        } catch (SQLException ex) {
            
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
        PedidosParaReparto pedid=new PedidosParaReparto();
        Iterator ip=pedidosPendientesTango.listIterator();
        
        while(ip.hasNext()){
            pedid=(PedidosParaReparto)ip.next();
            sql="update pedidos_carga1 set CANT_FACT="+pedid.getCantidadArticuloPendiente()+" where NRO_PEDIDO like '%"+pedid.getCodigoTangoDePedido()+"' and COD_ARTIC ='"+pedid.getCodigoArticulo()+"'";
            try {
                xt.executeUpdate(sql);
                System.out.println("CARGA PENDIENTES TANGO :"+sql);
            } catch (SQLException ex) {
                Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        try {
            xt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public Object actualizar(Object pedido) {
        PedidosParaReparto ped=new PedidosParaReparto();
        ped=(PedidosParaReparto)pedido;
        String empresa=ped.getEmpresa();
        String codigoPedido=ped.getCodigoTangoDePedido().substring(2);
        Double cantidad=(Double)ped.getCantidadArticulo();
        Double cantidadPendiente=(Double)ped.getCantidadArticuloPendiente();
        Double cantidadTotal=(Double)ped.getCantidadArticulosTotales();
        String codigoArticulo=ped.getCodigoArticulo();
        
        String sql="select GVA03.CAN_EQUI_V,GVA03.CANT_A_DES,GVA03.CANT_PEDID,GVA03.COD_ARTICU,GVA03.NRO_PEDIDO from GVA03 where NRO_PEDIDO like '%"+codigoPedido+"' and COD_ARTICU ='"+codigoArticulo+"'";
        
        Statement xt=null;
        
        int numeroConeccion=0;
                        if(empresa.equals("BU")){
                            numeroConeccion=1;
                        }else{
                            if(empresa.equals("SD")){
                                numeroConeccion=2;
                            }else{
                                numeroConeccion=3;
                            }
                        }
                        Connection sqlC=null;
                        switch (numeroConeccion){
                            case 1:
                                sqlC=(Connection)SiderconCapaatos.sqlBu;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case 2:
                                sqlC=(Connection)SiderconCapaatos.sqlSd;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                            case 3:
                                sqlC=(Connection)SiderconCapaatos.sqlSdSrl;
                                try {
                                    xt=sqlC.createStatement();
                                } catch (SQLException ex) {
                                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                        }
                        try {
                            xt.execute(sql);
                            ResultSet rs=xt.getResultSet();
                            Double cantidadT=0.00;
                            Double cantidadTPendiente=0.00;
                            Double cantidadEq=0.00;
                            while(rs.next()){
                                cantidadT=rs.getDouble("CANT_A_DES");
                                cantidadTPendiente=rs.getDouble("CANT_PEDID");
                                cantidadEq=rs.getDouble("CAN_EQUI_V");
                                System.out.println("CANT A DES "+cantidadT+" articulo "+rs.getString("COD_ARTICU")+" pedido "+rs.getString("NRO_PEDIDO"));
                                
                            }
                            rs.close();
                            xt.close();
                            cantidadT=cantidadT/cantidadEq;
                            cantidadTPendiente=cantidadTPendiente / cantidadEq;
                            System.err.println(" CANTIDADES PEDIDOS pedido "+ped.getRazonSocial()+" cant pend "+cantidadT+" articulo "+ped.getDescripcionArticulo()+" cod pedido "+ped.getCodigoTangoDePedido());
                            if(cantidad > cantidadT){
                                cantidad=cantidadT;
                            }else{
                                if(cantidad==0.00){
                                    cantidad=cantidadT;
                                }
                            }
                            if(cantidadPendiente > cantidadT){
                                cantidadPendiente=cantidadT;
                            }
                            if(cantidadTotal == cantidadTPendiente){
                            }else{
                                cantidadTotal=cantidadTPendiente;
                            }
                            ped.setCantidadArticulo(cantidad);
                            ped.setCantidadArticuloPendiente(cantidadPendiente);
                            ped.setCantidadArticulosTotales(cantidadTotal);
                        } catch (SQLException ex) {
                            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                        }
        
        return ped;

    }
    private Boolean verificarMatrices(ArrayList mys,ArrayList tang,PedidosParaReparto pd){
        
            Boolean vMat=false;
            int tamaMy=mys.size();
            int tamaTg=tang.size();
            int fTamano=0;
            int fcantidades=0;
            int fDetalle=0;
            int fTangoMenor=0;
            Checking ccK=null;
            String sql="";
            try {
            if(tamaMy==tamaTg){
                fTamano=1;
            }
            int bucle=0;
            if(fTamano==1){
                bucle=fTamano;
                //comparacionDeMatrices(mys,tang);
            }else{
            if(tamaMy > tamaTg){
                bucle=tamaMy;
                fTangoMenor=1;
                   Statement st=cnMy.createStatement();
                   int tamaTg1=tamaTg -1;
                   for(int i=0;i < bucle;i++){

                          if(i > tamaTg1){ 
                              int aa=i;
                              ccK=(Checking)mys.get(aa);
                              //tamaTg=tamaTg - 1;
                               sql = "delete from pedidos_carga1 where numero="+ccK.numeroIdMysql;
                               System.out.println(sql);
                               st.executeUpdate(sql);
                              //mys.remove(i);

                          } 

                   }
                   mys=revisarMyPedidos(pd);
                   st.close();

            }else{
                bucle=tamaTg;
                fTangoMenor=0;
                //comparacionDeMatrices(mys,tang);
            }
            
            }
            comparacionDeMatrices(mys,tang);
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
            
           
            return vMat;
        
    }
    private Boolean verif(Object pedido,Statement conTango,int cantidadItemTango,Double cantidadesTango,ArrayList idT){
        Boolean resultado=false;
        int cantidadItemsMysql=0;
        int h=0;
        int cantidadItemsTango=cantidadItemTango;
        Double cantidadesMy=0.00;
        Double totalMy=0.00;
        Double cantidadTango=cantidadesTango;
        Double cantidadITango=0.00;
        ArrayList cantidad=new ArrayList();
        ArrayList numeroI=new ArrayList();
        ArrayList codigoH=new ArrayList();
        String codigoHd="";
        Integer idPedido=0;
        Integer idPedidoTango=0;
        PedidosParaReparto ped=(PedidosParaReparto)pedido;
        String codigoPedido=ped.getCodigoTangoDePedido().substring(2);
        String codigoArticulo=ped.getCodigoArticulo();
        String sql1="";
        String sql="select pedidos_carga1.CANT_PEDID,pedidos_carga1.numero,pedidos_carga1.COD_ARTIC from pedidos_carga1 where NRO_PEDIDO like '"+ped.getCodigoTangoDePedido()+"' and entrega like '"+ped.getFechaEnvio()+"%'";
        Statement st=null;
        try {
            st = cnMy.createStatement();
        
        
            st.execute(sql);
        
        ResultSet rs=st.getResultSet();
        
        while(rs.next()){
            cantidadesMy=rs.getDouble("CANT_PEDID");
            idPedido=rs.getInt("numero");
            codigoHd=rs.getString("COD_ARTIC");
            totalMy=totalMy + cantidadesMy;
            cantidad.add(cantidadesMy);
            numeroI.add(idPedido);
            cantidadItemsMysql++;
            codigoH.add(codigoHd);
        }
        rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
        //st.close();
        int a=0;
        if(totalMy == cantidadesTango){
            resultado=true;
        }else{
           Iterator iCant=cantidad.listIterator();
           Statement stt=conTango;
           Double compTango=0.00;
           while(iCant.hasNext()){
               cantidadesMy=(Double)iCant.next();
               compTango=0.00;
              if(cantidadItemTango > a){
                   idPedidoTango=(Integer)idT.get(a);
                   codigoHd=(String)codigoH.get(a);
               
               sql="select GVA03.CAN_EQUI_V,GVA03.CANT_A_DES from GVA03 where ID_GVA03="+idPedidoTango;
               a++;
               System.err.println(sql);
                try {
                    stt.execute(sql);
               ResultSet rss=stt.getResultSet();
               Double cantEqui=0.00;
               while(rss.next()){
                   cantidadITango=rss.getDouble("CANT_A_DES");
                   cantEqui=rss.getDouble("CAN_EQUI_V");
               }
               rss.close();
               compTango=cantidadITango / cantEqui;
                } catch (SQLException ex) {
                    Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                    compTango=0.00;
                }

               //stt.close();
               
               h=a-1;
              }else{
                  h=a;
              }
               if(cantidadesMy==compTango){
                   
               }else{
                       int itemsMatrizHdr=numeroI.size();
                   /*
                       if(itemsMatrizHdr > cantidadItemTango){
                       
                   }else{
                       h=itemsMatrizHdr -1;
                   }
                   */ 
                   idPedido=(Integer) numeroI.get(h);
                   sql1="update pedidos_carga1 set CANT_PEDID ="+compTango+" where numero="+idPedido;
                   System.out.println(" SQL1 "+sql1);
                   try {
                       st.executeUpdate(sql1);
                   } catch (SQLException ex) {
                       Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                   }
                  int id_Pedido = ped.getiDPedido();
                  if(id_Pedido==idPedido){
                      ped.setCantidadArticulo(compTango);
                  }
               }
           
           }
            try {
                stt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
            }
           
        }
        try {
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return resultado;
    }
    private ArrayList revisarMyPedidos(PedidosParaReparto ped){
        ArrayList cantidadesMy=new ArrayList();
        try {
            Double cantidadM=0.00;
            String numeroPedido=ped.getCodigoTangoDePedido().substring(2);
            String sql="select pedidos_carga1.CANT_PEDID,pedidos_carga1.numero,pedidos_carga1.COD_ARTIC from pedidos_carga1 where NRO_PEDIDO like '%"+numeroPedido+"' and entrega like '"+ped.getFechaEnvio()+"%' order by numero";
            Statement st=cnMy.createStatement();
            st.executeQuery(sql);
            ResultSet rs=st.getResultSet();
            while(rs.next()){
                Checking cK=new Checking();
                
                cantidadM=rs.getDouble("CANT_PEDID");
                cK.cantidadMysql=cantidadM;
                cK.numeroIdMysql=rs.getInt("numero");
                cK.codigoMy=rs.getString("COD_ARTIC");
                cantidadesMy.add(cK);
            }
            rs.close();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cantidadesMy;
    }
    /*
     * FUNCION QUE SE ENCARGA DE VERIFICAR LA RELACION ITEMS A ITEMS ENTRE LOS PEDIDOS MYSQL Y TANGO
     * AMBAS MATRICES YA ESTAN CARGADAS E IGUALADAS EN CANTIDADES O ES MENOR LA MY, AQUI TENGO QUE HACER LA COMPARACION
     */
    private Boolean comparacionDeMatrices(ArrayList my,ArrayList tg){
        Boolean comparacion=true;
        int cantidadItemsAComparar=my.size();
        int cantidadItemsCargadosTg=tg.size();
        String codigoArticuloMy="";
        String codigoArticuloTg="";
        Double cantidadMy=0.00;
        Double cantidadTg=0.00;
        Checking myC=new Checking();
        Checking tgC=new Checking();
        for(int i=0;i < cantidadItemsCargadosTg;i++){
            tgC=(Checking)tg.get(i);
            codigoArticuloTg=tgC.codigoTango;
            cantidadTg=tgC.cantidadTango;
            for(int aaa=0;aaa <cantidadItemsAComparar;aaa++){
                myC=(Checking)my.get(aaa);
                codigoArticuloMy=myC.codigoMy.trim();
                cantidadMy=myC.cantidadMysql;
                if(codigoArticuloMy.equals(codigoArticuloTg)){
                    if(cantidadMy <= cantidadTg){
                        
                    }else{
                        cantidadMy=cantidadTg;
                        myC.cantidadMysql=cantidadTg;
                        modificarItems(myC);
                    }
                }
                
            }
            
        }
        
        return comparacion;
    }
    private void modificarItems(Checking cch){
        try {
            Statement st=cnMy.createStatement();
            String sql="update pedidos_carga1 set CANT_PEDID ="+cch.cantidadMysql+" where numero = "+cch.numeroIdMysql;
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public Integer leerId(String numeroDePedido, String codigoDeArticulo, Double cantidad, String descripcionArticulo, String empresa) {
        Statement xt=null;
            Integer id=0;
           int numeroConeccion=0;
        try {
            
                           if(empresa.equals("BU")){
                               numeroConeccion=1;
                           }else{
                               if(empresa.equals("SD")){
                                   numeroConeccion=2;
                               }else{
                                   numeroConeccion=3;
                               }
                           }
                           Connection sqlC=null;
                           switch (numeroConeccion){
                               case 1:
                                   sqlC=(Connection)SiderconCapaatos.sqlBu;
                                   try {
                                       xt=sqlC.createStatement();
                                   } catch (SQLException ex) {
                                       Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                   break;
                               case 2:
                                   sqlC=(Connection)SiderconCapaatos.sqlSd;
                                   try {
                                       xt=sqlC.createStatement();
                                   } catch (SQLException ex) {
                                       Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                   break;
                               case 3:
                                   sqlC=(Connection)SiderconCapaatos.sqlSdSrl;
                                   
                                   try {
                                       xt=sqlC.createStatement();
                                   } catch (SQLException ex) {
                                       
                                       Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                   break;
                           }
                           String numPed=numeroDePedido.substring(1);
                           String sql="select GVA03.ID_GVA03 from GVA03 where COD_ARTICU = '"+codigoDeArticulo+"' and NRO_PEDIDO like '%"+numPed+"'";
                           xt.execute(sql);
                           System.out.println("SQL TANGO "+sql);
                           ResultSet rs=xt.getResultSet();
                           while(rs.next()){
                               id=rs.getInt("ID_GVA03");
                               //System.out.println("SQL TANGO "+sql);
                           }
                           rs.close();
                           
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
            id=0;
        }
        return id;
    }

    @Override
    public Boolean guardarIdEnMysql(Integer idTango, Integer idMy) {
        Boolean veri=true;
        try {
            String sql="update pedidos_carga1 set ID_GVA03="+idTango+" where numero ="+idMy;
            //Statement sta=cnMy.createStatement();
            sta.executeUpdate(sql);
            //sta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
            veri=false;
        }
        return veri;
        
    }

    @Override
    public Boolean marcarComoLeido(Integer idMy) {
        Boolean veri=true;
        try {
            String sql="update pedidos_carga1 set idcheck=1 where numero ="+idMy;
            //Statement sta=cnMy.createStatement();
            sta.executeUpdate(sql);
            //sta.close();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
            veri=false;
        }
        return veri;
    }

    @Override
    public String verificarRemitosTotales(ArrayList listadoHdr) {
        String resultados="";
        Statement xt=null;
        Statement xt1=null;
        Statement xt2=null;
        
        Iterator iLis=listadoHdr.listIterator();
        
                           
                           Connection sqlC=null;
                           Connection sqlC1=null;
                           Connection sqlC2=null;
                           //System.out.println("numero de conexionnnnnnnnn "+numeroConeccion+" "+empresa);
                           
                                   sqlC=(Connection)SiderconCapaatos.sqlBu;
                                   
                                   try {
                                       xt=sqlC.createStatement();
                                   } catch (SQLException ex) {
                                       Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                   
                                   sqlC1=(Connection)SiderconCapaatos.sqlSd;
                                   try {
                                       xt1=sqlC1.createStatement();
                                   } catch (SQLException ex) {
                                       Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                   }
                                  
                                   sqlC2=(Connection)SiderconCapaatos.sqlSdSrl;
                                   
                                   try {
                                       xt2=sqlC2.createStatement();
                                   } catch (SQLException ex) {
                                       
                                       Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
                                   }
            
        while(iLis.hasNext()){
            PedidosParaReparto ped=(PedidosParaReparto)iLis.next();
            String empresa=ped.getEmpresa().trim();
            
          int numeroConeccion=0;
          Integer idTan=ped.getIdPedidoEnTango();
          String codigoDeArticulo=ped.getCodigoArticulo().trim();
          String numPed=ped.getCodigoTangoDePedido().substring(3);
            
                           String sql="select GVA03.CAN_EQUI_V,GVA03.CANT_A_DES from GVA03 where COD_ARTICU = '"+codigoDeArticulo+"' and NRO_PEDIDO like '%"+numPed+"'";
            try {
                System.out.println("ENTENCIAAAAA "+sql);
                ResultSet rr=null;
                Double cantTang=0.00;
                Double cantEq=0.00;
                Double cantTotal=0.00;
                if(empresa.equals("BU")){
                               xt.execute(sql);
                                rr=xt.getResultSet();
                                System.out.println("ENTRO EN BUUUUUUUUUU");
                                
                while(rr.next()){
                    cantTang=rr.getDouble("CANT_A_DES");
                    cantEq=rr.getDouble("CAN_EQUI_V");
                    
                    System.out.println("ENTENCIAAAAA "+ped.getIdPedidoEnTango()+" "+ped.getCodigoTangoDePedido()+" "+cantTotal+" a desc "+cantTang+" equiv "+cantEq);    
                    cantTotal=cantTang/cantEq;
                }
                rr.close();
                           }else{
                               if(empresa.equals("SD")){
                                  xt1.execute(sql);
                                 rr=xt1.getResultSet();
                                 
                while(rr.next()){
                    cantTang=rr.getDouble("CANT_A_DES");
                    cantEq=rr.getDouble("CAN_EQUI_V");
                    
                    System.out.println("ENTENCIAAAAA "+ped.getIdPedidoEnTango()+" "+ped.getCodigoTangoDePedido()+" "+cantTotal+" a desc "+cantTang+" equiv "+cantEq);    
                    cantTotal=cantTang/cantEq;
                }
                rr.close();
                               }else{
                                  xt2.execute(sql);
                                 rr=xt2.getResultSet();
                                 
                while(rr.next()){
                    cantTang=rr.getDouble("CANT_A_DES");
                    cantEq=rr.getDouble("CAN_EQUI_V");
                    
                    System.out.println("ENTENCIAAAAA "+ped.getIdPedidoEnTango()+" "+ped.getCodigoTangoDePedido()+" "+cantTotal+" a desc "+cantTang+" equiv "+cantEq);    
                    cantTotal=cantTang/cantEq;
                }
                rr.close();
                               }
                           }
                
                
                
                Double cantPed=ped.getCantidadArticulo();
                
                if(cantTotal == 0.00){
                    if(cantPed == 0.00){
                    resultados+="";
                    }else{
                        resultados+="\n FALTA EN LPM :"+cantPed+" unidades de "+ped.getDescripcionArticulo()+" del pedido nª "+ped.getCodigoTangoDePedido()+" del cliente "+ped.getRazonSocial()+"\n";
                    }
                }else{
                    //if(cantPed ==0.00){
                        resultados+="\n FALTA REMITIR : "+cantTotal+" unidades de "+ped.getDescripcionArticulo()+" del pedido nª "+ped.getCodigoTangoDePedido()+" del cliente "+ped.getRazonSocial()+"\n";
                    //}else{
                     //   resultados+="\n FALTA EN LPM :"+cantPed+" unidades de "+ped.getDescripcionArticulo()+" del pedido nª "+ped.getCodigoTangoDePedido()+" del cliente "+ped.getRazonSocial()+"\n";
                   // }
                    
                }
            } catch (SQLException ex) {
                Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return resultados;
    }

    @Override
    public Boolean regenerarCantidadesTango(ArrayList pedidoDetalladoTango) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
