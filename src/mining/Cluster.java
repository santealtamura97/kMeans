package mining;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import data.Data;
import data.Tuple;

/**
 * <p>Description:Rappresenta un insieme di righe di data con il relativo centroide.
 * @author sante
 *
 */
public class Cluster implements Serializable{
	private Tuple centroid;

	private Set<Integer> clusteredData=new HashSet<Integer>(); //contiene le righe di data facenti parte del Cluster
	
	/*Cluster(){
		
	}*/

	Cluster(Tuple centroid){
		this.centroid=centroid;
	}
		
	Tuple getCentroid(){
		return centroid;
	}
	
	/**
	 * Calcola il nuovo centroide del Cluster
	 * @param data riferimento ad oggetto Data
	 */
	void computeCentroid(Data data){
		for(int i=0;i<centroid.getLength();i++){
			centroid.get(i).update(data,clusteredData);
		}	
	}
	
	/**
	 * Return true se la tupla cambia cluster
	 * @param id indice di riga 
	 * @return
	 */
	boolean addData(int id){
		return clusteredData.add(id);	
	}
	
	/**
	 * Verifica se una transazione è clusterizzata nell'array corrente
	 * @param id indice di riga
	 * @return
	 */
	boolean contain(int id){
		return clusteredData.contains(id);
	}
	

	/**
	 * Rimuove la tupla che ha cambiato il cluster
	 * @param id indice di riga della tupla
	 */
	void removeTuple(int id){
		clusteredData.remove(id);
	}
	
	public String toString(){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=" "+centroid.get(i);
		str+=")\n";
		return str;
	}
	
	public String toString(Data data){
		String str="Centroid=(";
		for(int i=0;i<centroid.getLength();i++)
			str+=centroid.get(i)+ " ";
		str+=")\nExamples:\n";
		Integer[] array=new Integer[clusteredData.size()];
		clusteredData.toArray(array);
		for(int i=0;i<array.length;i++){
			str+="[";
			for(int j=0;j<data.getNumberOfAttributes();j++)
				str+=data.getAttributeValue(array[i], j)+" ";
			str+="] dist="+getCentroid().getDistance(data.getItemSet(array[i]))+"\n";
			
		}
		str+="\nAvgDistance="+getCentroid().avgDistance(data,array);
		return str;
		
	}
}
