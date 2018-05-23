package data;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;


/**
 * <p>Description: estende la classe Attribute e rappresenta un attributo discreto(categorico)
 * @author sante
 */
class DiscreteAttribute extends Attribute implements Iterable<String>{
	private TreeSet<String> values= new TreeSet<String>(); // Set di stringhe ordinato attraverso un albero.
							// i valori del dominio sono memorizzati in values seguendo un ordine lessicografico
	
	/**
	 * Invoca il costruttore della classe madre e inizializza il membro values con il parametro values in input
	 * @param name nome dell'attributo 
	 * @param index identificativo numerico dell'attributo
	 * @param values array di oggetti di tipo String che rappresentano il dominio discreto dell'attributo
	 */
	DiscreteAttribute(String name,int index,TreeSet<String> value){
		super(name, index);
		for(String s : value)
			this.values.add(s);	
	}
	/**
	 * Restituisce la dimensione di values
	 * @return numero di valori discreti nel dominio dell'attributo
	 */
	int getNumberOfDistinctValues() {
		return values.size();
	}

	/**
	 * Determina il numero di volte che il valore v compare in corrispondenza dell'attributo
	 * corrente(indice di colonna) negli esempi memorizzati in data e indicizzate(per riga) da idList
	 * @param data riferimento ad un oggetto Data
	 * @param idList riferimento ad oggetto ArraySet che mantiene l'insieme degli indici di riga di alcune tuple in data
	 * @param v valore discreto 
	 * @return numero di occorrenze del valore discreto
	 */
	int frequency(Data data,Set<Integer> idList,String v) {
		int numberOccurrences=0;
		Iterator<Integer> it = idList.iterator();
		while(it.hasNext()) {
			if(data.getAttributeValue(it.next(),getIndex()).equals(v)){
				numberOccurrences++;
			}
		}
		return numberOccurrences;
	}
	
	//L'interfaccia Iterator<String> è implementata da TreeSet<String> che implementa i
	//metodi hasnext ,next e remove(se è supportato).
	@Override
	public Iterator<String> iterator() {
		return values.iterator();
	}
}
