package mining;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import data.Data;
import data.OutOfRangeSampleSize;

/**
 * <p>Description: Include l'implementazione dell’algoritmo kmeans
 * @author sante
 *
 */
public class KMeansMiner implements Serializable{
	private ClusterSet C;
	/**
	 * Crea l'oggetto array riferito da C
	 * @param k numero di cluster da generare
	 */
	public KMeansMiner(int k){
		C=new ClusterSet(k);
	}
	
	/**
	 * Apre il file identificato da fileName, legge l'oggetto ivi memorizzato e lo assegna a C. 
	 * @param fileName percorso + nome file
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public KMeansMiner(String fileName) throws FileNotFoundException, IOException, ClassNotFoundException{
		ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName)); 
		KMeansMiner K=(KMeansMiner)in.readObject();
		C=K.getC();
		in.close();
	}
	
	/**
	 * Restituisce il clusterSet C
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
	public int kmeans(Data data) throws OutOfRangeSampleSize{
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
	
	/**
	 * Apre il file identificato da fileName e salva l'oggetto riferito da C in tale file. 
	 * @param fileName percorso + nome file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void save(String fileName) throws FileNotFoundException,IOException{
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName)); 
		out.writeObject(this);
		out.close();
	}
	
	public String toString() {
		return C.toString();
	}	
}
