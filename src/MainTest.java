import data.Data;
import mining.KMeansMiner;
import keyboardinput.Keyboard;

public class MainTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			char rip='y';
		do {
			Data data =new Data();
			System.out.println(data);
			System.out.println("Inserisci k:");
			int k;
			k=Keyboard.readInt();
			KMeansMiner kmeans=new KMeansMiner(k);
			int numIter=kmeans.kmeans(data);
			System.out.println("Numero di Iterazione:"+numIter);
			System.out.println(kmeans.getC().toString(data));
			System.out.println("\nVuoi ripetere l'esecuzione?(y/n)");
			rip=Keyboard.readChar();
			
		}while(rip=='y');
	}
}
