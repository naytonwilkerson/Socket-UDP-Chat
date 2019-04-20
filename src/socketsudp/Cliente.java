/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketsudp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nayton Wilkerson
 */
public class Cliente extends Thread {

    private static DatagramSocket s;
 
    
    public Cliente (DatagramSocket n){
        s = n;
    }
 
    
    public static void main(String[] args) {
        
      
          
        try {
      
            InetAddress dest = InetAddress.getByName("localhost");
            BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
            s = new DatagramSocket();
            
            System.out.print("Entre com o seu nome: ");
            String Nome = teclado.readLine();
            byte[] buffer = Nome.getBytes();
            DatagramPacket msg = new DatagramPacket(buffer,buffer.length, dest, 4545);
            s.send(msg);
            
            Thread t = new Cliente(s);
            t.start();
           
            String envio;
 
            do{
                
                System.out.print("> ");
                envio= Nome+" disse: "+ teclado.readLine();
                buffer = envio.getBytes(); 
                msg = new DatagramPacket(buffer,buffer.length, dest, 4545);
                s.send(msg);
                
            }while(!envio.equalsIgnoreCase(""));
            
            s.close();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
           
    
    @Override
    public void run(){
        try {
           String Dados;
           DatagramPacket resposta = new DatagramPacket(new byte[1024], 1024);
           s.receive(resposta);
 
           do{
                Dados = "";
                for(int i = 0; i < resposta.getLength(); i++){
                    Dados += ((char) resposta.getData()[i]);
                    System.out.print((char) resposta.getData()[i]);
                }
                System.out.println();
                System.out.print("> ");
                s.receive(resposta);
                  
           }while(!Dados.equals("")); 
           
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
  
        
        
        
    
    
    

