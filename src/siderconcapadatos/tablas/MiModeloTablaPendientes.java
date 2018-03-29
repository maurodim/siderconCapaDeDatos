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
public class MiModeloTablaPendientes extends DefaultTableModel {
    public Class getColumnClass(int colum){
        if(colum==3)return Double.class;
        if(colum==4)return Double.class;
        if(colum==5)return Double.class;
        if(colum==7)return Boolean.class;
        return String.class;
    }
      public boolean isCellEditable (int row, int column)
   {
       // Aquí devolvemos true o false según queramos que una celda
       // identificada por fila,columna (row,column), sea o no editable
       if (column == 0)return false;
       if(column==1)return false;
       if(column==2)return false;
       if(column==4)return false;
       return true;
   }

}
