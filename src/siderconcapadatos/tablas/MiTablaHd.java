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
public class MiTablaHd extends DefaultTableModel {
        public Class getColumnClass(int colum){
        if(colum==1)return Double.class;
        return String.class;
    }
}
