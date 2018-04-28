package data;
import java.util.Random;

import utility.ArraySet;
public class Data {
		private Object data [][];
		private int numberOfExamples;
		private int distinctTuple;
		private Attribute attributeSet[];
		
		
		 	public Data(){
			//data
			
			data = new Object [14][5];
			data[0][0]="Sunny";data[1][0]="Sunny";data[2][0]="OverCast";
			data[3][0]="Rain";data[4][0]="Rain";data[5][0]="Rain";
			data[6][0]="Overcast";data[7][0]="Sunny";data[8][0]="Sunny";
			data[9][0]="Rain";data[10][0]="Sunny";data[11][0]="Overcast";
			data[12][0]="Overcast";data[13][0]="Rain";
			
			data[0][1]="Hot";data[5][1]="Cool";data[10][1]="Mild";
			data[1][1]="Hot";data[6][1]="Cool";data[11][1]="Mild";
			data[2][1]="Hot";data[7][1]="Mild";data[12][1]="Hot";
			data[3][1]="Mild";data[8][1]="Cool";data[13][1]="Mild";
			data[4][1]="Cool";data[9][1]="Mild";
			
			data[0][2]="High";data[5][2]="Normal";data[10][2]="Normal";
			data[1][2]="High";data[6][2]="Normal";data[11][2]="High";
			data[2][2]="High";data[7][2]="High";data[12][2]="Normal";
			data[3][2]="High";data[8][2]="Normal";data[13][2]="High";
			data[4][2]="Normal";data[9][2]="Normal";
			
			data[0][3]="Weak";data[5][3]="Strong";data[10][3]="Strong";
			data[1][3]="Strong";data[6][3]="Strong";data[11][3]="Strong";
			data[2][3]="Weak";data[7][3]="Weak";data[12][3]="Weak";
			data[3][3]="Weak";data[8][3]="Weak";data[13][3]="Strong";
			data[4][3]="Weak";data[9][3]="Weak";
			
			data[0][4]="No";data[5][4]="No";data[10][4]="Yes";
			data[1][4]="No";data[6][4]="Yes";data[11][4]="Yes";
			data[2][4]="Yes";data[7][4]="No";data[12][4]="Yes";
			data[3][4]="Yes";data[8][4]="Yes";data[13][4]="No";
			data[4][4]="Yes";data[9][4]="Yes";
			
			// TO DO : memorizzare le transazioni secondo lo schema della tabella nelle specifiche

			// numberOfExamples
			
			 numberOfExamples=14;		 
			 
			
			//explanatory Set
			
			attributeSet = new Attribute[5];

			// TO DO : avvalorare ciascune elemento di attributeSet con un oggetto della classe DiscreteAttribute che modella il corrispondente attributo (e.g. outlook, temperature,etc)
			// nel seguito si fornisce l'esempio per outlook
			
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
			
			
			attributeSet[0] = new DiscreteAttribute("Outlook",0, outLookValues);
			attributeSet[1] = new DiscreteAttribute("Temperature",1,temperatureValues);
			attributeSet[2] = new DiscreteAttribute("Humidity",2,humidityValues);
			attributeSet[3] = new DiscreteAttribute("Wind",3,windValues);
			attributeSet[4] = new DiscreteAttribute("PlayTennis",4,playTennisValues);
			
			distinctTuple=countDistinctTuples(); 
			
		}
		
		public int getNumberOfExamples(){
			return numberOfExamples;
		}
		
		public int getNumberOfAttributes(){
			return attributeSet.length;
		}
		
		public Object getAttributeValue(int exampleIndex, int attributeIndex){
			return data[exampleIndex][attributeIndex];
		}
		
		Attribute[] getAttribute(){
			return attributeSet;
		}
		
		
		public String toString(){
			String schemeTable=new String();
			int i;
			for(i=0;i<getNumberOfAttributes();i++)
				 schemeTable=schemeTable+attributeSet[i].toString()+",";
			for(i=0;i<getNumberOfExamples();i++) {
				schemeTable=schemeTable+"\n"+(i)+":";
				for(int j=0;j<getNumberOfAttributes();j++) {
					schemeTable=schemeTable+getAttributeValue(i,j)+",";
				}
			}
			return schemeTable;
			
		}

		public static void main(String args[]){
			Data trainingSet=new Data();
			System.out.println(trainingSet);
		}
		
		/**
		 * Crea e restituisce un oggetto di Tuple che modella come sequenza
		 * di coppie Attributo-valore la i-esima riga in data
		 * @param Index indice di riga
		 * @return
		 */
		public Tuple getItemSet(int Index) {
			Tuple tuple=new Tuple(attributeSet.length);
			for(int i=0;i<attributeSet.length;i++)
				tuple.add(new DiscreteItem((DiscreteAttribute)attributeSet[i],(String)data[Index][i]),i);
			return tuple;
		}
		
		/**
		 * Sceglie i centroidi casualmente
		 * @param k numero di cluster da generare
		 * @return array di k interi rappresentanti gli indici di riga in data per le tuple inizialmente scelte come centroidi
		 */
		 public int[] sampling(int k) throws OutOfRangeSampleSize{
			if(k<=0 || k>distinctTuple) {
				throw new OutOfRangeSampleSize();
			}
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
		 * Conta il numero di transazioni distinte memorizzate in data
		 * @return Numero di  transazioni distinte memorizzate nella matrice data
		 */
		private int countDistinctTuples() {
			int distinctTuples=0;
			int indexOfDistinctTuples[]=new int[getNumberOfExamples()];
			for (int i=0;i<getNumberOfExamples();i++) {
				if(isDistinct(i,indexOfDistinctTuples)) {
					indexOfDistinctTuples[distinctTuples]=i;
					distinctTuples++;
				}	
			}
			return distinctTuples;
		}
		
		/**
		 * Ritorna vero se l'indice i non è contenuto in indexOfDistinctTuples
		 * @param i indice di riga di una tupla
		 * @param indexOfDistinctTuples array in cui sono memorizzati gli indici delle righe distinte
		 * @return 
		 */
		private boolean isDistinct(int i,int indexOfDistinctTuples[]) {
			for(int j=0;j<indexOfDistinctTuples.length;j++) {
				if(compare(i,indexOfDistinctTuples[j])) {
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
		Object computePrototype(ArraySet idList,Attribute attribute) {
			return computePrototype(idList,(DiscreteAttribute)attribute);
		}
		/**
		 * Determina il valore che occorre più frequentemente per attribute nel sottoinsieme
		 * di dati individuato da idList
		 * @param idList
		 * @param attribute
		 * @return
		 */
		private String computePrototype(ArraySet idList,DiscreteAttribute attribute) {
			String moreFrequentValue=null;
			int maxFrequency=0;
			for(int i=0;i<attribute.getNumberOfDistinctValues();i++) {
				if(attribute.frequency(this, idList, attribute.getValue(i))>=maxFrequency) {
					maxFrequency=attribute.frequency(this, idList, attribute.getValue(i));
					moreFrequentValue=attribute.getValue(i);
				}
			}
			return moreFrequentValue;
		}
}

