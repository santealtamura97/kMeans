/**
 * <p>Description: rappresenta una tupla come una coppia attributo-valore
 * @author sante
 *
 */
class Tuple {
	private Item[] tuple;
	
	/**
	 * Costruisce l'oggetto riferito da tuple
	 * @param size numero di item che costituir� a tupla
	 */
	Tuple(int size){
		tuple=new Item[size];
	}
	
	int getLength() {
		return tuple.length;
	}
	
	Item get(int i) {
		return tuple[i];
	}
	
	void add(Item c,int i) {
		tuple[i]=c;
	}
	
	/**
	 * Determina la distanza tra la tupla riferita da obj e la tupla corrente(riferita da this).
	 * La distanza � ottenuta come la somma delle distanze tra gli item in posizioni eguali nelle
	 * due tuple.
	 * @param obj
	 * @return
	 */
	double getDistance(Tuple obj) {
		double dist=0;
		for(int i=0;i<getLength();i++) {
			dist=dist+this.get(i).distance(obj.get(i));
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
	double avgDistance(Data data, int clusteredData[]){ 
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
