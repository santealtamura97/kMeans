/**
 * Estende la classe Item e rappresenta una coppia Attributo,valore discreto.
 * Esempio Outlook="Sunny"
 * @author sante
 *
 */
class DiscreteItem extends Item {
	DiscreteItem(DiscreteAttribute attribute,String value){
		super(attribute,value);
	}
	double distance(Object a) {
		if(getValue().equals(a))
			return 0;
		else
			return 1;
	}
}
