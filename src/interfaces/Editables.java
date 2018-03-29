/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AUXILIAR-ADM
 */
public interface Editables {
    public ArrayList ListarDetalleDePedidos(String codigoPedido);
    public ArrayList ListadoPedidosPorFecha(String fecha);
    public Boolean EliminarItems(Integer id);
    public DefaultTableModel MostrarEnTabla(ArrayList listado);
}
