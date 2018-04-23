import java.util.Arrays;
public class ArraySet {
	private boolean set[];
	private int size=0;
	ArraySet (){
		set=new boolean[50];
		for(int i=0;i<set.length;i++)
			set[i]=false;
	}
	
	//return true if add is changing the arraySet
	boolean add(int i){
		if(i>=set.length)
		{
			//enlarge the set
			boolean temp[]=new boolean[set.length*2];
			//Assegna il valore booleano specificato a ciascun elemento dell'array di boolean specificato.
			Arrays.fill(temp,false);
			//copia set partendo da 0 in temp partendo da 0 utilizzando la lunghezza di set: set.length
			System.arraycopy(set, 0, temp, 0, set.length);
			//assegna temp a set
			set=temp;
		}	
		boolean added=set[i];
		set[i]=true;
		if(i>=size)
			size=i+1;
		//se added � falso vuol dire che ho modificato l'array e quindi ritorno true.
		return !added;
	}
	
	boolean delete(int i){
		if(i<size){
			boolean deleted=set[i];
			set[i]=false;
			if(i==size-1){
				//update size
				int j;
				for(j=size-1;j>=0 && !set[j];j--);
				
				size=j+1;
			}
			
			return deleted;
		}
		return false;
	}
	
	boolean get(int i){
		return set[i];
	}
	
	int[] toArray(){
		int a[]=new int[0];
		for(int i=0;i<size;i++){
			if(get(i)){
				int temp[]=new int[a.length+1];
				System.arraycopy(a, 0, temp, 0, a.length);
				a=temp;
				a[a.length-1]=i;
			}
		}
		return a;
	}
}
