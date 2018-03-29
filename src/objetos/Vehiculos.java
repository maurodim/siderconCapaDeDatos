/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import interfaces.Cargable;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author MAURO DI
 */
public class Vehiculos implements Cargable{
        private static ArrayList ListadoVehiculos=new ArrayList();
	private Integer numero;
	private String descripcion;
	private Double capacidadDeCarga;
        private Integer kilometrosInicio;
        private Integer kilometrosFinales;
        private Integer kilometrosRecorridos;
        private Double kilosCargados;
        private String patente;
        private Integer kilometrosActuales;
        private String estadoVehiculo;
        private Integer numeroHdr;
        private ArrayList listadoDePedidos;
        private int condicion;//CONDICION CORRESPONDE A UNA BANDERA PARA SABER SI SE ELIMINA O NO
        static private Connection con;

    public Vehiculos() {
       
    }

    public ArrayList getListadoDePedidos() {
        return listadoDePedidos;
    }

    public void setListadoDePedidos(ArrayList listadoDePedidos) {
        this.listadoDePedidos = listadoDePedidos;
    }

    public Integer getNumeroHdr() {
        return numeroHdr;
    }

    public void setNumeroHdr(Integer numeroHdr) {
        this.numeroHdr = numeroHdr;
    }

    public int getCondicion() {
        return condicion;
    }

    public void setCondicion(int condicion) {
        this.condicion = condicion;
    }

        
    public String getEstadoVehiculo() {
        return estadoVehiculo;
    }

    public void setEstadoVehiculo(int estadoVehiculo) {
        String estado="";
        switch (estadoVehiculo){
            case 1:
                estado="EN PROCESO DE CARGA";
                        break;
            case 2:
                estado="CON CARGA COMPLETA";
                break;
            case 3:
                estado="EN REPARTO";
                break;
            case 4:
                estado="CON RECORRIDO FINALIZADO";
                break;
        }
        this.estadoVehiculo = estado;
    }
  
    public Integer getKilometrosActuales() {
        return kilometrosActuales;
    }

    public void setKilometrosActuales(Integer kilometrosActuales) {
        this.kilometrosActuales = kilometrosActuales;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }
        

    public Integer getKilometrosFinales() {
        return kilometrosFinales;
    }

    public void setKilometrosFinales(Integer kilometrosFinales) {
        this.kilometrosFinales = kilometrosFinales;
    }

    public Integer getKilometrosInicio() {
        return kilometrosInicio;
    }

    public void setKilometrosInicio(Integer kilometrosInicio) {
        this.kilometrosInicio = kilometrosInicio;
    }

    public Integer getKilometrosRecorridos() {
        return kilometrosRecorridos;
    }

    public void setKilometrosRecorridos(Integer kilometrosRecorridos) {
        this.kilometrosRecorridos = kilometrosRecorridos;
    }

    public Double getKilosCargados() {
        return kilosCargados;
    }

    public void setKilosCargados(Double kilosCargados) {
        this.kilosCargados = kilosCargados;
    }
        

	public Double getCapacidadDeCarga() {
		return capacidadDeCarga;
	}

	public void setCapacidadDeCarga(Double capacidadDeCarga) {
		this.capacidadDeCarga = capacidadDeCarga;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public void modificarCapacidadDeCarga(Double peso){
            this.capacidadDeCarga=this.capacidadDeCarga-peso;
        }
        public void modificarKilosCargados(Double peso){
            this.kilosCargados+=peso;
            if((this.kilosCargados == this.capacidadDeCarga)||(this.kilosCargados > this.capacidadDeCarga)){
                //JOptionPane.showMessageDialog(null,"SALDO DEL CLIENTE : $"+resultado+" cliente numero :"+ped.getCodigoCliente(),"SALDO DEL CLIENTE ",JOptionPane.PLAIN_MESSAGE);
                this.setEstadoVehiculo(2);
                this.enviarMensajeDeEstadoDelVehiculo();
            }
            
        }
        public void modificarKilometrosActual(Integer km){
            this.kilometrosActuales=km;
        }
        public void cargarKilometrosIniciales(Integer km){
            this.kilometrosInicio=km;
        }
        public void cargarKilometrosFinales(Integer km1){
            this.kilometrosFinales=km1;
        }
        public void calcularKilometrosRecorridos(){
            this.kilometrosRecorridos=this.kilometrosFinales - this.kilometrosInicio;
            this.kilometrosActuales+=this.kilometrosRecorridos;
        }
        private void enviarMensajeDeEstadoDelVehiculo(){
            
        }

    @Override
    public Boolean agregarPedido(Object pedido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList leerPedidosCargados(String fecha,int numeroUnidad) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void listarPedidosCargados() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean quitarPedidoCargado() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int pedidosParaNuevaRevision(Object pedido) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Boolean actualizarEstadoDePedidos() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
        
        
}
