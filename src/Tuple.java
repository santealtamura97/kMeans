/**
 * <p>Description: rappresenta una tupla coma una coppia attributo-valore
 * @author sante
 *
 */
class Tuple {
	private Item[] tuple;
	
	/**
	 * Costruisce l'oggetto riferito da tuple
	 * @param size numero di item che costituirà a tupla
	 */
	Tuple(int size){
		tuple=new Item[size];
	}
	
	private int getLength() {
		return tuple.length;
	}
	
	private Item get(int i) {
		return tuple[i];
	}
	
	void add(Item c,int i) {
		tuple[i]=c;
	}
	
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
			double d= getDistance(data.getItemSet(clusteredData[i])); 
			sumD+=d; 
		}
		p=sumD/clusteredData.length; 
		return p; 
	}
}
