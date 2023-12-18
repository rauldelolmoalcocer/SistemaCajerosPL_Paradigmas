/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author RauldelOlmo y MariaDelgado
 */
public class BufferDatos {
    
    
    private Queue<String> colaDatos;
    
    private Lock lock = new ReentrantLock();
    private Condition conAlgo  = lock.newCondition();
    
    public BufferDatos(){
        colaDatos=new LinkedList<>();
    }
    
    public void insertarMensajes(String mensaje){
        /**
         * OBJ: Encolar mensajes en la cola de datos para enviarlos al servidor.
         * 
         * PRE: Haber mensajes.
         * 
         */
        try{
            
            lock.lock();
            //Encolamos la cola de datos
            colaDatos.offer(mensaje);
            //Liberamos la condición
            conAlgo.signal();
            
        }catch(Exception e){
            System.out.println("ERROR: El sistema ha caido insertando mensajes en el buffer de comunicacion");
        }finally{
            lock.unlock();
        
        }
        
    }
    
    public String extraerMensajes(){
        /**
         * OBJ: Desencolar los mensajes de la cola de datos.
         */
        try{
            
            lock.lock();
            
            //Si la cola de datos esta vacia, hacemos que el servidor espere. 
            while(colaDatos.isEmpty()){
                conAlgo.await();
            }//Sino
            
            //Desencolamos los datos 
            String mensaje = colaDatos.poll();
            
            return(mensaje);
         
        }catch(Exception e){
            System.out.println("ERROR: El sistema ha caido insertando mensajes en el buffer de comunicacion");
            return("");
        }finally{
            lock.unlock();
        
        }
        
    
    }
    
    
    
    
}
