/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package proceso;

import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import objetos.DetalleListado;

/**
 *
 * @author mauro di
 */
public class ListaDataSource implements JRDataSource{
    private List<DetalleListado> articulos=new ArrayList<DetalleListado>();
    private int indiceArticulos=-1;
    
    @Override
    public boolean next() throws JRException {
        return ++indiceArticulos < articulos.size();
    }

    @Override
    public Object getFieldValue(JRField jrf) throws JRException {
        Object valor=null;
        if("codigoArticulo".equals(jrf.getName())){
            valor=articulos.get(indiceArticulos).getCodigoArticulo();
        }else if("descripcionArticulo".equals(jrf.getName())){
            valor=articulos.get(indiceArticulos).getDescripcionArticulo();
        }else if("cantidad".equals(jrf.getName())){
            valor=articulos.get(indiceArticulos).getCantidad();
        }else if("peso".equals(jrf.getName())){
            valor=articulos.get(indiceArticulos).getPeso();
        }
        return valor;
    }
    public void addListaDataSource(DetalleListado det){
        this.articulos.add(det);
    }
}
