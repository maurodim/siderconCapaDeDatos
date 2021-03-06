/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siderconcapadatos;

import actualizaciones.Actualizar;
import actualizaciones.ActualizarHdr;
import actualizaciones.ImportarPedidos;
import actualizaciones.objetosActualizables.EncabezadoHdr;
import actualizaciones.rastrearIdTango;
import config.Configuracion;
import config.Formularios;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import objetos.Articulos;
import objetos.Clientes;
import objetos.PedidosParaReparto;
import objetos.log;
import org.xml.sax.SAXException;
import proceso.Coneccion;
import proceso.ConeccionSqlTango;
import proceso.PesosPedidos;
import proceso.ProcesoDeConversionDeFechasDeEntrega;
import proceso.Procesos;
import seguimientos.Archivador;
import seguimientos.GuardarMovimientos;


/**
 *
 * @author USUARIO
 */
public class SiderconCapaatos {
    //private static Set s;
    public static ArrayList saldoCliente=new ArrayList();
    public static ArrayList listaPedidos=new ArrayList();
    public static ArrayList listadoDeConfiguraciones=new ArrayList();
    public static String fecha=null;
    public static Connection sqlBu=null;
    public static Connection sqlSd=null;
    public static Connection sqlSdSrl=null;
    public static String formularioConsolidado=null;
    public static String formularioDetallado=null;
    public static String formularioDescarga=null;
    public static String formularioHdr=null;
    public static int falloConecion;
    public static String formularioConsolidadoR=null;
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws SQLException, InterruptedException, ClassNotFoundException {
            //System.out.println(System.getProperty("java.classpath"));
            String codigo;
            String clave;
            falloConecion=0;
            Double valor = null;
            String cli;
            Double Saldo=0.00;
            Integer codigoNum;
            Double pesoItems = 0.00;
            ArrayList retorno=new ArrayList();	
            Actualizar act=new ImportarPedidos();
            //act.listarPedidos();
            ArrayList listadoHdr=null;
            ActualizarHdr hd=new EncabezadoHdr();
            Procesos pr=new Procesos();
            //listadoHdr=hd.leerEncabezados();
            //hd.copiarEncabezados(listadoHdr);
            //retorno.clear();
            
            log l=new log();
             Configuracion conf=new Configuracion();
             Formularios form=new Formularios();
            formularioConsolidado=form.getConsolidado();
             formularioDetallado=form.getDetallado();
             formularioDescarga=form.getDescarga();
             formularioHdr=form.getHdr();
             formularioConsolidadoR=form.getConsolidadoR();
             File archivo = new File ("Registro");
             if(!archivo.isDirectory())archivo.mkdirs();
        try {
            listadoDeConfiguraciones=conf.leerConfiguraciones();
        } catch (MalformedURLException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(SiderconCapaatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(SiderconCapaatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParserConfigurationException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(SiderconCapaatos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(SiderconCapaatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
             ProcesoDeConversionDeFechasDeEntrega pcfe=new ProcesoDeConversionDeFechasDeEntrega();
        pcfe.start();
        Integer numerador = 0; 
        PedidosParaReparto pedidos=new PedidosParaReparto();
        PesosPedidos pes=new PesosPedidos();
        pes.start();
       
        Map<String,Double> ar=new HashMap<String,Double>();
        Map<String,Double> detalle=new HashMap<String,Double>();
        /*
        ActOt at1=new ActOt();
        at1.start();

        Timer timer=new Timer(150000,new ActionListener(){ 
            @Override
    public void actionPerformed(ActionEvent e) 
    { 
        
        System.err.println("COMIENZO DEL CICLO DE RELOJ *******************************");
        ActOt at=new ActOt();
        at.start();
     } 
}); 
        timer.start();
        */
        ConeccionSqlTango cSql=new ConeccionSqlTango(1);
        
        try{
        sqlBu=(Connection) ConeccionSqlTango.ObtenerConeccion(1);
        sqlSd=(Connection) ConeccionSqlTango.ObtenerConeccion(2);
        sqlSdSrl=(Connection) ConeccionSqlTango.ObtenerConeccion(3);
        }catch(Exception ex){
            falloConecion=1;
            sqlBu=null;
            sqlSd=null;
            sqlSdSrl=null;
            JOptionPane.showMessageDialog(null,"CONEXION A SERVERTANGO NO ESTABLECIDA \n REVISE LAS MISMAS","CONEXION A SERVERTANGO ",JOptionPane.ERROR_MESSAGE);
        }
        
        
        
        Coneccion cn=new Coneccion(); 
        rastrearIdTango ras=new rastrearIdTango();
        
        ras.extraerIdTango("BU");
        ras.extraerIdTango("SD");
        ras.extraerIdTango("SRL");
        //RealizarBk rBk=new RealizarBk();
        
        //rBk.start();
        
        
        //ActualizacionPesos actPes=new ActualizacionPesos();
        //actPes.start();
             
        Articulos art=new Articulos(); 
        Clientes cl=new Clientes();
        art.cargarListado(); 
        Procesos pro=new Procesos();
        if(falloConecion==0){
            //esta bloqueado para pruebas
        saldoCliente=pro.cargarSaldosDeClientes();    
        }else{
        
        }
        ar=pro.cargarPesosDeArticulos();
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
        fecha=dia+"/"+mes+"/"+ano;
	System.err.println(fecha);
        //fecha="23/12/2011";
        String fh=ano+"-"+mes+"-"+dia;
        SimpleDateFormat ff=new SimpleDateFormat("yyyy-mm-dd");
        Date fechaVal = null;
        try {
            fechaVal = ff.parse(fh);
        } catch (ParseException ex) {
            GuardarMovimientos gArch=new Archivador();
                String cod1=String.valueOf(ex);
                gArch.registrarErrores(cod1, "", "");
                Logger.getLogger(SiderconCapaatos.class.getName()).log(Level.SEVERE, null, ex);
        }
        l.validar(fechaVal);
        listaPedidos=pro.ListarPedidosPorFecha(fecha);
        Iterator it=listaPedidos.listIterator();
                while(it.hasNext()){
                    Double totalPeso=0.00;
                    Double cantidadItems=0.00;
                    Saldo=0.00;
                    pedidos=(PedidosParaReparto) it.next();
                    numerador++;
                    detalle=pro.DetallePedido(pedidos.getCodigoTangoDePedido());
                    Iterator a=detalle.entrySet().iterator();
                    while(a.hasNext()){
                        Map.Entry e=(Map.Entry)a.next();
                        codigo=(String) e.getKey();
                        String trim = codigo.trim(); 
                        pesoItems=ar.get(codigo);
                        cantidadItems=(Double) e.getValue();
                           //System.out.println("codigo "+codigo+" valor"+pesoItems+" "+totalPeso);
                           if(pesoItems==null){
                               pesoItems=0.00;
                           }
                        totalPeso=totalPeso + (cantidadItems * pesoItems);
                        
                       
                    }
                    
                    cli=pedidos.getCodigoCliente();
                String trim = cli.trim();
                    
                    //System.out.println(cli+" "+cli.length());
                    Clientes clie=new Clientes();
                    /*
                    Iterator cil=saldoCliente.listIterator();
                    while(cil.hasNext()){
                        clie=(Clientes)cil.next();
                     
                     * //if(clie.getCodigoCliente().equals(cli)||clie.getRazonSocial().equals(pedidos.) )
                    Saldo=(Double)clie.getSaldo();
                    }
                    */ 
                     //Saldo=clie.getSaldo();
                     //Saldo=cl.getSaldo();
                     
 
                    //pesoItems=ar.get(codigo);
                    

                    pedidos.setPesoItems(pesoItems);
                    pedidos.setPesoTotal(totalPeso);
                    //pedidos.setSaldoCliente(Saldo);
                    //pedidos.setFechaActualizacionSaldoCliente(clie.getFechaActualizacion());
                    art.setPesoUnitario(pedidos.getPesoItems());
                    art.setPesoUnitario((Double)ar.get(pedidos.getCodigoArticulo()));
                    //System.out.println("El peso es :"+pesoItems+" "+codigo);
                    //System.out.println(numerador+" pedido numero "+pedidos.getCodigoTangoDePedido()+" razon social "+ pedidos.getRazonSocial()+" saldo :"+pedidos.getSaldoCliente()+" peso del items "+pedidos.getPesoTotal());

                }
                InicioSiderconHdr inicio=new InicioSiderconHdr();
                //JFrame InicioSiderconHdr;
                inicio.setExtendedState(JFrame.MAXIMIZED_BOTH);
                inicio.setTitle("ADMINISTRADOR HOJA DE RUTA");
                
                //inicio.setIconImage(null);
                inicio.setIconImage (new ImageIcon("iconos/logo.png").getImage());
                //inicio.setIconImage(ImageIO.read(getClass().getResource("/iconos/logo.png")));
                inicio.setVisible(true);
               //inicio.setIconImage (new ImageIcon("iconos/enc.JPG").getImage());
                //inicio.setVisible(true);
                    //inicio.pack();
        }
}
