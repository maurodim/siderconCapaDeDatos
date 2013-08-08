/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author mauro di
 */
public interface Conectable {
    /*
     * ACA SE PASA LA SENTENCIA SQL COMO PARAMETRO Y SE DEVUELVE UN ARRAY DE OBJETOS
     * VOY A TENER QUE MODIFICAR PARA QUE LA LECTURA SEA UN ITERATOR....
     * TENER EN CUENTA QUE VOY A TENER QUE GENERAR EN LA INTERFAZ Y EN LA CLASE TANTAS FUNCIONES COMO SEAN NECESARIAS PARA LAS DISTINTAS LECTURAS
     * YA SEAN PARCIALES O TOTALES DE LOS MISMOS
     * 
     * también se podría ver como alternativa el pasar determinados parametros( nombre tabla, campo a modificar, valor a asignar) para lo que son las distintas 
     * sentencias y en base a esto generar las funciones, mas restringidas en texto pero con un alcance mas general y reutilizable
     * Para poder implementar esto deberia trabajar con constantes con el fin de poder generar una relacion objeto-tabla-constante y modificar los distintos 
     * aspectos
     * 
     * EN PRINCIPIO DEBERÍA PROBAR CON EL PASO DE SENTENCIA Y LA DEVOLUCION DE (RESULTSET EN PRINCIPIO O BIEN DE UN ARRAYLIST)
     * CON EL ARRAYLIST LA COMPLICACION VA A SER LOS DISTINTOS OBJETOS QUE SE VAN GENERANDO, PARA PODER LEER EL ARRAYLIST
     * O IDEAL SERIA EL RESULTSET PERO NO SE SI ME ADMITIRA EL MISMO SIN CONEXION( LA IDEA ES MINIMIZAR LAS CONECCIONES AL SERVIDOR Y PODER ADMINISTRARLAS 
     * DESDE UNA ÚNICA CLASE, CON EL FIN DE PODER CONTROLAR ESE ACCESO)
     * LA ULTIMA ALTERNATIVA SERÍA, MIGRAR LA CLASE PROCESOS Y ADAPTARLA PARA QUE SEA UN PUENTE DE RESPUESTA A LAS DISTINTAS LLAMADAS
     * EN PRINCIPIO LA ULTIMA ME PARECE LA OPCION MAS VIABLE COMO ADAPTACION MOMENTANEA
     * 
     * 
     *
     */
    public ArrayList leerPedidosMy(String sentencia);
    /*
     * ACA PASO LA SENTENCIA PARA QUE SE DISTINGA (UTILIZANDO LA MISMA INTERFAZ) SI ES DE TODOS LOS PEDIDOS/PENDIENTES/DETALLADO/POR FECHA/POR CLIENTE
     */
    public Boolean actualizarPedidosMy(Object pedido);
    /*
     * ACA SE REALIZA UN UPDATE DE LA TABLA PEDIDOS 
     * 
     * 
     */
    
    
    
}
