package database;


import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import server.MultiServer;

public class DbAccess {
	//Driver manager  che gestisce dinamicamente tutti gli oggetti driver 
	//di cui hanno bisogno le interrogazioni a database di MySql
	String DRIVER_CLASS_NAME = "org.gjt.mm.mysql.Driver"; 
	
	final String DBMS = "jdbc:mysql"; 
	
	final String SERVER="localhost";//contiene l'identificativo del server su cui risiede la base di dati(per esempio localhost)
	
	final String DATABASE = "mapDB";//contine il nome della base di dati
	
	final int PORT=3306;// La porta su cui il DBMS MySQL accetta le connessioni 
	
	final String USER_ID = "MapUser";// contiene il nome dell’utente per l’accesso alla base di dati
	
	final String PASSWORD = "map";// contiene la password di autenticazione per l’utente identificato da  USER_ID 
	
	Connection conn;//gestisce una connessione 
	
	/**
	 * Impartisce al class loader l’ordine di caricare il driver mysql, 
	 * inizializza la connessione riferita da conn. Il metodo solleva e propaga
	 * una eccezione di tipo DatabaseConnectionException in caso di fallimento 
	 * nella connessione al database. 
	 * @throws DatabaseConnectionException
	 */
	private void initConnection() throws DatabaseConnectionException {
		try {
			ClassLoader classLoader = MultiServer.class.getClassLoader();
			Class aClass = classLoader.loadClass(DRIVER_CLASS_NAME);
			//Class.forName(DRIVER_CLASS_NAME);//impartisce al class loader l'ordine di caricare il driver mysql per la gestione di oggetti driver
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			//inizializzo la connessione riferita da conn
			conn=(Connection)DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE,USER_ID,PASSWORD);
		}catch(SQLException ex) {
			throw new DatabaseConnectionException();
		}
	}
	
	Connection getConnection() throws DatabaseConnectionException{
		this.initConnection();	
		return conn;
	}
	
	/**
	 * Chiude la connessione
	 * @throws SQLException
	 */
	void closeConnection() throws SQLException {
		conn.close();
	}
}
