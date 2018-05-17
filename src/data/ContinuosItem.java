package data;

/**
 * <p>Description:Estende la classe Item e modella una coppia Attributo continuo , valore numerico
 * @author sante
 *
 */
class ContinuosItem extends Item{
	/**
	 * Richiama il costruttore della super classe
	 * @param attribute riferimento ad un oggetto attribute
	 * @param value riferimento ad un oggetto Double
	 */
	ContinuosItem(Attribute attribute,Double value){
		super(attribute,value);
	}
	
	/**
	 * Determina la distanza (in valore assoluto) 
	 * tra il valore scalato memorizzato nello item 
	 * corrente (this.getValue()) e quello scalato 
	 * associato al parametro a. 
	 */
	double distance(Object a) {
		ContinuousAttribute x = (ContinuousAttribute)this.getAttribute();
		//return Math.abs(x.getScaledValue((double)this.getValue())-x.getScaledValue(Double.parseDouble(a.toString())));
		return Math.abs(x.getScaledValue((double)this.getValue())-x.getScaledValue((double)((ContinuosItem)a).getValue()));
	}
}
