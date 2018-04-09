/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetos.Articulos;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 *
 * @author mauro
 */
public class ExListadoArticulos {
    
    public void GenerarInforme(ArrayList art){
        
              HSSFWorkbook libro=new HSSFWorkbook();
        HSSFSheet hoja=libro.createSheet("LISTADO");
        /*
         * GENERAR LAS SIGUIENTES HOJAS
         * 1- DETALLE DE MOVIMIENTOS DE CAJA - LEE EN MOVIMIENTOS CAJA INDENTIFICANDO EL TIPO DE MOVIMIENTO, USUARIOS Y 
         * NUMERO DE CAJA
         * 2- DETALLE DE ARTICULOS VENDIDOS: LISTADO DE MOVIEMIENTOS DE ARTICULOS, CON USUARIOS Y CAJA
         * 3- DETALLE DE GASTOS : MOVIMIENTOS DE CAJA DETALLANDO LOS GASTOS
         * 
         */
        String codigo="";
        String ttx="celda numero :";
        HSSFRow fila=null;
        HSSFCell celda;
        HSSFCell celda1;
        HSSFCell celda2;
        HSSFCell celda3;
        HSSFCell celda4;
        HSSFFont fuente=libro.createFont();
        //fuente.setFontHeight((short)21);
        fuente.setFontName(fuente.FONT_ARIAL);
        fuente.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        String form=null;
//        String sql="select *,(select movimientoscaja.monto from movimientoscaja where movimientoscaja.tipoMovimiento=10 and movimientoscaja.idCaja=informemensualdecaja.numero and movimientoscaja.monto < 0 limit 0,1)as cierre from informemensualdecaja where fecha between '"+desde+"' and '"+hasta+"'";
//        System.out.println(sql);
//        Transaccionable tra=new Conecciones();
//        ResultSet rs=tra.leerConjuntoDeRegistros(sql);
        HSSFCellStyle titulo=libro.createCellStyle();
        titulo.setFont(fuente);
        //titulo.setFillBackgroundColor((short)22);
        titulo.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        titulo.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        //for(int a=0;a < 100;a++){
        int col=0;
        int a=0;
            if(a==0){
                fila=hoja.createRow(a);
            celda=fila.createCell(0);
            celda.setCellStyle(titulo);
            celda.setCellValue("CODIGO ARTIULO");
            celda1=fila.createCell(1);
            celda1.setCellStyle(titulo);
            celda1.setCellValue("DESCRIPCION ARTICULO");
            celda2=fila.createCell(2);
            celda2.setCellStyle(titulo);
            celda2.setCellValue("SINONIMO");
            celda3=fila.createCell(3);
            celda3.setCellStyle(titulo);
            celda3.setCellValue("PESO CARGADO");
            celda4=fila.createCell(4);
            celda4.setCellStyle(titulo);
            celda4.setCellValue("UN. MED");
            celda4=fila.createCell(5);
            celda4.setCellStyle(titulo);
            celda4.setCellValue("ES. SUP.");
            }
            Iterator rs=art.listIterator();
            Articulos articulo=null;
            while(rs.hasNext()){
                articulo=(Articulos)rs.next();
            a++;
            //col=rs.getInt("tipoMovimiento");
            switch(col){
                case 1:
                    
                    break;
                default:
                    
                    break;
            }
            fila=hoja.createRow(a);
            celda=fila.createCell(0);
            ttx=ttx;
            celda.setCellType(HSSFCell.CELL_TYPE_STRING);
            codigo=articulo.getCodigo().trim();
            if(codigo.length()==8){
                celda.setCellValue(" 0"+codigo);
            }else{
                celda.setCellValue(" "+codigo);
            }
            celda1=fila.createCell(1);
            ttx=ttx;
            celda1.setCellType(HSSFCell.CELL_TYPE_STRING);
            celda1.setCellValue(articulo.getDescripcionArticulo());
            celda2=fila.createCell(2);
            celda2.setCellType(HSSFCell.CELL_TYPE_STRING);
            celda2.setCellValue(articulo.getSinonimoArticulo());
            celda3=fila.createCell(3);
            celda3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            celda3.setCellValue(articulo.getPesoUnitario());
            celda4=fila.createCell(4);
            celda4.setCellType(HSSFCell.CELL_TYPE_STRING);
            celda4.setCellValue(articulo.getUnidadDeMedida());
            celda4=fila.createCell(5);
            celda4.setCellType(HSSFCell.CELL_TYPE_STRING);
            celda4.setCellValue(articulo.getEstructura());
        }
            
            /*
             * HOJA 1
             */
 
                //texto+="\r\n";
        String ruta="C://src//informes//listadoArticulos.xls";
        try {
            FileOutputStream elFichero=new FileOutputStream(ruta);
            try {
                libro.write(elFichero);
                elFichero.close();
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+ruta);
            } catch (IOException ex) {
                Logger.getLogger(ExListadoArticulos.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ExListadoArticulos.class.getName()).log(Level.SEVERE, null, ex);
        }
 
    }
}
