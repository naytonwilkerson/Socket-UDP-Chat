package socketsudp;


import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Nayton Wilkerson
 */
public class Servidor extends Thread{
    
    private static List clientes;
    private static List caminhoClientes;
    
    private static DatagramSocket n;
    private String Nome;
    // guarda o endereco remoto
     int auxi = 0;
  
    
    public Servidor (DatagramSocket s){
        n = s;  
    }
   
    public static void main(String[] args) throws SocketException{
        try {
          
            clientes = new ArrayList();
            caminhoClientes = new ArrayList();

            n = new DatagramSocket(4545);
           
            System.out.println("Servidor esperando conexão.......");
            
            Thread t = new Servidor(n);
            t.start();
    
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        
} 
    
    @Override
    public void run(){

        try {
            

            DatagramPacket recebe = new DatagramPacket(new byte[512], 512);
            n.receive(recebe);
                
            for(int i = 0; i < recebe.getLength(); i++){
                Nome += ((char) recebe.getData()[i]);
                System.out.print((char) recebe.getData()[i]);    
            }
            
            System.out.print(" Conectou-se ao Servidor");
            System.out.println();
            clientes.add(Nome);
            caminhoClientes.add(recebe.getAddress());
            
            do{
                 n.receive(recebe);
                 System.out.print("> ");
                 
                 for(int i = 0; i < recebe.getLength(); i++){
                    System.out.print((char) recebe.getData()[i]);    
                 }
               
                 System.out.println("");

                 sendToAll(recebe.getData(), recebe.getLength(), recebe.getPort());
                        
            }while(true);
            
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void sendToAll(byte getData[] , int getLength, int getPort) throws IOException {

        int aux = 0;
        
         while(aux < caminhoClientes.size()){
             
             System.out.println(caminhoClientes.get(aux));
             
            DatagramPacket resp = new DatagramPacket(getData, getLength, (InetAddress)caminhoClientes.get(aux), getPort);
             n.send(resp);
      
            aux++;
         }
        }
  
}      
