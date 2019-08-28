package com.mycompany.extraerxml;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Main {

    public static void main(String args[]) {

        try {
            File archivo = new File("cfdi_correcto.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.parse(archivo);
            document.getDocumentElement().normalize();
            
            //imprime el nodo raíz del xml
            System.out.println("Elemento raiz: " + document.getDocumentElement().getNodeName());
            
            //obtener el nombre del cliente y proveedor
            NodeList receptor = document.getElementsByTagName("cfdi:Receptor");
            NodeList emisor = document.getElementsByTagName("cfdi:Emisor");
            
            Node nodoE = emisor.item(0);
            Node nodoR = receptor.item(0);
            
            Element elementRes = (Element) nodoR;
            Element elementEmi = (Element) nodoE;
            
            //información cliente:
            String nombreCliente = elementRes.getAttribute("Nombre");
            String rfcCliente = elementRes.getAttribute("Rfc");
            
            //información del proveedor:
            String nombreProveedor = elementEmi.getAttribute("Nombre");
            String rfcProveedor = elementEmi.getAttribute("Rfc");

//          weas raras para xml   
//            NodeList listaEmpleados = document.getElementsByTagName("empleado");
//              
//            for (int temp = 0; temp < listaEmpleados.getLength(); temp++) {
//                
//                Node nodo = listaEmpleados.item(temp);
//                
//                System.out.println("Elemento: " + nodo.getNodeName());
//                
//                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
//                    
//                    
//                    
//                    Element element = (Element) nodo;
//                    
//                    
//                    
//                    System.out.println("id: " + element.getAttribute("id"));
//                    
//                    
////                    System.out.println("Nombre: " + element.getElementsByTagName("nombre").item(0).getTextContent());
////                    System.out.println("username: " + element.getElementsByTagName("username").item(0).getTextContent());
////                    System.out.println("password: " + element.getElementsByTagName("password").item(0).getTextContent());
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
