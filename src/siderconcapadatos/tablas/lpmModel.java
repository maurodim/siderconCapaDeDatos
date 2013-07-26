/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siderconcapadatos.tablas;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author hernan
 */
public class lpmModel extends DefaultTableModel{
    public Class getColumnClass(int colum){
        if(colum==0){
            System.out.println("SE MARCO COMO VERDADERO");
            return Boolean.class;
        }
        if(colum==2)return Integer.class;
        return String.class;
    }    
}
