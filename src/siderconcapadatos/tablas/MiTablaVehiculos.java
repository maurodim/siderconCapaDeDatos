/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siderconcapadatos.tablas;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrador
 */
public class MiTablaVehiculos extends DefaultTableModel {
    public Class getColumnClass(int columna){
        if(columna==0)return Boolean.class;
        if(columna==3)return Integer.class;
        //if(columna==2)return Double.class;
        return String.class;
    }
}
