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
public class MiTablaDeCarga extends DefaultTableModel  {
    public Class getColumnClass(int colum){
        if(colum==4)return Boolean.class;
        if(colum==3)return Integer.class;
        return String.class;
    }
  
}
