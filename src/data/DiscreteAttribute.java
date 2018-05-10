package data;
import java.util.Iterator;
import java.util.TreeSet;

import utility.ArraySet;

/**
 * <p>Description: estende la classe Attribute e rappresenta un attributo discreto(categorico)
 * @author sante
 *
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
	DiscreteAttribute(String name,int index,String value[]){
		super(name, index);
		for(int i=0;i<value.length;i++) {
			this.values.add(value[i]);
		}
	}
	/**
	 * @return dimensione di values
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
	@Override
	public Iterator<String> iterator() {
		return values.iterator();
	}
}
