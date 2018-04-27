package data;
import utility.ArraySet;

/**
 * <p>Description:Modella un generico item(coppia (attributo,valore),per esempio Outlook="Sunny");
 * @author sante
 */
public abstract class Item {
	private Attribute attribute; //attributo coinvolto nell'item
	private Object value; //valore assegnato all'attributo
	
	/**
	 * Inizializza i valori dei membri attributi
	 * @param attribute riferimento ad oggetto di tipo Attribute
	 * @param value riferimento ad oggetto di tipo Object
	 */
	Item(Attribute attribute,Object value){
		this.attribute=attribute;
		this.value=value;
	}
	
	Attribute getAttribute() {
		return attribute;
	}
	
	Object getValue() {
		return value;
	}
	
	public String toString() {
		return getValue().toString();
	}
	/**
	 * L'implementazione sarà diversa per item discreto e item continuo
	 * @param a
	 */
	abstract double distance(Object a);
	
	/**
	 * Modifica il membro value ,assegnandogli il valore restituito da data.computePrototype(clusteredData,attribute)
	 * @param data riferimento ad un oggetto di classe Data
	 * @param clusteredData insieme di indici delle righe delle matrice in data che formano il cluster
	 */
	public void update(Data data,ArraySet clusteredData) {
		value=data.computePrototype(clusteredData, attribute);
	}
}
