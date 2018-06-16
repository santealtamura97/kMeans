package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



import server.ServerOneClient;

/**
 * <p>Description:Tale classe definisce un server che puo' servire piu Client
 * @author sante
 *
 */
public class MultiServer {
	static int PORT;
	
	/**
	 * Inizializza la porta ed invoca run() che fara' partire il thread
	 * @param port
	 * @throws IOException
	 */
	public MultiServer(int port) throws IOException {
		PORT = port;
		run();
	}
	
	/**
	 * Istanzia un oggetto istanza della classe ServerSocket che pone in attesa di richiesta di connessioni da parte del client. 
	 * Ad ogni nuova richiesta connessione si istanzia ServerOneClient
	 */
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
					socket.close();
				}
			}
		} finally {
			s.close();
		}
	}
	
	/**
	 * Istanzia un oggetto di tipo MultiServer
	 * @param args
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		new MultiServer(8080);
	}
}
