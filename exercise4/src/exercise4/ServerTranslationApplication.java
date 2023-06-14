package exercise4;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTranslationApplication {
	
	public static void main (String[] args) throws IOException {
		ServerSocket serverSocket = null;
		
		try {
			//Bind Serversocket to port
			int portNo=4228;
			serverSocket = new ServerSocket(portNo);
			
			String text1="Good afternoon";
			System.out.println("Waiting for request");
			
			while (true){
				//Accept client request for connection
				Socket clientSocket = serverSocket.accept();
				
				//Create stream to write data on the network
				DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
				
				//Send the current date back to client
				outputStream.writeUTF(text1);
				
				//close the socket
				clientSocket.close();
			}
			
		} catch (Exception e) {
			if (serverSocket != null)
				serverSocket.close();
			
			e.printStackTrace();
		}
	}

}
