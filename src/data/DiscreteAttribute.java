package data;
import utility.ArraySet;

/**
 * <p>Description: estende la classe Attribute e rappresenta un attributo discreto(categorico)
 * @author sante
 *
 */
class DiscreteAttribute extends Attribute {
	private String values[]; // array di stringhe,una per ciascun valore del dominio discreto.
							// i valori del dominio sono memorizzati in values seguendo un ordine lessicografico
	
	/**
	 * Invoca il costruttore della classe madre e inizializza il membro values con il parametro values in input
	 * @param name nome dell'attributo 
	 * @param index identificativo numerico dell'attributo
	 * @param values array di oggetti di tipo String che rappresentano il dominio discreto dell'attributo
	 */
	DiscreteAttribute(String name,int index,String values[]){
		super(name, index);
		this.values=values;
	}
	/**
	 * @return dimensione di values
	 */
	int getNumberOfDistinctValues() {
		return values.length;
	}
	/**
	 * Restituisce values[i]
	 * @param i posizione di un valore in values
	 * @return valore discreto in posizione "i" di values
	 */
	String getValue(int i) {
		return values[i];
	}
	/**
	 * Determina il numero di volte che il valore v compare in corrispondenza dell'attributo
	 * corrente(indice di colonna) negli esempi memorizzati in data e indicizzate(per riga) da idList
	 * @param data riferimento ad un oggetto Data
	 * @param idList riferimento ad oggetto ArraySet che mantiene l'insieme degli indici di riga di alcune tuple in data
	 * @param v valore discreto 
	 * @return numero di occorrenze del valore discreto
	 */
	int frequency(Data data,ArraySet idList,String v) {
		int numberOccurrences=0;
		int r=0; 
		while(r<data.getNumberOfExamples())
		{
			if(idList.get(r))
			{
					if(data.getAttributeValue(r,getIndex()).equals(v))
						numberOccurrences++;		
			}
			r++;
		}
		return numberOccurrences;
	}
}
