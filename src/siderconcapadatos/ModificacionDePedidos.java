/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ModificacionDePedidos.java
 *
 * Created on 03-feb-2012, 12:01:39
 */
package siderconcapadatos;

import java.beans.PropertyVetoException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.PedidosParaReparto;
import proceso.Procesos;
import siderconcapadatos.tablas.miTablaModificacion;

/**
 *
 * @author Administrador
 */
public class ModificacionDePedidos extends javax.swing.JInternalFrame {

    /** Creates new form ModificacionDePedidos */
    static ArrayList listadoDelPedido;
    static String nmPedido;

    public static void setNmPedido(String nmPedido) {
        ModificacionDePedidos.nmPedido = nmPedido;
    }
    
    public ModificacionDePedidos() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        miTablaModificacion mod=new miTablaModificacion();
        PedidosParaReparto pedid=new PedidosParaReparto();
        Procesos pro=new Procesos();
        ArrayList listado=new ArrayList();
        try{
            listado=pro.detallePedidoParaCorreccion(ListadoDePedidosParaReparto.numeroDePedido,ListadoDePedidosParaReparto.fechaPedido);
            listadoDelPedido=listado;
            System.out.println("Pedido NUMERO :"+ListadoDePedidosParaReparto.numeroDePedido+"fehca "+ListadoDePedidosParaReparto.fechaPedido);
        }catch(Exception ex){
            System.out.println("no se cargo el detalle "+ex);
        }
        Iterator ii=listado.listIterator();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Modificacion de Pedidos");
        setVisible(true);

        jTable1.setModel(mod);
        mod.addColumn("Nro Pedido");
        mod.addColumn("Cod Articulo");
        mod.addColumn("descrip Articulo");
        mod.addColumn("cant a enviar");
        mod.addColumn("cant s/fecha");
        mod.addColumn("fecha de entrega");
        mod.addColumn("eliminar Item");
        mod.addColumn("Vendedor");
        Object[] fila=new Object[8];
        while(ii.hasNext()){
            pedid=(PedidosParaReparto)ii.next();
            fila[0]=pedid.getCodigoTangoDePedido();
            fila[1]=pedid.getCodigoArticulo();
            fila[2]=pedid.getDescripcionArticulo();
            fila[3]=pedid.getCantidadArticulo();
            fila[4]=pedid.getCantidadArticuloPendiente();
            fila[5]=pedid.getFechaEnvio();
            fila[6]=false;
            fila[7]=pedid.getNombreVendedor();
            mod.addRow(fila);
        }
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(574, 574, 574)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton1)
                .addGap(90, 90, 90))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Procesos pro=new Procesos();
        Double cantidad;
        Double valorOriginal;
        Double valorPendiente;
        PedidosParaReparto ped=new PedidosParaReparto();
        //listadoDelPedido=pro.DetallePedido(nmPedido);
        Iterator ip=listadoDelPedido.listIterator();
        for(int i=0;i< jTable1.getRowCount();i++){
            ped=(PedidosParaReparto) ip.next();
            System.out.println("en modificacion de pedidos "+ped.getDescripcionArticulo());
            valorOriginal=ped.getCantidadArticulo();
            cantidad=(Double) jTable1.getValueAt(i,3);
            ped.setCantidadArticulo(cantidad);
            valorPendiente=valorOriginal - cantidad;
            if(valorOriginal==cantidad){
                valorPendiente=(Double) jTable1.getValueAt(i,4);
            ped.setCantidadArticuloPendiente(valorPendiente);
            }else{
            //cantidad=(Double) jTable1.getValueAt(i,4);
            ped.setCantidadArticuloPendiente(valorPendiente);
            ped.setCantidadArticulosTotales(valorOriginal);
            }
            String fechha=(String) jTable1.getValueAt(i,5);
            ped.setFechaEnvio(fechha);
        }
        try {
            pro.guardarDetallePedido(listadoDelPedido);
        } catch (SQLException ex) {
            Logger.getLogger(ModificacionDePedidos.class.getName()).log(Level.SEVERE, null, ex);
        }
        listadoDelPedido.clear();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        try {
            setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ModificacionDePedidos.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
