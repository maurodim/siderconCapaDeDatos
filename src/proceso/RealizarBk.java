/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import objetos.BackUp;

/**
 *
 * @author hernan
 */
public class RealizarBk extends Thread{
    @Override
    public synchronized void run(){
        new BackUp().CrearBackup("C:\\copiaDeSeguridad.sql");
    }
}
