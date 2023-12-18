/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author RauldelOlmo y MariaDelgado
 */
public class ClienteTCP extends Thread {

    //Cliente que va a ser la interfaz 2 del server.
    private final int PORT_CLIENT_TCP = 5000;
    private Socket cliente;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private InterfazCliente interfazcli;

    public ClienteTCP(InterfazCliente interfazcli) {
        this.interfazcli = interfazcli;
    }

    @Override
    public void run() {
        try {
            int i = 0;
            boolean clienteEncendido = true;
            while (clienteEncendido) {
                cliente = new Socket(InetAddress.getLocalHost(), PORT_CLIENT_TCP);

                entrada = new DataInputStream(cliente.getInputStream());
                salida = new DataOutputStream(cliente.getOutputStream());

                String mensaje = entrada.readUTF();
                
                interfazcli.fachadaRetornaMensajes(mensaje);
                System.out.println(mensaje);
                i++;
                System.out.println(i);

                //Generamos un respuesta al user.
                if (mensaje.equals("Terminado")) {

                    clienteEncendido = false;
                    System.out.println("Apagando Cliente");

                    salida.writeUTF("Apagar");
              

                } else {

                    salida.writeUTF("Ok");

                }
              
                //En esta casuistica vamos a tener que meter las senniales de pausar de operarios etc.
            
                entrada.close();
                salida.close();
                cliente.close();
            }

        } catch (Exception e) {
            System.out.println("ERROR: se ha producido un error en el clienteTCP" + e.toString());
            e.printStackTrace();
        }
    }

}
