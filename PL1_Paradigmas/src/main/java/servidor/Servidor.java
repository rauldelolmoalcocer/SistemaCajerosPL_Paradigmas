
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author RauldelOlmo y MariaDelgado
 */
public class Servidor extends Thread {
    private BufferDatos buffer;

    public Servidor(BufferDatos buffer) {
        this.buffer = buffer;
    

    }

    @Override
    public void run() {
        /**
         * OBJ:
         */

        System.out.println("Servidor iniciado");
        //Protocolo TCP
        final int PORT_TCP_SERVER = 5000;

        //Servidor de recepcion de informacion modelo logico de clases
        ServerSocket servidor;
        Socket conexion;
        DataOutputStream salida;
        DataInputStream entrada;

        try {

            servidor = new ServerSocket(PORT_TCP_SERVER);
            String mensaje;
            String mensajeCliente;

            boolean servidorEncendido = true;
            while (servidorEncendido) {

                conexion = servidor.accept();

                entrada = new DataInputStream(conexion.getInputStream());
                salida = new DataOutputStream(conexion.getOutputStream());

                mensaje = buffer.extraerMensajes();

                System.out.println(mensaje);      
                salida.writeUTF(mensaje);
                
                mensajeCliente = entrada.readUTF();
                
                if(mensajeCliente.equals("Apagar")){
                    servidorEncendido = false;
                    System.out.println("Apagando Servidor");
                    
                    
                    //Aqui seguramente sea donde matemos a los operarios.
                    
                }else if(mensajeCliente.equals("Ok")){
                    System.out.println("El mensaje se ha recibido correctamente");
                }else {
                    System.out.println("Se ha producido una respuesta diferente por parte del cliente.");
                }
                
                

                if(mensajeCliente.equals("Apagar")){
                    sleep(3000);}

                entrada.close();
                salida.close();
                conexion.close();
                
                
            }
            
           

        } catch (Exception e) {

            System.out.println("ERROR: El server ha caido.");
        }

    }

}
