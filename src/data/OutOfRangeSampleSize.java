package data;

public class OutOfRangeSampleSize extends Exception {
	/**
	 * Default serial version id
	 */
	private static final long serialVersionUID = 1L;

	OutOfRangeSampleSize(){
		super("\nIl numero k di cluster inserito da\n"
				+ "tastiera è fuori dal range\n"
				+ "del numero di centroidi generabili\n"
				+ "dall'insieme di transazioni.\n\n");
	}
}
