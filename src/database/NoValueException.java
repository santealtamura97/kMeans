package database;

public class NoValueException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	NoValueException(){
		super("Valore non trovato all'interno del resultset");
	}
}
