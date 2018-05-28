package database;

/**
 *Modella l’assenza di un valore all’interno di un resultset
 * @author sante
 */
public class NoValueException extends Exception{
	private static final long serialVersionUID = 1L;

	NoValueException(){
		super("Valore non trovato all'interno del resultset");
	}
}
