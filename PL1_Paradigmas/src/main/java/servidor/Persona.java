/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.util.Random;

import service.GeneratingLogsService;

/**
 *
 * @author RauldelOlmo y MariaDelgado
 */
public class Persona extends Thread{
    
    private int idPersona;
    private int cartera;
    private BancoMonitor banco;
    private BufferDatos buffer;
    private BufferDatos bufferServidor;
    
    public Persona(int idPersona, int cartera, BancoMonitor banco, BufferDatos buffer, BufferDatos bufferServidor) {
        this.idPersona = idPersona;
        this.cartera = cartera;
        this.banco = banco;
        this.buffer = buffer;
        this.bufferServidor = bufferServidor;
    }
    
    
    @Override
    public void run(){
        
        try{
            
            sleep(400); //Se duerme el hilo durante 400 milis para evitar que el sistema sature.
            Random rand = new Random();
            System.out.println("Hola estoy funcionando. Soy el hilo " + toString());
            
            //Decido que operacion voy a realizar con mi hilo.
            
            int decisionPersona =rand.nextInt(2); //Genera os números aleatorios 0 o 1
            
            switch(decisionPersona){
            
                case 0: //La persona elige extraerDinero
                    
                    String cantidadDadaCajero = extraerDineroCajero((rand.nextInt(5)*1000) + 5000); //5000-10000 // retornoDinero o cantidad dada/numCajeroDisp/DineroTotalCajero
                    //Debemos trocear el String y hacer casting.
                    String[] palabras = cantidadDadaCajero.split("/");
                    
                    
                    
                    this.cartera +=  Integer.parseInt(palabras[0]);
                    System.out.println("La persona ha realizado con exito la operacion de extraer dinero");
                    
                    String mensaje =  idPersona + "/"+ "Persona" + idPersona + "-E-" + palabras[0] + "/" + palabras[1] + "/" + palabras[2]; //    idPersona/mensaje/cajero
                    
                    
                    System.out.println("El mensaje que voy a enviar es: " + mensaje);
                    
                    buffer.insertarMensajes(mensaje);
                    bufferServidor.insertarMensajes(mensaje);
                    
                    //Enchufar log
                    GeneratingLogsService.annadirInformacionALog(mensaje);
                    
                    
                    break;
                    
                
                case 1: //La persona elige ingresarDinero
                    
                    String msg = ingresarDineroCajero(cartera);
                    
                    
                    String[] palabrasbis = msg.split("/");
                    
                    
                    System.out.println("La persona ha realizado con exito la operacion de introducir dinero");
                    
                    msg = idPersona + "/" + "Persona" + idPersona + "-I+" + cartera + "/"+ palabrasbis[1] + "/" + palabrasbis[2]; //retornoDinero o cantidad dada/numCajeroDisp/DineroTotalCajero
                    System.out.println("Persona" + idPersona + "-I+" + cartera);
                    
                    buffer.insertarMensajes(msg);
                    bufferServidor.insertarMensajes(msg);
                    
                    //Enchufar log
                    GeneratingLogsService.annadirInformacionALog(msg);

                    break;
                    
                default:
                    System.out.println("ERROR: El sistema ha elegido un numero que no era.");
                    break;
            }
            
            System.out.println("SUCCESS: La persona " + toString() + " ha finalizado con exito su ejecucion");
            
        }catch(Exception e){
        
            System.out.println("ERROR:  + El sistema ha caido el run() de una persona" + toString());
            e.printStackTrace();
        }
        
        
        
        
        
    
    
    }
    
    
    public String extraerDineroCajero(int cantidad){
        
        /**
         * OBJ: Este metodo extrae dinero del monitor cajero, dada un cantidad.
         * PRE: La persona ha tenido que ser crear y haber sido llamada desde run()
         * 
         * extraerDineroCajero()--> run()
         */
        
        System.out.println("Quiero extraer dinero");
        return(banco.extraerDineroCajero(cantidad, this.idPersona));
    }
    
    public String ingresarDineroCajero(int cantidad){
        
        /**
         * OBJ: Este metodo ingresa dinero del monitor cajero, dada un cantidad.
         * PRE: La persona ha tenido que ser crear y haber sido llamada desde run()
         * 
         * ingresoDineroCajero()--> run()
        */
        System.out.println("Quiero ingresar dinero");

        return(banco.introducirDineroCajero(cantidad, this.idPersona));
        
    
    }

    
    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public int getCartera() {
        return cartera;
    }

    public void setCartera(int cartera) {
        this.cartera = cartera;
    }

    public BancoMonitor getBanco() {
        return banco;
    }

    public void setBanco(BancoMonitor banco) {
        this.banco = banco;
    }
    
     @Override
    public String toString() {
        return "Persona{" + "idPersona=" + idPersona + ", cartera=" + cartera + '}';
    }
    
}