package server;

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
import mining.KMeansMiner;

public class ServerOneClient extends Thread{
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	KMeansMiner kmeans;
	public ServerOneClient(Socket s) throws IOException {
		socket = s;
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		start();
	}
	public void run() {
		try {
			while(true) {
				if(in.readObject().equals(0)) {
					String table = "";
					table = (String)in.readObject();
					out.writeObject("OK");
					if(in.readObject().equals(1)) {
						int k =(int)in.readObject();
						out.writeObject("OK");
						System.out.println("1");
						Data data =new Data(table);
						System.out.println("2");
						KMeansMiner kmeans=new KMeansMiner(k);
						System.out.println("3");
						int numIter=kmeans.kmeans(data);
						System.out.println("4");
						out.writeInt(numIter);
						out.writeObject(kmeans.getC().toString(data));
					}
				}
			}
		}catch(IOException e){
			System.err.println("IO Exception");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EmptySetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabaseConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (OutOfRangeSampleSize e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
