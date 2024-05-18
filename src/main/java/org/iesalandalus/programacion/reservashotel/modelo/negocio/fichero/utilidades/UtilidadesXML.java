package org.iesalandalus.programacion.reservashotel.modelo.negocio.fichero.utilidades;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.sql.SQLOutput;

public class UtilidadesXML {

    public static Document xmlToDom(String raiz){
        Document documento=null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //Fabrica de constructores de documento.
            DocumentBuilder builder = factory.newDocumentBuilder();  // Se encarga de procesar el fichero xml y convertirlo en un arbol DOM
            documento = builder.parse(raiz);

        }catch (ParserConfigurationException | IOException e){
            System.out.println(e.getMessage());
        }catch (SAXException e){
            System.out.println(e.getMessage());
        }
        return documento;
    }

    public static boolean domToXml(Document documento, String ruta){

    try {
        File f= new File(ruta);
        TransformerFactory tFactory = TransformerFactory.newInstance();
        tFactory.setAttribute("indent-number", Integer.valueOf(4));
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

    public static Document crearDomVacio(String direccion,String raiz){

        try {
            DocumentBuilderFactory factoria = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructor = factoria.newDocumentBuilder();

            Document document = constructor.newDocument();
            Element root = document.createElement(raiz);
            document.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();


            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(direccion));
            transformer.transform(source, result);
                return document;
        }catch (ParserConfigurationException | TransformerConfigurationException e){
            System.out.println(e.getMessage());
        }catch (TransformerException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}
