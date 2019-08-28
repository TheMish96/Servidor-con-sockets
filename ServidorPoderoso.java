package servidorpoderoso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorPoderoso implements Runnable {

    Thread ServThread = new Thread(this);
    String ipCliente = "";

    public ServidorPoderoso() {

        // ServThread.start();
    }

    public static void main(String[] args) {
        ServidorPoderoso s = new ServidorPoderoso();
        s.ServThread.start();
    }

    @Override
    public void run() {
        XmlFunctions xm = new XmlFunctions();
        System.out.println("Listening....");

        try {
            ServerSocket servidor = new ServerSocket(8080);

            while (true) {

                Socket miscocket = servidor.accept();
                ipCliente = miscocket.getInetAddress().getHostName();

                DataInputStream nombre = new DataInputStream(miscocket.getInputStream());
                String nombreRuta = nombre.readUTF();

                DataInputStream dis = new DataInputStream(miscocket.getInputStream());
                String nombreArchivo = dis.readUTF().toString();
                System.out.println("ruta: " + nombreArchivo);

                Date fecha = new Date();
                String anio = String.valueOf(fecha.getYear() + 1900);
                String mes = String.valueOf(fecha.getMonth() + 1);
                String dia = String.valueOf(fecha.getDay());
                String hora = String.valueOf(fecha.getHours());
                String minutos = String.valueOf(fecha.getMinutes());
                String segundos = String.valueOf(fecha.getSeconds());
                String fe = "fecha" + anio + "_" + mes + "_" + dia + "_Hora_" + hora + "_" + minutos + "_" + segundos + "_";

                String nombreHost = "";
                if (nombreRuta.equals("h1")) {
                    nombreHost = "h1";
                } else if (nombreRuta.equals("h2")) {
                    nombreHost = "h2";

                } else if (nombreRuta.equals("h3")) {
                    nombreHost = "h3";
                } else {
                    nombreHost = "unknown";
                }
                int xml = nombreArchivo.indexOf(".xml");
                String sub = nombreArchivo.substring(0, xml);
                nombreHost += "_" + sub + "_" + fe + ".xml";

                File car = new File("C:\\Users\\luis\\Desktop\\archivosServer\\h1");
                if (car.exists()) {
                    car.delete();
                    car.mkdirs();
                } else {
                    car.mkdirs();
                }

                File car2 = new File("C:\\Users\\luis\\Desktop\\archivosServer\\h2");
                if (car2.exists()) {
                    car2.delete();
                    car2.mkdirs();
                } else {
                    car2.mkdirs();
                }
                File car3 = new File("C:\\Users\\luis\\Desktop\\archivosServer\\h3");
                if (car3.exists()) {
                    car3.delete();
                    car3.mkdirs();
                } else {
                    car3.mkdirs();
                }

                int tam = dis.readInt();
                System.out.println("Recibiendo Archivo " + nombreArchivo);
                // Creamos flujo de salida, este flujo nos sirve para 
                // indicar donde guardaremos el archivo
                FileOutputStream fos = new FileOutputStream("C:\\Users\\luis\\Desktop\\archivosServer\\" + nombreRuta + "\\" + nombreHost);
                BufferedOutputStream out = new BufferedOutputStream(fos);
                BufferedInputStream in = new BufferedInputStream(miscocket.getInputStream());

                // Creamos el array de bytes para leer los datos del archivo
                byte[] buffer = new byte[tam];
                // Obtenemos el archivo mediante la lectura de bytes enviados
                for (int i = 0; i < buffer.length; i++) {
                    buffer[i] = (byte) in.read();
                }

                out.write(buffer);
                //cierre 
                out.flush();
                in.close();
                out.close();
                miscocket.close();
                System.out.println("Archivo recibido " + nombreArchivo);

                //las weas que comparan xml:
                String xmlTarget = "C:\\Users\\luis\\Desktop\\archivosServer\\" + nombreRuta
                        + "\\" + nombreHost;
                String xmlOrigin = "C:\\Users\\luis\\Desktop\\archivosServer\\cfdi_correcto.xml";

                String contOrigin = xm.leerArchivos(xmlOrigin);
                String contTarget = xm.leerArchivos(xmlTarget);
                
//                if(xm.comparar(contOrigin, contTarget)){
//                     System.out.println("los ficheros son correctos");
//                }else{
//                     System.out.println("el fichero es incorrecto");
//                }
                
                
                boolean ban = false;
                try {
                    ban = xm.testCompareToSkeletonXML(contOrigin, contTarget);
                } catch (Exception ex) {
                    System.out.println("error: " + ex);
                }

                if (ban == true) {
                    System.out.println("los ficheros son correctos");
                } else {
                    System.out.println("el fichero es incorrecto");
                    File fichero = new File(xmlTarget);
                    fichero.delete();
                }

                Socket socket = new Socket(ipCliente, 5000);

                DataOutputStream servicio = new DataOutputStream(socket.getOutputStream());
                String mensaje;
                if (ban == true) {
                    mensaje = "\nEl archivo es valido";
                } else {
                    mensaje = "\nEl archivo no es valido";
                }
                servicio.writeUTF(mensaje);

                
            }

        } catch (IOException ex) {
            Logger.getLogger(ServidorPoderoso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
