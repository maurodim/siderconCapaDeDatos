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
public class lpmModel extends DefaultTableModel {
    public Class getColumnClass(int colum){
        if(colum==4){
            System.out.println("SE MARCO COMO VERDADERO");
            return Boolean.class;
        }else{
            System.out.println("se marco como falso");
        }
        if(colum==3)return Integer.class;
        if(colum==2)return Double.class;
        return String.class;
    }    
}
