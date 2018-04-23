/**
 * <p>Description:la classe Attributo modella le entità attributo,
 * non può essere istanziata e ha lo scopo di essere richiamata 
 * come superclasse per una nuova classe.
 * @author sante
 */
abstract class Attribute {
	private String name; //nome simbolico dell'attributo
	private int index; //identificativo numerico dell'attributo
	
	/**
	 * Inizializza il valore dei membri name,index
	 * @param name nome simbolico dell'attributo
	 * @param index identificativo numerico dell'attributo(primo,secondo...attributo della tupla)
	 */
	Attribute(String name,int index){
		this.name=name;
		this.index=index;
	}
	/**
	 * Restituisce name
	 * @return nome dell'attributo
	 */
	private String getName() {
		return name;
	}
	
	/**
	 * Restituisce index 
	 * @return identificativo numerico dell'atributo
	 */
	int getIndex() {
		return index;
	}
	
	/**
	 * Overriding del toString che sta nella classe Object:
	 * sovrascrive metodo ereditato dalla superclasse e 
	 * restuisce la stringa rappresentante
	 * lo stato dell'oggetto.
	 */
	public String toString() {
		return getName();
	}
}
