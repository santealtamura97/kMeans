import data.Data;
import mining.KMeansMiner;
import keyboardinput.Keyboard;
import data.OutOfRangeSampleSize;


public class MainTest {
	/**Nella guarded zone puo' essere generata un'eccezione di tipo OutOfRangeSampleSize
	 * che viene raccolta dal gestore dell'eccezione e stampa un messaggio di errore.
	 * @param args
	 */
	public static void main(String[] args) {
			char rip='y';
		do {
			Data data =new Data();
			System.out.println(data);
			System.out.println("Inserisci k:");
			int k;
			try {
				k=Keyboard.readInt();
				KMeansMiner kmeans=new KMeansMiner(k);
				int numIter=kmeans.kmeans(data);
				System.out.println("Numero di Iterazione:"+numIter);
				System.out.println(kmeans.getC().toString(data));
			}catch(OutOfRangeSampleSize e) {
				System.err.println(e.toString());
			}
				System.out.println("Vuoi ripetere l'esecuzione?(y/n)");
				rip=Keyboard.readChar();
		}while(rip=='y' || rip=='Y');
	}
}
