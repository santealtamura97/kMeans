package mining;
import data.Data;

public class KMeansMiner {
	private ClusterSet C;
	/**
	 * Crea l'oggetto array riferito da C
	 * @param k numero di cluster da generare
	 */
	public KMeansMiner(int k){
		C=new ClusterSet(k);
	}
	
	/**
	 * Restituisce C
	 * @return C
	 */
	public ClusterSet getC() {
		return C;
	}
	
	/**
	 * Esegue l'algoritmo k means eseguendo i passi dello pseudo codice
	 * @param data riferimento ad oggetto Data
	 * @return numero di iterazioni eseguite
	 */
	public int kmeans(Data data) {
		int numberOfIterations=0; 
		//STEP 1   
		C.initializeCentroids(data); 
		boolean changedCluster=false; 
		do{ 
			numberOfIterations++; 
			//STEP 2 
			changedCluster=false; 
			for(int i=0;i<data.getNumberOfExamples();i++){
				Cluster nearestCluster = C.nearestCluster(data.getItemSet(i)); 
				Cluster oldCluster=C.currentCluster(i); 
				boolean currentChange=nearestCluster.addData(i); 
				if(currentChange) 
					changedCluster=true; 
				//rimuovo la tupla dal vecchio cluster
				if(currentChange && oldCluster!=null)
					oldCluster.removeTuple(i); //il nodo va rimosso dal suo vecchio cluster 
			}
			//STEP 3 
			C.updateCentroids(data); 
		}while(changedCluster);
		return numberOfIterations;
	}
}
