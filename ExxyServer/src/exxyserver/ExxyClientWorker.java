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
* Class:    ExxyClientWorker
***********************************************************/
package exxyserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;


class ExxyClientWorker implements Runnable {
    
    private Socket clientSocket;
    private ExxyServer exxyServer;
    private static String date = "[" + new Date().toString() +" ] ExxyClient";
    ExxyBot bot;
   /**Needed by ExxyBot**/ 
    String sResponse = null;
    
    
ExxyClientWorker(Socket client, ExxyServer server) {
    
   this.clientSocket = client;
   this.exxyServer = server;
   }
  public void run(){
      bot = new ExxyBot();
    String classname = date + clientSocket.getLocalAddress()+"::" + clientSocket.getLocalPort()+": " ; 
    String received;
    BufferedReader in = null;
///   PrintWriter out = null;
    try{
      in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
///      out = new PrintWriter(clientSocket.getOutputStream(), true);
        received = in.readLine();
        System.out.println(classname + "RECEIVED: " + received );
///        out.println("ExxyServer returns:" + received);
        try {
                //ExxyBot.get_input(received);
                bot.get_input(received);
            } catch (Exception e) {
               System.out.println("get_input failed: " + e);
            }
                //ExxyBot.respond(clientSocket);
                bot.respond(clientSocket);
         
	if( received.startsWith("shutdown") ) {
            System.out.println("keepgoing false: " + received);
            exxyServer.keepgoing = false;
	}
        if( received.startsWith("test") ) {
            System.out.println("test function recieved: " + received);
            exxyServer.keepgoing = false;
	}
    } catch (IOException e) {
      System.out.println("in or out failed: " + e);
      System.exit(-1);
    }
  }
}
