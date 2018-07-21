/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ListadoDeCargaPorVehiculo.java
 *
 * Created on 28-feb-2012, 14:03:52
 */
package siderconcapadatos;

import actualizaciones.Checking;
import actualizaciones.ChequearCantidadesPedidos;
import interfaces.ActualizableTango;
import interfaces.Revisionar;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.Listados;
import objetos.PedidosParaReparto;
import objetos.RevisionDeListados;
import objetos.Vehiculos;
import proceso.Coneccion;
import proceso.EmisionDeListados;
import proceso.Procesos;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;
import siderconcapadatos.tablas.MiTablaDeCarga;

/**
 *
 * @author Mauro Di
 */
public class ListadoDeCargaPorVehiculo extends javax.swing.JInternalFrame {
    static Integer seleccion;
    static String fecha2;
    static String descUnidad;
    static String descUnidad1;
    public static ArrayList carga=new ArrayList();
    static ArrayList vehiculos=new ArrayList();
    static Double totalKg;
    static Double tKg;
    static Integer listadoNumero;
    static Listados ls;
    static ArrayList cargaRevi=new ArrayList();
    static Integer numeroDeListadoAnterior;
    static Integer numeroDeRevisionAnterior;
    static Boolean nuevoListado=false;
    static Integer nListado;
    static Integer rNum;
    private Coneccion con=null;
    static Connection ccn=null;
    private ArrayList desc=new ArrayList();
    static String pedidoSeleccionado;
    //static ArrayList revis=new ArrayList();
    //static int numeroListado;
    /** Creates new form ListadoDeCargaPorVehiculo */
    public ListadoDeCargaPorVehiculo(Integer unidad,String fecha,String descripcion) {
        con=new Coneccion();
        ccn=con.getCn();
        seleccion=unidad;
        Procesos pr=new Procesos();
        try {
            vehiculos=pr.ListarVehiculos();
        } catch (SQLException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
	fecha2=fecha;
        descUnidad=descripcion;
        descUnidad1=descripcion;
        ls=new Listados();
        ls=Listados.ultimoListado(unidad, fecha);
        nListado=ls.getNumeroListado();
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MiTablaDeCarga mtDc=new MiTablaDeCarga();
        //jButton4.setVisible(false);
        PedidosParaReparto pedid=new PedidosParaReparto();
        Procesos pro=new Procesos();
        Double kilos=0.00;
        //ArrayList carga=new ArrayList();
        try{
            carga=pro.ListarPedidosPorVehiculo(seleccion);
            System.out.println(fecha2);
        }catch(Exception ex){
            System.out.println("NO SE CARGARON LOS PEDIDOS "+ex);
        }
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de la Carga de Materiales del Vehiculo");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        System.out.println(" EL VEHICULO SELECCIONADO ES :"+seleccion);
        jTable1.setModel(mtDc);
        Iterator ic=carga.listIterator();

        mtDc.addColumn("Cliente");
        mtDc.addColumn("Nro Pedido");
        mtDc.addColumn("Saldo Cliente");
        Object[] fila=new Object[3];
        while(ic.hasNext()){
            pedid=(PedidosParaReparto) ic.next();
            fila[0]=pedid.getRazonSocial();
            fila[1]=pedid.getCodigoTangoDePedido();
            System.out.println("SALDO CLIENTE "+pedid.getSaldoCliente());
            if(pedid.getSaldoCliente()==null){
                fila[2]="PS";
            }else{
                fila[2]=pedid.getSaldoCliente();
            }
            if(pedid.getVerificadorRevision()==0){
                nuevoListado=true;
            }
            kilos+= pedid.getPesoTotal();
            //System.out.println(pedid.getPesoItems()+" PESOS "+pedid.getPesoTotal());
            mtDc.addRow(fila);
        }
        tKg=kilos;
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton1.setLabel("Imprimir Ultim Rev Consolidado");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton2.setText("Imprimir Listado Consolidado");
        jButton2.setToolTipText("Genera PDF del listado Consolidado de materiales");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton3.setToolTipText("Genera PDF del listado detallado de materiales");
        jButton3.setLabel("Generar Listado de Preparacion");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton4.setText("Emitir Hoja de Ruta");
        jButton4.setToolTipText("Generar HDR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("VEHICULO :"+descUnidad1+" PARA FECHA :"+fecha2);

        jButton5.setLabel("Imprimir Listado Detallado");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Detalle de Pedido");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE))))
                        .addContainerGap(24, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1)
                        .addComponent(jButton2)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(19, 19, 19)
                .addComponent(jButton4)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        PedidosParaReparto pedi=new PedidosParaReparto();
        EmisionDeListados em=new EmisionDeListados();
        Procesos pr=new Procesos();
                   if(desc.size() > 0){
                System.out.println("ENTRO COMO EMISION DE DESCARGA DE MATERIALES ");
                //em.ImprimirListadoDeDescargaDeMateriales(numeroListado2,numeroRev2,desc,vehiculosAnt,codigoCliente,nombreCliente);
                desc.clear();
            }
        
        //Listados ls=new Listados();
       int numeroListado=0;
       numeroListado=nListado;
       /*
        try {
            //ls=pr.GenerarNuevoListado(seleccion, fecha2);
            numeroListado=ls.getNumeroListado();
        } catch (SQLException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        */ 
       listadoNumero=numeroListado;
        
            
        try {
            em.ImprimirListadoConsolidado(fecha2, seleccion, descUnidad, tKg,listadoNumero,ls.getNumeroRevision());
            
            
            /*
            int pedidoModificable=jTable1.getSelectedRow();
            String numePedido=(String) jTable1.getValueAt(pedidoModificable,0);
            Procesos pro=new Procesos();
       
            //super.setVisible(false);
            //numeroDePedido=numePedido;
            
            //fechaPedido=SiderconCapaatos.fecha;
            
            ModificacionDePedidos mod1=new ModificacionDePedidos();
            
            //ModPedidos mod=null;
            //JFrame.addComponent(mod);
            //mod.pack();
            // super.add(mod);
            InicioSiderconHdr.jDesktopPane1.add(mod1);
            //mod1.setVisible(true);
            //mod.setLocation(this);
            mod1.toFront();
            mod1.setVisible(true);
            //mod1.pack();
            //this.setVisible(false);
            */ 
        } catch (IOException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        /*
         * EL EVENTO DE ESTE BOTON CUMPLE EL SIGUIENTE CICLO:
         * 
         * - GENERA UN NUEVO LISTADO (VERIFICA SI YA EXISTE EL MISMO Y LO GENERA O BIEN REALIZA O MARCA UNA NUEVA REVISION)
         * - RECORRE LA MATRIZ DE PEDIDOS CARGADOS POR EL VEHICULO Y FECHA
         * - FILTRA LOS PEDIDOS PARA SER INCORPORADOS A LA REVISION A EMITIR
         * - TRASMITE LA MATRIZ RESULTANTE PARA SER GUARDADA EN LA BASE DE DATOS
         * 
         * |----------------|
         * |CONSIDERACIONES:|
         * |----------------|
         * LOS DISTINTOS ITEMS VAN RECIBIENDO EN TABLA PEDIDOS_CARGA1 UN NUMERO DE LISTADO Y REVISION, LOS TENDRIA QUE GRABAR ASI COMO ESTAN EN HISTORICOPEDIDOSLISTADOS
         * CON EL FIN DE AGRUPARLOS E IR VERIFICANDO QUE LAS CANTIDADES LAS SUMEN CORRECTAMENTE, EN CASO DE UN ITEMS QUE SE DA DE BAJA, QUE EL SISTEMA CHEQUEE QUE EN EL PEDIDO
         * EXISTA UN NUMERO DE LISTADO Y/O REVISION Y GENERE UNA NUEVA REVISION EN CANTIDAD NEGATIVA, PARA INDICAR QUE SE QUITAN ELEMENTOS, CON LA NUMERACION DEL LISTADO 
         * ANTERIOR, SI EL LISTADO ES IGUAL SIMPLEMENTE GENERA UNA NUEVA REVISION
         * 
         */
        
        //this.jButton2.setEnabled(false);
        int totalFilas=jTable1.getRowCount();
        
        //Double totalKg=0.00;
        String vehiculosAnt="";
        String nombreCliente="";
        String codigoCliente="";
        Procesos pr=new Procesos();
       int numeroListado=0;
       Listados ls2=new Listados();
       int numeroRev2=0;
       int numeroListado2=0;
        try {
             Runtime r=Runtime.getRuntime();
            r.gc();
            ls=pr.GenerarNuevoListado(seleccion, fecha2,true,true);
            
            int numeroRev=ls.getNumeroRevision();
            numeroListado=ls.getNumeroListado();
            System.err.println("NUMERO DE REVISION QUE SE APLICA AL LISTADO "+ls.getNumeroRevision());
        } catch (SQLException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("PARA VER QUE ERROR ES :"+ex);
        }
        Iterator ic=carga.listIterator();
        System.out.println("CANTIDAD DE ITEMS EN MATRIZ CARGA :"+carga.size());
        PedidosParaReparto ped;
        PedidosParaReparto pedi;
        ArrayList cargaDetallada=new ArrayList();
        ArrayList cargaDetallada1=new ArrayList();
        ArrayList detall;
        ArrayList descarga=new ArrayList();
        //ver por el chequeo de las cantidades de los pedidos
        
        ChequearCantidadesPedidos ch=new Checking();
        Integer revisionNum=0;
        int senal=0;
        while(ic.hasNext()){
            ped=(PedidosParaReparto)ic.next();
            /*
             * SUPONGO QUE AQUI VOY A ENCONTRAR EL PROBLEMA DE LAS REVISIONES QUE NO ME NUMERA CORRECTAMENTE
             * DE TODAS FORMAS, PRESTAR ATENCION QUE ES LO QUE QUEDA CARGADO POR QUE AL REINICIAR EL SISTEMA TOMA CORRECTAMENTE LAS CANTIDADES Y 
             * REVISIONES, NO ASI SI EL SISTEMA ESTA EN MARCHA
             * -- SUPONGO QUE ALGO ESTA VA QUEDANDO EN MEMORIA Y NO SE REINICIA O QUEDA CON UN VALOR ERRONEO
             * APARENTEMENTE LA MATRIZ CARGA ES LA QUE ME ESTA HACIENDO LIO
             * 
             * SOLUCIONADO EL RESTO DE LOS TEMAS PERO EL FUNDAMENTAL AHORA
             * ES POR QUE EN LA REVISON 2 ME RENOMBRA TODO, ME HACE DESAPARECER LAS OTRAS REVISIONES Y ME PONE TODO COMO 2
             * 
             */
            if(ped.getNumeroDeListadoDeMateriales()==numeroListado){
            // vamos a ver si asi esta bien
                
                ped.setNumeroDeListadoDeMateriales(numeroListado);
            
                int revisionListado=ped.getNumeroDeRevisionDeListado();
            int lSRevisionDeListado=ls.getNumeroRevision();
            int verRev=ped.getVerificadorRevision();
            if(verRev==1){
            //ped.setNumeroDeRevisionDeListado(ls.getNumeroRevision());
            //ped.setNumeroDeRevisionDeListado(0);
            //revisionNum=ls.getNumeroRevision();
                revisionNum=ped.getNumeroDeRevisionDeListado();
            }else{
            ped.setNumeroDeRevisionDeListado(ls.getNumeroRevision());
            //ped.setNumeroDeRevisionDeListado(0);
            revisionNum=ls.getNumeroRevision();
             try {
                //pedi=(PedidosParaReparto)ch.check(ped);
                //ped=pedi;
                detall=pr.detallePedidoParaCorreccion(ped.getCodigoTangoDePedido(),fecha2);
                Iterator id=detall.listIterator();
                while(id.hasNext()){
                    pedi=(PedidosParaReparto)id.next();
                    pedi=(PedidosParaReparto) ch.check(pedi);
                    pedi.setNumeroDeRevisionDeListado(revisionNum);
                    pedi.setNumeroDeListadoDeMateriales(numeroListado);
                    
                    //System.out.println("DETALLE CARGA "+pedi);
                    cargaDetallada.add(pedi);
                    cargaDetallada1.add(pedi);
                }
                Revisionar rrev=new RevisionDeListados();
                rrev.convertirARevision(carga);
                if(rrev.chequearCambioDeListado(carga)){
                    //rrev.guardarDatosRevision(cargaDetallada);
                    ListadoDeCargaPorVehiculo.numeroDeListadoAnterior=rrev.leerNumeroDeListadoAnterior();
                }
                rrev.guardarDatosRevision(carga);
                cargaDetallada.clear();
            } catch (SQLException ex) {
                GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }

                
            }else{
              /*
               * ACA TENGO QUE VERIFICAR QUE EL NUMERO DE LISTADO DE MATERIALES SEA MAYOR A 0, SI ES ASI GENERO UNA REVISION DE EL NUMERO QUE TIENE CARGADO EL PEDIDO
               * COMO REVISION PARA QUITAR MATERIAL -----
               * O BIEN PUEDO GENERAR UNA NUEVA REVISION CON NUMEROS NEGATIVOS, CON EL FIN DE MANTENER LAS CANTIDADES CORRECTAS
               * 
               * 
               */  
              
            ped.setNumeroDeListadoDeMateriales(numeroListado);
            int revisionListado=ped.getNumeroDeRevisionDeListado();
            int lSRevisionDeListado=ls.getNumeroRevision();
            //if(revisionListado < lSRevisionDeListado){
            //ped.setNumeroDeRevisionDeListado(ls.getNumeroRevision());
            //ped.setNumeroDeRevisionDeListado(0);
            //revisionNum=ls.getNumeroRevision();
            //}else{
             ped.setNumeroDeRevisionDeListado(ls.getNumeroRevision());
             ped.setVerificadorRevision(1);
            //ped.setNumeroDeRevisionDeListado(0);
            revisionNum=ls.getNumeroRevision();            
             try {
                //pedi=(PedidosParaReparto)ch.check(ped);
                //ped=pedi;
                detall=pr.detallePedidoParaCorreccion(ped.getCodigoTangoDePedido(),fecha2);
                Iterator id=detall.listIterator();
                while(id.hasNext()){
                    pedi=(PedidosParaReparto)id.next();
                    pedi=(PedidosParaReparto) ch.check(pedi);
                        if(pedi.getNumeroDeListadoDeMateriales() > 0){
                        /*
                         * SOLUCION MAS SIMPLE:
                         * -GENERO UNA NUEVA PLANTILLA DE LISTADO PARA ANULACIONES
                         * - PASO LOS DATOS DE NUMERO DE PEDIDO, FECHA, NUMERO LISTADO, REVISION DE LOS QUE HALLA QUE ELIMINAR
                         * - GUARDO EN HISTORICO
                         * - SALE LA NUEVA REVISION
                         * 
                         * POR OTRO LADO SI MANDO ASI NO TENGO DONDE PUEDA LEER EL LISTADO , A NO SER QUE LO HAGA SOLAMENTE CON PARAMETROS, O SEA LOS DATOS DEL PEDIDO Y LISTO
                         * DE ESA FORMA SOLAMENTE IMPRIMO Y NO ESTOY MODIFICANDO LAS BASE PARA QUE SALGA LA INFO SOLAMENTE PARA UNA REVISION, NO TIENE MUCHO SENTIDO. EL RESTO ESTARÍA HECHO 
                         * Y NO TENDRÍA QUE TRAER MAYORES PROBLEMAS
                         * 
                         * 
                         */
                                int unidadActual=pedi.getVehiculoAsignado();
                                int unidadAnterior=pedi.getVehiculoAnterior();
                         if(unidadActual == unidadAnterior){
                             
                         }else{ 
                          if(senal==0){   
                              Vehiculos vh=new Vehiculos();
                              vh=(Vehiculos) vehiculos.get(pedi.getVehiculoAnterior());
                              vehiculosAnt=vh.getDescripcion();
                              nombreCliente=ped.getRazonSocial();
                              codigoCliente=ped.getCodigoCliente();
                        ls2=pr.GenerarNuevoListado(pedi.getVehiculoAnterior(), pedi.getFechaEnvio(),nuevoListado,true);
                        
                        numeroRev2 = ls2.getNumeroRevision();
                        numeroListado2=ls2.getNumeroListado();
                          }
                        //pedi.setNumeroDeListadoDeMateriales(numeroListado2);
                        //pedi.setNumeroDeRevisionDeListado(numeroRev2);
                        descarga.add(pedi);
                         }
                    }
                    pedi.setNumeroDeRevisionDeListado(revisionNum);
                    //pedi.setCantidadArticuloPendiente(totalKg);
                    pedi.setNumeroDeListadoDeMateriales(numeroListado);
                    System.out.println("DETALLE CARGA para verificar las cantidades: "+pedi.getCantidadArticulo());
                    
                    cargaDetallada.add(pedi);
                    cargaDetallada1.add(pedi);
                }
                Revisionar rrev=new RevisionDeListados();
                rrev.convertirARevision(cargaDetallada);
                if(rrev.chequearCambioDeListado(carga)){
                    //rrev.guardarDatosRevision(cargaDetallada);
                    ListadoDeCargaPorVehiculo.numeroDeListadoAnterior=rrev.leerNumeroDeListadoAnterior();
                }
                Runtime r=Runtime.getRuntime();
            r.gc();
                rrev.guardarDatosRevision(carga);
 
                cargaDetallada.clear();
            } catch (SQLException ex) {
                GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
            //}
            //totalKg+=ped.getPesoItems();
            //System.out.println("DETALLE PEDIDO "+ped.getDescripcionArticulo()+" cant "+ped.getCantidadArticulo());
            }
            
            
        }

        listadoNumero=numeroListado;
        
        
        try {
            try {
                pr.GuardarNumeroListadoEnPedido(cargaDetallada1,seleccion,fecha2);
            } catch (IOException ex) {
                GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("EL NUMERO DE LISTADO ES "+numeroListado);
        cargaDetallada1.clear();
        /*
         * A PARTIR DE ACA COMIENZA A EMITIRSE LOS LISTADOS DE REVISION Y DETALLADO, ESTO VA HABER QUE SACARLO Y DISSOCIARLO
         * A PARTIR DE ACA DEBE IR UNA PARTE A EN EMISION DE DETALLADO - DEBO GENERAR EL FORMULARIO PARA EL CONSOLIDADO - Y VER COMO
         * INDEPENDIZAR LA ULTIMA REVISION.
         * 
         */
        nListado=numeroListado;
            rNum=revisionNum;
        desc=descarga;
        
        //ACA SE LLAMA AL THREAD GUARDARLISTADOS Y SE GUARDA EL DATO DEL MISMO COMO EL NUMERO DE REVISION CORRESPONDIENTE EN EL CAMPO DE LA BD, EN LA TABLA
        // PEDIDOS_CARGA1
        /*
         * DE SER NECESARIO MODIFICAR LA REVISION DE UNO SE DEBE HACER DESDE AQUI, PARA QUE EL REPORTE SEA LEIDO SOLAMENTE POR EL NUMERO DE REVISION
         * TRASMITO EL ARRAY REVIS, POR QUE ES DONDE CARGUE EL LISTADO FILTRADO 
         * 
         */
        
        
        //GuardarListados gl=new GuardarListados();
        //gl.setList(carga);
        //gl.start();
        
        
        /*
        try {
            emision.ImprimirListadoConsolidado(fecha2, seleccion, descUnidad, totalKg);
        } catch (JRException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
         * 
         */
        Runtime r=Runtime.getRuntime();
        r.gc();
        //ls=null;
        //this.jButton2.setEnabled(true);
        this.jButton3.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EmisionDeListados em=new EmisionDeListados();
        Procesos pr=new Procesos();
        //Listados ls=new Listados();
       int numeroListado=0;
       numeroListado=nListado;
       /*
        try {
            //ls=pr.GenerarNuevoListado(seleccion, fecha2);
            numeroListado=ls.getNumeroListado();
        } catch (SQLException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        */ 
       listadoNumero=numeroListado;
        
            
        try {
            em.ImprimirListadoConsolidadoR(fecha2, seleccion, descUnidad, tKg,listadoNumero,ls.getNumeroRevision());
        } catch (IOException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        try {
            //actualizarCarga();
            Procesos pr=new Procesos();
             try {
                 try {
                     carga=pr.actualizarComprobantesPedidos(carga);
                 } catch (ParseException ex) {
                     GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
                 }
             } catch (SQLException ex) {
                GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
             }
             //DatosClientes dt=new DatosClientes();
            
            //dt.setCargados(carga);
            //dt.start();
            //carga=dt.getCargados();
             ArrayList detall1=new ArrayList();
            try {
                /*
                Iterator ica=carga.listIterator();
                while(ica.hasNext()){
                    PedidosParaReparto ped=(PedidosParaReparto)ica.next();
                   try {
                       detall1.add(pr.detallePedidoParaCorreccion(ped.getCodigoTangoDePedido(),ped.getFechaEnvio()));
                   } catch (SQLException ex) {
                       Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
                   }
                }
                */
                detall1=pr.listadoDetalladoPorVehiculo(seleccion, fecha2);
            } catch (SQLException ex) {
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
            ActualizableTango actu=new Checking();
            String res=actu.verificarRemitosTotales(detall1);
            if(res.equals("")){
               System.out.println("NOOOOOOOOOOOOOOOOO INGRESO CON DIFERENCIAS "+res); 
            }else{
                System.out.println(res);
                JOptionPane.showMessageDialog(null,res,"EXISTENCIA CANT PENDIENTES DE REMITIR DE LOS PEDIDOS DE LA HDR ",JOptionPane.PLAIN_MESSAGE);
            }
            VistaHdr vista=new VistaHdr(carga,seleccion);
            InicioSiderconHdr.jDesktopPane1.add(vista);
            vista.toFront();
            vista.setVisible(true);
            
            /*
             EmisionHojaDeRuta emis=new EmisionHojaDeRuta();
             emis.setListado(carga);
             emis.start();
             * 
             */
        } catch (ParseException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        try {
            con.CerrarCn(ccn);
        } catch (SQLException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formInternalFrameClosed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
       EmisionDeListados emision=new EmisionDeListados();
        Procesos pro=new Procesos();
        carga=pro.RecalcularPesosTotalesPedidos(carga);
            try {
                
                
                emision.ImprimirListadoDetallado(nListado,tKg,rNum,descUnidad,carga);
            } catch (IOException ex) {
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
 
       
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
                int pedidoModificable=jTable1.getSelectedRow();
            String numePedido=(String) jTable1.getValueAt(pedidoModificable,1);
            pedidoSeleccionado=numePedido;
            ModificacionDePedidos mod=new ModificacionDePedidos(1);
            InicioSiderconHdr.jDesktopPane1.add(mod);
            mod.setVisible(true);
            mod.toFront();
    }//GEN-LAST:event_jButton6ActionPerformed
    private void actualizarCarga(){
        Iterator ic=carga.listIterator();
        PedidosParaReparto ped=new PedidosParaReparto();
        
        while(ic.hasNext()){
            ped=(PedidosParaReparto)ic.next();
            ped.setNumeroDeListadoDeMateriales(listadoNumero);
           
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
