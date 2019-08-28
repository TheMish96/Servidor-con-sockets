package servidorpoderoso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.custommonkey.xmlunit.DetailedDiff;
import org.custommonkey.xmlunit.Diff;
import org.custommonkey.xmlunit.Difference;
import org.custommonkey.xmlunit.DifferenceListener;
import org.custommonkey.xmlunit.IgnoreTextAndAttributeValuesDifferenceListener;
import org.custommonkey.xmlunit.XMLUnit;
import org.xml.sax.SAXException;

public class XmlFunctions {
    
    public static void main(String[] args) {

        try {
            System.out.println(comparar(leerArchivos("/home/luis/Escritorio/Servidor/xmlTrue/origen.xml"),
                    leerArchivos("/home/luis/Escritorio/Servidor/xmlTrue/target.xml")));
        } catch (NullPointerException e) {
            System.out.println("No hay archivo que comparar: " + e);
        }

        //System.out.println(leerArchivos("/home/luis/Escritorio/Servidor/xmlTrue/origen.xml"));
    }

    public static String leerArchivos(String ruta) {
        String c;
        String contenido = "";

        try {
            File inputFile = new File(ruta);
            FileInputStream fis = new FileInputStream(inputFile);

            BufferedReader dis = new BufferedReader(new InputStreamReader(fis));
            while ((c = dis.readLine()) != null) //ta.setText(ta.getText()+c+"\n");
            {
                contenido += c + "\n";
            }

            dis.close();
            fis.close();

        } catch (FileNotFoundException e) {
            System.err.println("Error" + e);
        } catch (IOException e) {
            System.err.println("Error" + e);
        }
        return contenido;

    }

    public static boolean comparar(String r1, String r2) {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreAttributeOrder(true);
        XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
        XMLUnit.setIgnoreComments(true);

        DetailedDiff comparacion = null;
        List<?> diferencias = null;
        try {
            comparacion = new DetailedDiff(XMLUnit.compareXML(r1, r2));
        } catch (SAXException ex) {

            System.out.println("El archivo xml está mal escrito: " + ex);

        } catch (IOException ex) {

            System.out.println("Archivo no valido: " + ex);

        }

        diferencias = comparacion.getAllDifferences();

        if (diferencias.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    public boolean testCompareToSkeletonXML(String prueba, String estandar) throws Exception {

        //XMLUnit.setIgnoreWhitespace(true);
        //XMLUnit.setIgnoreAttributeOrder(true);
        //XMLUnit.setIgnoreDiffBetweenTextAndCDATA(true);
        //XMLUnit.setIgnoreComments(true);

        DifferenceListener myDifferenceListener = new IgnoreTextAndAttributeValuesDifferenceListener();

        Diff myDiff = new Diff(prueba, estandar);

        myDiff.overrideDifferenceListener(myDifferenceListener);
        System.out.println("los xml son:" + myDiff.similar());
        boolean va = myDiff.similar();
        return va;
    }

}
