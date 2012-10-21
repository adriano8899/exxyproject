/***********************************************************
* project:  Exxy (X1/9 in car communication)
* author:   Adriano Celentano
* email:    adriano8899[at]gmail[dot]com
* 
* version:  0.01
* date:     Oct 2012
* edited:   21/Oct/2012
* 
* Package:  exxyserver
* Class:    ExxyServer
***********************************************************/
package exxyserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.util.Date;

public class ExxyServer {
     
    private static String classname = "[" + new Date().toString() +" ] ExxyServer: ";
    ServerSocket server = null;
    public boolean keepgoing = true;


 
/**** Base Constructor ****/
ExxyServer(){}     
    
  public void listenSocket(int port){
            
    try{
      server = new ServerSocket(port);
    } catch (IOException e) {
      System.out.println(classname + "Could not listen on default port 5000");
      System.exit(-1);
    }
    do{
      ExxyClientWorker w;
      try{
        w = new ExxyClientWorker(server.accept(), this);
        Thread t = new Thread(w);
        t.start();
      } catch (IOException e) {
        System.out.println(classname + "Accept failed: 5000");
        System.exit(-1);
      }
    }while(keepgoing);
  }

  protected void finalize(){
     try{
        server.close();
    } catch (IOException e) {
        System.out.println(classname + "Could not close socket");
        System.exit(-1);
    }
  }
    
    public static void main(String[] args) throws IOException {
         ExxyServer server = new ExxyServer();   
         System.out.println(classname + "Server Started...");
    
    /*  System.out.println(classname + "Select a port (Default: 5000)?"); 
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String portString = in.readLine();
            int p = Integer.parseInt(portString);
    */
        int p = 5000;
        System.out.println(classname + "Server Listening Port: " + p);    
        System.out.println(classname + "Waiting for client to connect...");
        server.listenSocket(p);
    }
}
