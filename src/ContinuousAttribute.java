/**
 * <p>Description:Estende la classe Attribute e modella un attributo continuo(numerico).
 * Tale classe include i metodi per la “normalizzazione” del dominio dell'attributo nell'intervallo [0,1] 
 * al fine da rendere confrontabili attributi aventi domini diversi 
 * @author sante
 *
 */
class ContinuousAttribute extends Attribute {
	private double max;
	private double min;// rappresentano gli estremi dell'intervallo di valori(dominio) che l'attributo può realmente assumere
	
	/**
	 * Invoca il costruttore della classe madre e inizializza i membri aggiunti per estensione
	 * @param name	
	 * @param index identificativo numerico
	 * @param min	valore minimo dell'attributo
	 * @param max	valore massimo dell'attributo
	 */
	ContinuousAttribute(String name, int  index, double min, double max) {
		super(name,index);
		this.max=max;
		this.min=min;
	}
	/**
	 * Calcola e restituisce il valore normalizzato del parametro in input.La normalizzazione
	 * ha come codiminio l'intervallo [0,1].
	 * @param v valore dell'attributo da normalizzare
	 * @return valore normalizzato
	 */
	double getScaledValue(double v) {
		return (v-min)/(max-min);
	}
}
