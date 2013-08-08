/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package seguimientos;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import siderconcapadatos.SiderconCapaatos;

/**
 *
 * @author mauro di
 */
public class Archivador implements GuardarMovimientos{

    @Override
    public void registrarMovimiento(String texto, String nombre, String fecha) {
        String ttx="";
        String ruta="C://bases//STHDR//MvHdr//"+fecha+"_"+nombre;
        try {
            FileWriter salida=new FileWriter(ruta);
            salida.write(texto);
            salida.close();
        } catch (IOException ex) {
            Logger.getLogger(Archivador.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void registrarSentencias(String texto, String nombre, String fecha) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void registrarErrores(String texto, String nombre, String fecha) {
        String fech=SiderconCapaatos.fecha; 
        HSSFWorkbook libro=new HSSFWorkbook();
        HSSFSheet hoja=libro.createSheet();
        String ttx="celda numero :";
        HSSFRow fila=null;
        HSSFCell celda5=null;
        String form=null;
        for(int a=0;a < 100;a++){
            fila=hoja.createRow(a);
            HSSFCell celda=fila.createCell(1);
            ttx=ttx;
            celda.setCellValue(ttx);
            HSSFCell celda2=fila.createCell(2);
            celda2.setCellValue(true);
            HSSFCell celda3=fila.createCell(3);
            celda3.setCellValue(a);
            HSSFCell celda4=fila.createCell(4);
            int b=a+1;
            form="D"+b+"+E"+b;
            System.out.println("FORMULA "+form);
            celda4.setCellValue(25.24);
            
           
            celda5=fila.createCell(5);
            celda5.setCellFormula(form);
            
            
        }
 
        
        texto+="\r\n";
        String ruta="C://bases//STHDR//RegErr//registros de errores.xls";
        try {
            FileOutputStream elFichero=new FileOutputStream(ruta);
            try {
                libro.write(elFichero);
                elFichero.close();
            } catch (IOException ex) {
                Logger.getLogger(Archivador.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Archivador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
