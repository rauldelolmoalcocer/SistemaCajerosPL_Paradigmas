/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RauldelOlmo y MariaDelgado
 */
public class Operario extends Thread {

    private int nOperario; //id de operario
    private BancoMonitor banco;
    private BancoCentral bancoCen;

    public Operario(int nOperario, BancoMonitor banco, BancoCentral bancoCen) {
        this.nOperario = nOperario;
        this.banco = banco;
        this.bancoCen = bancoCen;
    }

    @Override
    public void run() {

        try {
            boolean vivo = true;
            while (vivo) {

                int cantidad = 0;
                //Cogemos la posicion del  cajero elegido
                int cajeroElegido = banco.operacionCajero(nOperario);

                if (cajeroElegido != -1) {
                    //Cogemos el cajero elegido y sabiendo la operacion realizamos la operacion.
                    int operacion = banco.getArr(cajeroElegido);

                    if (banco.comprabarOpBoleans(cajeroElegido)) {

                        if (operacion == 2) {
                            //Sacamos el dinero del cajero.
                            cantidad = banco.sacarCantidadCajero(operacion, cajeroElegido);
                            System.out.println("CANTIDAD SACADA DEL BANCO: " + cantidad);

                            //Enviar al banco central el dinero.
                            bancoCen.traerDineroBanco(cantidad, nOperario, cajeroElegido);

                        } else if (operacion == 1) {
                            //Sacamos dinero del banco central el dinero.
                            cantidad = bancoCen.extraerDineroBanco(nOperario, cajeroElegido);
                            
                            //Lo enviamos al cajero
                            System.out.println("Se estan metiendo el dinero en el cajero");
                            banco.meterDineroCajero(cantidad, cajeroElegido);

                        } else {

                            System.out.println("ERROR: El sistema ha caído en el Operario.");
                        }
                    }

                }else{
                    System.out.println("ME MUEROOOOOOOOOOOOOOOOOO");
                    vivo = false;
                
                }
            }

        } catch (Exception ex) {
            System.out.println("ERROR: El sistema ha caído en el run() del Operario.");
            ex.printStackTrace();
        }
    }
}
