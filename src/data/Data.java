package data;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import database.DatabaseConnectionException;
import database.DbAccess;
import database.EmptySetException;
import database.Example;
import database.NoValueException;
import database.QUERY_TYPE;
import database.TableData;
import database.TableSchema;
import database.TableSchema.Column;

/**
 * <p>Description:La classe Data modella l'insieme di transazioni (o tuple)
 * @author sante
 */
public class Data {
		private List<Example> data = new ArrayList<Example>();
		private int numberOfExamples;
		private List<Attribute> attributeSet = new LinkedList<Attribute>(); 
		
			/**
			 * Inizializza l'ArrayList data con transazioni di table 
			 * Inizializza attributeSet creando oggetti di tipo DiscreteAttribute 
			 * oppure ContinuousAttribute uno per ciascun attributo  
			 * Inizializza numberOfExamples. 
			 * @param nome della tabella del databas relazionale
			 * @throws EmptySetException 
			 * @throws SQLException 
			 * @throws DatabaseConnectionException 
			 */
			@SuppressWarnings("unchecked")
			public Data(String table) throws SQLException, EmptySetException, DatabaseConnectionException , NoValueException{
				
				TableData tableData = new TableData(new DbAccess());
				data = tableData.getDistinctTransazioni(table);
				
				TableSchema tableSchema = new TableSchema(new DbAccess(),table);
				numberOfExamples=data.size();
				
				QUERY_TYPE min,max;
				min = QUERY_TYPE.MIN;
				max = QUERY_TYPE.MAX;
				
				for (int i=0;i<tableSchema.getNumberOfAttributes();i++) {
					if(!tableSchema.getColumn(i).isNumber()) {
						attributeSet.add(new DiscreteAttribute(tableSchema.getColumn(i).getColumnName(),i,(TreeSet)tableData.getDistinctColumnValues(table, tableSchema.getColumn(i))));
					}else {
						attributeSet.add(new ContinuousAttribute(tableSchema.getColumn(i).getColumnName(),i,(double)tableData.getAggregateColumnValue(table,tableSchema.getColumn(i),min),(double)tableData.getAggregateColumnValue(table,tableSchema.getColumn(i),max)));
					}
						
				}	
			}
		
		/**
		 * restituisce numberOfExamples
		 * @return cardinalita' dell'insieme di transazioni
		 */
		public int getNumberOfExamples(){
			return numberOfExamples;
		}
		
		/**
		 * resituisce la dimensione di attributeSet
		 * @return cardinalita' dell'insieme degli attributi
		 */
		public int getNumberOfAttributes(){
			return attributeSet.size();
		}
		
		/**
		 * Restituisce data[exampleIndex][attributeIndex]
		 * @param exampleIndex indice di riga in riferimento alla matrice memorizzata in data
		 * @param attributeIndex indice di colonna in riferimento alla matrice memorizzata in data
		 * @return  valore assunto in data dall'attributo in posizione attributeIndex,nella riga in posizione exampleIndex 
		 */
		public Object getAttributeValue(int exampleIndex, int attributeIndex){
			return data.get(exampleIndex).get(attributeIndex);
			
		}
		
		/**
		 * Restituisce lo schema degli attributi
		 * @return attributeSet
		 */
		List<Attribute> getAttributeSchema(){
			return attributeSet;
		}
		
		/**
		 * Crea una stringa in cui memorizza lo schema della tabella e le transazioni memorizzate in data,
		 * opportunamente enumerate
		 * @return stringa che modella lo stato dell'oggetto
		 */
		public String toString(){
			String schemeTable=new String();
			int i;
			for(i=0;i<getNumberOfAttributes();i++)
				 schemeTable=schemeTable+attributeSet.get(i).toString()+",";
			for(i=0;i<getNumberOfExamples();i++) {
				schemeTable=schemeTable+"\n"+(i)+":";
				for(int j=0;j<getNumberOfAttributes();j++) {
					schemeTable=schemeTable+getAttributeValue(i,j)+",";
				}
			}
			return schemeTable;
			
		}

		/**
		 * Crea e restituisce un oggetto di Tuple che modella come sequenza
		 * di coppie Attributo-valore la i-esima riga in data
		 * @param Index indice di riga
		 * @return
		 */
		public Tuple getItemSet(int Index) {
			Tuple tuple=new Tuple(attributeSet.size());
			for(int i=0;i<attributeSet.size();i++) {
				if(attributeSet.get(i) instanceof DiscreteAttribute) {
					tuple.add(new DiscreteItem((DiscreteAttribute)attributeSet.get(i),(String)getAttributeValue(Index,i)),i);
				}else if(attributeSet.get(i) instanceof ContinuousAttribute)
					tuple.add(new ContinuosItem((ContinuousAttribute)attributeSet.get(i),(Double)getAttributeValue(Index,i)),i);
			}
				
			return tuple;
		}
		
		/**
		 * Sceglie i centroidi casualmente
		 * @param k numero di cluster da generare
		 * @return array di k interi rappresentanti gli indici di riga in data per le tuple inizialmente scelte come centroidi
		 */
		 public int[] sampling(int k) throws OutOfRangeSampleSize{
			if(k==0 || k>numberOfExamples) {
				throw new OutOfRangeSampleSize();
			}else {
				int centroidIndexes[]=new int[k];
			//sceglie a caso k centroidi differenti in data
			Random rand=new Random();
			//inizializza il generatore di numeri casuali. Imposta il generatore su un punto di partenza casuale
			rand.setSeed(System.currentTimeMillis());
			for (int i=0;i<k;i++) {
				boolean found=false;
				int c;
				do {
					found=false;
					c=rand.nextInt(getNumberOfExamples());
					//verifica che centroid[c] non è uguale al centroide già memorizzato in CentroidIndexes
					for(int j=0;j<i;j++)
						if(compare(centroidIndexes[j],c)) {
							found=true;
							break;//esce dal ciclo se la condizione è soddisfatta
						}
				}
				while(found);
				centroidIndexes[i]=c;
			}
			return centroidIndexes;
			}	
		}
		/**
		 * Restituisce vero se le due righe di data contengono gli stessi valori,falso altrimenti
		 * @param i indice di riga
		 * @param j indice di riga
		 */
		private boolean compare(int i,int j) {
			for(int k=0;k<getNumberOfAttributes();k++) {
				if(getAttributeValue(i,k)!=getAttributeValue(j,k)) {
					return false;
				}	
			}
			return true;
		}
		
	
		/**
		 * Restituisce computePrototype(idList, (DiscreteAttribute)attribute) se attribute è un ' istanza di DiscreteAttribute
		 * altrimenti restituisce computePrototype(idList,(ContinuousAttribute)attribute) se attribute è un ' istanza di ContinuousAttribute
		 * @param idList insieme di indici di riga
		 * @param attribute attributo rispetto al quale calcolare il prototipo(centroide)
		 * @return valore centroide rispetto ad attribute
		 */
		Object computePrototype(Set<Integer> idList,Attribute attribute) {
			if(attribute instanceof ContinuousAttribute)
				return computePrototype(idList,(ContinuousAttribute)attribute);
			return computePrototype(idList, (DiscreteAttribute)attribute); 
		}
		
		/**
		 * Determina il valore prototipo come media dei valori osservati per attribute
		 * nelle transazioni di data aventi indice di riga in idList
		 * @param idList
		 * @param attribute
		 * @return 
		 */
		private double computePrototype(Set<Integer> idList,ContinuousAttribute attribute) {
			double prototypeValue=0.0;
			int numberOfValues=0;
			Iterator<Integer> iterator=idList.iterator();
			while(iterator.hasNext()) {
				prototypeValue=prototypeValue + (double)data.get(iterator.next()).get(attribute.getIndex());
				numberOfValues++;
			}
			return prototypeValue/numberOfValues;
		}
		
		/**
		 * Determina il valore che occorre più frequentemente per attribute nel sottoinsieme
		 * di dati individuato da idList
		 * @param idList
		 * @param attribute
		 * @return
		 */
		private String computePrototype(Set<Integer> idList,DiscreteAttribute attribute) {
			String moreFrequentValue="";
			String currentValue="";
			int maxFrequency=0;
			int currentFrequency=0;
			Iterator<String> iterator=attribute.iterator();
			Iterator<String> iterator1=attribute.iterator();
			while(iterator.hasNext() && iterator1.hasNext()) {
				currentFrequency=attribute.frequency(this, idList, iterator.next());
				currentValue=iterator1.next();
				if(currentFrequency>=maxFrequency) {
					maxFrequency=currentFrequency;
					moreFrequentValue=currentValue;
				}
			}
			return moreFrequentValue;
		}
}

