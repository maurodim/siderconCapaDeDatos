/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Excel;

import Conversores.Numeros;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.Articulos;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import proceso.Procesos;

/**
 *
 * @author mauro di
 */
public class LeerExcel {
   public void leerExcel1(String fileName) throws SQLException {
       List cellDataList = new ArrayList();
try
{
/**
* Create a new instance for FileInputStream class
*/
FileInputStream fileInputStream = new FileInputStream(fileName);
/**
* Create a new instance for POIFSFileSystem class
*/
POIFSFileSystem fsFileSystem = new POIFSFileSystem(fileInputStream);
/*
* Create a new instance for HSSFWorkBook Class
*/
HSSFWorkbook workBook = new HSSFWorkbook(fsFileSystem);
HSSFSheet hssfSheet = workBook.getSheetAt(0);
/**
* Iterate the rows and cells of the spreadsheet
* to get all the datas.
*/
Iterator rowIterator = hssfSheet.rowIterator();
while (rowIterator.hasNext())
{
HSSFRow hssfRow = (HSSFRow) rowIterator.next();
Iterator iterator = hssfRow.cellIterator();
List cellTempList = new ArrayList();
while (iterator.hasNext())
{
HSSFCell hssfCell = (HSSFCell) iterator.next();
cellTempList.add(hssfCell);
}
cellDataList.add(cellTempList);
}
}
catch (Exception e)
{
e.printStackTrace();
}
/**
* Call the printToConsole method to print the cell data in the
* console.
*/
printToConsole(cellDataList);
}
/**
* This method is used to print the cell data to the console.
* @param cellDataList - List of the data's in the spreadsheet.
*/
private void printToConsole(List cellDataList)
{
    String error=""; 
    int fila=0;
    try {
           Articulos articulo;
           Procesos proceso=new Procesos();
           HashMap listadoArticulos=new HashMap();
           listadoArticulos=(HashMap) proceso.cargarArticulos();
           Boolean verif=false;
           ArrayList lstArt=new ArrayList();
           String unidadDeMedida="";
           String estructura="";
           Double peso=0.00;
           
           System.out.println("cantidad de celdas "+cellDataList.size()+" cantidad map "+listadoArticulos.size());
           for (int i = 0; i < cellDataList.size(); i++)
           {
               List cellTempList = (List) cellDataList.get(i);
               articulo=new Articulos();
               String descrip="";
               for (int j = 0; j < cellTempList.size(); j++)
               {
                   HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
                   String stringCellValue = hssfCell.toString();
                   descrip="";
                    if(i > 0){
                   switch (j){
                       case 0:
                           stringCellValue=stringCellValue.trim();
                           descrip=stringCellValue.trim();
                           if(stringCellValue.length()==8)stringCellValue="0"+stringCellValue;
                           articulo.setCodigo(stringCellValue);
                           fila=i + 1;
                           System.out.println("codigo ingresado o leido "+articulo.getCodigo()+" fila "+fila);
                          error="codigo ingresado o leido "+articulo.getCodigo()+" fila "+fila+" desc "+descrip;
                           
                          if(listadoArticulos.containsValue(descrip)){
                               articulo=(Articulos)listadoArticulos.get(descrip);
                               System.out.println("codigo leido "+articulo.getCodigo()+" desc "+articulo.getDescripcionArticulo());
                               verif=true;
                               articulo.setEstado(0);
                           }else{
                               articulo=new Articulos();
                               articulo.setCodigo(stringCellValue);
                               System.out.println(" codigo "+articulo.getCodigo());
                               verif=false;
                               articulo.setEstado(3);
                           }
                           
                           break;
                       case 1:
                           if(stringCellValue.isEmpty()){
                               stringCellValue=" ";
                           }
                           articulo.setDescripcionArticulo(stringCellValue);
                           System.out.println(stringCellValue+" - 1");
                           break;
                       case 2:
                           if(stringCellValue.isEmpty()){
                               stringCellValue=" ";
                           }
                           articulo.setSinonimoArticulo(stringCellValue);
                           System.out.println(stringCellValue+" - 2");
                           break;
                       case 3:
                           peso=Numeros.ConvertirStringADouble(stringCellValue);
                           articulo.setPesoUnitario(peso);
                           System.out.println(stringCellValue+" - 3");
                           break;
                       case 4:
                           System.out.println("el tamaño del campo es "+stringCellValue.length());
                           System.out.println(stringCellValue+" - 4");
                           if(stringCellValue.length()== 0){
                               articulo.setEstado(3);
                               unidadDeMedida="UND";
                           }else{
                           unidadDeMedida=stringCellValue;
                           }
                           articulo.setUnidadDeMedida(unidadDeMedida);
                           break;
                       case 5:
                           System.out.println("el tamaño del campo es "+stringCellValue.length());
                           System.out.println(stringCellValue+" - 5");
                           
                                estructura=stringCellValue;
                                int si=0;
                          if(estructura.equals("1.0")|| estructura.equals("1"))si=1;
                           articulo.setEstructura(si);
                           break;
                   }
                   
                   System.out.print(stringCellValue +" "+ j+" fila "+i+ "\t");
                   
               }
               }
               if(i > 0){
               if(verif){
                       lstArt.add(articulo);
                       proceso.ModificacionDeArticulos(lstArt);
                   }else{
                       System.out.println("la unidad de medida es "+unidadDeMedida);
                       proceso.GuardarNuevoArticulo(articulo, unidadDeMedida);
                   }
               }
                   verif=false;
                   lstArt.clear();
                   
               System.out.println();
           }
          JOptionPane.showMessageDialog(null,"PROCESO EXITOSO \n CANTIDAD DE FILAS PROCESADAS "+fila); 
    } catch (SQLException ex) {
                System.out.println(" error en "+error);
                JOptionPane.showMessageDialog(null,"ERROR GENERADO EN FILA "+fila);
               Logger.getLogger(LeerExcel.class.getName()).log(Level.SEVERE, null, ex);
           
       }
   }
}
