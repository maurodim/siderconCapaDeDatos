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
    static int origen;

    public static void setNmPedido(String nmPedido) {
        ModificacionDePedidos.nmPedido = nmPedido;
    }
    
    public ModificacionDePedidos(int or) {
        origen=or;
        initComponents();
        if(origen==1){
            this.jLabel1.setText("");
            this.jButton1.setEnabled(false);
        }
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
            if(origen==0){
                listado=pro.detallePedidoParaCorreccion(ListadoDePedidosParaReparto.numeroDePedido,ListadoDePedidosParaReparto.fechaPedido);
            }else{
                listado=pro.DetallePedidoCompleto(ListadoDeCargaPorVehiculo.pedidoSeleccionado);
            }
            listadoDelPedido=listado;
            System.out.println("Pedido NUMERO :"+ListadoDePedidosParaReparto.numeroDePedido+"fehca "+ListadoDePedidosParaReparto.fechaPedido);
        }catch(Exception ex){
            System.out.println("no se cargo el detalle "+ex);
        }
        Iterator ii=listado.listIterator();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

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
        jButton1.setToolTipText("Presione para guardar las modificaciones o eliminaciones en el pedido actual");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("<html>\nPARA LA MODIFICACION DE PEDIDOS:<br>\n\t- Para modificar la cantidad a enviar: En la columna con el mismo nombre, borrar el valor<br>\n\ty cargar la cantidad a enviar, luego presionar Guardar<br>\n\t( El sistema calcula automáticamente la cantidad sin fecha asignada)<br>\n\t- Para modificar la fecha de entrega: modificar directamente en la columna de fecha, el <br>\n\tvalor correspondiente a dia/mes/año, según sea lo correspondiente respetando dicho formato<br>\"\n\t\nPARA LA ELIMINACION DE UNO O VARIOS ITEMS:<br>\n\t-Marcar el checkbox correspondiente al item a eliminar<br>\n\t-Una vez marcado el/los items, presionar Guardar<br>\n</html>");
        jLabel1.setToolTipText("");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 796, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
            //ped.setIdPedidoEnTango(WIDTH);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
