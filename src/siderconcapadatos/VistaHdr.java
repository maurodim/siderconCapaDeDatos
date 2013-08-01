/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * VistaHdr.java
 *
 * Created on 30-mar-2012, 15:40:19
 */
package siderconcapadatos;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.Fleteros;
import objetos.PedidosParaReparto;
import proceso.EmisionHojaDeRuta;
import proceso.Procesos;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;
import siderconcapadatos.tablas.MiTablaHd;

/**
 *
 * @author Administrador
 */
public class VistaHdr extends javax.swing.JInternalFrame {

    /** Creates new form VistaHdr */
    static ArrayList cargados=new ArrayList();
    static int unidad;
    static String fechaE;
    static Integer numeroDeListadoDeMateriales;
    public VistaHdr(ArrayList car,int vehiculo) throws ParseException {
        Procesos pr=new Procesos();
        try {
            cargados=pr.actualizarComprobantesPedidos(car);
            cargados=pr.actualizarVistaHdr(cargados);
            System.err.println("cantidad array "+cargados.size());
        } catch (SQLException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(VistaHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
        unidad=vehiculo;
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
        MiTablaHd hd=new MiTablaHd();
        //jPanel4.setVisible(false);
        PedidosParaReparto ped=new PedidosParaReparto();
        String cli=null;
        Procesos pr=new Procesos();
        Iterator ih=ListadoDeCargaPorVehiculo.carga.listIterator();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Fleteros fl=new Fleteros();
        Procesos pra=new Procesos();
        ArrayList ls=new ArrayList();
        try{
            ls=pr.ListarFleteros();
        }catch(Exception ex){
            System.out.println("no se listaron");
        }
        jComboBox1 = new javax.swing.JComboBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        jTable1.setModel(hd);
        jTable1.setToolTipText("Vista previa y editable de la Hoja de Ruta");
        hd.addColumn("Cliente");
        hd.addColumn("Saldo");
        hd.addColumn("Comprobante");
        hd.addColumn("Observaciones");
        Object[] fila=new Object[4];
        while(ih.hasNext()){
            ped=(PedidosParaReparto)ih.next();
            fila[0]=ped.getRazonSocial();
            cli=ped.getCodigoCliente();
            cli.trim();

            fila[1]=(Double)ped.getSaldoCliente();
            String comprobantes=ped.getNumeroComprobante().trim();
            String parte=null;
            if(comprobantes.length()>13){
                parte=comprobantes.substring(0,9);
                comprobantes=comprobantes.replaceAll(parte,"/");
            }
            System.out.println("COMPROBANTES "+comprobantes+" PARTES "+parte+" cantidad"+comprobantes.length());
            fila[2]=comprobantes;
            fila[3]="";
            hd.addRow(fila);
            fechaE=ped.getFechaEnvio();
            numeroDeListadoDeMateriales=ped.getNumeroDeListadoDeMateriales();
        }
        this.jPanel4.setVisible(false);
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 399, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton1.setText("Imprimir Hoja De Ruta");
        jButton1.setToolTipText("Genera PDF de la Hoja de Ruta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton2.setText("Guardar Cambios");
        jButton2.setEnabled(false);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton3.setText("Agregar Item");
        jButton3.setToolTipText("Para cargar un nuevo Items en el detalle");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        jLabel1.setText("Seleccione el Fletero");

        Iterator il=ls.listIterator();
        while(il.hasNext()){
            fl=(Fleteros)il.next();
            jComboBox1.addItem(fl.getNombreFletero());
        }
        jComboBox1.setToolTipText("Listado de Fleteros habilitados ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("RAZON SOCIAL :");

        jTextField1.setToolTipText("destino del reparto");

        jLabel3.setText("EMPRESA");

        jLabel4.setText("COMPROBANTE");

        jLabel5.setText("MONTO");

        jLabel6.setText("OBSERVACIONES");

        jTextField2.setToolTipText("BU o SD");

        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/logo.png")));
        jButton4.setText("AGREGAR A HOJA DE RUTA");
        jButton4.setToolTipText("Agrega el renglón con el detalle ingresado en la vista de la HDR");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(480, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jButton4)
                .addContainerGap(107, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)))
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        EmisionHojaDeRuta hdr=new EmisionHojaDeRuta();
        PedidosParaReparto ped=new PedidosParaReparto();
        DecimalFormat fr=new DecimalFormat("####.##");
        int numFt=jComboBox1.getSelectedIndex();
        numFt++;
        hdr.setNumeroFletero(numFt);
        hdr.setNumeroVehiculo(unidad);
        String numeroComprobante=null;
        String importe=null;
        String vto=null;
        Double monto=0.00;
        Double vuelto=0.00;
        Double valor=0.00;
        Double val1=0.00;
        Double totalMonto=0.00;
        Double totalVuelto=0.00;
        int condVenta=0;
        String cliente=null;
        String clienteAnt=null;
        for(int i=0;i<jTable1.getRowCount();i++){
            System.err.println("cantidad array "+cargados.size());
            ped=(PedidosParaReparto)cargados.get(i);
            numeroComprobante=(String) jTable1.getValueAt(i,2);
            cliente=(String)jTable1.getValueAt(i,0);
            System.out.println("CLIENTE "+cliente);
            if(i>0){
            int anterior=i-1;
            clienteAnt=(String)jTable1.getValueAt(anterior,0);
            }
            monto=(Double) jTable1.getValueAt(i,1);
            //condVenta=ped.getCondicionDeVenta();
            if ((monto==null)||(monto==0.00)){
                //condVenta=ped.getCondicionDeVenta();
                if(ped.getCondicionDeVenta()==null){
                    condVenta=1;
                }else{
                    condVenta=ped.getCondicionDeVenta();
                }
                System.out.println("COND E VENTA "+condVenta);
                if(condVenta < 2){
                    importe="PS";
                    vto=null;
                }else{
                    importe="FIRMA";
                    vto="";
                }
            }else{
                if((ped.getCondicionDeVenta() ==2)||(ped.getCondicionDeVenta() > 2)){
                 importe="FIRMA";
                 vto="";
                }else{
                if(cliente.equals(clienteAnt)){
                    
                }else{
                Double valor2=0.00;
                    valor=Math.ceil(monto);
                totalMonto+=monto;
                System.err.println("VALOR ENTERO "+valor);
                //valor=valor / 10;
                val1=valor/10;
                valor2=Math.ceil(val1);
                valor2=valor2 * 10;
                //valor=Math.(val1);
                vuelto=valor2 - monto;
                totalVuelto+=vuelto;
                importe=fr.format(monto);
                //importe=monto.toString();
                vto=fr.format(vuelto);
                }
                }
            }
            //importe=(String) jTable1.getValueAt(i,1);
            ped.setNumeroDeFletero(numFt);
            ped.setNumeroComprobante(numeroComprobante);
            ped.setSaldoACobrar(importe);
            ped.setVuelto(vto);
            //ped.setSaldoCliente();
        }
        try {
            Iterator ic=cargados.listIterator();
            while(ic.hasNext()){
                ped=(PedidosParaReparto)ic.next();
                System.out.println("detalle cargados en vistaHdr "+ped.getNumeroComprobante()+" "+ped.getRazonSocial());
                
            }
            hdr.setTotalMonto(totalMonto);
            hdr.setTotalVuelto(totalVuelto);
            hdr.setListado(cargados);
            //hdr.setListado(ListadoDeCargaPorVehiculo.carga);
        } catch (SQLException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
            Logger.getLogger(VistaHdr.class.getName()).log(Level.SEVERE, null, ex);
        }
        hdr.start();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField5ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       this.jPanel1.setVisible(false);
       this.jPanel3.setVisible(false);
        this.jPanel4.setVisible(true);
        this.jTextField1.setText("");
        this.jTextField2.setText("");
        this.jTextField3.setText("");
        this.jTextField4.setText("");
        this.jTextField5.setText("");
        this.jTextField1.requestFocus();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       String razonSocial=jTextField1.getText();
       String empresa=jTextField2.getText();
       String comprobante=jTextField3.getText();
       String valor=jTextField4.getText();
       //String fechaE=null;
       String observaciones=jTextField5.getText();
       Double monto=Double.parseDouble(valor.trim());
       PedidosParaReparto ped=new PedidosParaReparto();
       ped.setRazonSocial(razonSocial);
       ped.setEmpresa(empresa);
       ped.setNumeroComprobante(comprobante);
       ped.setSaldoCliente(monto);
       if(monto > 0){
           ped.setCondicionDeVenta(1);
       }
       ped.setPesoTotal(0.00);
       if(fechaE==null){
           DecimalFormat fr=new DecimalFormat("00");
           Calendar c1=Calendar.getInstance();
	Calendar c2=new GregorianCalendar();
	String dia=Integer.toString(c2.get(Calendar.DAY_OF_MONTH));
	String mes=Integer.toString(c2.get(Calendar.MONTH));
	String ano=Integer.toString(c2.get(Calendar.YEAR));
	
        int da=Integer.parseInt(dia);
        int me=Integer.parseInt(mes);
        me++;
        dia=fr.format(da);
        mes=fr.format(me);
        fechaE=dia+"/"+mes+"/"+ano;
       }
       ped.setFechaEnvio(fechaE);
       ped.setVehiculoAsignado(unidad);
       ped.setNumeroDeListadoDeMateriales(numeroDeListadoDeMateriales);
       
       //ped.setObservaciones(observaciones);
       ListadoDeCargaPorVehiculo.carga.add(ped);
       cargados.add(ped);
       Iterator il=ListadoDeCargaPorVehiculo.carga.listIterator();
       MiTablaHd hd=new MiTablaHd();
        jTable1.setModel(hd);
       hd.addColumn("Cliente");
       hd.addColumn("Saldo");
       hd.addColumn("Comprobante");
       hd.addColumn("Observaciones");

       Object[] fila=new Object[4];
       while(il.hasNext()){
           ped=(PedidosParaReparto)il.next();
           fila[0]=ped.getRazonSocial();
//           cli=ped.getCodigoCliente();
//           cli.trim();
 
           fila[1]=(Double)ped.getSaldoCliente();
           fila[2]=ped.getNumeroComprobante();
           fila[3]="";
           hd.addRow(fila);

       }
/*
       fila[0]=razonSocial;
       fila[1]=monto;
       fila[2]=comprobante;
       fila[3]=observaciones;
       hd.addRow(fila);
*/
       this.jPanel1.setVisible(true);
       this.jPanel3.setVisible(true);
       this.jPanel4.setVisible(false);
    }//GEN-LAST:event_jButton4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
