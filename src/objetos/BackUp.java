/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import proceso.Coneccion;

/**
 *
 * @author hernan
 */
public class BackUp {
    private int BUFFER = 10485760;  
    //para guardar en memmoria
    private StringBuffer temp = null;
    //para guardar el archivo SQL
    private FileWriter  fichero = null;
    private PrintWriter pw = null;
    
 public boolean CrearBackup(String file_backup){
    boolean ok=false;
        //sentencia para crear el BackUp
        
        //datos de coneccion
         
        Connection cnn=Coneccion.ObtenerConeccion();
        String db="sidercon";
        String host="//192.168.0.111/";
        //String dumpMy="c:/xampp/mysql/bin/mysqldump --opt --host="+host+"--user="+Coneccion.getUsuario()+"--password="+Coneccion.getClave()+" "+db;
        //String dumpMy="Hola mundo 13-01-28";
        //System.out.println(dumpMy);
         //Process run = Runtime.getRuntime().exec(dumpMy);
        /*"mysqldump --host=" + host + " --port=3306 --user=" + con.getUsuario() + " --password=" + con.getClave() +
        " --compact --complete-insert --extended-insert --skip-quote-names" +
        " --skip-comments --skip-triggers " +db;
        */
        //se guarda en memoria el backup
         //InputStream input = run.getInputStream();
/*
FileOutputStream output = new FileOutputStream(file_backup);
transfer(input, output);
*/       

/*
        InputStream in = run.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        temp = new StringBuffer();
        int count;
        char[] cbuf = new char[BUFFER];
        while ((count = br.read(cbuf, 0, BUFFER)) != -1) {
            temp.append(cbuf, 0, count);
        }
        br.close();
        in.close(); 
        */ 
        /* se crea y escribe el archivo SQL */
/*
fichero = new FileWriter(file_backup);
        pw = new PrintWriter(fichero);                                                    
        pw.println(temp.toString()); 
        
        ok=true;
   }
    catch (Exception ex){
    } finally {
       try {           
         if (null != fichero) {
               fichero.close();
           }
       } catch (Exception e2) {
       }
    } 
 */  
    
    return true; 
 }  
 public void transfer(InputStream input, OutputStream output) throws Exception {
  byte[] buf = new byte[1024];
  int len;
  while ((len = input.read(buf)) > 0) {
    output.write(buf, 0, len);
  }
  input.close();
  output.close();
}
}
