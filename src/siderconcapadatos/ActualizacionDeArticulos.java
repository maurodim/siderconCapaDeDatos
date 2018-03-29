/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ActualizacionDeArticulos.java
 *
 * Created on 01-mar-2012, 13:00:28
 */
package siderconcapadatos;

import Excel.ExListadoArticulos;
import Excel.LeerExcel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.Articulos;
import proceso.Procesos;
import siderconcapadatos.tablas.MiTablaArticulos;

/**
 *
 * @author Administrador
 */
public class ActualizacionDeArticulos extends javax.swing.JInternalFrame {

    /** Creates new form ActualizacionDeArticulos */
    private static ArrayList articulos=new ArrayList();
    private static ArrayList tablaCodigo=new ArrayList();
    public ActualizacionDeArticulos() {
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
        MiTablaArticulos TablaArticulos=new MiTablaArticulos();
        Procesos pro=new Procesos();
        try{
            articulos=pro.ListadoDeArticulos();
        }catch(Exception ex){
            System.err.println("NO SE CARGARON LOS ARTICULOS "+ex);
        }
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jButton6 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Actualizacion de Datos de Articulos Cargados en el Sistema HDR");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED, new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153), new java.awt.Color(153, 153, 153)));

        jTable1.setModel(TablaArticulos);
        TablaArticulos.addColumn("Codigo Articulo");
        TablaArticulos.addColumn("Descripcion Articulo");
        TablaArticulos.addColumn("Sinonimo");
        TablaArticulos.addColumn("Peso Cargado");
        TablaArticulos.addColumn("Eliminar Articulo");
        TablaArticulos.addColumn("Unid. De Medida");
        Object[] fila=new Object[6];
        Iterator ia=articulos.listIterator();
        while(ia.hasNext()){
            Articulos art=null;
            art=(Articulos)ia.next();
            fila[0]=art.getCodigo();
            fila[1]=art.getDescripcionArticulo();
            fila[2]=art.getSinonimoArticulo();
            fila[3]=art.getPesoUnitario();
            fila[4]=false;
            fila[5]=art.getUnidadDeMedida();
            TablaArticulos.addRow(fila);
        }
        jPanel3.setVisible(false);
        //tablaCodigo=articulos;
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 153, 255), 1, true));

        jButton1.setText("Generar Excel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Buscar Codigo");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Guardar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Buscar Sinonimo");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Agregar Articulo");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton7.setText("Leer Excel");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("Abrir Excel");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jLabel6.setText("<html>Instructivo para la Carga/Actualización Masiva de Artículos:<br>\n1.- Click en \"Abrir Excel\"<br>\n2.- Completar la hoja Excel que se abre, con los datos de los registros a actualizar (agregar nuevos / corregir existentes).<br>\n3.- Guardar y cerrar libro.<br>\n4.- Click en \"Leer Excel\".   <br>\n</html>"); // NOI18N

        jLabel7.setText("<html>\nInstructivo para la Eliminación de un registro:<br>\n1.- Click en el Check-box correspondiente.<br>\n2.- Click en \"Guardar\".<br>\nAl salir y entrar a la funcion de actualización de articulos se verá que el registro ha sido eliminado. <br>\n</html>");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(157, 157, 157)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton3)
                    .addComponent(jButton7)
                    .addComponent(jButton8))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jLabel7))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jLabel1.setText("Codigo Articulo :");

        jLabel2.setText("Descripcion");

        jLabel3.setText("Sinonimo :");

        jLabel4.setText("Peso Unitario :");

        jButton6.setText("Guardar");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "KG", "UND", "MTS" }));

        jLabel5.setText("Unid de Medida");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6))
                .addContainerGap(61, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String codigo=JOptionPane.showInputDialog("POR FAVOR INGRESE EL CODIGO :");
        //codigo+="      ";
        //System.out.println("codigo ingresado"+codigo+"largo"+codigo.length());
        tablaCodigo.clear();;
        Articulos art=new Articulos();
        Boolean verificacion=false;
        Articulos seleccionado=null;
        Iterator ia=articulos.listIterator();
        //System.out.println("cantidad articulos "+articulos.size());
        while(ia.hasNext()){
            art=(Articulos)ia.next();
            String codigoArt=art.getCodigo();
            codigo.trim();
            int cantidad=codigo.length();
            codigoArt.trim();
            String cd=codigoArt.substring(0,cantidad);
            System.out.println("codigo Articulos"+codigoArt+" tamaño: "+codigoArt.length()+" cod ing "+codigo+" tamaño :"+codigo.length());
            if(codigo.equals(cd)){
                tablaCodigo.add(art);
                //seleccionado=art;
                verificacion=true;
                System.err.println("codigo Articulos"+codigoArt);
            }
        }
        if(verificacion){
        jTable1.removeAll();
        MiTablaArticulos tablaA=new MiTablaArticulos();

        tablaA.addColumn("Codigo Articulo");
        tablaA.addColumn("Descripcion Articulo");
        tablaA.addColumn("Sinonimo");
        tablaA.addColumn("Peso Cargado");
        tablaA.addColumn("Eliminar Articulo");
        tablaA.addColumn("Unid. De Medida");
        Object[] fila=new Object[6];
        Iterator it=tablaCodigo.listIterator();
        while(it.hasNext()){
            seleccionado=(Articulos)it.next();
            String cod=seleccionado.getCodigo();
            String desc=seleccionado.getDescripcionArticulo();
            String sin=seleccionado.getSinonimoArticulo();
            Double peso=seleccionado.getPesoUnitario();
            fila[0]=cod;
            fila[1]=desc;
            fila[2]=sin;
            fila[3]=peso;
            fila[4]=false;
            fila[5]=seleccionado.getUnidadDeMedida();
            tablaA.addRow(fila);
        }
        jTable1.setModel(tablaA);
        }else{
            JOptionPane.showMessageDialog(null,"ALERTA CODIGO NO ENCONTRADO","codigo no encontrado",JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String sinonimo=JOptionPane.showInputDialog("POR FAVOR INGRESE EL SINONIMO :");
        //codigo+="      ";
        //System.out.println("codigo ingresado"+codigo+"largo"+codigo.length());
        
        Articulos art=new Articulos();
        Boolean verificacion=false;
        Articulos seleccionado=null;
        tablaCodigo.clear();
        Iterator ia=articulos.listIterator();
        System.out.println("cantidad articulos "+articulos.size());
        while(ia.hasNext()){
            art=(Articulos)ia.next();
            String codigoArt=art.getSinonimoArticulo();
            sinonimo.trim();
            sinonimo=sinonimo.toUpperCase();
            int cantidad=sinonimo.length();
            System.out.println(codigoArt);
            //codigoArt.trim();
            String cd=null;
            if(codigoArt==null){
            cd="";
            }else{
                System.out.println(codigoArt+" cantidad "+cantidad);
                //cantidad=cantidad - 1;
                try{
                cd=codigoArt.substring(0,cantidad);
                }catch(java.lang.IndexOutOfBoundsException e){
                    cd="";
                    System.out.println(e+"articulo  "+art.getDescripcionArticulo());
                }
            }
                System.out.println("codigo Articulos"+cd+art.getCodigo()+" tamaño: "+cd.length()+" cod ing "+sinonimo+" tamaño :"+sinonimo.length());
            if(sinonimo.equals(cd)){
                tablaCodigo.add(art);
                //seleccionado=art;
                verificacion=true;
                System.err.println("sinonimo Articulos"+codigoArt+" tamaño:"+tablaCodigo.size());
            }
        }
        if(verificacion){
            
        jTable1.removeAll();
        MiTablaArticulos tablaA=new MiTablaArticulos();

        tablaA.addColumn("Codigo Articulo");
        tablaA.addColumn("Descripcion Articulo");
        tablaA.addColumn("Sinonimo");
        tablaA.addColumn("Peso Cargado");
        tablaA.addColumn("Eliminar Articulo");
        tablaA.addColumn("Unid. De Medida");
        Object[] fila=new Object[6];
        Iterator it=tablaCodigo.listIterator();
        while(it.hasNext()){
            seleccionado=(Articulos)it.next();
            String cod=seleccionado.getCodigo();
            String desc=seleccionado.getDescripcionArticulo();
            String sin=seleccionado.getSinonimoArticulo();
            Double peso=seleccionado.getPesoUnitario();
            fila[0]=cod;
            fila[1]=desc;
            fila[2]=sin;
            fila[3]=peso;
            fila[4]=false;
            fila[5]=seleccionado.getUnidadDeMedida();
            tablaA.addRow(fila);
        }
        jTable1.setModel(tablaA);
        //articulos.clear();
        //articulos=tablaCodigo;
        }else{
            JOptionPane.showMessageDialog(null,"ALERTA SINONIMO NO ENCONTRADO","sinonimo no encontrado",JOptionPane.PLAIN_MESSAGE);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        ArrayList paraEnvio=null;
        if(tablaCodigo.size()>0){
            paraEnvio=tablaCodigo;
        }else{
            paraEnvio=articulos;
        }
        int cantidad=jTable1.getRowCount();
        Articulos ar=new Articulos();
        for(int i=0;i<cantidad;i++){
            int esst=0;
            ar=(Articulos) paraEnvio.get(i);
            String descripcion=(String) jTable1.getValueAt(i,1);
            String sinonimo=(String) jTable1.getValueAt(i,2);
            Double peso=(Double) jTable1.getValueAt(i,3);
            Boolean est=(Boolean) jTable1.getValueAt(i,4);
            String unidad=(String)this.jTable1.getValueAt(i,5);
            ar.setUnidadDeMedida(unidad);
            ar.setDescripcionArticulo(descripcion);
            ar.setSinonimoArticulo(sinonimo);
            ar.setPesoUnitario(peso);
            if(est)esst=2;
            ar.setEstado(esst);
        }
        Procesos pr=new Procesos();
        try {
            pr.ModificacionDeArticulos(paraEnvio);
        } catch (SQLException ex) {
            Logger.getLogger(ActualizacionDeArticulos.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        try {
            setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ActualizacionDeArticulos.class.getName()).log(Level.SEVERE, null, ex);
        }  
        */
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        jPanel1.setVisible(false);
        jPanel3.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        
        Articulos art=new Articulos();
        String codigo=jTextField1.getText();
        String sinonimo=jTextField3.getText();
        String descripcion=jTextField2.getText();
        String pp=this.jTextField4.getText().replace(",",".");
        String unidMedida=(String)this.jComboBox1.getSelectedItem();
        Double peso=Double.parseDouble(pp);
        
        art.setCodigo(codigo.trim());
        art.setDescripcionArticulo(descripcion.trim());
        art.setSinonimoArticulo(sinonimo.trim());
        art.setPesoUnitario(peso);
        art.setEstado(3);
        Procesos prart=new Procesos();
        prart.GuardarNuevoArticulo(art,unidMedida);
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField3.setText("");
        this.jTextField4.setText("");
        /*
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
        try {
            setClosed(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(ActualizacionDeArticulos.class.getName()).log(Level.SEVERE, null, ex);
        }     
        */
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        ExListadoArticulos ex=new ExListadoArticulos();
        if(tablaCodigo.size() > 0){
            ex.GenerarInforme(tablaCodigo);   
        }else{
            ex.GenerarInforme(articulos);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        tablaCodigo.clear();
        articulos.clear();
    }//GEN-LAST:event_formInternalFrameClosed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       LeerExcel leer=new LeerExcel();
       String ruta="c:"+File.separator+"src"+File.separator+"informes"+File.separator+"listadoArticulos.xls";
       System.out.println(ruta);
        try {
            leer.leerExcel1(ruta);
        } catch (SQLException ex) {
            System.out.println("el error lo tira en actualizacion de articulos pero es en la lectura del excel, aca tiene que aparecer el mensaje de error");
            Logger.getLogger(ActualizacionDeArticulos.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        String ruta="c:"+File.separator+"src"+File.separator+"informes"+File.separator+"listadoArticulos.xls";
        
            File elFichero=new File(ruta);
        
            try {
                
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+ruta);
            } catch (IOException ex) {
                Logger.getLogger(ExListadoArticulos.class.getName()).log(Level.SEVERE, null, ex);
            }
    }//GEN-LAST:event_jButton8ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
