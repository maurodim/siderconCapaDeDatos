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
public class MiTablaUnidades extends DefaultTableModel {
    public Class getColumnClass(int columna){
        //if(columna==2)return Double.class;
        if(columna==3)return Integer.class;
        if(columna==4)return Double.class;
        if(columna==0)return Integer.class;
        return String.class;
    } 
}
