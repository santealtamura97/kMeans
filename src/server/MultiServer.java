package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	static int PORT;
	public static void main(String[] args) throws IOException {
		int port = 8080;
		new MultiServer(port);
	}
	
	public MultiServer(int port) throws IOException {
		PORT = port;
		run();
	}
	
	void run() throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		System.out.println("Server Started");
		try {
			while(true) {
				// Si blocca finchè non si verifica una connessione:
				Socket socket = s.accept();
				try {
					new ServerOneClient(socket);
				} catch(IOException e) {
					// Se fallisce chiude il socket,
					// altrimenti il thread la chiuderà:
					socket.close();
				}
			}
		} finally {
			s.close();
		}
	}
}
