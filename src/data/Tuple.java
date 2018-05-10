package data;
/**
 * <p>Description: rappresenta una tupla come una coppia attributo-valore
 * @author sante
 *
 */
public class Tuple {
	private Item[] tuple;
	
	/**
	 * Costruisce l'oggetto riferito da tuple
	 * @param size numero di item che costituirà a tupla
	 */
	Tuple(int size){
		tuple=new Item[size];
	}
	
	public int getLength() {
		return tuple.length;
	}
	
	public Item get(int i) {
		return tuple[i];
	}
	
	/**
	 * Memorizza c in tuple[i]
	 * @param c riferimento ad oggetto Item
	 * @param i indice di tuple
	 */
	void add(Item c,int i) {
		tuple[i]=c;
	}
	
	/**
	 * Determina la distanza tra la tupla riferita da obj e la tupla corrente(riferita da this).
	 * La distanza è ottenuta come la somma delle distanze tra gli item in posizioni eguali nelle
	 * due tuple.
	 * @param obj 
	 * @return
	 */
	 public double getDistance(Tuple obj) {
		double dist=0;
		for(int i=0;i<this.getLength();i++) {
			dist+=this.get(i).distance(obj.get(i));
		}
		return dist;
	}
	
	/**
	 * Restituisce la media delle distanze tra la tupla corrente e
	 * quelle ottenibili dalla righe della matrice in data 
	 * aventi l'indice clusteredData
	 * @param data 
	 * @param clusteredData indice di riga
	 * @return
	 */
	public double avgDistance(Data data, Integer clusteredData[]){ 
		double p=0.0,sumD=0.0; 
		for(int i=0;i<clusteredData.length;i++){ 
			//data.getItemSet(clusteredData[i]) mi restituisce la tupla di indice memorizzato in clusteredData[i]
			double d= getDistance(data.getItemSet(clusteredData[i])); 
			sumD+=d; 
		}
		p=sumD/clusteredData.length; 
		return p; 
	}
}
