/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siderconcapadatos.tablas;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Administrador
 */
public class MiModeloTabla extends DefaultTableModel {
    public Class getColumnClass(int colum){
        if(colum==4){
            System.out.println("SE MARCO COMO VERDADERO");
            return Boolean.class;
        }else{
            System.out.println("se marco como falso");
        }
        if(colum==3)return Integer.class;
        if(colum==2)return Double.class;
        //if(colum==5)return Integer.class;
        return String.class;
    }
      public boolean isCellEditable (int row, int column)
   {
       // Aquí devolvemos true o false según queramos que una celda
       // identificada por fila,columna (row,column), sea o no editable
       if (column == 0)return false;
       if(column==1)return false;
       if(column==2)return false;
       return true;
   }

    public void AgregarFila(Object[] renglon){
        this.addRow(renglon);
    }



}
