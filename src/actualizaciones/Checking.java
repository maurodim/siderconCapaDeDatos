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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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
        String sql="select * from ar_pedidos where ID_GVA03 ="+ped.getIdPedidoEnTango();
        //System.out.println(sql+"  -- sentencia en tango");
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
                            System.out.println("CONEXION "+numeroConeccion+" empresa "+empresa);
                            while(rs.next()){
                                Checking tgCh=new Checking();
                                cantidadT=rs.getDouble("cantidad_descargada");
                                cantidadTPendiente=rs.getDouble("cantidad_facturada");
                                cantidadEq=rs.getDouble("equiva");
                                System.out.println("descargada "+cantidadT+" facturada "+cantidadTPendiente+" equivalencia "+cantidadEq+" CONEXION "+numeroConeccion);
                                //cantidadEq=1.00;
                                //idTT=rs.getInt("ID_GVA03");
                                System.out.println("CANT A DES "+cantidadT+" articulo "+rs.getString("COD_ARTICU"));
                                cantidadesItemsTango++;
                               cantidadT=cantidadT/cantidadEq;
                                cantidadTPendiente=cantidadTPendiente / cantidadEq;
                                cantidadTotal=cantidadTPendiente - cantidadT;
                                tgCh.cantidadTango=cantidadT;
                                tgCh.idNumeroTango=idTT;
                                tgCh.codigoTango=rs.getString("COD_ARTICU");
                                //cantidadesTango.add(tgCh);
                                //idTango.add(idTT);
                            }
                            try{
                                System.out.println("cantidad Total "+cantidadTotal+"cantidad pedido "+ped.getCantidadArticulo()+" id pedido "+ped.getIdPedidoEnTango());
                                if(cantidadTotal != null){

                                }else{
                                    JOptionPane.showMessageDialog(null,"HA HABIDO UN PROBLEMA DE CONEXION CON TANGO, INFORMELO POR FAVOR");
                                }
                                if(cantidadTotal == ped.getCantidadArticulo() || cantidadTotal > ped.getCantidadArticulo()){
                                }else{
                                   if(ped.getCantidadArticulo() != null){

                                       //aca no deberia pasar nada
                                   } else{
                                        JOptionPane.showMessageDialog(null,"CUIDADO!!! SE ESTAN AJUSTANDO LAS CANTIDADES A TANGO, DADO QUE LAS CANTIDADES RECIBIDAS ESTABAN EN VALOR NULO.");
                                   }
                                   ped.setCantidadArticulo(cantidadTotal);
                                        modificarPedidos(ped);
                                }
                            }catch(java.lang.NullPointerException ex){
                                rastrearIdTango ras=new rastrearIdTango();
                                System.out.println("ID TANGO RE ACONDICIONADO: "+ras.ExtraerIdPedidoTango(empresa, ped));
                                JOptionPane.showMessageDialog(null,"HA HABIDO UN PROBLEMA DE CON EL PEDIDO "+ped.getCodigoTangoDePedido()+" DEL CLIENTE "+ped.getRazonSocial()+" POR FAVOR CAMBIE DE DIA E INTENTE NUEVAMENTE VER LA CARGA. GRACIAS");
                            }
                            
                            rs.close();
                            //xt.close();
                            
                            
                            /*
                             * TENGO QUE BUSCAR DE DONDE VIENE EL LLAMADO AL CHECKING - PARA PODER RENOVAR LOS DATOS DEL PEDIDO/PEDIDOS PARA MODIFICARLOS
                             */
                            //ArrayList pdMy=revisarMyPedidos(ped);
                            //System.out.println("RESULTADO DEL CHEQUEO :"+verificarMatrices(pdMy,cantidadesTango,ped));
                                
                            
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
        
        //String sql="select GVA03.CAN_EQUI_V,GVA03.CANT_A_DES,GVA03.CANT_PEDID,GVA03.COD_ARTICU,GVA03.NRO_PEDIDO,AR_SALDOS.SALDO_CC AS SALDO from GVA03 where ID_GVA03="+ped.getIdPedidoEnTango()+" INNER JOIN AR_SALDOS ON AR_SALDOS.COD_CLIENT='"+ped.getCodigoCliente()+"'";
        String sql="select AR_PEDIDOS.equiva,AR_PEDIDOS.cantidad_descargada,AR_PEDIDOS.cantidad_facturada,AR_PEDIDOS.COD_ARTICU,AR_PEDIDOS.NRO_PEDIDO,AR_SALDOS.SALDO_CC AS SALDO from AR_PEDIDOS where ID_GVA03="+ped.getIdPedidoEnTango()+" INNER JOIN AR_SALDOS ON AR_SALDOS.COD_CLIENT=AR_PEDIDOS.COD_CLIENT";
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
                            Double cantt=0.00;
                            Double saldo=0.00;
                            while(rs.next()){
                                cantidadT=rs.getDouble("cantidad_descargada");
                                cantidadTPendiente=rs.getDouble("cantidad_facturada");
                                cantidadEq=rs.getDouble("equiva");
                                saldo=rs.getDouble("saldo");
                                System.out.println("CANT A DES "+cantidadT+" articulo "+rs.getString("COD_ARTICU")+" pedido "+rs.getString("NRO_PEDIDO"));
                                
                            }
                            rs.close();
                            xt.close();
                            cantidadT=cantidadT/cantidadEq;
                            cantidadTPendiente=cantidadTPendiente / cantidadEq;
                            
                            System.err.println(" CANTIDADES PEDIDOS pedido "+ped.getRazonSocial()+" cant pend "+cantidadT+" articulo "+ped.getDescripcionArticulo()+" cod pedido "+ped.getCodigoTangoDePedido());
                            cantt=cantidadTPendiente - cantidadT;
                            
                            if(cantidad > cantt){
                                cantidad=cantt;
                            }else{
                                if(cantidad==0.00){
                                    //cantidad=cantidadT;
                                }
                            }
                            if(cantidadPendiente > cantt){
                                cantidadPendiente=cantt;
                            }
                            if(cantidadTotal == cantidadTPendiente){
                            }else{
                                cantidadTotal=cantidadTPendiente;
                            }
                            ped.setCantidadArticulo(cantidad);
                            ped.setCantidadArticuloPendiente(cantidadPendiente);
                            ped.setCantidadArticulosTotales(cantidadTotal);
                            ped.setSaldoCliente(saldo);
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
                               //st.executeUpdate(sql);
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
                    Iterator tangI=tg.listIterator();
                    Double mayor=0.00;
                    Double mayor1=0.00;
                    Checking tgC1=new Checking();
                    while(tangI.hasNext()){
                        tgC1=(Checking)tangI.next();
                        
                        mayor1=tgC1.cantidadTango;
                        if(codigoArticuloMy.equals(tgC1.codigoTango)){
                        if(mayor1 >= mayor){
                            mayor=mayor1;
                            mayor1=0.00;
                        }
                    }
                    }
                    if(mayor > 0)mayor1=mayor;
                    if(cantidadMy <= mayor1){
                        
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
private void modificarPedidos(PedidosParaReparto cch){
        try {
            Statement st=cnMy.createStatement();
            String sql="update pedidos_carga1 set CANT_PEDID ="+cch.getCantidadArticulo()+" where ID_GVA03 = "+cch.getIdPedidoEnTango();
            st.executeUpdate(sql);
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(Checking.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Override
    public Integer leerId(ArrayList lst, String empresa) {
        Statement xt=null;
            Integer id=0;
            Double cannt=0.00;
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
                           //String numPed=numeroDePedido.substring(1);
                           String sql=null;
                           ResultSet rs = null;
                           PedidosParaReparto pedido = null;
                           String sqlM=null;
                           
                           Iterator it=lst.listIterator();
                           
                           while(it.hasNext()){
                            pedido=(PedidosParaReparto) it.next();
                            int cantid=pedido.getCodigoTangoDePedido().length();
                            cantid=cantid -8;
                            String numPed=pedido.getCodigoTangoDePedido().substring(cantid);
                            sql="select ID_GVA03,(cant_pedid - cantidad_descargada / equiva) as cantidad from ar_pedidos where COD_ARTICU = '"+pedido.getCodigoArticulo()+"' and NRO_PEDIDO like '%"+numPed+"'";
                            xt.execute(sql);
                            System.out.println("SQL TANGO "+sql);
                            rs=xt.getResultSet();
                            while(rs.next()){
                                id=rs.getInt("ID_GVA03");
                                cannt=Math.round(rs.getDouble("cantidad") *100.0) / 100.0;
                                sqlM="update pedidos_carga1 set ID_GVA03="+id+",cant_desc=0,cant_pedid="+cannt+" where numero="+pedido.getiDPedido();
                                System.out.println(sqlM);
                                sta.executeUpdate(sqlM);
                                //System.out.println("SQL TANGO "+sql);
                            }
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
        DecimalFormat formato=new DecimalFormat("####.#");
        String cTotal="";
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
            
          String sql="select GVA03.CAN_EQUI_V,GVA03.CANT_A_DES from GVA03 where ID_GVA03 ="+ped.getIdPedidoEnTango();
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
                    cTotal=formato.format(cantTotal);
                        resultados+="\n FALTA REMITIR : "+cTotal+" unidades de "+ped.getDescripcionArticulo()+" del pedido nª "+ped.getCodigoTangoDePedido()+" del cliente "+ped.getRazonSocial()+"\n";
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
