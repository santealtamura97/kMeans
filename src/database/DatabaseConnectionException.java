package database;

/**
 * Eccezione in caso di fallimento nella connessione al database
 * @author sante
 *
 */
public class DatabaseConnectionException extends Exception{
	private static final long serialVersionUID = 1L;

	DatabaseConnectionException(){
		super("Connessione al database fallita.");
	}
}
