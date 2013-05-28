/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package actualizaciones;

import actualizaciones.objetosActualizables.ClientesAct;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.ConversionesFecha;
import proceso.Coneccion;

/**
 *
 * @author Administrador
 */
public class ActClientesMysql extends Thread{
    private ArrayList clientes;
    ArrayList resumen=new ArrayList();

    public void setClientes(ArrayList clientes) {
        this.clientes = clientes;
        
        /*
        Iterator ic=clientes.listIterator();
        ClientesAct cli=new ClientesAct();
        ClientesAct nuevCli=null;
        String nombre=null;
        String nombAnt=null;
        String codigo=null;
        String codigoAnt=null;
        Date actualizacion=null;
        Double total=0.00;
        System.err.println("cantidad clientes "+this.clientes.size());
        while(ic.hasNext()){
            cli=(ClientesAct)ic.next();
            nombre=cli.getRazonSocial();
            codigo=cli.getCodigo();
            //if(resumen.isEmpty()){
             //   codigoAnt=codigo;
              //  nombAnt=nombre;
            //}
            if((nombre.equals(nombAnt))&&(codigo.equals(codigoAnt))){
                total+=cli.getImporte();
                actualizacion=cli.getFechaExportacion();
                codigoAnt=cli.getCodigo();
                nombAnt=cli.getRazonSocial();
                //nuevCli.setImporte(total);
            }else{
                //total=0.00;
                codigoAnt=cli.getCodigo();
                nombAnt=cli.getRazonSocial();
                nuevCli=new ClientesAct();
                nuevCli.setCodigo(codigoAnt);
                nuevCli.setRazonSocial(nombAnt);
                nuevCli.setImporte(total);
                //total=cli.getImporte();
                if(actualizacion==null){
                    actualizacion=cli.getFechaExportacion();
                }
                nuevCli.setFechaExportacion(actualizacion);
                System.err.println("listado de saldos "+nuevCli.getRazonSocial()+"fecha "+nuevCli.getFechaExportacion()+" saldo "+nuevCli.getImporte());
                resumen.add(nuevCli);
 
                total=0.00;
         
         
            }
        }
         
         */
    }
    public synchronized void run(){
        try {
            ClientesAct cli=new ClientesAct();
            Coneccion con=new Coneccion();
            Connection cp=null;
            String sql=null;
            cp=con.ObtenerConeccion();
            Statement st=cp.createStatement();
            if(this.clientes.size() > 100){
                sql="truncate table tabla1";
                st.execute(sql);
            }
            Iterator ic=this.clientes.listIterator();
            Integer cantidad=this.clientes.size();
            String fecha=null;
            Date conv=null;
            while(ic.hasNext()){
                cli=(ClientesAct)ic.next();
            SimpleDateFormat fd=new SimpleDateFormat("dd-MM-yyyy");
            //DecimalFormat df=new DecimalFormat("00");
            //DecimalFormat da=new DecimalFormat("0000");
            //String fec=null;
            //String dia=null;
            //String mes=null;
            //String ano=null;
            ConversionesFecha conv1=new ConversionesFecha();
            fecha=String.valueOf(cli.getFecha());
                conv1.setFechaOriginal(fecha);
                //conv1.setNumeroDeCampo();
                System.out.println(fecha.length()+"cantidad fecha");
                
                /*
                dia=fecha.substring(0,1);
                System.out.println(dia);
                //dia=df.format(dia);
                mes=fecha.substring(3,4);
                ano=fecha.substring(6,7);
                fecha=ano+"-"+mes+"-"+dia;
                */
                  fecha=String.valueOf(cli.getFechaExportacion());
                  System.out.println("lectura de fecha "+cli.getFechaExportacion());
                  if(cli.getFechaExportacion()==null){
                      fecha="2010-01-01";
                  }
                System.out.println("fecha Clientes "+fecha);
                try {
                    //Date fec1=fd.format(fec);
                  conv=fd.parse(fecha);
                  //conv1.setFechaConvertida();
                } catch (ParseException ex) {
                    Logger.getLogger(ActClientesMysql.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(fecha.length()+"cantidad fecha");
                }

            //fecha=ano+"-"+mes+"-"+dia;
                //fecha=String.valueOf(cli.getFecha());
                sql="insert into tabla1 (COD_CLI,RAZON_SOC,N_COMP,IMPORTE,actualizacion) values('"+cli.getCodigo()+"','"+cli.getRazonSocial()+"','"+cli.getNumComprobante()+"',"+cli.getImporte()+",'"+cli.getFechaExportacion()+"')";
                st.executeUpdate(sql);
                System.err.println(cantidad + " carga de saldos "+sql);

            }
            st.close();
            con.CerrarConneccion(cp);
            ActClientesAcc acCli=new ActClientesAcc();
            acCli.setClientes(clientes);
            acCli.start();
        } catch (SQLException ex) {
            Logger.getLogger(ActClientesMysql.class.getName()).log(Level.SEVERE, null, ex);
        //System.out.println(fecha.length()+"cantidad fecha");
        }

    }
}
