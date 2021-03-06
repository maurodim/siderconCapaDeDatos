/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Pendientes.java
 *
 * Created on 15-mar-2012, 10:28:49
 */
package siderconcapadatos;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.PedidosParaReparto;
import objetos.PedidosTango;
import proceso.Procesos;
import siderconcapadatos.tablas.MiModeloTablaPendientes;

/**
 *
 * @author Administrador
 */
public class Pendientes extends javax.swing.JInternalFrame {

    /** Creates new form Pendientes */
    private static ArrayList pedidosPendientes=new ArrayList();
    public Pendientes() {
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        MiModeloTablaPendientes modeloPendientes=new MiModeloTablaPendientes();
        Procesos pp=new Procesos();
        try{
            pedidosPendientes=pp.listadoDeMaterialesPendientes();
        }catch(Exception ex){
            System.err.println("No se cargaron los pendientes "+ex);
        }
        Iterator ip=pedidosPendientes.listIterator();
        PedidosParaReparto ped=new PedidosParaReparto();

        modeloPendientes.addColumn("Razon social");
        modeloPendientes.addColumn("Pedido Tango");
        modeloPendientes.addColumn("Descripcion Articulo");
        modeloPendientes.addColumn("cant. s/fecha");
        modeloPendientes.addColumn("cant. Asignadas");
        modeloPendientes.addColumn("cant. total");
        modeloPendientes.addColumn("Fecha Entrega");
        modeloPendientes.addColumn("Eliminar");
        modeloPendientes.addColumn("Fecha Ped.");
        Object[] fila=new Object[9];
        while(ip.hasNext()){
            ped=(PedidosParaReparto)ip.next();
            fila[0]=ped.getRazonSocial();
            fila[1]=ped.getCodigoTangoDePedido();
            fila[2]=ped.getDescripcionArticulo();
            fila[3]=ped.getCantidadArticuloPendiente();
            fila[4]=ped.getCantidadArticulo();
            fila[5]=ped.getCantidadArticulosTotales();
            fila[6]="00/00/0000";
            fila[7]=false;
            fila[8]=ped.getFechaPedidosTango();
            modeloPendientes.addRow(fila);
        }
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Materiales Pendientes sin fecha de entrega asignada");

        jTable1.setModel(modeloPendientes);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 952, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jButton1.setText("Guardar");
        jButton1.setToolTipText("Guarda las modificaciones hechas a la tabla superior");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Ver Detalle de entrega");
        jButton2.setToolTipText("Muestra un detallado de las entregas realizadas con respecto al pedido señalado");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Ejecutar Limpieza");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(418, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jLabel1.setText("<html>- Para asignar fecha y/o cantidad, doble click sobre el campo y modificar el valor por el deseado.<br>\n- Para modificar la cantidad a ser enviada, borrar cantidad Asignada e insertar el valor correspondiente\n</html>\n");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 717, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       int filas=jTable1.getRowCount();
       PedidosParaReparto ped=new PedidosParaReparto();
       Procesos pr=new Procesos();
       System.err.println("cantidad items "+pedidosPendientes.size());
       Iterator ipp=pedidosPendientes.listIterator();
       int i=0;
       while(ipp.hasNext()){
           ped=(PedidosParaReparto)ipp.next();
           String fechaEnvio=(String) jTable1.getValueAt(i,6);
           if(fechaEnvio.equals("00/00/0000")){
               ped.setEstado(0);
           }else{
               //ped.setEstado(1);
           Double cantidadOriginal=ped.getCantidadArticuloPendiente();
           Double cantidadTotalOriginal=ped.getCantidadArticulosTotales();
           String fechaOriginal=ped.getFechaEnvio();
           Double cantidadPendiente=(Double) jTable1.getValueAt(i,3);
           Double cantidadEntregada=(Double) jTable1.getValueAt(i,4);
           Double cantidadTotal=(Double)jTable1.getValueAt(i,5);
           String empresa=(String)ped.getEmpresa();
           String nuevoPedido=(String)jTable1.getValueAt(i,1);
           String indicador=nuevoPedido.substring(0,1);
           int cantidad=nuevoPedido.length();
           String resto=nuevoPedido.substring(1,cantidad);
           String numerador=null;
           
           
           if(indicador=="X"){
            numerador="Z";   
           }else{
               numerador="X";
           }
           //int numerador=Integer.parseInt(indicador);
           //numerador++;
           //indicador=(String)numerador;
           String pedidosSistema=numerador+resto;
           
               ped.setCantidadArticulo(cantidadPendiente);
               Double cant=0.00;
               cant=cantidadOriginal - cantidadPendiente;
               //if(cant < 0){
               //    JOptionPane.showMessageDialog(null,"Cantidad ingresada incorrecta","ALERTA",JOptionPane.PLAIN_MESSAGE);               
               //}else{
               System.out.println("resultado cuenta "+cant);
               Double ent=cantidadEntregada + cant;
               ped.setCantidadArticuloPendiente(cant);
               ped.setEmpresa(empresa);
               ped.setFechaEnvio(fechaEnvio);
               ped.setCodigoTangoDePedido(pedidosSistema);
               ped.setEstado(1);
               //}
           }
           Boolean estado=(Boolean) jTable1.getValueAt(i,7);
           if(estado){
               ped.setEstado(2);
           }
           
           System.out.println(ped.getRazonSocial()+" "+ped.getCodigoArticulo()+" "+ped.getCantidadArticulo());
           i++;
           
       }
        try {
            pr.GuardarModificacionesPendientes(pedidosPendientes);
            JOptionPane.showMessageDialog(null,"Sr. Operador, el sistema se cerrará para que los cambios sean permanentes");
        } catch (SQLException ex) {
            Logger.getLogger(Pendientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        try {
            setClosed(true);
            System.exit(1);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(Pendientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int fila=jTable1.getSelectedRow();
        String pedido=(String) jTable1.getValueAt(fila,1);
        String pd=pedido.substring(2);
       //String cd=jTable1.getValueAt(fila,)
        DetallePendientes det=new DetallePendientes(pd);
        InicioSiderconHdr.jDesktopPane1.add(det);
            //mod1.setVisible(true);
            //mod.setLocation(this);
            det.toFront();
            det.setVisible(true);
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
    
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // ACA DEBO CONECTAR CON TANGO Y PURGAR UNO OR UNO LOS PEDIDOS
        PedidosTango pedT=new PedidosTango();
        try {
            try {
                pedT.verificarPedidos(pedidosPendientes);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pendientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Pendientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
        
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables

    
}
