package socketsudp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
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
    
    private static List<InetAddress> caminhoClientes;
    private static List<Integer> caminhoPort;
    
  
    
    public Servidor () throws SocketException{
        
        caminhoClientes = new ArrayList<InetAddress>();
        caminhoPort = new ArrayList<Integer>();  
    }
   
    public static void main(String[] args) throws Exception{
        Thread envia = new Servidor();
        envia.start();                                  
} 
    
    public void run(){
        try {
            
                DatagramSocket envia = new DatagramSocket(4545);
                System.out.println("Esperando conexão com Servidor.....");
                DatagramPacket recebe = new DatagramPacket(new byte[1024], 1024);
                    
            while(true){
                envia.receive(recebe);
                
                for(int i = 0; i < recebe.getLength(); i++){
                    System.out.print((char) recebe.getData()[i]);
                }
                System.out.print(" "+recebe.getPort());
                
                System.out.println();
                
              if (!caminhoPort.contains(recebe.getPort())) {
                    caminhoClientes.add(recebe.getAddress());
                    caminhoPort.add(recebe.getPort());
                }
              
              for(int i = 0;  i < caminhoClientes.size();i++){  
                 if (caminhoPort.get(i) != recebe.getPort()) {
                  DatagramPacket resp = new DatagramPacket(recebe.getData(), recebe.getLength(),caminhoClientes.get(i), caminhoPort.get(i));
                  envia.send(resp);
                 }
              } 
            }
            
        } catch (SocketException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
}      
