package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.SQLOutput;

public class UtilidadesXML {

    public static Document xmlToDom(String raiz){
        Document documento=null;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            documento = db.parse(raiz);

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return documento;
    }

    public static boolean domToXml(Document documento, String ruta){

    try {
        File f= new File(ruta);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        tFactory.setAttribute("indent-number", 2);
        Transformer transformador = tFactory.newTransformer();
        transformador.setOutputProperty(OutputKeys.INDENT, "yes");
        FileOutputStream fos= new FileOutputStream(f);
        StreamResult resultado = new StreamResult(new OutputStreamWriter(fos, "UTF-8"));
        DOMSource fuente = new DOMSource(documento);
        transformador.transform(fuente, resultado);
        return true;
    }catch (TransformerException e){
        System.out.println(e.getMessage());

    }catch (UnsupportedEncodingException e){
        System.out.println(e.getMessage());
    }catch (FileNotFoundException e){
        System.out.println(e.getMessage());
    }
    return false;
    }

    public static Document crearDomVacio(String raiz){
        DocumentBuilderFactory dbf= DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Document d= null;

        try {
            db = dbf.newDocumentBuilder();
            d = db.newDocument();
            d.appendChild(d.createElement(raiz));
        }catch (ParserConfigurationException e){
            System.out.println(e.getMessage());
        }
        return d;
    }
}
