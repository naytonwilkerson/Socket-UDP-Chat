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

/**
 *
 * @author Nayton Wilkerson
 */
public class Cliente extends Thread {

    private static DatagramSocket s;
 
    
    public Cliente (DatagramSocket s){
        s = s;
    }
 
    
    public static void main(String[] args) throws SocketException, IOException {
        
          s = new DatagramSocket();
          InetAddress dest = InetAddress.getByName("localhost");
           
           BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in)); 
           System.out.print("Entre com o seu nome: ");
            String Nome = teclado.readLine();
            
            byte[] buffer1 = Nome.getBytes();
            DatagramPacket msg1 = new DatagramPacket(buffer1,buffer1.length, dest, 4545);
            s.send(msg1);
            
            Thread t = new Cliente(s);
            t.start();
            
            String envio;
            System.out.print("> ");
            envio=teclado.readLine();
           
           while(!envio.equalsIgnoreCase("")){
               
                byte[] buffer = envio.getBytes(); 
                DatagramPacket msg = new DatagramPacket(buffer,buffer.length, dest, 4545);
                s.send(msg);
                System.out.print("> ");
                envio=teclado.readLine();
            }
                           
           s.close();
        }
           
    
    @Override
    public void run(){
        try {
           String Dados = "";
           DatagramPacket resposta = new DatagramPacket(new byte[512], 512);
 
           do{
                s.receive(resposta);
                System.out.print("Servidor diz: ");
                for(int i = 0; i < resposta.getLength(); i++){
                    Dados += ((char) resposta.getData()[i]);
                    System.out.print((char) resposta.getData()[i]);
                }
                System.out.println();
                System.out.println("> ");
                  
           }while(!Dados.equals("")); 
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
    }
}
  
        
        
        
    
    
    

