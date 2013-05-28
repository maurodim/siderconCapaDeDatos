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
import interfaces.Revisionar;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import objetos.Listados;
import objetos.PedidosParaReparto;
import objetos.RevisionDeListados;
import proceso.EmisionDeListados;
import proceso.GuardarListados;
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
    public static ArrayList carga=new ArrayList();
    static Double totalKg;
    static Double tKg;
    static Integer listadoNumero;
    static Listados ls;
    static ArrayList cargaRevi=new ArrayList();
    //static ArrayList revis=new ArrayList();
    //static int numeroListado;
    /** Creates new form ListadoDeCargaPorVehiculo */
    public ListadoDeCargaPorVehiculo(Integer unidad,String fecha,String descripcion) {
        seleccion=unidad;
	fecha2=fecha;
        descUnidad=descripcion;
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

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Listado de la Carga de Materiales del Vehiculo");

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
            kilos+= pedid.getPesoTotal();
            //System.out.println(pedid.getPesoItems()+" PESOS "+pedid.getPesoTotal());
            mtDc.addRow(fila);
        }
        tKg=kilos;
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton1.setText("Verificador");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton2.setText("Imprimir Listado Consolidado");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton3.setText("Emitir Listado de Preparacion");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton4.setText("Emitir Hoja de Ruta");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
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
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(24, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(103, Short.MAX_VALUE))
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
        Iterator ica=carga.listIterator();
        int lpm=0;
        String cartel=" PEDIDOS SIN LPM ASIGNADA :\n";
        int flg=0;
        while(ica.hasNext()){
            pedi=(PedidosParaReparto)ica.next();
            lpm=0;
            lpm=pedi.getNumeroDeListadoDeMateriales();
            if(lpm==0){
                flg=1;
                cartel+=pedi.getRazonSocial()+" "+pedi.getCodigoTangoDePedido()+"\n";
                
            }
        }
        if(flg==1){
            JOptionPane.showMessageDialog(this,cartel);
        }
        
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
         */
        
        this.jButton2.setEnabled(false);
        int totalFilas=jTable1.getRowCount();
        ls=new Listados();
        //Double totalKg=0.00;
        Procesos pr=new Procesos();
       int numeroListado=0;

        try {
            ls=pr.GenerarNuevoListado(seleccion, fecha2);
            numeroListado=ls.getNumeroListado();
            System.err.println("NUMERO DE REVISION QUE SE APLICA AL LISTADO "+ls.getNumeroRevision());
        } catch (SQLException ex) {
            Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("PARA VER QUE ERROR ES :"+ex);
        }
        Iterator ic=carga.listIterator();
        PedidosParaReparto ped=new PedidosParaReparto();
        PedidosParaReparto pedi=new PedidosParaReparto();
        ArrayList cargaDetallada=new ArrayList();
        ArrayList detall=new ArrayList();
        ChequearCantidadesPedidos ch=new Checking();
        Integer revisionNum=0;
        while(ic.hasNext()){
            ped=(PedidosParaReparto)ic.next();
            
            if(ped.getNumeroDeListadoDeMateriales()==numeroListado){
            // vamos a ver si asi esta bien
                
                ped.setNumeroDeListadoDeMateriales(numeroListado);
            
                int revisionListado=ped.getNumeroDeRevisionDeListado();
            int lSRevisionDeListado=ls.getNumeroRevision();
            if(ped.getVerificadorRevision()==1){
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
                    pedi.setNumeroDeRevisionDeListado(revisionNum);
                    //System.out.println("DETALLE CARGA "+pedi);
                    cargaDetallada.add(pedi);
                }
                Revisionar rrev=new RevisionDeListados();
                rrev.convertirARevision(carga);
            } catch (SQLException ex) {
                GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            }

                
            }else{
                /*
                 * ver por que no me esta contabilizando las revisiones
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
            //ped.setNumeroDeRevisionDeListado(0);
            revisionNum=ls.getNumeroRevision();            
             try {
                //pedi=(PedidosParaReparto)ch.check(ped);
                //ped=pedi;
                detall=pr.detallePedidoParaCorreccion(ped.getCodigoTangoDePedido(),fecha2);
                Iterator id=detall.listIterator();
                while(id.hasNext()){
                    pedi=(PedidosParaReparto)id.next();
                    pedi.setNumeroDeRevisionDeListado(revisionNum);
                    pedi.setNumeroDeListadoDeMateriales(ls.getNumeroListado());
                    //System.out.println("DETALLE CARGA "+pedi);
                    cargaDetallada.add(pedi);
                }
                Revisionar rrev=new RevisionDeListados();
                rrev.convertirARevision(cargaDetallada);
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
                pr.GuardarNumeroListadoEnPedido(cargaDetallada,seleccion,fecha2);
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
        EmisionDeListados emision=new EmisionDeListados();
        try {
            emision.ImprimirListadoDetallado(numeroListado,tKg,revisionNum);
        } catch (IOException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
           GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        }
        //ACA SE LLAMA AL THREAD GUARDARLISTADOS Y SE GUARDA EL DATO DEL MISMO COMO EL NUMERO DE REVISION CORRESPONDIENTE EN EL CAMPO DE LA BD, EN LA TABLA
        // PEDIDOS_CARGA1
        /*
         * DE SER NECESARIO MODIFICAR LA REVISION DE UNO SE DEBE HACER DESDE AQUI, PARA QUE EL REPORTE SEA LEIDO SOLAMENTE POR EL NUMERO DE REVISION
         * TRASMITO EL ARRAY REVIS, POR QUE ES DONDE CARGUE EL LISTADO FILTRADO 
         * 
         */
        GuardarListados gl=new GuardarListados();
        gl.setList(carga);
        gl.start();
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
        this.jButton2.setEnabled(true);
        //this.jButton3.setEnabled(false);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EmisionDeListados em=new EmisionDeListados();
        Procesos pr=new Procesos();
        //Listados ls=new Listados();
       int numeroListado=0;
       numeroListado=ls.getNumeroListado();
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
        } catch (JRException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(ListadoDeCargaPorVehiculo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
