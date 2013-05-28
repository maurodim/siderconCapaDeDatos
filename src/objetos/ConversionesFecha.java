/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package objetos;

import java.util.Date;



/**
 *
 * @author Administrador
 */
public class ConversionesFecha {
    private String fechaOriginal;
    private Date fechaConvertida;
    private Integer numeroDeCampo;

    public Date getFechaConvertida() {
        return fechaConvertida;
    }

    public void setFechaConvertida(Date fechaConvertida) {
        this.fechaConvertida = fechaConvertida;
    }

    public String getFechaOriginal() {
        return fechaOriginal;
    }

    public void setFechaOriginal(String fechaOriginal) {
        this.fechaOriginal = fechaOriginal;
    }

    public Integer getNumeroDeCampo() {
        return numeroDeCampo;
    }

    public void setNumeroDeCampo(Integer numeroDeCampo) {
        this.numeroDeCampo = numeroDeCampo;
    }
    
    
}
