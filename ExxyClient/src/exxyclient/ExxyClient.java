/***********************************************************
* project:  Exxy (X1/9 in car communication)
* author:   Adriano Celentano
* email:    adriano8899[at]gmail[dot]com
* 
* version:  0.01
* date:     Oct 2012
* edited:   21/Oct/2012
* 
* Package:  exxyclient
* Class:    ExxyClient
***********************************************************/
package exxyclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


class ExxyClient {

static String hostName = "192.168.0.5";  
Integer socketNum = 5000;
Socket socket = null;
PrintWriter toServer = null;
BufferedReader inSocket = null;


    
/**** Base Constructor ****/  
public void ExxyClient(){}
 
public void sendSocket(String input){
//Create socket connection
     try{
       socket = new Socket(hostName, socketNum);
       toServer = new PrintWriter(socket.getOutputStream(), true);
       toServer.println(input);
       
     } catch (UnknownHostException e) {
       System.out.println("Unknown host: " + hostName + " ERROR: " + e);
       System.exit(1);
     } catch  (IOException e) {
       System.out.println("FAILED Connection " + hostName + " (ERROR: " + e +")");
       System.exit(1);
     }
  }
    public void listenSocket(){
        //Create socket connection
     try{   inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
     try{
	  String line = inSocket.readLine();
          System.out.println(hostName + " Server Response:" + line);
       } catch (IOException e){
	 System.out.println(hostName + " Server Response FAILED");
       	 System.exit(1);
       }

     } catch (UnknownHostException e) {
       System.out.println("Unknown host: " + hostName + " ERROR: " + e);
       System.exit(1);
     } catch  (IOException e) {
       System.out.println("FAILED Connection " + hostName + " ERROR: " + e);
       System.exit(1);
     }
  }

   public static void main(String[] args) throws Exception {
          
       
 
       ExxyClient client = new ExxyClient();
          
          
          
          client.sendSocket("CLIENT CONNECT");
          client.listenSocket();
    while(true) {
     System.out.println(hostName + ": ");
     BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
     String sInput = in.readLine();
     
            if(sInput.equalsIgnoreCase("shutdown")) {
                client.sendSocket("shutdown");
               	System.out.println("Request shutdown sent!");
            }
            if(sInput.equalsIgnoreCase("quit")) {
                client.sendSocket("Client Shutdown");
               	System.out.println("Client Shutdown, Goodbye");
		break;
            }
            if(sInput.equalsIgnoreCase("test"))
            {
                client.sendSocket("Client: test function called!");
                System.out.println("test function called!");
            }
            else {
                client.sendSocket(sInput);
		client.listenSocket();
			}
        }

   
   }
       
}
