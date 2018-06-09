package server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;

import data.Data;
import data.OutOfRangeSampleSize;
import database.DatabaseConnectionException;
import database.EmptySetException;
import database.NoValueException;
import keyboardinput.Keyboard;
import mining.KMeansMiner;

/**
 * <p>Description: tale classe rappresenta un gestore di un Client
 * @author sante
 *
 */
public class ServerOneClient extends Thread{
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	KMeansMiner kmeans;
	/**
	 * Costruttore di classe. 
	 * Inizializza gli attributi socket, in e out. Avvia il thread. 
	 * @param s
	 * @throws IOException
	 */
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
		
	}
	
	/**
	 * Riscrive il metodo run della superclasse Thread al fine di gestire le richieste del client. 
	 */
	public void run(){
		try {
			while(true) {
				Integer choise = (Integer)in.readObject();
				if(choise.equals(0)) {
					String table = "";
					table = (String)in.readObject();
					out.writeObject("OK");
						if(in.readObject().equals(1)) {
							int k =(int)in.readObject();
							try {
								Data data =new Data(table);
							
								KMeansMiner kmeans=new KMeansMiner(k);
						
								kmeans.kmeans(data);
							
								out.writeObject("OK");
								out.writeObject(data.toString()+kmeans.getC().toString(data));
								if(in.readObject().equals(2)) {
									String fileName = (String)in.readObject();
									kmeans.save(fileName);
									out.writeObject("OK");
								}
							}catch(OutOfRangeSampleSize e) {
								out.writeObject(e.getMessage());
							}catch(NoValueException e) {
								out.writeObject(e.getMessage());
							}catch(DatabaseConnectionException e) {
								out.writeObject(e.getMessage());
							}catch(EmptySetException e) {
								out.writeObject(e.getMessage());
							}catch(SQLException e) {
								out.writeObject(e.getMessage());
							}catch(NegativeArraySizeException e) {
								out.writeObject("Il numero k di cluster inserito da tastiera non puo' essere negativo.");
							}
					}
				}else if(choise.equals(3)) {
					try {
						String table  = (String)in.readObject();
						KMeansMiner kmeans=new KMeansMiner(table+".dmp");
						out.writeObject("OK");
						out.writeObject(kmeans.toString());
					}catch(FileNotFoundException e) {
						out.writeObject("Il file inserito e' inesistente");
					}catch(ClassNotFoundException e) {
						out.writeObject("Impossibile leggere il file");
					}
					
				}
			}
		}catch(IOException e) {
			System.out.println("Client chiuso.");
			System.out.println("In attesa del prossimo Client...");
		}catch(ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}finally {
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
