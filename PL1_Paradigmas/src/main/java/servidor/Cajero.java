/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author RauldelOlmo y MariaDelgado
 */
public class Cajero{
    
    private int saldoCajero;
    private int idCajero;
    private int capacidadMaxima;
    private boolean libre;


    public Cajero(int saldoCajero, int idCajero, int capacidadMaxima) {
        this.saldoCajero = saldoCajero;
        this.idCajero = idCajero;
        this.capacidadMaxima = capacidadMaxima;
        this.libre = true;
    }
    
    
    public int extraerDineroCajero(int cantidad, int nPersona){
        /**
         * OBJ: La persona entra al cajero y se extrae la cantidad correspondiente a la
         * asociada en el parametro cantidad.
         */
        try {
            Random rand = new Random();
            Thread.sleep(rand.nextInt(2500) + 2000); //Entre 2,5 y 4,5 seg
            //Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cajero.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("Estoy extrayendo dinero del cajero.");
        System.out.println("Se ha extraido del cajero la siguiente cantidad" + cantidad + " y ha sido la persona: " + nPersona);
        System.out.println("------------------------------------------------------------------");
        
        this.saldoCajero = saldoCajero - cantidad;
        
        imprimirDineroCajero();
        
        return(cantidad);
    }
    
    public void introducirCajero(int cantidad, int nPersona){
        /**
         * OBJ: La persona introduce una cantidad de dinero en el cajero.
         * 
         * PRE: El cajero debe tener dinero suficiente.
         * 
         * introducirDinero() -> Introducir dinero 
         */
        try {
            Random rand = new Random();
            Thread.sleep(rand.nextInt(2000) + 2000); //Entre 2 y 4 seg
            //Thread.sleep(1500);
        } catch (InterruptedException ex) {
            Logger.getLogger(Cajero.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("He introducido el dinero");
        System.out.println("La persona " + nPersona + " ha introducido la siguiente cantidad" + cantidad);
        System.out.println("------------------------------------------------------------------");
        
        this.saldoCajero = saldoCajero + cantidad;
        
        imprimirDineroCajero();
    
    }
    
    public void imprimirDineroCajero(){
        /**
         * OBJ: Imprime el dinero total que tiene el cajero disponible.
         * 
         */
        System.out.println("------------------------------------------------------------------");
        System.out.println("EL CAJERO TIENE UN TOTAL: " + this.saldoCajero);
        System.out.println("------------------------------------------------------------------");
    }
    
    public int  operarioSacaDinero(){     
        this.saldoCajero = saldoCajero - 50000;        
        return(50000);
    }
    
    public void operarioMeteDinero(int cantidad){
        this.saldoCajero = saldoCajero + cantidad;
        imprimirDineroCajero();
    }
    public boolean isLibre() {
        return libre;
    }
    
    public void notLibre(){
        this.libre = false;
    }
    
    public void Libre(){
        this.libre = true;
    }
    
    public int getSaldoCajero() {
        return saldoCajero;
    }
    
    public void setSaldoCajero(int saldoCajero) {
        this.saldoCajero = saldoCajero;
    }

    public int getIdCajero() {
        return idCajero;
    }

    public void setIdCajero(int idCajero) {
        this.idCajero = idCajero;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    @Override
    public String toString() {
        return "Cajero{" + "saldoCajero=" + saldoCajero + ", idCajero=" + idCajero + ", capacidadMaxima=" + capacidadMaxima + '}';
    }
    
    
}
