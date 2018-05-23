package database;

/**
 * Eccezione per modellare la restituzione di un resultset vuoto.
 * @author sante
 *
 */
public class EmptySetException extends Exception{

	private static final long serialVersionUID = 1L;

	EmptySetException(){
		super("resultset vuoto");
	}
}
