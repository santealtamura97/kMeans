package database;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

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
	
	private void initConnection() throws DatabaseConnectionException {
		try {
			Class.forName(DRIVER_CLASS_NAME);//impartisce al class loader l'ordine di caricare il driver mysql per la gestione di oggetti driver
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			//inizializzo la connessione riferita da conn
			conn=(Connection)DriverManager.getConnection(DBMS + "://" + SERVER + ":" + PORT + "/" + DATABASE,USER_ID,PASSWORD);
		}catch(SQLException ex) {
			System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
		}
	}
	
	Connection getConnection(){
		try {
			this.initConnection();	
		}catch(DatabaseConnectionException e) {
			System.out.println(e.toString());
		}

		return conn;
	}
	
	void closeConnection() throws SQLException {
		conn.close();
	}
}
