/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author mauro di
 */
public class Configuracion {
    private String nombreConeccion;
    private String stringDeUrl;
    private String bd;
    private String usuario;
    private String clave;

    public String getNombreConeccion() {
        return nombreConeccion;
    }

    public String getStringDeUrl() {
        return stringDeUrl;
    }

    public void setNombreConeccion(String nombreConeccion) {
        this.nombreConeccion = nombreConeccion;
    }

    public void setStringDeUrl(String stringDeUrl) {
        this.stringDeUrl = stringDeUrl;
    }

    public void setBd(String bd) {
        this.bd = bd;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getBd() {
        return bd;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getClave() {
        return clave;
    }

    public Configuracion(){
        
    }
    public ArrayList leerConfiguraciones() throws MalformedURLException, IOException, ParserConfigurationException, SAXException{
     String arc="config.xml";
        URL url=new URL("http://www.bambusoft.com.ar/xml/config.xml");//modificar luego a config.xml
        BufferedReader br=new BufferedReader(new InputStreamReader(url.openStream()));
        String entrada;
        String cadena="";
        ArrayList listadoConecciones=new ArrayList();
        while((entrada=br.readLine())!=null){
            cadena =cadena + entrada;
        }
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder db=dbf.newDocumentBuilder();
        InputSource archivo=new InputSource();
        archivo.setCharacterStream(new StringReader(cadena));
        Document documento=db.parse(archivo);
        documento.getDocumentElement().normalize();
        org.w3c.dom.NodeList nodeLista=documento.getElementsByTagName("coneccion");
        int cantidad=nodeLista.getLength();
        System.out.println("Informacion de conecciones");
        
        for (int s = 0; s < cantidad; s++) {
            
	Node primerNodo = nodeLista.item(s);
	String titulo;
	String autor;
	String hits;
        System.err.println("numero nodo "+s);
        
	if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {

	Element primerElemento = (Element) primerNodo;
        Configuracion conf=new Configuracion();

	        org.w3c.dom.NodeList primerNombreElementoLista =primerElemento.getElementsByTagName("nombre");
	Element primerNombreElemento =(Element) primerNombreElementoLista.item(0);
	        org.w3c.dom.NodeList primerNombre = primerNombreElemento.getChildNodes();
	nombreConeccion = ((Node) primerNombre.item(0)).getNodeValue().toString();
	System.out.println("NOMBRE : "  + nombreConeccion);
        conf.setNombreConeccion(nombreConeccion);
	        org.w3c.dom.NodeList segundoNombreElementoLista =primerElemento.getElementsByTagName("url");
	Element segundoNombreElemento =(Element) segundoNombreElementoLista.item(0);
	        org.w3c.dom.NodeList segundoNombre = segundoNombreElemento.getChildNodes();

	stringDeUrl = ((Node) segundoNombre.item(0)).getNodeValue().toString();
	System.out.println("URL : "  + stringDeUrl);
        conf.setStringDeUrl(stringDeUrl);
	        org.w3c.dom.NodeList tercerNombreElementoLista =primerElemento.getElementsByTagName("usuario");
	Element tercerNombreElemento =(Element) tercerNombreElementoLista.item(0);
	        org.w3c.dom.NodeList tercerNombre = tercerNombreElemento.getChildNodes();
    	usuario = ((Node) tercerNombre.item(0)).getNodeValue().toString();
	System.out.println("USUARIO : "  + usuario);
        conf.setUsuario(usuario);
        org.w3c.dom.NodeList cuartoNombreElementoLista =primerElemento.getElementsByTagName("clave");
	Element cuartoNombreElemento =(Element) cuartoNombreElementoLista.item(0);
	        org.w3c.dom.NodeList cuartoNombre = cuartoNombreElemento.getChildNodes();
    	clave = ((Node) cuartoNombre.item(0)).getNodeValue().toString();
	System.out.println("CLAVE : "  + clave);
        conf.setClave(clave);
        listadoConecciones.add(conf);
	}
        }
        return listadoConecciones;
    }
        
}
    
    

