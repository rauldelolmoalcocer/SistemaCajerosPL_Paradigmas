/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import static java.lang.Thread.MAX_PRIORITY;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;


/**
 *
 * @author Rauld y mcata
 */
public class QA {
    
    /**
     * Estas clases sirven para realizar en el sistema.
     */

    public static void Logstest() {

        /**
         * OBJ: Comprobar que los metodos empleados en GeneratingLogsService
         * funcionan de manera correcta
         *
         * Resultado: positivo, tanto crear, como annadir, como borrar fichero
         * funcionan de manera correcta
         */
        GeneratingLogsService.crearFichero();
        GeneratingLogsService.annadirInformacionALog("HolaMundo");
        GeneratingLogsService.annadirInformacionALog("HolaMundo");

        GeneratingLogsService.borrarFichero();

    }

    public static void TestPersonasGenerating() {
        /**
         * OBJ: Generar 200 personas con sus correspondientes recursos
         * compartidos y poner en funcionamiento el sistema.
         */
        GeneratingPersonasService.GeneratePersonas();

    }

    public static void firstTestPersonas_BancoMonitor_cajeros() {
        /**
         * OBJ: Exactamente lo mismo que arriba.
         */
        GeneratingPersonasService.GeneratePersonas();

    }

    public static void testServerProveUDP() {

        final int PORT_SOCKET_UDP = 5000;
        DatagramSocket servidor;

        try {
            servidor = new DatagramSocket(PORT_SOCKET_UDP);
            servidor.setReceiveBufferSize(MAX_PRIORITY); //

            while (true) {

                byte[] buffer = new byte[1024];

                //Creamos el DatagramPacket
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length);
                servidor.receive(datagram);
                String respuesta = new String(datagram.getData(), 0, datagram.getLength());
                System.out.println("La respuesta es: " + respuesta);

                //cola.offer(respuesta); //Encolamos la repuesta.
            }

        } catch (Exception e) {
            System.out.println("ERROR: El cliente ha caído en clienteApp().");
            e.printStackTrace();
        }
    }

    public static void testClientProveUDP() {

        /**
         * OBJ: Los hilos que los usen enviaran los datos al Servidor.java para
         * que estos sean consumidos y enviados al cliente.
         *
         * PRE: El hilo debe estar en ejecucion
         *
         *
         * enviarDatosAServidor(String mensaje) --> enviamos mediante
         * datagramas.
         *
         */
        //Constante
        final String LOCALHOST = "localHost";
        final int PORT = 5000;

        try {

            //Levantamos el socket UDP
            DatagramSocket socket = new DatagramSocket();

            for (int i = 0; i < 100; i++) {
                //Crear el paquete a enviar
                String mensaje = "Mensaje" + i;
                byte[] buffer = mensaje.getBytes();
                InetAddress addr = InetAddress.getByName(LOCALHOST);

                //Creamos el paquete 
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, addr, PORT);

                socket.send(packet);

            }
        } catch (Exception e) {

            System.out.println("ERROR: El sistema ha caido enviando datos al servidor");
        }

    }

    public static void testServerProve() {
        ServerSocket servidor;
        Socket conexion;
        ArrayList<String> cola = new ArrayList<>();
        DataOutputStream salida;
        DataInputStream entrada;
        Semaphore em = new Semaphore(1);

        try {

            servidor = new ServerSocket(5000);
            String mensaje;
            while (true) {

                conexion = servidor.accept();
                entrada = new DataInputStream(conexion.getInputStream());
                salida = new DataOutputStream(conexion.getOutputStream());

                mensaje = entrada.readUTF();

                System.out.println(mensaje);

                cola.add(mensaje);

                System.out.println(cola.size());

                for (String elemento : cola) {
                    System.out.print(elemento + " ");
                }
                System.out.println(); // Para agregar una línea nueva al final

                entrada.close();
                salida.close();
                conexion.close();
            }

        } catch (Exception e) {

            System.out.println("ERROR: El server ha caido.");
        }

    }

    public static void testClientProve(String mensaje) {

        Socket cliente;

        DataOutputStream salida;
        DataInputStream entrada;

        try {
            cliente = new Socket(InetAddress.getLocalHost(), 5000);

            entrada = new DataInputStream(cliente.getInputStream());
            salida = new DataOutputStream(cliente.getOutputStream());

            salida.writeUTF(mensaje);

            entrada.close();
            salida.close();
            cliente.close();

        } catch (Exception e) {
            System.out.println("ERROR:");
        }

    }

 
}
