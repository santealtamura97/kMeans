package data;
import java.util.List;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>Description:La classe Data modella l'insieme di transazioni (o tuple)
 * @author sante
 */
public class Data {
		private List<Example> data = new ArrayList<Example>();
		private int numberOfExamples;
		private List<Attribute> attributeSet = new LinkedList<Attribute>(); 
		
		class Example implements Comparable<Example>{
			private List<Object> example=new ArrayList<Object>();//array di Object che rappresentano la singola transazione(o riga di una tabella)
			
			/**
			 * Aggiunge o in coda ad example
			 * @param o riferimento ad oggetto di classe Object
			 */
			private void add(Object o) {
				example.add(o);
			}
			
			/**
			 * 
			 * @param i riferimento collezionato in example
			 * @return i-esimo riferimento collezionato in example
			 */
			private Object get(int i) {
				return example.get(i);
			}
			
			/**
			 * restituisce 0, -1, 1  sulla base del risultato del confronto. 
			 * 0 se  i due esempi includono gli stessi valori. 
			 * Altrimenti il risultato del compareTo(...) invocato sulla prima coppia di valori in disaccordo. 
			 */
			public int compareTo(Example ex) {
				if(!this.toString().equals(ex.toString())) {
					String[] parts1=this.toString().split(" ");
					String[] parts2=ex.toString().split(" ");
					
					for(int i=0;i<parts1.length;i++) {
						if(!parts1[i].equals(parts2[i])) {
							return parts1[i].compareTo(parts2[i]);
						}		
					}
				}
				return 0;
			}
			/**
			 * Restituisce una stringa che rappresenta lo stato di example
			 */
			public String toString() {
				String str="";
				for(Object el : this.example) {
					str+=el.toString()+" ";
				}
				return str;
			}
		}
		
			/**
			 * Inizializza l'ArrayList data con transazioni di esempio  
			 * (in questo momento, 14 esempi e 5 attributi come riportato nella 
			 * tabella sottostante);  Inizializza attributeSet creando cinque 
			 * oggetti di tipo DiscreteAttribute, uno per ciascun attributo  
			 * (nella tabella sottostante).
			 * Inizializza numberOfExamples. 
			 */
		 	public Data(){
			//data
			
			TreeSet<Example> tempData = new TreeSet<Example>();
			
			Example ex0=new Example();
			Example ex1=new Example();
			Example ex2=new Example();
			Example ex3=new Example();
			Example ex4=new Example();
			Example ex5=new Example();
			Example ex6=new Example();
			Example ex7=new Example();
			Example ex8=new Example();
			Example ex9=new Example();
			Example ex10=new Example();
			Example ex11=new Example();
			Example ex12=new Example();
			Example ex13=new Example();
			
			ex0.add(new String ("Sunny"));
			ex1.add(new String ("Sunny"));
			ex2.add(new String ("Overcast"));
			ex3.add(new String ("Rain"));
			ex4.add(new String ("Rain"));
			ex5.add(new String ("Rain"));
			ex6.add(new String ("Overcast"));
			ex7.add(new String ("Sunny"));
			ex8.add(new String ("Sunny"));
			ex9.add(new String ("Rain"));
			ex10.add(new String ("Sunny"));
			ex11.add(new String ("Overcast"));
			ex12.add(new String ("Overcast"));
			ex13.add(new String ("Rain"));
			
			ex0.add(new String ("Hot"));
			ex1.add(new String ("Hot"));
			ex2.add(new String ("Hot"));
			ex3.add(new String ("Mild"));
			ex4.add(new String ("Cool"));
			ex5.add(new String ("Cool"));
			ex6.add(new String ("Cool"));
			ex7.add(new String ("Mild"));
			ex8.add(new String ("Cool"));
			ex9.add(new String ("Mild"));
			ex10.add(new String ("Mild"));
			ex11.add(new String ("Mild"));
			ex12.add(new String ("Hot"));
			ex13.add(new String ("Mild"));
			
			ex0.add(new String ("High"));
			ex1.add(new String ("High"));
			ex2.add(new String ("High"));
			ex3.add(new String ("High"));
			ex4.add(new String ("Normal"));
			ex5.add(new String ("Normal"));
			ex6.add(new String ("Normal"));
			ex7.add(new String ("High"));
			ex8.add(new String ("Normal"));
			ex9.add(new String ("Normal"));
			ex10.add(new String ("Normal"));
			ex11.add(new String ("High"));
			ex12.add(new String ("Normal"));
			ex13.add(new String ("High"));
			
			ex0.add(new String ("Weak"));
			ex1.add(new String ("Strong"));
			ex2.add(new String ("Weak"));
			ex3.add(new String ("Weak"));
			ex4.add(new String ("Weak"));
			ex5.add(new String ("Strong"));
			ex6.add(new String ("Strong"));
			ex7.add(new String ("Weak"));
			ex8.add(new String ("Weak"));
			ex9.add(new String ("Weak"));
			ex10.add(new String ("Strong"));
			ex11.add(new String ("Strong"));
			ex12.add(new String ("Weak"));
			ex13.add(new String ("Strong"));
			
			ex0.add(new String ("No"));
			ex1.add(new String ("No"));
			ex2.add(new String ("Yes"));
			ex3.add(new String ("Yes"));
			ex4.add(new String ("Yes"));
			ex5.add(new String ("No"));
			ex6.add(new String ("Yes"));
			ex7.add(new String ("No"));
			ex8.add(new String ("Yes"));
			ex9.add(new String ("Yes"));
			ex10.add(new String ("Yes"));
			ex11.add(new String ("Yes"));
			ex12.add(new String ("Yes"));
			ex13.add(new String ("No"));
			
			tempData.add(ex0);
			tempData.add(ex1);
			tempData.add(ex2);
			tempData.add(ex3);
			tempData.add(ex4);
			tempData.add(ex5);
			tempData.add(ex6);
			tempData.add(ex7);
			tempData.add(ex8);
			tempData.add(ex9);
			tempData.add(ex10);
			tempData.add(ex11);
			tempData.add(ex12);
			tempData.add(ex13);
			data = new ArrayList<Example> (tempData);
			
			
			numberOfExamples=tempData.size();
			
			String outLookValues[]=new String[3];//array di stringhe
			String temperatureValues[]=new String[3];
			String humidityValues[]=new String[2];
			String windValues[]=new String[2];
			String playTennisValues[]=new String[2];
			
			outLookValues[0]="Overcast";
			outLookValues[1]="Rain";
			outLookValues[2]="Sunny";
			
			temperatureValues[0]="Cool";
			temperatureValues[1]="Hot";
			temperatureValues[2]="Mild";
			
			humidityValues[0]="High";
			humidityValues[1]="Normal";
			
			windValues[0]="Strong";
			windValues[1]="Weak";
			
			playTennisValues[0]="No";
			playTennisValues[1]="Yes";
			
			
			attributeSet.add(new DiscreteAttribute("Outlook",0, outLookValues));
			attributeSet.add(new DiscreteAttribute("Temperature",1,temperatureValues));
			attributeSet.add(new DiscreteAttribute("Humidity",2,humidityValues));
			attributeSet.add(new DiscreteAttribute("Wind",3,windValues));
			attributeSet.add(new DiscreteAttribute("PlayTennis",4,playTennisValues));	
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
		
		/*
		Attribute[] getAttributeSchema(){
			return attributeSet;
		}*/
		
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
			for(int i=0;i<attributeSet.size();i++)
				tuple.add(new DiscreteItem((DiscreteAttribute)attributeSet.get(i),(String)getAttributeValue(Index,i)),i);
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
		 * Restituisce computePrototype(idList, (DiscreteAttribute)attribute) 
		 * @param idList insieme di indici di riga
		 * @param attribute attributo rispetto al quale calcolare il prototipo(centroide)
		 * @return valore centroide rispetto ad attribute
		 */
		Object computePrototype(Set<Integer> idList,Attribute attribute) {
			return computePrototype(idList,(DiscreteAttribute)attribute);
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

