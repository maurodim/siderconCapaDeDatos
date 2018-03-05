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
public class MiModeloTablaCargaHdr extends DefaultTableModel{
      public Class getColumnClass(int colum){
        
          if(colum==4)return Boolean.class;
          if(colum==6)return Boolean.class;
        return String.class;
      }
      }
