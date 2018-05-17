package mining;
import data.Data;
import data.OutOfRangeSampleSize;
import data.Tuple;

/**
 * <p>Description: Rappresenta un insieme di cluster (determinati da  k-means) 
 * @author sante
 *
 */
public class ClusterSet {
	private Cluster C[];
	private int i=0; //posizione valida per la memorizzazione di un nuovo cluster in C
	
	/**
	 * Creo l'oggetto array riferito da C
	 * @param k numero di cluster da generare(k means)
	 */
	ClusterSet(int k) {
		C=new Cluster[k];
	}
	
	private void add(Cluster c) {
		C[i]=c;
		i++;
	}
	
	private Cluster get(int i) {
		return C[i];
	}
	
	/**
	 * Sceglie i centroidi,crea un cluster per ogni centroide e lo memorizza in C
	 * @param data riferimento ad un oggetto Data
	 */
	void initializeCentroids(Data data) throws OutOfRangeSampleSize{
		//array che memorizza gli indici di riga dei centroidi
		int centroidIndexes[]=data.sampling(C.length);
		//per ogni indice prende la tupla che corrisponde e quell'indice(centroide) e crea un cluster e lo memorizza in C
		for(int i=0;i<centroidIndexes.length;i++) {
			Tuple centroidi=data.getItemSet(centroidIndexes[i]);
			add(new Cluster(centroidi));
		}
	}
	
	/**
	 * Calcola la distanza tra la tupla riferita da tuple ed il centroide di ciascun cluster in C
	 * e restituisce il cluster più vicino
	 * @param tuple riferimento ad un oggetto Tuple
	 * @return cluster più "vicino" alla tupla passata
	 */
		Cluster nearestCluster(Tuple tuple) {
		int j=0;
		double minDistance=get(j).getCentroid().getDistance(tuple);
		Cluster nearestCluster=get(j);
		j++;
		while(j<i) {
			if(get(j).getCentroid().getDistance(tuple)<=minDistance) {
				minDistance=get(j).getCentroid().getDistance(tuple);
				nearestCluster=get(j);
			}
			j++;
		}
		return nearestCluster;
	}
	
	/**
	 * Calcola il nuovo centroide per ciascun cluster in C
	 * @param data riferimento ad un oggetto Data
	 */
	void updateCentroids(Data data) {
		for(int j=0;j<i;j++) 
			C[j].computeCentroid(data);
	}
	
	/**
	 * Identifica e restituisce il cluster a cui la tupla rappresentate l'esempio 
	 * identificato da id appartiene. Se la tupla non è inclusa in  nessun cluster restituisce null.
	 * @param id indice di una riga della matrice in Data
	 * @return
	 */
	Cluster currentCluster(int id) {
		for(int j=0;j<i;j++) {
			if(C[j].contain(id))
				return C[j];
		}
		return null;
	}
	
	/**
	 * Restituisce una stringa fatta da ciascun centroide dell'insieme dei cluster
	 */
	public String toString() {
		String str="";
		for (int j=0;j<i;j++) {
			str=str+" "+C[j].toString();
		}
		return str;
	}
	
	public String toString(Data data) {
		String str="";
		for(int i=0;i<C.length;i++) {
			if(C[i]!=null) {
				str+=i+":"+C[i].toString(data) +"\n";
			}
		}
	return str;
	}
}
